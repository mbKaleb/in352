#!/bin/bash

echo "Building before deploy..."
./build.sh

if [ $? -ne 0 ]; then
    echo "Deploy aborted due to build failure."
    exit 1
fi

echo ""
echo "Packaging application..."
jar cfe deploy/Unit8App.jar Test -C build .

echo "Deploy complete: deploy/Unit8App.jar"
echo ""
echo "Run with:"
echo "  java -jar deploy/Unit8App.jar"
