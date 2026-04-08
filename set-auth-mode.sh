#!/usr/bin/env bash
set -e

MODE="${1:-none}"

case "$MODE" in
  none|basic|oauth2) ;;
  *)
    echo "Usage: ./run-demo.sh [none|basic|oauth2]"
    exit 1
    ;;
esac

./mvnw spring-boot:run -Dspring-boot.run.arguments="--app.auth.mode=$MODE"
