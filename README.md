# spring-modularizer [![Build Status](https://travis-ci.org/choonchernlim/spring-modularizer.svg?branch=master)](https://travis-ci.org/choonchernlim/spring-modularizer) [![codecov](https://codecov.io/gh/choonchernlim/spring-modularizer/branch/master/graph/badge.svg)](https://codecov.io/gh/choonchernlim/spring-modularizer)

A design pattern to transform an input object into a modularized result object based on the requested data module(s).

## Maven Dependency

```xml
<dependency>
    <groupId>com.github.choonchernlim</groupId>
    <artifactId>spring-modularizer</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Use Case

Given a scenario where the user requests module A and module C, the generated result object will contain just the requested modules.

The generated fingerprints allow the user to quickly determine whether the values have changed.

```
     RAW DATA                       TRANSFORMATION                           RESULT
                                                             
ORM entity, ID, etc.           Either cherry-picking from            Result object that can be 
                               input object or query against         sent back to the user either
                               data source(s) to construct           as object, JSON or XML.
                               module objects.                 
                                                             
                                                             
                                                                     {
                                                                        "id": "<some-id>",
                                                                        "fingerprint": "<some-fingerprint>"
                                                                        "a": {
                                                                            "fingerprint": "<some-fingerprint>",
                                   ✓ [ Mapper A   ]                         "prop-1": ...
                                     [ Mapper B   ]                         "prop-2": ...
    Input Object       --->        ✓ [ Mapper C   ]           --->      },
                                     [ Mapper D   ]                     "b": null,
                                     [ Mapper ... ]                     "c": {
                                     [ Mapper Z   ]                         "fingerprint": "<some-fingerprint>",
                                                                            "prop-3": ...
                                                                            "prop-4": ...
                                                                        },
                                                                        "d": null,
                                                                        ...
                                                                     }
```

At high level, the code looks something like this:- 

```groovy
Set<SomeModule> selectedModules = [ SomeModule.A, SomeModule.C ]

SomeEntity entity = // query from DB, etc

// autowired `modularizerService`
SomeResult result = modularizerService.create(entity, selectedModules, SomeResult)
```
    
## Example

The best way to understand this API is to run `Main.groovy` under [`example` package](src/test/groovy/example) 
and play around with it.