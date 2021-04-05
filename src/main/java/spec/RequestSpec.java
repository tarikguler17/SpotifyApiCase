package spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpec {
    RequestSpecification requestSpecification;
    String authToken = "BQChVxAaIXwXUs7m1vsCXTuyW87ynVe1XGXEuLGy4F5kEjjzzbvE7XvP8JE1jJL7WFoYHH1DjRLaU_C3YUocdH1x9nOI32ogzgm-EQ_72cqYntgv-B6by5UC38O0iXHwg4uj_G9HtTi3hZ3QZ_V0bxBNwetZFdF7nkXHT0hZMITCYb6MuD6opRfcWQ59dMTHm6N34dZVh6uKIFi8JTl922OIV4SVoGQTjvlZi3cWkIbLXvFMLUUvpxb_sUexSI83NJC-htfGnNApkxp4Ds7sHIY";

    public RequestSpec(String baseUrl) {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType("application/json")
                .addHeader("Authorization", "Bearer " + authToken)
                .build();
    }

    public RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }
}
