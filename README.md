<div align="center">

<h1>Voyah Radio</h1>

<p>A sleek internet radio player built for Voyah vehicles in Lebanon.</p>

[![License](https://img.shields.io/badge/license-GPLv3-yellow.svg)](LICENSE)
![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-Multiplatform-7F52FF?logo=kotlin&logoColor=white)

</div>

---

## About

Voyah Radio is an internet radio player designed for Voyah vehicle owners in Lebanon. It streams live radio stations from Lebanon using the [Radio Browser API](https://www.radio-browser.info), with automatic song recognition that identifies what's currently playing in real time.

Developed by **Elie Meouchi** for **Voyah Lebanon**.

## Features

- Stream live Lebanese radio stations
- Automatic song detection — see what's playing in real time
- Save your favorite stations for quick access
- Search stations by name, genre, or language
- Dark and Light theme support
- Play, Pause, and Volume controls
- Background audio playback
- Clean, minimal interface optimized for in-car use

## Tech Stack

| Library | Purpose |
|---|---|
| [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) | Shared business logic |
| [Compose Multiplatform](https://www.jetbrains.com/compose-multiplatform) | Declarative UI framework |
| [Media3 ExoPlayer](https://developer.android.com/media/media3/exoplayer) | Audio playback engine |
| [Ktor Client](https://ktor.io/docs/getting-started-ktor-client.html) | HTTP networking |
| [Koin](https://insert-koin.io/) | Dependency injection |
| [Room / SQLite](https://developer.android.com/jetpack/androidx/releases/sqlite) | Local database for saved stations |
| [Coil](https://coil-kt.github.io/coil) | Image loading |
| [Datastore](https://developer.android.com/topic/libraries/architecture/datastore) | Preferences storage |
| [Radio Browser API](https://www.radio-browser.info) | Radio station directory |

## Architecture

The project follows **MVVM + Clean Architecture**:

- **Presentation** — Compose UI, ViewModels, unidirectional data flow
- **Domain** — Use cases and business logic, fully independent
- **Data** — Repository pattern abstracting API and local database

## Contact

- **Developer:** Elie Meouchi
- **Email:** elie@meouchi.net
- **Website:** [voyahlebanon.com](https://voyahlebanon.com)

## License

This project is licensed under the [GPL-3.0 License](LICENSE).
