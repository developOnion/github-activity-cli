# GitHub Activity CLI

A command-line tool to fetch and display recent GitHub activity for any user, built with Java.

## Features

- Fetches recent GitHub public events for any user
- Displays activity in a human-readable format
- Supports multiple event types:
  - Push events (with commit count)
  - Issues (opened/updated)
  - Pull requests (opened/updated)
  - Stars, forks, creates, deletes, comments

## Requirements

- Java 21+
- Maven 3+

## Installation

**1. Clone the repository**
```bash
git clone https://github.com/your-username/github-activity.git
cd github-activity
```

**2. Build the JAR**
```bash
mvn package
```

**3. (Optional) Create a shell script for easier usage**

Create a file called `github-activity` in `/usr/local/bin/`:
```bash
#!/bin/bash
java -jar /path/to/target/github-activity-1.0-SNAPSHOT.jar "$@"
```

Make it executable:
```bash
chmod +x /usr/local/bin/github-activity
```

## Usage

```bash
java -jar target/github-activity-1.0-SNAPSHOT.jar <username>
```

Or if you set up the shell script:
```bash
github-activity <username>
```

### Example

```bash
github-activity kamranahmedse
```

### Example Output

```
GitHub Activity for user: kamranahmedse
- Pushed 3 commits to kamranahmedse/developer-roadmap at 2026-03-05 10:22:11
- Opened an issue in kamranahmedse/developer-roadmap at 2026-03-05 09:15:43
- Starred kamranahmedse/developer-roadmap at 2026-03-04 22:10:05
```

## Project Structure

```
src/
└── main/
    └── java/
        └── com/reaksmey/github_activity/
            ├── Application.java          # Entry point, CLI handling, output formatting
            ├── GitHubActivityClient.java # HTTP client for GitHub API
            └── GitHubEvent.java          # Event model
```

## Dependencies

| Dependency | Version | Purpose |
|---|---|---|
| [Jackson Databind](https://github.com/FasterXML/jackson) | 3.1.0 | JSON parsing |
| [Lombok](https://projectlombok.org/) | 1.18.42 | Boilerplate reduction |

## GitHub API

This project uses the [GitHub Events API](https://docs.github.com/en/rest/activity/events):

```
GET https://api.github.com/users/{username}/events
```

> **Note:** The API is unauthenticated and limited to **60 requests per hour** per IP. Returns up to the last **90 days** of public activity.
