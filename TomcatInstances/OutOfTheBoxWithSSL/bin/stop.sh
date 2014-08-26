#!/bin/bash
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
CATALINA_BASE="$(dirname "$DIR")"
CATALINA_HOME="$(dirname "$(dirname "$CATALINA_BASE")")/apache-tomcat-8.0.9"
cd "$CATALINA_HOME/bin/"
CATALINA_BASE="$CATALINA_BASE" ./shutdown.sh
