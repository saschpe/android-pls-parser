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

import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Playlist file (*.pls) parser.
 *
 * See https://en.wikipedia.org/wiki/PLS_(file_format)
 */
public final class PlaylistParser {
    private static final String TAG = PlaylistParser.class.getSimpleName();
    private static final Pattern HEADER_PATTERN = Pattern.compile("\\s*\\[playlist\\]\\s*");
    private static final Pattern FOOTER_NUMBER_OF_ENTRIES_PATTERN = Pattern.compile("\\s*NumberOfEntries=(.*)", Pattern.CASE_INSENSITIVE);
    private static final Pattern FOOTER_VERSION_PATTERN = Pattern.compile("\\s*Version=(.*)", Pattern.CASE_INSENSITIVE);

    private static final Pattern FILE_PATTERN = Pattern.compile("\\s*File(\\d+)=(.*)", Pattern.CASE_INSENSITIVE);
    private static final Pattern TITLE_PATTERN = Pattern.compile("\\s*Title(\\d+)=(.*)", Pattern.CASE_INSENSITIVE);
    private static final Pattern LENGTH_PATTERN = Pattern.compile("\\s*Length(\\d+)=(.*)", Pattern.CASE_INSENSITIVE);

    private PlaylistParser() {
        // No instance
    }

    /**
     * Parses a playlist file (*.pls) provided as {@link InputStream}.
     *
     * @param inputStream PLS file {@link InputStream}
     * @return {@link Playlist} instance
     */
    public static Playlist parse(final InputStream inputStream) {
        List<Playlist.Track> tracks = new ArrayList<>();
        int version = -1, entries = -1;

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line;
            while ((line = reader.readLine()) != null) {

                Matcher fileMatcher = FILE_PATTERN.matcher(line);
                if (fileMatcher.matches()) {
                    int trackIndex = getTrackIndexAndAddIfMissing(tracks, fileMatcher);
                    tracks.get(trackIndex).file = fileMatcher.group(2).trim();
                    continue;
                }
                Matcher titleMatcher = TITLE_PATTERN.matcher(line);
                if (titleMatcher.matches()) {
                    int trackIndex = getTrackIndexAndAddIfMissing(tracks, titleMatcher);
                    tracks.get(trackIndex).title = titleMatcher.group(2).trim();
                    continue;
                }
                Matcher lengthMatcher = LENGTH_PATTERN.matcher(line);
                if (lengthMatcher.matches()) {
                    int trackIndex = getTrackIndexAndAddIfMissing(tracks, lengthMatcher);
                    tracks.get(trackIndex).length = Long.valueOf(lengthMatcher.group(2).trim());
                    continue;
                }

                Matcher numberOfEntries = FOOTER_NUMBER_OF_ENTRIES_PATTERN.matcher(line);
                if (numberOfEntries.matches()) {
                    entries = Integer.valueOf(numberOfEntries.group(1).trim());
                    continue;
                }
                Matcher versionMatcher = FOOTER_VERSION_PATTERN.matcher(line);
                if (versionMatcher.matches()) {
                    version = Integer.valueOf(versionMatcher.group(1).trim());
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Failed to parse playlist file: " + e);
        }

        if (tracks.size() != entries) {
            Log.w(TAG, "Parsed track count doesn't match proclaimed NumberOfEntries");
        }

        return new Playlist(tracks, version);
    }

    /**
     * Parses a playlist file (*.pls) provided as {@link String}.
     *
     * @param string PLS file {@link String}
     * @param encoding String encoding (such as "UTF-8")
     * @return {@link Playlist} instance
     */
    public static Playlist parse(final String string, final String encoding) {
        try {
            return parse(new ByteArrayInputStream(string.getBytes(encoding)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Parses a playlist file (*.pls) provided as {@link String}.
     *
     * Assumes UTF-8 encoding.
     *
     * @param string PLS file {@link String}
     * @return {@link Playlist} instance
     */
    public static Playlist parse(final String string) {
        return parse(string, "UTF-8");
    }

    private static int getTrackIndexAndAddIfMissing(final List<Playlist.Track> tracks, final Matcher matcher) {
        int trackIndex = Integer.valueOf(matcher.group(1)) - 1;
        try {
            tracks.get(trackIndex);
        } catch (IndexOutOfBoundsException e) {
            tracks.add(trackIndex, new Playlist.Track());
        }
        return trackIndex;
    }
}
