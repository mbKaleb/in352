#!/bin/sh
# Compiles all Java sources into ./build and runs Main.
set -e

cd "$(dirname "$0")"
rm -rf build
mkdir build

javac -d build *.java
java -cp build Main
