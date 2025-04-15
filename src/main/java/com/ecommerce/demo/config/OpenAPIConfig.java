package com.ecommerce.demo.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// // import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

// // import io.swagger.models.Contact;
// // import io.swagger.v3.oas.models.info.Contact;
// import springfox.documentation.builders.ApiInfoBuilder;
// import springfox.documentation.builders.PathSelectors;
// import springfox.documentation.builders.RequestHandlerSelectors;
// import springfox.documentation.service.ApiInfo;
// import springfox.documentation.service.Contact;
// import springfox.documentation.spi.DocumentationType;
// import springfox.documentation.spring.web.plugins.Docket;
// // import springfox.documentation.swagger2.annotations.EnableSwagger2;

// @Configuration
// // @EnableSwagger2
// public class SwaggerConfig {

//     @Bean
//     public Docket productApi(){
//         return new Docket(DocumentationType.SWAGGER_2)
//                 .apiInfo(getApiInfo())
//                 .select()
//                 .apis(RequestHandlerSelectors.basePackage("com.ecommerce.demo"))
//                 .paths(PathSelectors.any())
//                 .build();

//     }

//     private ApiInfo getApiInfo(){
//         Contact contact = new Contact("webutsplus","http://webutsplus.com","contact.webutsplus@gmail.com");
//         // Contact contact = new Contact();
//         // contact.setEmail("test@gmail.com");
//         // contact.setName("TestUser");
//         // contact.setUrl("https://www.api.com");
//         return new ApiInfoBuilder()
//                  .title("Ecommerce API")
//                  .description("Documentation Ecommerce api")
//                  .version("1.0.0")
//                  .license("Apache 2.0")
//                  .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
//                  .contact(contact)
//                  .build();
//     }

// }






// ------this is what i did in lms_day2 project (library management project)

// package com.crudapp.lms.config;
// // package com.example.test_app.config;


import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

  @Value("${demo.openapi.dev-url}")
  private String devUrl;

  @Value("$demo.openapi.prod-url}")   
  private String prodUrl; 
    
  @Bean
  public OpenAPI myOpenAPI() {
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Server URL in Development environment");

    Server prodServer = new Server();
    prodServer.setUrl(prodUrl);
    prodServer.setDescription("Server URL in Production environment");

    Contact contact = new Contact();
    contact.setEmail("test@gmail.com");
    contact.setName("TestUser");
    contact.setUrl("https://www.api.com");

    License mitLicense = new License().name("ABC Company License").url("https://choosealicense.com/licenses/lic1/");

    Info info = new Info()
        .title("Ecommerce API")
        .version("1.0")
        .contact(contact)
        .description("This API exposes endpoints to manage tutorials.").termsOfService("https://www.test.com/terms")
        .license(mitLicense);

    return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
  }
}
