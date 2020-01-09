## Cyclism

**Introduction**

This application is small and artificial, but the goal here is to show a things around
**Kotlin, Spring Functional Web Framework, WebFlux, ReactiveMongoDB, WebTestClient**


**Prerequisites :** Java 12,  Docker

* [Start](#start)
* [Help](#help)

#### Start

Retrieve repository
```
git clone https://github.com/hichmo/cyclism.git
```
Start the mongodb container
```
cd cyclism/docker
docker-compose up -d
```

Build the application
```
cd cyclism
./mvnw clean install
```
Run the application
```
java -jar target/cyclism.jar
```
Access

curl -v http://localhost:8080/cyclists

#### Help
Please post any questions or comments by email at hichem.mouatadiri@gmail.com

TEST
