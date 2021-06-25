# Project 2 - *Flixster*

**Flixster** shows the latest movies currently playing in theaters. The app utilizes the Movie Database API to display images and basic information about these movies to the user.

Time spent: **12** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **scroll through current movies** from the Movie Database API
* [x] Display a nice default [placeholder graphic](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#advanced-usage) for each image during loading
* [x] For each movie displayed, user can see the following details:
  * [x] Title, Poster Image, Overview (Portrait mode)
  * [x] Title, Backdrop Image, Overview (Landscape mode)
* [x] Allow user to view details of the movie including ratings within a separate activity

The following **stretch** features are implemented:

* [x] Improved the user interface by experimenting with styling and coloring (Minor changes).
* [x] Apply rounded corners for the poster or background images using [Glide transformations](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#transformations)
* [x] Apply the popular [View Binding annotation library](http://guides.codepath.org/android/Reducing-View-Boilerplate-with-ViewBinding) to reduce boilerplate code.
* [x] Allow video trailers to be played in full-screen using the YouTubePlayerView from the details screen.

The following **additional** features are implemented:

* [x] Added some extra styling on movie details activity
* [x] Added support for all orientations for YouTube video player
* [x] Declare a new field on Movie to store the video id and use it to avoid fetching the same value more than once
* [x] Display a “play” overlay image over the backdrop on the details activity as a visual cue to the user

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='walkthrough1.gif' title='Video Walkthrough - Vertical' width='' alt='Video Walkthrough Vertical' />
<img src='walkthrough2.gif' title='Video Walkthroug - Horizontal' width='' alt='Video Walkthrough Horizontal' />

GIFs created with [Kap](https://getkap.co/).

## Notes

Most of my challenges and blockers definitely arose during the bonus user story section and tweaking the UI. First, the YouTube video feature was the most convoluted for me especially because the process was different from our usual listener -> intent code. Figuring that out using the guide and Google was a way to unblock myself, but it took quite a while. Then, in addition to the backend side of the movie trailer addition, I was having issues formatting the image views on top of one other and using the layout gravity attribute to properly organize the UI elements. I did figure it out in the end after referring to docs and CodePath notes!

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android

## License

    Copyright 2021 Alexis Echano

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
