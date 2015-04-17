package com.focus.foodtab.rest.swagger;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.paths.RelativeSwaggerPathProvider;
import com.mangofactory.swagger.paths.SwaggerPathProvider;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

@Configuration
@EnableSwagger
public class SwaggerConfig
{
    private static final String ApiVersion = "documentation.services.version=1.0";

    @Autowired
    private ServletContext servletContext;

    private SpringSwaggerConfig springSwaggerConfig;

    /**
     * Required to autowire SpringSwaggerConfig
     */
    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig)
    {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    /**
     * Every SwaggerSpringMvcPlugin bean is picked up by the swagger-mvc framework - allowing for multiple swagger groups i.e. same code base multiple swagger resource listings.
     */
    @Bean
    public SwaggerSpringMvcPlugin customImplementation()
    {
        SwaggerPathProvider pathprovider = new RelativeSwaggerPathProvider(servletContext);
        // @formatter:off
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
            .apiInfo(new ApiInfo("FoodTab", "rest services", null, null, null, null))
            .apiVersion(ApiVersion)
            .pathProvider(pathprovider)
            .includePatterns(".*/.*")
            .build();
        // @formatter:on
    }

}
