#!/bin/bash
set -e

echo "=== BrowserStack NL Automation Agent Test Runner ==="

cd nl-automation-demo

# Load credentials from .env
if [ -f "../.env" ]; then
  set -a; source "../.env"; set +a
else
  echo "ERROR: .env file not found. Copy .env.example to .env and fill in your credentials." >&2
  exit 1
fi

# Run tests on BrowserStack
mvn test