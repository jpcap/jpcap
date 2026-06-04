#!/bin/sh
# ----------------------------------------------------------------------------
# Minimal Maven wrapper.
# Downloads and uses Apache Maven on first run (cached in ~/.m2/wrapper).
# ----------------------------------------------------------------------------
set -e

BASE_DIR="$(cd "$(dirname "$0")" && pwd)"
WRAPPER_PROPS="$BASE_DIR/.mvn/wrapper/maven-wrapper.properties"

if [ ! -f "$WRAPPER_PROPS" ]; then
  echo "mvnw: missing $WRAPPER_PROPS" >&2
  exit 1
fi

DIST_URL=$(grep '^distributionUrl=' "$WRAPPER_PROPS" | cut -d= -f2-)
DIST_DIR="$HOME/.m2/wrapper/dists"
DIST_NAME=$(basename "$DIST_URL" .zip)
DIST_ROOT="$DIST_DIR/$DIST_NAME"

if ! ls "$DIST_ROOT"/*/bin/mvn >/dev/null 2>&1; then
  mkdir -p "$DIST_DIR"
  ZIP="$DIST_DIR/$DIST_NAME.zip"
  if [ ! -f "$ZIP" ]; then
    echo "mvnw: downloading $DIST_URL"
    if command -v curl >/dev/null 2>&1; then
      curl -fsSL -o "$ZIP" "$DIST_URL"
    elif command -v wget >/dev/null 2>&1; then
      wget -q -O "$ZIP" "$DIST_URL"
    else
      echo "mvnw: need curl or wget on PATH" >&2
      exit 1
    fi
  fi
  rm -rf "$DIST_ROOT"
  mkdir -p "$DIST_ROOT"
  unzip -q -o "$ZIP" -d "$DIST_ROOT"
fi

MAVEN_HOME=$(ls -d "$DIST_ROOT"/apache-maven-*/ 2>/dev/null | head -1)
MAVEN_HOME=${MAVEN_HOME%/}
chmod +x "$MAVEN_HOME/bin/mvn"

# pick up java from PATH (caller may have set JAVA_HOME)
if [ -z "$JAVA_HOME" ] && [ -x /usr/bin/java ]; then
  : # mvn handles missing JAVA_HOME by using `java` on PATH
fi

exec "$MAVEN_HOME/bin/mvn" "$@"
