# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

A small demo web app built on **RePlay** (`io.github.replay-framework`, v2.8.0) — a fork/successor of Play1. It exists as a reference for comparing Play1-style and RePlay-style projects. The app lets an operator log in (with email OTP as a second step) and check whether a person (by SSN) has a criminal record, delegating to an external records service over HTTP.

## Commands

- `./gradlew test` — unit tests only (`ui/**` is excluded; see `build.gradle`)
- `./gradlew uitest` — Selenide UI/integration tests (`ui/**`), runs headless Chrome
- `./gradlew build` — runs `check` and builds `build/libs/criminals.jar`
- `./gradlew jar` — builds the JAR without tests
- `./gradlew check` / `./gradlew` (default tasks are `clean check`) — full unit-test verification
- Run a single test: `./gradlew test --tests services.OtpCodeServiceTest`
- Run a single UI test: `./gradlew uitest --tests ui.LoginTest`

Requires a **Java 25** toolchain (`build.gradle`), despite `.travis.yml` still naming openjdk11.

Tests force locale/encoding via system properties: `user.country=TR`, `user.language=tr`, `file.encoding=UTF-8`. Keep this in mind — locale-sensitive code (case conversion, number/date formatting) runs under Turkish locale in tests.

## Architecture

RePlay uses **convention over configuration**, so wiring lives in conventionally-located files rather than in imports:

- `conf/routes` — maps HTTP verb+path to `Controller.method`. This is the entry-point map; read it first to find which controller handles a request.
- `conf/application.conf` — config with Play-style env prefixes (`%test.`, `%prod.`). Test runs use `%test.*` values (e.g. SMTP on `127.0.0.1:9010`, records service on `0.0.0.0:9020`).
- `conf/play.plugins` — ordered RePlay plugins (validation, i18n, the GT template engine).
- `conf/messages` — i18n message bundle.

Source layout (non-standard, configured in `build.gradle` sourceSets): app code is under `app/` (not `src/`), tests under `test/`. The `src/` directory is essentially empty.

### Layers

- `app/controllers/*` — extend `play.mvc.Controller`, return a `Result` (usually `play.rebel.View` pointing at an `app/views/**/*.html` GT template, or a `Redirect`/`PdfResult`). Dependencies are field/constructor-injected with `@Inject`.
- `app/services/*` — `@Singleton` business logic, Guice-injected. `CriminalSafetyCalculator` is the core: it calls the external records service via `RestClient` and produces a `Verdict`; on `IOException` it fails safe (treats the person as unsafe).
- `app/models/*` — plain records (`User`, `Verdict`, `CriminalRecord`). `UserRepository` holds a single hardcoded in-memory user (`john`/`secret`); there is no database.
- `app/views/**/*.html` — Groovy Template (GT) views, referenced by name from controllers.

### Bootstrap & DI

- `criminals/Application.java` is the `main` entry point: it loads config, builds a Guice `GuiceBeanSource`, and starts the Netty-backed `play.server.Server`. UI tests start the app the same way via `BaseUITest`.
- `criminals/Module.java` is the Guice module. It currently only binds the `@Named("criminal-records.service.url")` String from configuration — add new explicit bindings here. Most services are auto-wired by `@Singleton`/`@Inject`.

### Login flow (two-step)

`Login.firstStep` validates credentials via `LoginService`, then `OtpCodeService` generates an OTP, emails it (`play.libs.Mail`), and stashes username/code in the session. `Login.secondStep` compares the submitted code against the session value before setting `user.name`. Session is a signed cookie (`CookieSessionStore`, `application.secret`), not server-side state.

## Testing patterns

- **UI tests** (`test/ui/`) extend `BaseUITest`, which boots the real app once (`Play.started` guard) and configures Selenide. They use the **Page Object** pattern (`*Page.java` classes) with `open("/path", SomePage.class)`.
- External HTTP dependency is stubbed with **WireMock** on port 9020 (`@WireMockTest(httpPort = 9020)`), matching the `%test.criminal-records.service.url`.
- Email is captured with a local **SubEthaSMTP** server (`MailServerEmulator`) on port 9010, so OTP codes can be asserted.
- Unit tests (`test/services/`) test services directly with AssertJ + Mockito.

Note: text assertions in UI tests are in Russian (the views/verdicts are Russian-language); this is expected, not a bug.