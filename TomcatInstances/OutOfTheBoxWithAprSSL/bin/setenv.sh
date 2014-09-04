# You may need to configure this, depend on your OS and setup
#JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"

# You will need to configure this.  In fact, you will also need to compile the native library.
JAVA_OPTS="$JAVA_OPTS -Djava.library.path=$CATALINA_HOME/bin/tomcat-native-1.1.31-src/jni/native/.libs"
