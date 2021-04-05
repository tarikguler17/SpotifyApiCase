package service;

import io.restassured.response.Response;
import helper.RequestMaps;
import io.restassured.specification.ResponseSpecification;
import spec.RequestSpec;

import java.util.ArrayList;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Search extends RequestSpec {

    public Search() {
        super("https://api.spotify.com/v1");
    }

    public String getTrackId(String trackName, ResponseSpecification responseSpecification) {
        Map<String, Object> trackMap = RequestMaps.getTrackIdMap(trackName);

        Response trackIdResponse =
                given()
                        .spec(super.getRequestSpecification())
                        .queryParams(trackMap)
                        .when()
                        .get("search")
                        .then()
                        .spec(responseSpecification)
                        .extract()
                        .response();

        ArrayList<String> arrayList = trackIdResponse.path("tracks.items.id");
        return arrayList.get(0);
    }
}
