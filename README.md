
# DECIDE

[![Java CI with Gradle](https://github.com/KTH-Software-Engineering-DD2480/decide/actions/workflows/gradle.yml/badge.svg)](https://github.com/KTH-Software-Engineering-DD2480/decide/actions/workflows/gradle.yml)

> Group 17

This is an implementation of the DECIDE routine described [here](https://canvas.kth.se/courses/31884/files/4932282/download).


## Building from Source

This project uses Gradle to build. You can either choose to install it from [here](https://gradle.org/) or use one of the build scripts (`gradlew` on *nix or `gradlew.bat` on Windows) from the terminal.

### Compiling

```sh
gradle build
```

### Running the Test Suite

```sh
gradle test
```


# Contributions

We work by submitting and then merging Pull Requests (PRs). Every PR must address an open Issue. 

When creating a branch to work on an issue, create a new branch with the following name: `<descriptive-title>-#<issue-number>`. For example: `input-class-#5`, `point-math-#7`, etc.

Every commit must start with one of the following:

- `feat:` if a new feature was added
- `fix:` if a bug was fixed
- `doc:` if documentation was added
- `refactor:` if code was restructured/renamed
- `wip:` if unfinished work has to be commited temporarily (discouraged, consider squashing the commits afterward)


# Contributors

This project is a group effort by:

- Christofer Nolander [cnol@kth.se](mailto:cnol@kth.se)
    - LIC 4
    - `Point` class and methods
    - `Input` parser
    - CI and build system
    - This `README`
- Jiayi Guo [jiayig@kth.se](mailto:jiayig@kth.se)
    - LIC 5, 6, 7, and 11
    - Integration test 1
- Kunal Bhatnagar [kunalb@kth.se](mailto:kunalb@kth.se)
    - LIC 0, 2, 9, 12
    - Integration test 3
- Mark Bergrahm [bergrahm@kth.se](mailto:bergrahm@kth.se)
    - LIC 1, 8 and 13
    - Implementation of PUM
    - `main` function
- Philip Salqvist [phisal@kth.se](mailto:phisal@kth.se)
    - LIC 3, 10 and 14
    - Integration test 2

We also all participated in the items:

- Implementation and testing of FUV and LAUNCH

## Way of Working

[https://docs.google.com/document/d/1WA-QcM7Koo8VzZklwBr6dFwLt58cqgjsHH9NLUDth1o/edit#](https://docs.google.com/document/d/1WA-QcM7Koo8VzZklwBr6dFwLt58cqgjsHH9NLUDth1o/edit#)