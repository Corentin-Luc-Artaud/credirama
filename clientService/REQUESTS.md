- run prepare.sh file at the root of the repository in order to 
build artifacts and starts containers

Request to add client :
- POST http://localhost:8080/clients/
- JSON body example :
```
{
  "firstName": "Sami",
  "lastName": "Lazrak",
  "city": "Nice",
  "height": "175",
  "weight": "70"
}
```
- request to add transaction :
```
{
  "isClient": 1,
  "idAccount": 1,
  "difference": 100
}
```