# spring-modularizer [![Build Status](https://travis-ci.org/choonchernlim/spring-modularizer.svg?branch=master)](https://travis-ci.org/choonchernlim/spring-modularizer) [![codecov](https://codecov.io/gh/choonchernlim/spring-modularizer/branch/master/graph/badge.svg)](https://codecov.io/gh/choonchernlim/spring-modularizer)

Transforms an input object into a modularized result object based on the requested data module(s).

## Maven Dependency

```xml
<dependency>
    <groupId>com.github.choonchernlim</groupId>
    <artifactId>spring-modularizer</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Motivation

To provide an ability to enrich the result object by adding more data modules over time without introducing too many cross cutting concerns.



```text
---------------------------------------------------------------------------------------------------------
     RAW DATA                 TRANSFORMATION                        RESULT
---------------------------------------------------------------------------------------------------------
ORM entity, ID, etc.     Either cherry-picking from         Result object that can be sent back to the 
                         input object or query against      user either as object, JSON or XML.
                         data source(s) to construct        The fingerprints allow the user to quickly 
                         requested module(s).               determine whether the values have changed 
                                                            or not.                                                                   
                                                      
                                                      
                                                             {
                                                                "id": "<some-id>",
                                                                "fingerprint": "<some-fingerprint>"
                                                                "a": {
                                                                    "fingerprint": "<some-fingerprint>",
                              ✓ [ Mapper A   ]                      "prop-1": ...
                                [ Mapper B   ]                      "prop-2": ...
    Input Object    --->      ✓ [ Mapper C   ]        --->      },
                                [ Mapper D   ]                  "b": null,
                                [ Mapper ... ]                  "c": {
                                [ Mapper Z   ]                      "fingerprint": "<some-fingerprint>",
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
    
## Real World Example

Run `Main.groovy` under ["example" package](src/test/groovy/example) and play around with it.