#!/usr/bin/env bash

set -e
echo "=== BrowserStack Test Selection Agent Test Runner ==="

cd test-selection-demo

# ==============================================================================
# Configuration Variables - loaded from .env
# ==============================================================================
if [ -f "../.env" ]; then
  set -a; source "../.env"; set +a
else
  echo "ERROR: .env file not found. Copy .env.example to .env and fill in your credentials." >&2
  exit 1
fi

# Directory setup
BASE_DIR="$(pwd)"
APP_DIR="${BASE_DIR}/test-selection-demo-app-browserstack"
TEST_DIR="${BASE_DIR}/test-selection-demo-test-browserstack"

echo "======================================================================"
echo " Starting BrowserStack Smart Test Selection Automation Script"
echo "======================================================================"

mkdir -p "${BASE_DIR}"

# ------------------------------------------------------------------------------
# Step 1: Clone and Setup Application Repository
# ------------------------------------------------------------------------------
echo -e "\n[Step 1] Cloning and setting up App Repo..."
if [ ! -d "${APP_DIR}" ]; then
  git clone https://github.com/browserstack/test-selection-demo-app-browserstack.git "${APP_DIR}"
fi

cd "${APP_DIR}"
git checkout demo_app_v2
npm install

# ------------------------------------------------------------------------------
# Step 2: Clone and Setup Test Repository
# ------------------------------------------------------------------------------
echo -e "\n[Step 2] Cloning and setting up Test Repo..."
if [ ! -d "${TEST_DIR}" ]; then
  git clone https://github.com/browserstack/test-selection-demo-test-browserstack.git "${TEST_DIR}"
fi

cd "${TEST_DIR}"
git checkout testng-automate
mvn clean install -DskipTests

# ------------------------------------------------------------------------------
# Helper Function to Update browserstack.yml
# ------------------------------------------------------------------------------
update_browserstack_yml() {
  local selection_enabled=$1

  # Uses sed to update only the 'enabled:' line under 'runSmartSelection'
  if [[ "$OSTYPE" == "darwin"* ]]; then
    # macOS version of sed
    sed -i '' -E "/runSmartSelection:/,/enabled:/ s/(enabled:[[:space:]]*)(true|false)/\1${selection_enabled}/" browserstack.yml
  else
    # Linux version of sed
    sed -i -E "/runSmartSelection:/,/enabled:/ s/(enabled:[[:space:]]*)(true|false)/\1${selection_enabled}/" browserstack.yml
  fi
}

# ------------------------------------------------------------------------------
# Step 3 & 4: Configure Credentials & Run Baseline Build (Smart Selection Disabled)
# ------------------------------------------------------------------------------
echo -e "\n[Step 3 & 4] Running Build WITHOUT Smart Test Selection..."
update_browserstack_yml "false"

echo "Running baseline tests via Maven..."
mvn test || echo "Baseline tests finished with failures/skips (as expected in demo)."

# ------------------------------------------------------------------------------
# Step 5: Run Optimized Build (Smart Selection Enabled)
# ------------------------------------------------------------------------------
echo -e "\n[Step 5] Running Build WITH Smart Test Selection..."
update_browserstack_yml "true"

echo "Running Smart Selection tests via Maven..."
mvn test || echo "Smart Selection tests finished with failures/skips (as expected in demo)."

echo -e "\n======================================================================"
echo " Demo Run Complete!"
echo " Compare the two test executions to see time savings and filtered tests."
echo "======================================================================"