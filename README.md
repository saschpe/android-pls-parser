# Android Playlist File Parser
[ ![Download](https://api.bintray.com/packages/saschpe/maven/android-pls-parser/images/download.svg) ](https://bintray.com/saschpe/maven/android-pls-parser/_latestVersion)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Android%20Playlist%20File%20Parser-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/5538)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/saschpe/android-pls-parser.svg?branch=master)](https://travis-ci.org/saschpe/android-pls-parser) 
<a href="http://www.methodscount.com/?lib=saschpe.android%3Apls-parser%3A1.0.5"><img src="https://img.shields.io/badge/Methods and size-47 | 6 KB-e91e63.svg"/></a>

A playlist file (*.pls) parser library for Android. Please check the
format's [Wikipedia page][wp_pls] for details.

# Usage
To parse a playlist file provided as an InputStream:
```java
// ...
Playlist playlist = PlaylistParser.parse(inputStream);
for (Playlist.Track track : playlist.getTracks()) {
    track.getFile();
    track.getTitle();
    // ...
}
```

To parse a playlist file provided as a String:
```java
// ...
Playlist playlist = PlaylistParser.parse(string);
for (Playlist.Track track : playlist.getTracks()) {
    track.getFile();
    track.getTitle();
    // ...
}
```

# Download
```groovy
compile 'saschpe.android:pls-parser:1.0.5'
```

Snapshots of the development version are available in [Sonatype's `snapshots` repository][snap].

# Users
* [Alpha+ Player](https://play.google.com/store/apps/details?id=saschpe.alphaplus)

# License

    Copyright 2017 Sascha Peilicke

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


 [snap]: https://oss.sonatype.org/content/repositories/snapshots/
 [wp_pls]: https://en.wikipedia.org/wiki/PLS_(file_format)
