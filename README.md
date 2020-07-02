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


### Development flow (auto-compilation and hot-swapping)

Unlike with Play1, RePlay does not make use of the `play` command line tool to start the application in development mode (i.e.: with `play run`).

To get your changes automatically compiled and hot-swapped into a running application you need to set up your IDE to take care of it.

Here we describe how to do that with IntelliJ IDEA:

1. Open the criminals project (not "import") by selecting the root of this project's repository
2. Go to `Run -> Edit Configurations...`, click the `+` (Add New Configuration) in the top-right corner and select "Application"
3. Fill in the following details, and click `OK`:
  * Name: `criminals`
  * Main class: `criminals.Application`
  * Use classpath of module: `criminals.main`
  * JRE: select one your prefer, e.g. `11`

Now the `criminals` run configuration shows up in the top-right of the screen. You can press the "Run" button (with the green play icon) to start the application.

Code changes are automatically compiles and hot-swapped into the running application. In many cases it works immediately (until you change signatures of some methods).  
You do not even need to save the file you are working on: simply reload the page in your browser to see the changes in effect.

If hot-swapping failed, you will see a notification in IDEA after which you need to restart the application.  


### Deploying

As a result of `./gradlew jar` you will have the JAR file `build/libs/criminals.jar`.

The following should start your application from the command line:

    java -cp "build/classes/java/main:build/resources/main:build/lib/*:build/libs/*" criminals.Application

Add this flag `--add-opens java.base/java.lang=ALL-UNNAMED` to reduce "illegal reflective access" warnings from `guice` (this will be fixed in a future version of `guice`).

The "illegal reflective access" warnings from `netty` are harder to fix: RePlay should upgrade it's `netty` dependency from `3.10.6.Final` to `io.netty:netty-all:4.1.43.Final` or greater.
 

### License and contributing

The code in this repository is [MIT](https://github.com/asolntsev/criminals/blob/master/LICENSE) licensed.

In case you want to improve upon the project: fork it, commit changes to your branch, open a pull request. Thanks in advance.

You can see build status of your pull requests at https://travis-ci.org/github/asolntsev/criminals 
