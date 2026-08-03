#!/bin/bash
set -e

echo "building..."
./build.sh

# --- locate tomcat ---
TOMCAT_HOME=$(brew --prefix tomcat 2>/dev/null)/libexec

if [ ! -d "$TOMCAT_HOME/webapps" ]; then
    echo " Tomcat not found at: $TOMCAT_HOME"
    echo "   Try: ls /opt/homebrew/Cellar/tomcat/"
    exit 1
fi

echo "✅ Tomcat: $TOMCAT_HOME"

# --- stop if running ---
if pgrep -f "catalina.startup.Bootstrap" > /dev/null; then
    echo "Stopping Tomcat..."
    "$TOMCAT_HOME/bin/catalina.sh" stop || true
    sleep 3
fi

# --- clean old deploy ---
rm -rf "$TOMCAT_HOME/webapps/grades-app" "$TOMCAT_HOME/webapps/grades-app.war"

echo "Deploying..."
cp build/grades-app.war "$TOMCAT_HOME/webapps/"

echo "Starting Tomcat..."
"$TOMCAT_HOME/bin/catalina.sh" start
sleep 6

echo ""
echo "http://localhost:8080/grades-app/"
echo "Logs: $TOMCAT_HOME/logs/"
ls -la "$TOMCAT_HOME/logs/" 2>/dev/null || echo "   (no logs dir yet)"
echo ""
curl -s -o /dev/null -w "HTTP status: %{http_code}\n" http://localhost:8080/grades-app/