package guru.springframework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String CUSTOMER_TAG = "Customer Controller";
    public static final String VENDOR_TAG = "Vendor Controller";


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build().tags(new Tag(CUSTOMER_TAG, "This is the Customer Controller."), new Tag(VENDOR_TAG, "Provides APIs to retrieve and update vendors.")).pathMapping("/").apiInfo(metaData());
    }

    private ApiInfo metaData() {

        Contact contact = new Contact("Damian Grande", "https://damiangrande.com", "damian77grande@gmail.com");

        return new ApiInfo("Spring Framework Guru", "Spring Framework 5: Beginner to Guru", "1.0", "None", contact, "Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());

    }

}
