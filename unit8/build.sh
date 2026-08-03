#!/bin/bash

echo "Compiling Java sources..."
javac -d build src/*.java

if [ $? -ne 0 ]; then
    echo "Build failed."
    exit 1
fi

echo "Build successful."
echo ""
echo "Run with:"
echo "  java -cp build GpsDistanceService"
echo "  java -cp build RouteOptimizerService"
echo "  java -cp build RealTimeTrackingService"
echo "  java -cp build Test"
