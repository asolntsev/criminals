# criminals

This is an example of a [RePlay](https://github.com/codeborne/replay) project.

It may be used to understand the differences between a project based on [Play1](https://github.com/playframework/play1) and a project based on RePlay.

[![travis-ci](https://travis-ci.org/asolntsev/criminals.svg?branch=master)](https://travis-ci.org/asolntsev/criminals)

### Building

 * `./gradlew build`  --  to build the project
 * `./gradlew test`   --  to run unit-tests
 * `./gradlew uitest` -- to run UI tests

And you are good to go.


### Auto-compiling of code

You don't need to have any special tricks (like it was in Play1).

Every time when you make a change in Java/Groovy/Kotlin code, JVM tries to apply yur changes on-the-fly.  
In many cases it works immediately (until you change signatures of some methods).  

If hot redeploy failed, you will see a notification in IDEA, and then you need to restart the application.  


### Deploying

As a result of `./gradlew build` you will get file `build/libs/criminals.jar`.  
You just need to copy this file with all dependencies and start app like `java -cp ... criminals.Application` 
(like any other Java application).


### License and contributing

The code in this repository is [MIT](https://github.com/asolntsev/criminals/blob/master/LICENSE) licensed.

In case you want to improve upon the project: fork it, commit changes to your branch, open a pull request. Thanks in advance.

You can see build status of your pull requests at https://travis-ci.org/github/asolntsev/criminals 