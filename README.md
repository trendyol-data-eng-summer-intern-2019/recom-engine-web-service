# Recommender Engine - Web Service Component

## Description
This repository contains the web service (RESTful API) component of the recommender engine. The component is written using Spring Boot.

The API mainly uses two HTTP methods: `POST` and `GET`. `POST` method is for users to make reviews with score on a product. `GET` method is used for retrieving the user specific recommendations from MongoDB. If no entry for user is found in MongoDB, default list of recommendations is returned.

## Usage
### Run
This component cannot be run by itself. In order to run this component, all of the project components must be run using `docker-compose`. See [Recommender Engine - Docker Files](https://github.com/trendyol-data-eng-summer-intern-2019/recom-engine-docker)

### Making POST Request


### Making GET Request
You can retrieve the user specific recommendations by invoking a `GET` request to the URL **localhost:8080/users/{userId}/recommendations**. User id of the user whom recommendations you want to retrieve is given in the URL path. For example, if you want to fetch the recommendations for the user with id 3, you should send a `GET` request to following URL path:
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


## Members
- [Sercan Ersoy]()
- [Yasin Uygun]()
- [Oğuzhan Bölükbaş]()
