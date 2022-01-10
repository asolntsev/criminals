# criminals

This is an example of a [RePlay](https://github.com/codeborne/replay) project.

It may be used to understand the differences between a project based on [Play1](https://github.com/playframework/play1) and a project based on RePlay.

[![travis-ci](https://travis-ci.org/asolntsev/criminals.svg?branch=master)](https://travis-ci.org/asolntsev/criminals)


### Test and build

After cloning this repository, you may run the following commands from the project's repository root:

 * `./gradlew test` — runs unit tests
 * `./gradlew uitest` — runs integration (UI) tests
 * `./gradlew build` — runs unit tests and builds the JAR file (`build/libs/criminals.jar`)
 * `./gradlew jar` — just builds the JAR file


### Development flow and deployment

These topics are covered in [the README of the RePlay project](https://github.com/codeborne/replay#readme).


### Troubleshooting

You may find some `WARNING` blocks in the logs when running the application. These are safe to ignore, below some further explanation on what causes then and how to possibly fix them.

Add this flag `--add-opens java.base/java.lang=ALL-UNNAMED` to reduce "illegal reflective access" warnings from `guice` (this will be fixed in a future version of `guice`).

The "illegal reflective access" warnings from `netty` are harder to fix: RePlay should upgrade it's `netty` dependency from `3.10.6.Final` to `io.netty:netty-all:4.1.43.Final` or greater.
 

### License and contributing

The code in this repository is [MIT](https://github.com/asolntsev/criminals/blob/master/LICENSE) licensed.

In case you want to improve upon the project: fork it, commit changes to your branch, open a pull request. Thanks in advance.

You can see build status of your pull requests at https://travis-ci.org/github/asolntsev/criminals 

