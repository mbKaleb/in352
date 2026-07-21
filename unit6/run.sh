#!/bin/bash

mkdir -p build

javac -d build UserStore.java LoginServer.java SignUpForm.java SignInForm.java RegistrationSummary.java
if [ $? -ne 0 ]; then
    exit 1
fi

java -cp build LoginServer &
SERVER_PID=$!
sleep 1

java -cp build SignInForm

kill $SERVER_PID
