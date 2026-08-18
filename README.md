# BrowserStack AI Agents — Demo Suite

A collection of runnable demos that showcase BrowserStack's AI-powered testing agents with Selenium + TestNG (Java) test suites executed on the BrowserStack cloud grid via the [BrowserStack Java SDK](https://www.browserstack.com/docs/automate/selenium/java).

Each demo is self-contained, shares a single set of BrowserStack credentials, and can be launched with a one-line shell script.

## Demos

| Demo | Directory | AI capability | What it shows |
| --- | --- | --- | --- |
| **NL Automation Agent** | `nl-automation-demo/` | `aiAuthoring: true` | Drives a browser using **plain-English instructions** (e.g. "Click on the Sign In button") through the `browserstack_executor: {"action": "ai"}` hook against [bstackdemo.com](https://bstackdemo.com/). |
| **Self-Heal Agent** | `self-heal-demo/` | `selfHeal: true` | Runs the same test twice against [selfheal-demo-app](https://browserstack.github.io/selfheal-demo-app) — once against the stable DOM, once after locators (id, XPath, class name, button text) have been mutated — so you can see BrowserStack **auto-repair broken locators** instead of failing. |
| **Smart Test Selection Agent** | `test-selection-demo/` | `testOrchestrationOptions.runSmartSelection` | Clones a React app repo and a 30+ test TestNG suite, then runs the suite **twice — with and without Smart Test Selection** — so you can compare execution time and the set of tests BrowserStack chose to run for a given code change. |

## Prerequisites

- **Java 17+** (the NL demo compiles at release 17; `self-heal-demo` targets 1.8 but builds fine on newer JDKs)
- **Apache Maven 3.6+**
- **Node.js + npm** — required only for the Smart Test Selection demo's app repo
- **Git** — required only for the Smart Test Selection demo (it clones two upstream repos)
- A **BrowserStack account** with Automate access ([sign up free](https://www.browserstack.com/users/sign_up))

Verified locally with OpenJDK 22 and Maven 3.9.11.

## Setup

Copy the credential template and fill in your BrowserStack details:

```sh
cp .env.example .env
```

```dotenv
BROWSERSTACK_USERNAME=your_username_here
BROWSERSTACK_ACCESS_KEY=your_access_key_here
```

Find both values under **Account → Settings → Automate** in the BrowserStack dashboard.

> `.env` is gitignored — only `.env.example` is tracked. Never commit real credentials.

## Running the demos

### 🤖 NL Automation Demo

Runs Selenium/TestNG tests on BrowserStack using natural language-driven automation.

```sh
# From project root
./run-nl.sh

# Or from the subfolder
cd nl-automation-demo && ./run.sh
```

**Stack:** Java · Maven · TestNG · BrowserStack Automate

---

### 🔧 Self-Heal Demo

Demonstrates BrowserStack's AI self-healing selector capability — tests automatically recover from broken locators.

```sh
# From project root
./run-selfheal.sh

# Or from the subfolder
cd self-heal-demo && ./run.sh
```

**Stack:** Java · Maven · Selenium · TestNG · BrowserStack Automate

---

### 🎯 Smart Test Selection Demo

Shows BrowserStack's AI-powered test selection — runs a baseline build, then an optimised build with smart selection enabled, and compares time savings.

```sh
# From project root
./run-testselection.sh

# Or from the subfolder
cd test-selection-demo && ./run.sh
```

**Stack:** Java · Maven · TestNG · React/Vite (demo app) · BrowserStack Automate

---

Every script sources `.env`, exports the credentials, and invokes `mvn test`. If `.env` is missing the script exits with an explanatory error rather than failing mid-build.

Once a run starts, watch it live (video, network logs, AI healing/authoring annotations) on the [BrowserStack Automate dashboard](https://automate.browserstack.com/), and view analytics in [Test Observability](https://observability.browserstack.com/).

## Project structure

```
.
├── .env.example                  # Credential template (copy to .env)
├── run-nl.sh                     # Root launcher: NL Automation demo
├── run-selfheal.sh               # Root launcher: Self-Heal demo
├── run-testselection.sh          # Root launcher: Smart Test Selection demo
│
├── nl-automation-demo/
│   ├── browserstack.yml          # Windows 11 / Chrome, aiAuthoring + testObservability
│   ├── pom.xml                   # Selenium 4.27, TestNG 7.10, BrowserStack SDK (LATEST)
│   ├── testng.xml                # BStackDemoSuite
│   └── src/test/java/com/demo/BStackDemoTest.java
│
├── self-heal-demo/
│   ├── browserstack.yml          # Windows 11 / Edge, selfHeal: true
│   ├── pom.xml                   # Selenium 4.14, TestNG 7.9, BrowserStack SDK 1.59.3
│   ├── JenkinsFile               # Sample Jenkins pipeline with BrowserStack reporting
│   └── src/test/java/org/browserstack/selfheal/SelfHealTest.java
│
└── test-selection-demo/
    ├── run.sh                    # Clones + builds both repos, runs baseline vs. optimized
    ├── test-selection-demo-app-browserstack/   # React/Vite/TS demo app (cloned, branch demo_app_v2)
    └── test-selection-demo-test-browserstack/  # TestNG suite (cloned, branch testng-automate)
```

## How it works

The BrowserStack Java SDK is attached as a JVM agent via the Surefire `argLine` in each `pom.xml`:

```xml
<argLine>-javaagent:${com.browserstack:browserstack-java-sdk:jar}</argLine>
```

This means the tests are written as ordinary local Selenium tests — the agent transparently redirects sessions to the BrowserStack grid and applies the capabilities declared in `browserstack.yml`. That's why `SelfHealTest` can construct a `RemoteWebDriver` pointing at `127.0.0.1:4723` and still execute in the cloud.

Per-demo AI configuration lives entirely in `browserstack.yml`:

- `aiAuthoring: true` — enables natural-language step execution
- `selfHeal: true` — enables locator auto-repair
- `testOrchestrationOptions.runSmartSelection` — enables change-aware test selection (`mode: relevantOnly` plus a `source` path pointing at the application repo)

The Smart Selection `source` is declared as a **relative path** (`../test-selection-demo-app-browserstack`). Maven executes from inside the test repo and the app repo is cloned as a sibling directory, so the path resolves correctly on any machine with no per-user configuration.

## Notes and known gotchas

- **`browserstack.yml` credential style differs between demos.** `nl-automation-demo` uses `${BROWSERSTACK_USERNAME}` interpolation; `self-heal-demo` and the test-selection suite use the SDK's bare `BROWSERSTACK_USERNAME` placeholder convention. Both resolve from environment variables — leave them as-is.
- **Smart Test Selection depends on the sibling-directory layout.** The `source` path in `test-selection-demo/test-selection-demo-test-browserstack/browserstack.yml` is relative (`../test-selection-demo-app-browserstack`). It resolves automatically as long as both repos stay siblings under `test-selection-demo/` — if you relocate the app repo, update that path to match.
- **`run-testselection.sh` tolerates test failures by design.** Both builds are wrapped with `|| echo ...`; skipped and failing tests are part of the demo narrative, so compare the two builds in the dashboard rather than relying on the exit status.
- **The test-selection repos are cloned at fixed branches** (`demo_app_v2` and `testng-automate`) and are skipped if the directories already exist. Delete them to force a fresh clone.
- **`log/` directories** contain SDK and automation logs from previous runs and are gitignored.

## Troubleshooting

| Symptom | Fix |
| --- | --- |
| `ERROR: .env file not found` | Run `cp .env.example .env` and add your credentials. |
| `Authorization failed` / 401 from BrowserStack | Re-check the username and access key; ensure no trailing whitespace or quotes in `.env`. |
| Sessions never appear on the dashboard | Confirm the SDK `-javaagent` flag is intact in `pom.xml` and that Maven resolved `browserstack-java-sdk`. |
| Local/staging site under test is unreachable | Set `browserstackLocal: true` in the relevant `browserstack.yml`. |
| Smart Selection runs the whole suite | Confirm the app repo exists at `test-selection-demo/test-selection-demo-app-browserstack` (the relative `source` path depends on it) and that it has changes to analyze. |

## References

- [BrowserStack Automate — Java + Selenium](https://www.browserstack.com/docs/automate/selenium?fw-lang=java%2Ftestng)
- [Self-Healing tests](https://www.browserstack.com/docs/automate/selenium/self-healing)
- [Smart Test Selection](https://www.browserstack.com/docs/automate/selenium/smart-test-selection)
- [Test Observability](https://www.browserstack.com/docs/test-observability)

## License

MIT.
