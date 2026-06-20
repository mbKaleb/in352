#!/usr/bin/env bash
# need to chmod +x build
# compile all sources from section-a and section-b into build/, then run Main

set -e
# always run from unit-1 regardless of cwd
cd "$(dirname "$0")"   

OUT=build
echo "Compiling -> $OUT/"
javac -d "$OUT" section-a/*.java section-b/*.java

echo "Running Main:"
echo "---"
java -cp "$OUT" Main