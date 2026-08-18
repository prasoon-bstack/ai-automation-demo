#!/bin/bash
set -e

echo "=== BrowserStack NL Automation Agent Test Runner ==="

# Load credentials from root .env
ENV_FILE="$(dirname "$0")/../.env"
if [ -f "$ENV_FILE" ]; then
  set -a; source "$ENV_FILE"; set +a
else
  echo "ERROR: .env file not found at $ENV_FILE. Copy .env.example to .env and fill in your credentials." >&2
  exit 1
fi

# Run tests on BrowserStack
cd "$(dirname "$0")"
mvn test