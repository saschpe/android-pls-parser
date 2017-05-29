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

import java.util.List;

/**
 * Playlist file.
 *
 * Contains a list of tracks and a version.
 */
public final class Playlist {
    private final List<Track> tracks;
    private final int version;

    Playlist(final List<Track> tracks, final int version) {
        this.tracks = tracks;
        this.version = version;
    }

    /**
     * Get track list.
     *
     * @return {@link Track} list
     */
    public List<Track> getTracks() {
        return tracks;
    }

    /**
     * Get playlist file version.
     *
     * @return Version number
     */
    public int getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "tracks=" + tracks +
                ", version=" + version +
                '}';
    }

    /**
     * Playlist file track.
     */
    public static final class Track {
        String file;
        String title;
        long length;

        public String getFile() {
            return file;
        }

        public String getTitle() {
            return title;
        }

        public long getLength() {
            return length;
        }

        @Override
        public String toString() {
            return "Track{" +
                    "file='" + file + '\'' +
                    ", title='" + title + '\'' +
                    ", length=" + length +
                    '}';
        }
    }
}
