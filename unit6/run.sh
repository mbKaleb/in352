#!/bin/bash

echo "Compiling..."
javac UserStore.java LoginServer.java SignUpForm.java SignInForm.java
if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
fi

echo "Starting login server..."
java LoginServer &
SERVER_PID=$!
sleep 1

echo "Opening Sign In form..."
java SignInForm

kill $SERVER_PID
