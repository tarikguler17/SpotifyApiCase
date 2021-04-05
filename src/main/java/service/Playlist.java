package service;

import helper.JsonHelper;
import helper.RequestMaps;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONObject;
import spec.RequestSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Playlist extends RequestSpec {

    public Playlist() {
        super("https://api.spotify.com/v1");
    }

    public String createNewPlaylist(String userId, ResponseSpecification responseSpecification) throws IOException {
        String jsonBody = JsonHelper.jsonToString("newPlaylist.json");

        Response playlistResponse = given()
                .spec(super.getRequestSpecification())
                .body(jsonBody)
                .when()
                .post("users/{userId}/playlists", userId)
                .then()
                .spec(responseSpecification)
                .extract()
                .response();
        String playlistId = playlistResponse.getBody().jsonPath().getString("id");
        System.out.println("PlaylistID: " + playlistId);
        return playlistId;
    }

    public void addItemsToPlaylist(ArrayList<String> trackList, String playlistId, ResponseSpecification responseSpecification) {
        String stringOfTracks = "";
        for (Object track : trackList) {
            stringOfTracks += "spotify:track:" + track.toString() + ",";
        }
        Map<String, Object> itemsToPlaylistMap = RequestMaps.addItemsToPlaylistMap(playlistId, stringOfTracks);

        given()
                .spec(super.getRequestSpecification())
                .queryParams(itemsToPlaylistMap)
                .when()
                .post("playlists/{playlist_id}/tracks", playlistId)
                .then()
                .spec(responseSpecification);
    }

    public void changePlaylistName(String playlistName, String playlistId, ResponseSpecification responseSpecification) throws IOException {
        String jsonBody = JsonHelper.jsonToString("newPlaylist.json", "name", playlistName);

        given()
                .spec(super.getRequestSpecification())
                .queryParam("playlist_id", playlistId)
                .body(jsonBody)
                .when()
                .put("playlists/{playlist_id}", playlistId)
                .then()
                .spec(responseSpecification);
    }

    public Response getPlaylist(String playlistId, ResponseSpecification responseSpecification) {
        return given()
                .spec(super.getRequestSpecification())
                .queryParam("playlist_id", playlistId)
                .when()
                .get("playlists/{playlist_id}", playlistId)
                .then()
                .spec(responseSpecification)
                .extract()
                .response();
    }

    public Response getPlaylistsItems(String playlistId, ResponseSpecification responseSpecification) {
        return given()
                .spec(super.getRequestSpecification())
                .queryParam("playlist_id", playlistId)
                .when()
                .get("playlists/{playlist_id}/tracks", playlistId)
                .then()
                .spec(responseSpecification)
                .extract()
                .response();
    }

    public void deleteTrack(String trackId, String playlistId, ResponseSpecification responseSpecification) throws IOException {

        JSONObject deletetrackBody = new JSONObject(JsonHelper.jsonToString("deleteTrackBody.json"));

        deletetrackBody.getJSONArray("tracks").getJSONObject(0).put("uri", "spotify:track:" + trackId);
        given()
                .spec(super.getRequestSpecification())
                .queryParam("playlist_id", playlistId)
                .body(deletetrackBody.toString())
                .when()
                .delete("playlists/{playlist_id}/tracks", playlistId)
                .then()
                .spec(responseSpecification);
    }
}
