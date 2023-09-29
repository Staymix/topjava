# REST API - Example Requests
## 1. *Getting Information About a Meal by ID*
### HTTP GET
#### 'curl http://localhost:8080/topjava/rest/meals/100009'

## 2. *Deleting a Meal by ID*
### HTTP DELETE
#### 'curl -X DELETE http://localhost:8080/topjava/rest/meals/100009'

## 3. *Getting a List of All Meals*
### HTTP GET (Get All Meals)
#### 'curl http://localhost:8080/topjava/rest/meals'

## 4. *Creating a New Meal*
### HTTP POST
#### 'curl -X POST -H 'Content-Type: application/json' -d '{"dateTime":"2020-02-01T10:00:00","description":"new","calories":555}' http://localhost:8080/topjava/rest/meals'

## 5. *Updating a Meal*
### HTTP PUT
#### 'curl -X PUT -H 'Content-Type: application/json' -d '{"dateTime":"2020-01-31T20:30:00","description":"update","calories":750}' http://localhost:8080/topjava/rest/meals/100008'

## 6. *Getting a List of Meals in a Selected Date and Time Range*
### HTTP GET(Filtering by Date and Time)
#### 'curl "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-31&startTime=10:00:00&endDate=2020-01-31&endTime=22:00:00"'