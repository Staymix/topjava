GET
'curl http://localhost:8080/topjava/rest/meals/100009'

DELETE
'curl -X DELETE http://localhost:8080/topjava/rest/meals/100009'

GetAll
'curl http://localhost:8080/topjava/rest/meals'

Create
'curl -X POST -H 'Content-Type: application/json' -d '{"dateTime":"2020-02-01T10:00:00","description":"new","calories":555}' http://localhost:8080/topjava/rest/meals'

Update
'curl -X PUT -H 'Content-Type: application/json' -d '{"dateTime":"2020-01-31T20:30:00","description":"update","calories":750}' http://localhost:8080/topjava/rest/meals/100008'

Filter
'curl "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-31&startTime=10:00:00&endDate=2020-01-31&endTime=22:00:00"'