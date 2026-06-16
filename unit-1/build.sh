#!/usr/bin/env bash
# compile all sources from section-a and section-b into build/, then run Main

set -e
cd "$(dirname "$0")"   # always run from unit-1 regardless of cwd

OUT=build
echo "Compiling -> $OUT/"
javac -d "$OUT" section-a/*.java section-b/*.java

echo "Running Main:"
echo "---"
java -cp "$OUT" Main