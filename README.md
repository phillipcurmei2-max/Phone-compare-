# PhoneCompare

A native Android app for comparing smartphone **screen sizes** using accurately
scaled visual screen outlines. PhoneCompare draws phone screens at
mathematically correct relative physical dimensions so you can see how devices
genuinely compare — not just read a spec sheet.

> **Status: Milestone 1 — Project foundation.**
> This repository currently contains a clean, buildable foundation: the project
> structure, dependencies, design system, navigation, an offline-first data
> layer scaffold, and a polished landing screen. The device catalogue and the
> comparison engine are **not** implemented yet. No real device data ships in
> this milestone, and nothing in the UI that looks like device data is real —
> placeholders are explicitly labelled.

---

## Product overview

PhoneCompare will eventually let users:

- Search a catalogue of **10,000+ real smartphone models**.
- Select two or more devices.
- Compare their **screen outlines** drawn at true relative physical size.
- See physical screen **width and height in millimetres**.
- Compare **diagonal**, **resolution**, **pixel density** and **aspect ratio**.
- **Overlay** screen outlines to see size differences directly.

The product is designed to feel like a dedicated technical comparison
instrument, not a phone-shopping website.

## Technology stack

| Area | Choice |
| --- | --- |
| Language | Kotlin |
| UI toolkit | Jetpack Compose |
| Design system | Material 3 |
| Navigation | Navigation Compose (type-safe) |
| State | ViewModel + `StateFlow`, immutable UI state |
| Persistence | Room (offline-first) |
| Async | Kotlin Coroutines + Flow |
| Serialization | kotlinx.serialization (type-safe navigation args) |
| Build | Gradle (Kotlin DSL) + version catalog |
| Min SDK | 24 |
| Compile / target SDK | 35 |

Dependency versions are centralised in [`gradle/libs.versions.toml`](gradle/libs.versions.toml).

## Project structure

The app is a single Gradle module (`:app`) organised into clean, layered
packages under `com.phonecompare.app`:

```
com.phonecompare.app
├── core/                 Cross-cutting concerns
│   └── di/               Manual dependency container (AppContainer)
├── data/                 Data layer (offline-first)
│   ├── local/            Room entities, DAO, database
│   ├── mapper/           Entity <-> domain mappers
│   └── repository/       PhoneRepository implementation
├── domain/               Pure business layer (no Android deps)
│   ├── model/            PhoneSpec, ScreenSpec, DatabaseStatus, ...
│   └── repository/       PhoneRepository interface
├── ui/                   Presentation layer (Jetpack Compose)
│   ├── theme/            Color, Type, Shape, Dimens, Theme
│   ├── components/       Reusable composables (branding, search, badges)
│   └── home/             Landing screen + ViewModel + immutable UI state
├── navigation/           NavHost and type-safe destinations
├── MainActivity.kt       Single-activity Compose host
└── PhoneCompareApplication.kt
```

**Dependency direction:** `ui → domain ← data`. The UI and data layers both
depend on the `domain` layer's abstractions; the domain layer depends on
nothing Android-specific. This keeps the comparison logic (future milestone)
testable and free of framework coupling.

## Build instructions

### Prerequisites

- JDK 17+
- Android SDK with:
  - Platform `android-35`
  - Build-Tools `35.0.0`
  - Platform-Tools
- A `local.properties` file at the repo root pointing at your SDK (this file is
  intentionally **not** committed):

  ```properties
  sdk.dir=/absolute/path/to/Android/sdk
  ```

### Build the debug APK

```bash
./gradlew :app:assembleDebug
```

The APK is produced at:

```
app/build/outputs/apk/debug/app-debug.apk
```

### Run the tests

```bash
./gradlew :app:testDebugUnitTest
```

### Install on a connected device / emulator

```bash
./gradlew :app:installDebug
```

> The debug build uses the application id `com.phonecompare.app.debug` so it can
> coexist with a future release build.

## Milestone roadmap

- **Milestone 1 — Project foundation _(this milestone)_**
  Project structure, dependencies, Material 3 design system, navigation,
  offline-first data-layer scaffold (empty Room database), and a polished
  landing screen. Builds to a debug APK and launches to the landing screen.
- **Milestone 2 — Device catalogue & search**
  Import the real device dataset into Room, implement search, and render real
  results. Introduce schema export and migrations.
- **Milestone 3 — Screen comparison engine**
  Compute and render screen outlines at accurate relative physical scale;
  side-by-side and overlay comparison; width/height/diagonal/density/aspect
  readouts.
- **Milestone 4 — Comparison workspace & polish**
  Multi-device selection, saved comparisons, sharing, accessibility and
  performance passes.

## Database & data-source strategy

PhoneCompare is **offline-first**. The entire device catalogue is stored in a
local **Room** database so the app is fast and fully usable without a network
connection. Milestone 1 introduces no networking at all.

- **Source of truth:** a bundled/importable dataset of real smartphone
  specifications. The import pipeline (Milestone 2) will populate Room once,
  then serve all reads locally.
- **Accuracy over quantity:** every physical dimension must be sourced from
  manufacturer specifications or reliable measured data. **No device record may
  be invented or estimated and presented as verified data.** Until the
  catalogue is populated, the app reports a *"preparing"* status rather than
  showing placeholder specs as if they were real.
- **Schema & migrations:** the schema starts at version 1 with `exportSchema`
  disabled. When the importer lands, schema export (`room.schemaLocation`) and
  Room migrations will be enabled so upgrades are safe.
- **Networking:** deferred. If remote dataset updates are added later, they will
  sync into the same local Room cache behind the existing `PhoneRepository`
  abstraction, keeping reads offline-first.

## Screen-measurement accuracy requirements

The core promise of PhoneCompare is **true-to-size** screen rendering. To honour
it:

- Physical screen dimensions are stored and compared in **millimetres**
  (`ScreenSpec.physicalWidthMm` / `physicalHeightMm`), independent of pixels.
- Resolution, pixel density (PPI) and aspect ratio are stored as first-class
  fields so derived values are consistent and verifiable.
- The future comparison engine must map millimetres to on-screen pixels using
  the **viewing device's real pixel density**, so a 6.1" screen outline is
  physically 6.1" when measured against the running device — not merely scaled
  to fill available space.
- Aspect ratios are derived geometrically from native resolution rather than
  rounded marketing figures.
- Any value that cannot be sourced accurately is omitted rather than
  approximated.

## Design language

- Dark charcoal backgrounds with restrained, layered neutral surfaces.
- A single bright **electric-blue** accent used sparingly.
- Clean system typography with tight display headings and a consistent spacing
  scale.
- Minimal visual clutter: no heavy gradients, no emoji-based UI.

The intent is a precise, trustworthy **technical instrument**.

## License

Not yet specified.
