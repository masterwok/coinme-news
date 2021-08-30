# Coinme Technical Interview Project

This repository contains the Coinme technical interview project for Android. This application allows users to search for news articles, view article details, and open articles within a [WebView](https://developer.android.com/reference/android/webkit/WebView). news articles are provided using [https://newsapi.org/](https://newsapi.org/).

## Architecture

This application follows the MVVM (model-view-viewmodel) architectural pattern. This architecture was used because it's the [application architecture recommened by Google](https://developer.android.com/jetpack/guide?gclid=CjwKCAjwn6GGBhADEiwAruUcKoX2rUi1rlaWQeICQLQgLiaegSKjemkcR7Ezho2US3PTU_2rgv_YiBoCQr8QAvD_BwE&gclsrc=aw.ds#recommended-app-arch).

This application also follows the single-activity architecture and uses the [JetPack Navigation Component](https://developer.android.com/guide/navigation?gclid=CjwKCAjw4KyJBhAbEiwAaAQbE-1ac2kTg1BCtC6qT-CIDNjEutTHwAGXevW8788L0C6NlqyLec-sWBoC2OMQAvD_BwE&gclsrc=aw.ds) library by Google.

Dependencies within the application are provided using the [Dagger dependency injection framework](https://developer.android.com/training/dependency-injection/dagger-android).

Fragments subscribe to state changes using [flows](https://kotlinlang.org/docs/flow.html) contained within view models.

The architectural layers that exist in the application:
- Activity/Fragment
- View Models
- Repositories
- Clients

## Screenshots

The below screenshots show the entirety of the application:

<img src="screenshots/home.png?raw=true" height="600" title="Home">  <img src="screenshots/homeSearchActive.png?raw=true" height="600" title="Home Search Active">  <img src="screenshots/articleDetail.png?raw=true" height="600" title="Article Detail">  <img src="screenshots/articleDetailShare.png?raw=true" height="600" title="Article Detail Share">  <img src="screenshots/webView.png?raw=true" height="600" title="Web View">

