#!/bin/bash
set -e

echo "🔨 Building IN352 Unit 7..."

rm -rf build
mkdir -p build/WEB-INF/classes

echo "📝 Compiling Java..."
javac -d build/WEB-INF/classes src/com/unit7/demo/*.java

echo "📋 Copying resources..."
cp web/WEB-INF/web.xml build/WEB-INF/
cp web/*.jsp build/
cp web/*.xml build/

echo "📦 Packaging WAR..."
cd build
jar cf grades-app.war WEB-INF *.jsp *.xml
cd ..

echo "✅ Build complete → build/grades-app.war"