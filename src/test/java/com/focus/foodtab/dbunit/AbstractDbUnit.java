package com.focus.foodtab.dbunit;

import java.io.File;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.sql.DataSource;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.FileSystemResourceAccessor;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.DatabaseUnitException;
import org.dbunit.IDatabaseTester;
import org.dbunit.assertion.DbUnitAssert;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:app-contextForTest.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, AbstractDbUnit.DbunitTestExecutionListener.class })
public abstract class AbstractDbUnit
{
    private IDatabaseTester databaseTester;
    private String expectedDatasetFileName;
    private String setUpDatasetFileName;
    private boolean setReplacementsForSetupData;
    private boolean setReplacementsForExpectedData;
    private Map<String, Object> replacements = new HashMap<String, Object>();
    private static final String DATA_SET_BASE_PATH = "src/test/resources/testDatasets/";
    private static final String databaseSchema = "src/main/resources/liquibase/master.xml";
    private IDatabaseConnection iDatabaseConnection;
    private boolean truncateDatabase = true;
    private final FlatXmlDataSetBuilder dataSetBuilder;

    @Autowired
    @Qualifier("testDataSource")
    private DataSource dataSource;

    public AbstractDbUnit()
    {
        super();
        dataSetBuilder = new FlatXmlDataSetBuilder();
        dataSetBuilder.setColumnSensing(true);
    }

