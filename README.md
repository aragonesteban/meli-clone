<h1 align="center">Meli Clone</h1>

<p align="center">
🛒 Mercado Libre App Clone using modern Android development with Hilt, Coroutines, Jetpack (Room, ViewModel), and Jetpack Compose based on MVVM architecture.
</p>

<img src="/previews/preview.gif" align="right" width="32%"/>

## Tech stack
- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/) 
- [Jetpack Compose](https://developer.android.com/jetpack/compose) Android’s modern toolkit for building native UI. (The app was built using JetpackCompose and XML for create interfaces to give a demostration of how we can inject new way to create interfaces with old way to create interfaces).
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection.
- Jetpack
  - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - Observe Android lifecycles and handle UI states upon the lifecycle changes.
  - [View Binding](https://developer.android.com/topic/libraries/view-binding) - Feature that allows you to more easily write code that interacts with views. 
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
  - [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started) - Handling and manage the navigation in the app.
  - [Room Persistence](https://developer.android.com/jetpack/androidx/releases/room) - Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - Construct the REST APIs.
- [Kotlin Serialization](https://kotlinlang.org/docs/serialization.html) - A modern JSON library for Kotlin.
- [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) - state-holder observable flow that emits the current and new state updates to its collectors.
- [Coroutines](https://developer.android.com/kotlin/coroutines) - for asynchronous calls.
- [Coil](https://coil-kt.github.io/coil/) - for loading images from network. 
- [Mockito-Kotlin](https://github.com/mockito/mockito-kotlin#mockito-kotlin) - Library for unit testing based in kotlin.
- [RefreshVersion](https://github.com/jmfayard/refreshVersions) - to manage dependencies version from kotlinDSL.

<br />

## Architecture
Meli is based on the MVVM architecture and the Repository pattern and it has the approach in Clean Architecture where we can manage differents layers.
This architecture containts three main layers: 

- **data**: In this module/layer it will be everything about datasources like network and cache 
- **domain**: In this module lives everything about bussines logic working with use cases, also is the responsible to get data from data module to send it to the view.
- **features**: In this module lives all presentation

<div style="display: grid; grid-template-columns: 50% 50%;">
<img src="https://user-images.githubusercontent.com/24237865/77502018-f7d36000-6e9c-11ea-92b0-1097240c8689.png" />

<img alt="Screen Shot 2022-06-29 at 10 23 31 AM" src="https://user-images.githubusercontent.com/93212523/176474644-a2fb49ef-05cd-4a5a-8c6a-8e7199f6ded4.png">
</div>

<br />
<br />

## How I run the app?
 - Clone the repository
 - Open it in Android Studio
 - Wait until dependencies are installed
 - Run app in your emulator or physical device
 
If you don't have Android Studio and you don't want to clone the repository, you can downlad an apk [here](https://www.mediafire.com/file/hywdta71149p3py/MeliClone.apk/file) 

