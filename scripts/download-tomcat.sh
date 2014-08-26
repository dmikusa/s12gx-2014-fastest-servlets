#!/bin/bash
#!/bin/bash

# Lookup latest version
LATEST_VERSION=$(curl -s http://tomcat.apache.org/download-80.cgi | grep "<h3 id=\"8.0." | xpath '/h3/text()' 2>/dev/null)

# Find download URL
DOWNLOAD_URL=$(curl -s http://tomcat.apache.org/download-80.cgi | grep "apache-tomcat-$LATEST_VERSION.tar.gz" | grep ">tar.gz</a>" | xpath 'string(/a/@href)' 2>/dev/null)

# Download
echo -n "Downloading Tomcat version $LATEST_VERSION... "
curl -s -L -O "$DOWNLOAD_URL"

# Extract
tar zxf "apache-tomcat-$LATEST_VERSION.tar.gz"

# Clean Up
rm "apache-tomcat-$LATEST_VERSION.tar.gz"
echo "done!"
