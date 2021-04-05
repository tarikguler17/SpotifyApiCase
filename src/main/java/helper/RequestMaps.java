package helper;

import java.util.HashMap;
import java.util.Map;

public class RequestMaps {

    public static Map<String, Object> getTrackIdMap(String trackName) {
        Map<String, Object> params = new HashMap<>();
        params.put("q", trackName);
        params.put("type", "track");
        params.put("market", "US");
        params.put("limit", "1");
        return params;
    }

    public static Map<String, Object> addItemsToPlaylistMap(String playlistId, String stringOfTracks) {
        Map<String, Object> params = new HashMap<>();
        params.put("playlist_id", playlistId);
        params.put("uris", stringOfTracks);

        return params;
    }
}
