<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# Endpoint Finder Changelog

> Versions up to 0.4.7-stable were released as [RestfulHelper](https://github.com/Nayacco/RestfulHelper) by Nayacco.

## [Unreleased]

- Raise the minimum supported IDE version to 2026.1 (build 261)
- Declare K2 Kotlin plugin mode support so Kotlin endpoints are found when the K2 mode is enabled
- Replace `NameUtil.MatchingCaseSensitivity` (scheduled for removal) with `MatchingMode`
- Replace deprecated `ReadAction.compute` with `ReadAction.computeBlocking`

## [1.1.0]

- Register the JetBrains Marketplace error handler so exception and UI freeze reports reach the plugin developer
- Fix UI freezes while rendering search results by precomputing the location text instead of reading PSI on the UI thread
- Cache endpoint scan results until code or project structure changes, avoiding a full project rescan on every keystroke
- Upgrade Gradle wrapper to 9.9.2

## [1.0.0]

- Fork RestfulHelper and rename the project to Endpoint Finder (plugin ID `com.ddoong2.endpointfinder`)
- Rename package `com.github.nayacco.restfulhelper` to `com.ddoong2.endpointfinder`
- Sort endpoint search results by match tier, URL path and HTTP method
- Add HTTP method-specific color icons (GET, POST, PUT, DELETE, PATCH) in search popup
- Add Jakarta EE (`jakarta.ws.rs`) namespace support for JAX-RS annotations
- Automatically detect `javax.ws.rs` / `jakarta.ws.rs` namespace from annotation qualified name
- Rename internal `jaxrs` package to `ws` to reflect support for both namespaces
- Normalize repeated curly brackets in search input so `{{var}}`-style URLs copied from Postman match `{var}` mappings
- Show only the URL path in search popup items, since the HTTP method is shown by its icon
- Fix read access errors when navigating to an endpoint on recent IDE versions
- Upgrade Gradle to 9.0 and update plugin dependencies

## [0.4.7-stable]

- Resolve [issue-51](https://github.com/Nayacco/RestfulHelper/issues/51)

## [0.4.4-stable] - 2024-11-17

- Support IntelliJ IDEA 2024.3
- Upgrade Kotlin to 2.0.21
- Upgrade org.jetbrains.intellij.platform to 2.1.0
- Upgrade platformVersion to 2024.3

## [0.4.3-stable]

- Support IntelliJ IDEA 2024.2
- Upgrade Gradle to 8.9
- Upgrade Kotlin to 1.9.24
- Upgrade org.jetbrains.intellij.platform to 2.0.0
- Remove deprecated api org.apache.commons.lang.StringEscapeUtils
- Upgrade Java to 21

## [0.4.2-stable]

- Support IntelliJ IDEA 2024.1

## [0.4.0-stable]

- Support IntelliJ IDEA 2023.3

## [0.3.0-stable]

- Support IntelliJ IDEA 2023.2
- Add module name to location

## [0.2.8-stable]

- Support IntelliJ IDEA 2023.1
- Upgrade Gradle to 8.0.2
- Upgrade org.jetbrains.kotlin.jvm to 1.8.10
- Upgrade org.jetbrains.intellij to 1.13.3

## [0.2.7-stable]

- Support IntelliJ IDEA 2022.3
- Upgrade org.jetbrains.intellij to 1.10.0

## [0.2.6-stable]

- Support IntelliJ IDEA 2022.2
- Upgrade Java to 17
- Remove deprecated api
- Upgrade Gradle to 7.5
- Upgrade org.jetbrains.intellij to 1.7.0
- Upgrade org.jetbrains.changelog to 1.3.1

## [0.2.5-stable]

- Support IntelliJ IDEA 2022.1
- Upgrade Gradle to 7.4.2
- Upgrade org.jetbrains.intellij to 1.5.2
- Upgrade org.jetbrains.kotlin.jvm to 1.6.20

## [0.2.4-stable]

- Remove deprecated api
- Fix the problem of incorrectly identifying mybatis annotations

## [0.2.2-stable]

- Support IntelliJ IDEA 2021.3

## [0.2.1-stable]

### Fix

- Support IntelliJ IDEA higher than 2021.2

## [0.2.0-stable]

### Fix

- remove internal api

## [0.1.0-stable.1]

### Added

- 🎉 release for 2021.2

[Unreleased]: https://github.com/daejoon/endpoint-finder/compare/v1.0.0...HEAD
[1.0.0]: https://github.com/daejoon/endpoint-finder/commits/v1.0.0
[0.4.4-stable]: https://github.com/Nayacco/RestfulHelper/compare/v0.4.3-stable...v0.4.4-stable
[0.4.3-stable]: https://github.com/Nayacco/RestfulHelper/compare/v0.4.2-stable...v0.4.3-stable
[0.4.2-stable]: https://github.com/Nayacco/RestfulHelper/compare/v0.4.0-stable...v0.4.2-stable
[0.4.0-stable]: https://github.com/Nayacco/RestfulHelper/compare/v0.3.0-stable...v0.4.0-stable
[0.3.0-stable]: https://github.com/Nayacco/RestfulHelper/compare/v0.2.8-stable...v0.3.0-stable
[0.2.8-stable]: https://github.com/Nayacco/RestfulHelper/compare/v0.2.7-stable...v0.2.8-stable
[0.2.7-stable]: https://github.com/Nayacco/RestfulHelper/compare/v0.2.6-stable...v0.2.7-stable
[0.2.6-stable]: https://github.com/Nayacco/RestfulHelper/compare/v0.2.5-stable...v0.2.6-stable
[0.2.5-stable]: https://github.com/Nayacco/RestfulHelper/compare/v0.2.4-stable...v0.2.5-stable
[0.2.4-stable]: https://github.com/Nayacco/RestfulHelper/compare/v0.2.2-stable...v0.2.4-stable
[0.2.2-stable]: https://github.com/Nayacco/RestfulHelper/compare/v0.2.1-stable...v0.2.2-stable
[0.2.1-stable]: https://github.com/Nayacco/RestfulHelper/compare/v0.2.0-stable...v0.2.1-stable
[0.2.0-stable]: https://github.com/Nayacco/RestfulHelper/compare/v0.1.0-stable.1...v0.2.0-stable
[0.1.0-stable.1]: https://github.com/Nayacco/RestfulHelper/commits/v0.1.0-stable.1
