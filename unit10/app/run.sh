#!/bin/bash

./mvnw spring-boot:run &
PID=$!

until curl -s -o /dev/null http://localhost:8080 || ! kill -0 "$PID" 2>/dev/null; do
  sleep 1
done

if ! kill -0 "$PID" 2>/dev/null; then
  echo "App failed to start - see the Maven output above for the error." >&2
  exit 1
fi

open http://localhost:8080
