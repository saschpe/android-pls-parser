/*
 * Copyright 2017 Sascha Peilicke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package saschpe.android.parser.pls;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public final class PlaylistTest {
    private static final int TEST_VERSION = 2;
    private static final String TEST_TRACK_FILE = "file";
    private static final String TEST_TRACK_TITLE = "title";
    private static final long TEST_TRACK_LENGTH = 10;

    @Test
    public void track() {
        // Arrange, act
        Playlist.Track track = new Playlist.Track();
        track.file = TEST_TRACK_FILE;
        track.title = TEST_TRACK_TITLE;
        track.length = TEST_TRACK_LENGTH;

        // Verify
        assertEquals(TEST_TRACK_FILE, track.getFile());
        assertEquals(TEST_TRACK_TITLE, track.getTitle());
        assertEquals(TEST_TRACK_LENGTH, track.getLength());
    }

    @Test
    public void playlist() {
        // Arrange
        List<Playlist.Track> tracks = new ArrayList<>();
        tracks.add(new Playlist.Track());

        // Act
        Playlist playlist = new Playlist(tracks, TEST_VERSION);

        // Verify
        assertEquals(TEST_VERSION, playlist.getVersion());
        assertEquals(tracks, playlist.getTracks());
        assertEquals(1, playlist.getTracks().size());
    }
}
