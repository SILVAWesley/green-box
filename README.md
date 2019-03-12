[![Java: version](https://img.shields.io/badge/JAVA-%3E%3D%207-blue.svg)](https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

# Green Box

 Green Box - Your File on the Cloud! is a web application for remote file storage. 

> Course project for the discipline *Information Systems 1*, U.F.C.G. Computer Science program.

## Prerequisites

* `Java >= 7`
* Maven

## Execution
- To start up the server run the class *GreenBoxApplication.java* : `green-box/src/main/java/org/ufcg/si/GreenBoxApplication.java`
- To access the application homepage via localhost go to [localhost:8080](http://localhost:8080/);

**Observations**

- Some funcionalities demand double refresh to appear on the front-end;


**Data restrictions**
- `Username must have between 4 and 15 characters`
  -  `Username must be made of letters, numbers and - or _`
- `Email must be valid`
- `Password must have at least 6 characters, at least one letter and a number`
- `Directory and file names must have at least one character` 
  - `Directory and file names must be made of letters, numbers and _ or spaces.`

## Building from source

1. Ensure you have 

   ```java``` installed - goto http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html to download installer for your OS.    
   ```maven``` installed - goto https://maven.apache.org/download.cgi to download latest version of maven.

1. Clone this repository to your local filesystem (default branch is 'master')

1. To download the dependencies
   ```
    mvn install
   ```

1. To run the application, run the following command on the project root folder

   ```
    mvn spring-boot:run
   ```

   Note: If you see a warning similar to the one shown below on running `Disabling contextual LOB creation as createClob() method threw error`, see this question https://stackoverflow.com/questions/4588755/disabling-contextual-lob-creation-as-createclob-method-threw-error

   ```
    java.lang.reflect.InvocationTargetException: null
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:na]
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
        at java.base/java.lang.reflect.Method.invoke(Method.java:564) ~[na:na]
        at org.hibernate.engine.jdbc.env.internal.LobCreatorBuilderImpl.useContextualLobCreation(LobCreatorBuilderImpl.java:113) [hibernate-core-5.2.17.Final.jar:5.2.17.Final]
        at org.hibernate.engine.jdbc.env.internal.LobCreatorBuilderImpl.makeLobCreatorBuilder(LobCreatorBuilderImpl.java:54) [hibernate-core-5.2.17.Final.jar:5.2.17.Final]
        at org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentImpl.<init>(JdbcEnvironmentImpl.java:271) [hibernate-core-5.2.17.Final.jar:5.2.17.Final]
   ```
## Running maven tasks

### Running the tests

You just need to run the following command:

`mvn test`

## Demo
 To access a live **demo** deployment go to https://green-box-si1.herokuapp.com

## Authors

* **Benardi Nunes** - *Testing and Back-end* - [Benardi](https://github.com/Benardi)
* **Iaron da Costa Araújo** - *Back-end* - [iaronaraujo](https://github.com/iaronaraujo)
* **Natan Macena Ribeiro** - *Front-end* - [Natan7](https://github.com/Natan7)
* **RaviLeite** - *Back-end* - [ravileite](https://github.com/ravileite)
* **Ronan Souza** - *Front-end* - [RonnanSouza](https://github.com/RonnanSouza)
* **Wesley Silva** - *Front-end Back-end* - [SILVAWesley](https://github.com/SILVAWesley)

See also the list of [contributors](https://github.com/the-green-box/green-box/contributors) who participated in this project.

## License

This project is licensed under the GPL v3 License - see the [LICENSE.md](LICENSE.md) file for details

## Further information

**Link to the files with project decisions:**
- [Parte 1](https://docs.google.com/a/ccc.ufcg.edu.br/document/d/1UiMmcIcAxkNDWNESD-hiz2iYHh934GrmqlwTLSeR4oA/edit?usp=sharing).
- [Parte 2](https://docs.google.com/a/ccc.ufcg.edu.br/document/d/1wrVjfn9iEbF9c0AOgRZd7funolg8Pzj761QL75_xqao/edit?usp=sharing).
- [Parte 3](https://docs.google.com/a/ccc.ufcg.edu.br/document/d/1ZT_eIiIiDkF6JBpxURnH-kjoP4PFzgvg0VwJg5RYqD4/edit?usp=sharing).
