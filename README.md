# Flight Advisor

Test project for my new pozzition.<br />
`dist` folder contains latest build (if i didn't forget to copy the jar)<br />
`dataset` folder contains datasets for airports and routes to import

## To start the server

```bash
mvn clean package spring-boot:run
```
or to run precompiled build (no maven)

```bash
java -jar dist/flight-advisor-0.0.1-SNAPSHOT.jar
```
## Endpoints and flow

*Swagger can be found* [here](http://localhost:8080/swagger-ui/)

1. sign up<br />
```bash
curl --location --request POST 'http://localhost:8080/users/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username":"dusan",
    "password":"dusan"
}'
```
2. login<br />
```bash
curl --location --request POST 'http://localhost:8080/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username":"dusan",
    "password":"dusan"
}'
```
3. admin<br />
&nbsp;&nbsp;4. add cities<br />
&nbsp;&nbsp;5. import data<br />
6. user<br />
&nbsp;&nbsp;7. get all the cities (x comments)<br />
```bash
curl --location --request GET 'http://localhost:8080/api/cities/' \
--header 'Authorization: <token_from_login>'
```
&nbsp;&nbsp;8. search for the city name (x comments)<br />
```bash
curl --location --request GET 'http://localhost:8080/api/cities/search/San?limit=2' \
--header 'Authorization: <token_from_login>'
```
&nbsp;&nbsp;9. add/edit/delete a comment for the city<br />
```bash
curl --location --request POST 'http://localhost:8080/api/comments/' \
--header 'Authorization: <token_from_login>' \
--header 'Content-Type: application/json' \
--data-raw '{
    "cityId":"1382",
    "comment":"my comment on Paris"
}'
```
```bash
curl --location --request PATCH 'http://localhost:8080/api/comments/2' \
--header 'Authorization: <token_from_login>' \
--header 'Content-Type: application/json' \
--data-raw '{
    "comment":"The loveliness of Paris seems somehow sadly gay"
}'
```
```bash
curl --location --request DELETE 'http://localhost:8080/api/comments/2' \
--header 'Authorization: <token_from_login>' \
--header 'Content-Type: application/json' \
--data-raw '{
    "comment":""
}'
```
&nbsp;&nbsp;10. search for the cheapest path from A to B <br />
```bash
curl --location --request GET 'http://localhost:8080/api/routes/search/Belgrade/Punta Arenas' \
--header 'Authorization: <token_from_auth>'
```
## Example datasets 

### Airports
At the moment, datasets require headers.<br /> For airport data, it's:<br />

```bash
airportId,name,city,country,iata,icao,latitude,longitude,altitude,timezone,dst,tz,type,source
```

```bash
1,"Goroka Airport","Goroka","Papua New Guinea","GKA","AYGA",-6.081689834590001,145.391998291,5282,10,"U","Pacific/Port_Moresby","airport","OurAirports"
2,"Madang Airport","Madang","Papua New Guinea","MAG","AYMD",-5.20707988739,145.789001465,20,10,"U","Pacific/Port_Moresby","airport","OurAirports"
3,"Mount Hagen Kagamuga Airport","Mount Hagen","Papua New Guinea","HGU","AYMH",-5.826789855957031,144.29600524902344,5388,10,"U","Pacific/Port_Moresby","airport","OurAirports"
4,"Nadzab Airport","Nadzab","Papua New Guinea","LAE","AYNZ",-6.569803,146.725977,239,10,"U","Pacific/Port_Moresby","airport","OurAirports"
5,"Port Moresby Jacksons International Airport","Port Moresby","Papua New Guinea","POM","AYPY",-9.443380355834961,147.22000122070312,146,10,"U","Pacific/Port_Moresby","airport","OurAirports"
```
### Routes
Header for routes:<br />

```bash
airline,airlineId,sourceAirport,sourceAirportId,destinationAirport,destinationAirportId,codeshare,stops,equipment
```

```bash
2B,410,AER,2965,KZN,2990,,0,CR2
2B,410,ASF,2966,KZN,2990,,0,CR2
2B,410,ASF,2966,MRV,2962,,0,CR2
2B,410,CEK,2968,KZN,2990,,0,CR2
2B,410,CEK,2968,OVB,4078,,0,CR2
```
### City validation request param
validation during import of airports and routes on the existance of the city record can be turned off by passing query param `validateCity=false`
