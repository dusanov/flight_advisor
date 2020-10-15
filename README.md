# Flight Advisor

Test project for my new pozzition.
Dist folder contains latest build (if i didn't forget to copy the jar)

## To start the server

```bash
mvn clean package spring-boot:run
```
or to run precompiled build (no maven)

```bash
java -jar dist/flight-advisor-0.0.1-SNAPSHOT.jar
```
## Endpoints and flow
1. sign up<br />
2. login<br />
3. admin<br />
&nbsp;&nbsp;4. add cities<br />
&nbsp;&nbsp;5. import data<br />
6. user<br />
&nbsp;&nbsp;7. get all the cities (x comments)<br />
&nbsp;&nbsp;8. search for the city name (x comments)<br />
&nbsp;&nbsp;9. add/edit/delete(just a flag) a comment for the city (created / modified date)<br />
&nbsp;&nbsp;10. search for the cheapest path from A to B (multiple routes) <br />

## Example datasets 
### Airports
```bash
todo,todo,todooo
```
### Routes
```bash
todo,todo,todooo
```