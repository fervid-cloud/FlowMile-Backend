//package com.example.demo.apidocumentation;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger.web.UiConfiguration;
//import springfox.documentation.swagger.web.UiConfigurationBuilder;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
/***
 *
 A final variable must be initialized at the declaration or in a constructor.

 If it has not been initialized when the constructor returns, it may never be initialized, and may remain an uninitialized variable. The compiler cannot prove it will be initialized, and thus throws an error.
 If an instance variable is declared with final keyword, it means it can not be modified later, which makes it a constant. That is why we have to initialize the variable with final keyword.Initialization must be done explicitly because JVM doesnt provide default value to final instance variable.Final instance variable must be initialized either at the time of declaration like:
 it would be called a blank final if it is not initialized when declared.
 class Test{
 final int num = 10;
 }

 or it must be declared inside an instance block like:

 class Test{
 final int x;
 {
 x=10;
 }
 }

 or it must be declared BEFORE constructor COMPLETION like:

 class Test{
 final int x;
 Test(){
 x=10;
 }
 }

 Keep in mind that we can initialize it inside a consructor block because initialization must be done before constructor completion.

 */


//
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig extends WebMvcConfigurationSupport {
//    @Bean
//    public Docket version1() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                   .select()
//                   .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
//                   .build()
//                   .enable(true)
//                   .groupName("User Management-1.0.0.0")
//                   .apiInfo(new ApiInfoBuilder()
//                                .description("User Management APIs")
//                                .title("User Management 1.0.0.0")
//                                .version("1.0.0.0")
//                                .build()
//                   );
//    }
//
//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        assert registry != null;
//        registry.addResourceHandler("swagger-ui.html")
//            .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**")
//            .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
//
//    /**
//     * Ui config ui configuration.
//     *
//     * @return the ui configuration
//     */
//    @Bean
//    public UiConfiguration uiConfig() {
//        return UiConfigurationBuilder.builder()
//                   .displayRequestDuration(true)
//                   .validatorUrl("")
//                   .build();
//    }
//
//
//}