# Recommender Engine - Web Service Component

## Description
This repository contains the web service (RESTful API) component of the recommender engine. The component is written using Spring Boot.

The API mainly uses two HTTP methods: `POST` and `GET`. `POST` method is for users to make reviews with score on a product. `GET` method is used for retrieving the user specific recommendations from MongoDB. If no entry for user is found in MongoDB, default list of recommendations is returned.

## Usage
### Run
This component cannot be run by itself. In order to run this component, all of the project components must be run using `docker-compose`. See [Recommender Engine - Docker Files](https://github.com/trendyol-data-eng-summer-intern-2019/recom-engine-docker)

### Making a POST Request
New review can be posted to the system by invoking `POST` request to the URL **localhost:8080/users/{userId}/reviews**. For example, if you want to post a review for user with id 2, you should send a `POST` request to following URL path:
```
localhost:8080/users/{userId}/reviews
```

A request body of the following form must be specified for the `POST` request:
```
{
  'productId': [some_alphanumeric_id],
  'score': [some_float_number],
  'timestamp': [some_timestamp_greater_than_zero]
}
```

Following header must be specified:
```
Content-Type: application/json
```

You can use the following command to post a new review to the system:
```
curl -H 'Content-Type: application/json' -d '{
  "productId": "3",
  "score": 4.5,
  "timestamp": 10
}' localhost:8080/users/3/reviews
```

Output:
```
{"productId":"3","score":4.5,"timestamp":"1970-01-01T00:00:00.010+0000","userId":"3"}
```

You can install `jq` and beautify the output as follows:
```
curl -H 'Content-Type: application/json' -d '{
  "productId": "3",
  "score": 4.5,
  "timestamp": 10
}' localhost:8080/users/3/reviews | jq
```

Output:
```
{
  "productId": "3",
  "score": 4.5,
  "timestamp": "1970-01-01T00:00:00.010+0000",
  "userId": "3"
}
```

### Making a GET Request
User specific recommendations can be retrieved by invoking a `GET` request to the URL **localhost:8080/users/{userId}/recommendations**. User id of the user whom recommendations you want to retrieve is given in the URL path. For example, if you want to fetch the recommendations for the user with id 3, you should send a `GET` request to following URL path:
```
localhost:8080/users/3/recommendations
```

You can use the following command to retrieve recommendations for user with id 3:
```
curl local:8080/users/3/recommendations
```

Output:
```
{"_id":"3","recommendations":["100","101","102","103","104","105","106","107","108","109"]}
```

You can install `jq` and beautify the output as follows:
```
curl local:8080/users/3/recommendations | jq
```
Output:
```
{
  "_id": "3",
  "recommendations": [
    "100",
    "101",
    "102",
    "103",
    "104",
    "105",
    "106",
    "107",
    "108",
    "109"
  ]
}
```

## Notes
If you want to change the component's source code and insert it into the project, you can follow these steps:
- Clone docker repository: [Recommender Engine - Docker Files](https://github.com/trendyol-data-eng-summer-intern-2019/recom-engine-docker).
- Clone this repository, change the desired part of the source code.
- Run `sbt clean assembly` in the root directory of this repository.
- Move the created jar file `target/scala-2.11/recom-engine-streaming-assembly-0.1.jar` in this repo's root directory to `images/spring-boot/target` in docker repo's root directory.
- Run `docker-compose up` in the docker repo's root directory.

## Members
- [Sercan Ersoy](https://github.com/sercanersoy)
- [Yasin Uygun](https://github.com/yasinuygun)
- [Oğuzhan Bölükbaş](https://github.com/oguzhan-bolukbas)
