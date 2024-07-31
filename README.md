# magazine-loadtest-script

## description

This is a gatling load test script for a magazine website.


## requirements
- Java version: 15.0.2 or above
- maven: 3.8.1 or above
- scala: 2.13.4


## structure

The project is organized into several folders, each divided by type:
 - **./utils** - static parameters, additional functions, and data generation scripts
 - **./requests** - all request definitions
 - **./scenarios** - different testing scenarios 
 - **./simulations** - all types of simulations (debug, regress, stress, etc.).

## covered functionality
**login/logout**:
 - requests in  `./requests/MagazineUserRequests.scala`
 - scenario in  `./scenarios/login/LoginScenario.scala` and `./scenarios/login/LogoutScenario.scala`

**add article**:
- requests in  `./requests/MagazineContentRequests.scala`
- scenario in  `./scenarios/content/AddContentScenario.scala`

**common scenario** - contains both functionalities: `./scenarios/RegressScenario.scala`


## run
To run a simulation, choose a simulation name and set it in the `-Dgatling.simulationClass` variable:

to run **regress** simulation:
```bash
mvn gatling:test -Dgatling.simulationClass=simulations.LoadtestSimulation
```
to run **debug** simulation:
```bash
mvn gatling:test -Dgatling.simulationClass=simulations.DebugSimulation
```

## data generation
The function `UsersGeneration.generate()` can generate users in the database with the default password: `Yfuheprf20!(`.

The SQL request is located at: `./resources/sql_users_generation.sql`

## flow

1. generate user data in the database (tables: users_field_data,user__roles,users)
2. select these users for `.feed(users)` in RegressScenario
3. select tags for articles from the database and implement them with `.feed(tags)` in RegressScenario
4. log in
5. create an article
6. log out



## TODO improvements


- extracting important parameters (like different tokens) from large HTML response bodies using regex is inefficient.
  `This approach can consume a lot of CPU`. Consider alternative methods to retrieve these parameters.
- delete created articles from the database before running the test to ensure that the same amount of data is present in the database before each test.
- generate articles in the database before running the test.
- it can be added more checks I guess.. but im feeling wrong to use heavy regexp for huge body.
