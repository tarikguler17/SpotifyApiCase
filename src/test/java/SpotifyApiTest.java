import org.testng.annotations.Test;
import spec.ResponseSpec;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SpotifyApiTest extends BaseServiceTest {

    @Test
    public void OrderJourney() throws IOException {

        ArrayList<String> trackList = new ArrayList<>();
        String userId = usersProfile.getUserID(ResponseSpec.checkStatusCodeOk());
        String playlistId = playlist.createNewPlaylist(userId, ResponseSpec.checkStatusCodeCreated());

        trackList.add(search.getTrackId("Jackie Chan", ResponseSpec.checkStatusCodeOk()));
        trackList.add(search.getTrackId("Carry You Home", ResponseSpec.checkStatusCodeOk()));
        String lastTrackId = search.getTrackId("The Business",ResponseSpec.checkStatusCodeOk());
        trackList.add(lastTrackId);

        playlist.addItemsToPlaylist(trackList, playlistId, ResponseSpec.checkStatusCodeCreated());

        String newPlaylistName = "ATG Playlist 1";
        playlist.changePlaylistName(newPlaylistName, playlistId, ResponseSpec.checkStatusCodeOk());
        assertEquals(newPlaylistName, responseParser.getName(playlist.getPlaylist(playlistId, ResponseSpec.checkStatusCodeOk()), "name"));

        playlist.deleteTrack(lastTrackId, playlistId, ResponseSpec.checkStatusCodeOk());

        assertFalse(responseParser.getName(playlist.getPlaylistsItems(playlistId, ResponseSpec.checkStatusCodeOk()), "items").contains("The Business"));
    }
}