    protected void setUpDataWithoutAnnotations(String dataSetFileName) throws Exception
    {
        FlatXmlDataSetBuilder dataSetBuilder = new FlatXmlDataSetBuilder();
        dataSetBuilder.setColumnSensing(true);
        IDataSet dataSet = dataSetBuilder.build(new File(DATA_SET_BASE_PATH + dataSetFileName));
        databaseTester.setDataSet(replaceDynamicData(dataSet, replacements));
        try
        {
            databaseTester.onSetup();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    protected void setupDatasetUsingAnnotations() throws Exception
    {
        if (setUpDatasetFileName != null)
        {
            FlatXmlDataSetBuilder dataSetBuilder = new FlatXmlDataSetBuilder();
            dataSetBuilder.setColumnSensing(true);
            IDataSet setUpDataSet = dataSetBuilder.build(new File(DATA_SET_BASE_PATH + setUpDatasetFileName));
            if (setReplacementsForSetupData == Boolean.TRUE && replacements != null)
            {
                ReplacementDataSet updatedSetUpDataSet = replaceDynamicData(setUpDataSet, replacements);
                databaseTester.setDataSet(updatedSetUpDataSet);
            }
            else
            {
                databaseTester.setDataSet(setUpDataSet);
            }

            try
            {
                databaseTester.onSetup();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return;
        }
    }

    private ReplacementDataSet replaceDynamicData(IDataSet dataset, Map<String, Object> replacementData)
    {

        ReplacementDataSet updatedSetUpDataSet = new ReplacementDataSet(dataset);
        for (Iterator<String> iterator = replacementData.keySet().iterator(); iterator.hasNext();)
        {
            String key = iterator.next();
            Object value = replacementData.get(key);
            updatedSetUpDataSet.addReplacementObject(key, value);
        }
        return updatedSetUpDataSet;
    }

    protected void setReplaceMentData(Map<String, Object> keyValue)
    {
        replacements.putAll(keyValue);
    }

    protected void disableTruncateAfterDatabaseIsSetInitially()
    {
        truncateDatabase = false;
    }

    protected void assertDatabaseEquals(String dataSetPath) throws Exception
    {
        IDataSet inputDataSet = dataSetBuilder.build(new File(DATA_SET_BASE_PATH + dataSetPath));
        ReplacementDataSet expectedDataSet = replaceDynamicData(inputDataSet, replacements);
        iDatabaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
        IDataSet actualDataSet = iDatabaseConnection.createDataSet(expectedDataSet.getTableNames());
        DataSetAssertionBuilder.getBuilder(expectedDataSet, actualDataSet).assertEquals();
    }

    public static class DbunitTestExecutionListener extends AbstractTestExecutionListener
    {
        private boolean isDatabaseSetUp = false;
        private Connection connection;
        private IDatabaseTester databaseTesterInListener;
        private IDatabaseConnection iDatabaseConnection;

        /** method to execute after the test class */
        @Override
        public void afterTestClass(TestContext testContext) throws Exception
        {
            closeConnection();
        }

        /** method to execute before the test method initialization */
        @Override
        public void beforeTestMethod(TestContext testContext) throws Exception
        {
            AbstractDbUnit testInstance = (AbstractDbUnit) testContext.getTestInstance();
            if (!isDatabaseSetUp)
            {
                establishConnection(testInstance);
                setupDatabase(testInstance);
                initialiseDatabaseTester(testInstance);
            }
            testInstance.databaseTester = databaseTesterInListener;
            testInstance.iDatabaseConnection = iDatabaseConnection;
            if (testInstance.truncateDatabase)
            {
                truncateSchema(testInstance);
            }
            Method method = testContext.getTestMethod();

            SetUpDataSet setUpDataSetannotation = method.getAnnotation(SetUpDataSet.class);
            if (setUpDataSetannotation != null)
            {
                testInstance.setUpDatasetFileName = setUpDataSetannotation.xmlPath();
                testInstance.setReplacementsForSetupData = setUpDataSetannotation.setReplacements();
            }
            ExpectedDataSet expectedDataSet = method.getAnnotation(ExpectedDataSet.class);
            if (expectedDataSet != null)
            {
                testInstance.expectedDatasetFileName = expectedDataSet.xmlPath();
                testInstance.setReplacementsForExpectedData = expectedDataSet.setReplacements();
            }
            testInstance.setupDatasetUsingAnnotations();

        }

        private void establishConnection(AbstractDbUnit testInstance) throws Exception
        {
            connection = testInstance.dataSource.getConnection();
        }

        private void setupDatabase(AbstractDbUnit testInstance) throws Exception
        {
            String schemaPath = AbstractDbUnit.databaseSchema;
            try
            {
                Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
                Liquibase liquibase = new Liquibase(schemaPath, new FileSystemResourceAccessor(), database);
                liquibase.dropAll();
                liquibase.clearCheckSums();
                Contexts noContextMode = null;
                liquibase.update(noContextMode);
            }
            catch (Exception exception)
            {
                connection.close();
            }
            isDatabaseSetUp = true;

        }

        private void initialiseDatabaseTester(AbstractDbUnit testInstance) throws Exception
        {
            if (databaseTesterInListener == null)
            {
                databaseTesterInListener = new DataSourceDatabaseTester(testInstance.dataSource);
                databaseTesterInListener.setOperationListener(new DBUnitListener());
                databaseTesterInListener.setSetUpOperation(DatabaseOperation.REFRESH);
                databaseTesterInListener.setTearDownOperation(DatabaseOperation.NONE);
                iDatabaseConnection = databaseTesterInListener.getConnection();
            }
        }

        @Override
        public void afterTestMethod(TestContext testContext) throws Exception
        {
            AbstractDbUnit testInstance = (AbstractDbUnit) testContext.getTestInstance();
            assertAfterTransaction(testInstance);
        }

        public void assertAfterTransaction(AbstractDbUnit testInstance) throws Exception
        {
            if (databaseTesterInListener == null)
            {
                Assert.fail();
            }
            if (testInstance.expectedDatasetFileName != null)
            {
                FlatXmlDataSetBuilder dataSetBuilder = new FlatXmlDataSetBuilder();
                dataSetBuilder.setColumnSensing(true);
                IDataSet expectedDataSet = dataSetBuilder.build(new File(DATA_SET_BASE_PATH + testInstance.expectedDatasetFileName));
                iDatabaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
                IDataSet actualDataSet = iDatabaseConnection.createDataSet(expectedDataSet.getTableNames());

                ReplacementDataSet updatedExpectedDataSet = new ReplacementDataSet(expectedDataSet);
                if (testInstance.setReplacementsForExpectedData == Boolean.TRUE && testInstance.replacements != null)
                {
                    updatedExpectedDataSet = testInstance.replaceDynamicData(updatedExpectedDataSet, testInstance.replacements);
                }
                DataSetAssertionBuilder.getBuilder(updatedExpectedDataSet, actualDataSet).assertEquals();
            }
        }

        public void truncateSchema(AbstractDbUnit testInstance) throws SQLException
        {
            try
            {
                connection.createStatement().execute("TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK");
            }
            catch (Exception exception)
            {
                connection.close();
            }
        }

        private void closeConnection() throws Exception
        {
            connection.close();
            iDatabaseConnection.close();
        }

    }

    public final static class DataSetAssertionBuilder
    {
        private Logger log = LoggerFactory.getLogger(this.getClass());

        protected IDataSet expectedDataSet;
        protected IDataSet actualDataSet;

        public static DataSetAssertionBuilder getBuilder(IDataSet expectedDataSet, IDataSet actualDataSet)
        {
            return new DataSetAssertionBuilder(expectedDataSet, actualDataSet);
        }

        private DataSetAssertionBuilder(IDataSet expectedDataSet, IDataSet actualDataSet)
        {
            this.expectedDataSet = expectedDataSet;
            this.actualDataSet = actualDataSet;
        }

        private void assertEquals() throws DatabaseUnitException
        {
            for (String tableName : expectedDataSet.getTableNames())
            {
                doAssert(tableName);
            }
        }

        private void doAssert(String tableName) throws DatabaseUnitException
        {
            ITable expectedTable = expectedDataSet.getTable(tableName);
            Column[] filteredColumns = expectedTable.getTableMetaData().getColumns();

            ITable expectedTableFiltered = DefaultColumnFilter.includedColumnsTable(expectedTable, filteredColumns);
            ITable sortedExpectedTable = new SortedTable(expectedTableFiltered, filteredColumns);

            ITable actualTable = actualDataSet.getTable(tableName);
            ITable actualTableFiltered = DefaultColumnFilter.includedColumnsTable(actualTable, filteredColumns);
            ITable sortedActualTable = new SortedTable(actualTableFiltered, filteredColumns);
            new DbUnitAssert().assertEquals(sortedExpectedTable, sortedActualTable);
            log.debug("Actual and Expected datasets are equal for table : '" + tableName + "'");
        }
    }

}