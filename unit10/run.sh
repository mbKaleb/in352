#!/bin/bash

./mvnw spring-boot:run &

sleep 5

open http://localhost:8080