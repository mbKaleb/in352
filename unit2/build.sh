#!/usr/bin/env bash
# need to chmod +x build
# compile all sources in parent folder (excluding build/) into build/, then run Main

set -e
cd "$(dirname "$0")"

OUT=build
echo "Compiling -> $OUT/"
mkdir -p "$OUT"
find . -path "./$OUT" -prune -o -name "*.java" -print | javac -d "$OUT" @/dev/stdin

echo "Running Main:"
echo "---"
java -cp "$OUT" Main