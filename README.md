# MyResumeAPi

This is my first springboot project.
It's the REST API of my future personal website.

## Dev Env
### Prerequisite
- JDK 11.0.7 
- Apache Maven 3.6.3 
- docker-ce
- docker-compose

### Instruction
 
1 - Clone this repository 
```
$ git clone https://github.com/BriceMichalski/myResumeApi.git
$ cd myResumeApi 
```

2 - Run docker-compose

```
$ docker-compose up -d
```

3 - Init Couchbase 
```
$  docker exec myresumeapi_couchbase_1 /initCouchbase.sh
```