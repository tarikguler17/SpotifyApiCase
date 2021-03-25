import com.google.common.io.Resources;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class ApiTest {
    String userId = "";
    String playlistId = "";
    String authToken = "BQCQik7LtfzsrkZaBGzIXD93Xw04aXhlFXlg6GVlOHZi9NQ0qhY7DVbJ4uAum33D160l5aiFf-b39Qzszll0zyqX7LfyaMvPa8NUiEt_KK15BE51m8jPGmRgVqdlBO7wnpEgQeLGccBB-O8YFQ9ZSvdsDePE4xsWfI0VWoJx254cSxAAiO_5fI0-NVX_njFmDpYaVtA_SROddWRxAvqVRLtC8N8hgjKEwTVk9siOgcYEzcdMHXVtOlZwBmCa0xOIfrL43P4w5gX6xQvYc97TaBw";

    @BeforeMethod
    public void beforeTest() throws IOException {
        RestAssured.baseURI = "https://api.spotify.com/v1";
    }

    @Test
    public void spotifyTest() throws IOException {
        String trackName = "Split U";
        String trackName1 = "Alors On Danse";
        String trackName2 = "Beat it";
        String newName = "ATG Playlist";

        getUserId();
        createNewPlaylist();

        addItemsToPlaylist(getTrackId(trackName));
        assertEquals(getTrackId(trackName),isItemAdded());

        addItemsToPlaylist(getTrackId(trackName1));
        addItemsToPlaylist(getTrackId(trackName2));

        changePlaylistName(newName);
        assertEquals(newName,getPlaylistName());
    }

    public void getUserId() {
        Response response =
                given()
                        .contentType("application/json; charset=UTF-8")
                        .header("Authorization", "Bearer " + authToken)
                        .when()
                        .get("/me")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        userId = response.getBody().jsonPath().getString("id");
        System.out.println("User ID: " + userId);
    }

    public void createNewPlaylist() throws IOException {
        URL file = Resources.getResource("newPlaylist.json");
        String myJson = Resources.toString(file, Charset.defaultCharset());
        JSONObject json = new JSONObject(myJson);
        Response playlistResponse =
                given()
                        .contentType("application/json; charset=UTF-8")
                        .header("Authorization", "Bearer " + authToken)
                        .body(json.toString())
                        .when()
                        .post("users/{userId}/playlists", userId)
                        .then()
                        .statusCode(201)
                        .extract()
                        .response();
        playlistId = playlistResponse.getBody().jsonPath().getString("id");
        System.out.println("PlaylistID: " + playlistId);
    }

    public String getTrackId(String trackName) {
        Response trackIdResponse =
                given()
                        .contentType("application/json; charset=UTF-8")
                        .header("Authorization", "Bearer " + authToken)
                        .queryParam("q", trackName)
                        .queryParam("type", "track")
                        .queryParam("market", "US")
                        .queryParam("limit", "1")
                        .when()
                        .get("search")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        ArrayList arrayList = trackIdResponse.path("tracks.items.id");
        return arrayList.get(0).toString();
    }

    public void addItemsToPlaylist(String trackId) {
        given()
                .contentType("application/json; charset=UTF-8")
                .header("Authorization", "Bearer " + authToken)
                .queryParam("playlist_id", playlistId)
                .queryParam("uris", "spotify:track:" + trackId)
                .when()
                .post("playlists/{playlist_id}/tracks", playlistId)
                .then()
                .statusCode(201);
    }
    public String isItemAdded() {
        Response itemResponse =
                given()
                        .contentType("application/json; charset=UTF-8")
                        .header("Authorization", "Bearer " + authToken)
                        .queryParam("playlist_id", playlistId)
                        .queryParam("market", "TR")
                        .queryParam("limit", "3")
                        .when()
                        .get("playlists/{playlist_id}/tracks", playlistId)
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        ArrayList arraylist = itemResponse.path("items.track.id");
        return arraylist.get(0).toString();
    }
    public void changePlaylistName(String playlistName) throws IOException {
        URL file = Resources.getResource("newPlaylist.json");
        String myJson = Resources.toString(file, Charset.defaultCharset());
        JSONObject json = new JSONObject(myJson);
        json.put("name",playlistName);
        given()
                .contentType("application/json; charset=UTF-8")
                .header("Authorization", "Bearer " + authToken)
                .queryParam("playlist_id", playlistId)
                .body(json.toString())
                .when()
                .put("playlists/{playlist_id}",playlistId)
                .then()
                .statusCode(200);
    }
    public String getPlaylistName(){
        Response nameResponse =
                given()
                        .contentType("application/json; charset=UTF-8")
                        .header("Authorization", "Bearer " + authToken)
                        .queryParam("playlist_id", playlistId)
                        .when()
                        .get("playlists/{playlist_id}",playlistId)
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        return nameResponse.getBody().jsonPath().getString("name");
    }
//    Çalışmadı :(
    public void deleteTrack(String trackName) throws IOException {
        URL file = Resources.getResource("deleteTrackBody.json");
        String myJson = Resources.toString(file, Charset.defaultCharset());
        JSONObject json = new JSONObject(myJson);

//        json.getJSONObject("tracks").put("uri",getTrackUri(trackName));
//        JSONObject hashMap = json.getJSONObject("tracks");
//        HashMap<String, String> hashMap1 = new HashMap<String, String>();
//        hashMap1 = json.getJSONObject("tracks");
//        hashMap1.put(json.getJSONObject("tracks"))
//        System.out.println(json.toString());
        given()
                .contentType("application/json; charset=UTF-8")
                .header("Authorization", "Bearer " + authToken)
                .queryParam("playlist_id", playlistId)
                .body(json.toString())
                .when()
                .delete("playlists/{playlist_id}/tracks",playlistId)
                .then()
                .statusCode(200);
    }
}