# TaskApp 
 
Task tracker built for IN352 Unit 10. Spring Boot backend, Thymeleaf frontend. 
 
## What it does 

- Register/login (Spring Security)
- Create, edit, delete tasks (title, description, due date, priority)
- Dashboard with filtering by status/priority and pagination   
- Form validation, error messages, and logging
- Pulls current weather and date/time from public APIs to show on the dashboard 

## Setup 

**Requirements:** Java 17+ and Maven (or just use the included `mvnw` wrapper, no install needed). 


1. Clone/unzip the repo and `cd app`. 
2. Copy the config template:
   ```
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```
3. Sign up for a free weather API key at [weatherapi.com](https://www.weatherapi.com/) (takes about a minute, no credit card), then set it in `application.properties`:
   ```
   weather.api.key=your_key_here
   ```
4. No database setup needed. It uses a file-based H2 database that gets created automatically at `data/taskdb` the first time you run the app.
5. Default port is 8080. If something else is already using it, change `server.port` in `application.properties`.

## Running it

```bash
./run.sh
```

or just

```bash
./mvnw spring-boot:run
``` 

Then go to http://localhost:8080.   

## APIs used 

- [WeatherAPI](https://www.weatherapi.com/): current weather on the dashboard
- [TimeAPI.io](https://timeapi.io/): timestamps tasks with the current date/time

## Project structure

```
src/main/java/com/example/taskmanager/ 
├── controller/
├── service/
├── model/
└── repository/ 
src/main/resources/
├── templates/
├── static/
└── application.properties
```
 