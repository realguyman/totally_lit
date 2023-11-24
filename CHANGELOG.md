# Changelog

All notable changes to the project are logged here. This project is compliant with [semantic versioning](https://semver.org/).

## [0.13.1+1.19.4] - 2023-03-31

### Changed

- Port to Minecraft 1.19.4

## [0.13.1+1.19.3] - 2023-03-31

### Added

- Local dependencies to development environment for testing purposes
- CHANGELOG file
- Reminder to read wiki in README

### Fixed

- Incompatibility with Lithium causing campfires to not extinguish over time

### Changed

- Moved API package into base package
- Simplify Jitpack configuration
- Update Modrinth gradle task to reflect current dependencies
- Refactor some namings for campfire related mixins and duck interface

### Removed

- Planned section in README

## [0.13.0+1.19.3] - 2023-03-17

### Added

- Automated testing with gametests
- Generated assets directory to `.gitignore`
- `owo-lib` dependency requirement
- Logger for debugging purposes

### Changed

- Reformat `gradle.properties`
- Refactor mixins and registries
- Update dependencies
- Use `owo-config` instead of `cloth-config` for config system

### Fixed

- Fix candles not extinguishing over time

### Removed

- `cloth-config` dependency requirement
