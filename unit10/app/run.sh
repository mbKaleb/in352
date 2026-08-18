#!/bin/bash

./mvnw spring-boot:run &

until curl -s -o /dev/null http://localhost:8080; do
  sleep 1
done

open http://localhost:8080
