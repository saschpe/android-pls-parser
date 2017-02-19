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

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public final class PlaylistParserTest {
    private static final String ENCODING_UTF8 = "UTF-8";
    private static final String TEST_PLS =
            "[playlist]\n" +
            "\n" +
            "File1=http://relay5.181.fm:8068\n" +
            "Length1=-1\n" +
            "File2=example2.mp3\n" +
            "Title2=Just some local audio that is 2mins long\n" +
            "Length2=120\n" +
            "File3=F:\\Music\\whatever.m4a\n" +
            "Title3=absolute path on Windows\n" +
            "\n" +
            "File4=%UserProfile%\\Music\\short.ogg\n" +
            "Title4=example for an Environment variable\n" +
            "Length4=5\n" +
            "NumberOfEntries=4\n" +
            "Version=2";
    private static final int TEST_PLAYLIST_SIZE = 4;
    private static final long TEST_PLAYLIST_VERSION = 2;

    @Test
    public void parseInputStream() throws UnsupportedEncodingException {
        // Arrange, act
        Playlist playlist = PlaylistParser.parse(new ByteArrayInputStream(TEST_PLS.getBytes(ENCODING_UTF8)));

        // Verify
        assertNotNull(playlist);
        assertEquals(TEST_PLAYLIST_SIZE, playlist.getTracks().size());
        assertEquals(TEST_PLAYLIST_VERSION, playlist.getVersion());

        Playlist.Track track1 = playlist.getTracks().get(0);
        assertEquals("http://relay5.181.fm:8068", track1.getFile());
        assertEquals(null, track1.getTitle());
        assertEquals(-1, track1.getLength());

        Playlist.Track track2 = playlist.getTracks().get(1);
        assertEquals("example2.mp3", track2.getFile());
        assertEquals("Just some local audio that is 2mins long", track2.getTitle());
        assertEquals(120, track2.getLength());

        Playlist.Track track3 = playlist.getTracks().get(2);
        assertEquals("F:\\Music\\whatever.m4a", track3.getFile());
        assertEquals("absolute path on Windows", track3.getTitle());
        assertEquals(0, track3.getLength());

        Playlist.Track track4 = playlist.getTracks().get(3);
        assertEquals("%UserProfile%\\Music\\short.ogg", track4.getFile());
        assertEquals("example for an Environment variable", track4.getTitle());
        assertEquals(5, track4.getLength());
    }

    @Test
    public void parseStringWithCustomEncoding() {
        // Arrange, act
        Playlist playlist = PlaylistParser.parse(TEST_PLS, ENCODING_UTF8);

        // Verify
        assertNotNull(playlist);
        assertEquals(TEST_PLAYLIST_SIZE, playlist.getTracks().size());
        assertEquals(TEST_PLAYLIST_VERSION, playlist.getVersion());
    }

    @Test
    public void parseString() {
        // Arrange, act
        Playlist playlist = PlaylistParser.parse(TEST_PLS);

        // Verify
        assertNotNull(playlist);
        assertEquals(TEST_PLAYLIST_SIZE, playlist.getTracks().size());
        assertEquals(TEST_PLAYLIST_VERSION, playlist.getVersion());
    }
}
