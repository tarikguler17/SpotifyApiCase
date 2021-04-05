package service;

import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import spec.RequestSpec;

import static io.restassured.RestAssured.given;

public class UsersProfile extends RequestSpec {

    public UsersProfile() {
        super("https://api.spotify.com/v1");
    }

    public String getUserID(ResponseSpecification responseSpecification) {
        String userId;
        Response response =
                given()
                        .spec(super.getRequestSpecification())
                        .when()
                        .get("/me")
                        .then()
                        .spec(responseSpecification)
                        .extract()
                        .response();

        userId = response.getBody().jsonPath().getString("id");
        return userId;
    }
}
