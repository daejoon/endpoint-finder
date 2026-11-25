<div align="center">
    <img src="./src/main/resources/META-INF/pluginIcon.svg" width="220" height="220" alt="logo"/>
</div>
<h1 align="center">Endpoint Finder</h1>
<p align="center">Endpoint Finder is an IntelliJ IDEA plugin for quick navigation to URL mapping declarations.</p>

<p align="center">
<a href="https://github.com/daejoon/endpoint-finder/actions"><img src="https://github.com/daejoon/endpoint-finder/workflows/Build/badge.svg"></a>
</p>

<!-- Plugin description -->

Quickly search and navigate to HTTP endpoint (URL mapping) declarations in Java and Kotlin projects.

This plugin is a fork of [RestfulHelper](https://github.com/Nayacco/RestfulHelper) by Nayacco,
which itself is a fork of [request-mapper](https://plugins.jetbrains.com/plugin/9567-request-mapper).
RestfulHelper is no longer maintained, so development continues here.

## Plugin info 🌵

#### 📙 Supported languages:

- Java
- Kotlin

#### 📙 Supported annotations:

| Spring  | JAX-RS (`javax` / `jakarta`)  | Micronaut  | Helidon (JAX-RS) |
|:-:|:-:|:-:|:-:|
| ```@RequestMapping``` | | | |
| ```@GetMapping``` | ```@GET``` | ```@Get``` | ```@GET``` |
| ```@PostMapping```  | ```@POST``` | ```@Post``` | ```@POST``` |
| ```@PutMapping``` | ```@PUT``` | ```@Put``` | ```@PUT``` |
| ```@DeleteMapping``` | ```@DELETE``` | ```@Delete``` | ```@DELETE``` |
| ```@PatchMapping``` | ```@PATCH``` |  ```@Patch``` | ```@PATCH``` |
| | ```@OPTIONS``` |  ```@Options``` | ```@OPTIONS``` |
| | ```@HEAD``` | ```@Head``` | ```@HEAD``` |

## Usage 👣

- Search everywhere (```Shift``` twice)
- Keyboard shortcuts:
  - MacOS: ```Cmd + Back slash```
  - Linux: ```Ctrl + Back slash```
  - Windows: ```Ctrl + Back slash```
- Navigate (menu bar) -> Request mapping

<!-- Plugin description end -->

## Installation ⏳

- Using IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "Endpoint Finder"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/daejoon/endpoint-finder/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

## Credits 🙏

- Original project: [RestfulHelper](https://github.com/Nayacco/RestfulHelper) by [Nayacco](https://github.com/Nayacco)
  ([JetBrains Marketplace](https://plugins.jetbrains.com/plugin/17400-restfulhelper)), licensed under the MIT License.
- RestfulHelper is based on [request-mapper](https://plugins.jetbrains.com/plugin/9567-request-mapper).

## License 📄

This project is licensed under the [MIT License](./LICENSE).

- Copyright (c) 2021 Nayacco (original RestfulHelper)
- Copyright (c) 2026 daejoon (Endpoint Finder modifications)
