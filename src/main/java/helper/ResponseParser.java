package helper;

import io.restassured.response.Response;

public class ResponseParser {
    public String getName(Response response, String pathName) {
        return response.getBody().jsonPath().getString(pathName);
    }
}
