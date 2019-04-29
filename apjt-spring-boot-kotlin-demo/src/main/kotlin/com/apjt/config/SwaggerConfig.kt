package com.apjt.config;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import com.google.common.base.Predicate
import com.google.common.base.Predicates

import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
 * Spring context configuration to wire up swagger.
 */
@Configuration
@EnableSwagger2
class SwaggerConfig @Autowired constructor(
    private val buildProperties : BuildProperties
) {
	@Value("\${swagger.title}")
	lateinit var swaggerTitle : String

	@Value("\${swagger.description}")
	lateinit var swaggerDescription : String

	@Bean
	fun docket() = Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(paths())
				.build()

	private fun apiInfo() = ApiInfoBuilder()
                .title(this.swaggerTitle)
                .description(this.swaggerDescription)
                .version(this.buildProperties.getVersion())
                .build()

    private fun paths() = Predicates.and(
        		Predicates.not(PathSelectors.regex("/actuator")),
        		Predicates.not(PathSelectors.regex("/error.*"))
		)
}
