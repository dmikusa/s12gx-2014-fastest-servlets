#!/bin/bash
CATALINA_BASE="$(cd "$1" && pwd)"
CMD="$2"
ROOT="$(dirname "$(dirname "$CATALINA_BASE")")"
echo "Root [$ROOT]"

# install Tomcat, if not present
cd "$ROOT"
CATALINA_HOME=$(find . -maxdepth 1 -type d -name "apache-tomcat-8.0.*" | head -n 1)
if [ "$CATALINA_HOME" == "" ]; then
    echo "Tomcat does not seem to be installed."
    ./scripts/download-tomcat.sh
    CATALINA_HOME=$(find . -maxdepth 1 -type d -name "apache-tomcat-8.0.*" | head -n 1)
fi

# start Tomcat
cd "$CATALINA_HOME/bin/"
CATALINA_BASE="$CATALINA_BASE" "./$CMD.sh"
