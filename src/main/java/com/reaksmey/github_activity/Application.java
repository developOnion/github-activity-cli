package com.reaksmey.github_activity;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Application {

	public static void main(String[] args) {

		if (args.length == 0) {
			System.out.println("Usage: github-activity <username>");
			System.exit(1);
		}
		String username = args[0];

		GitHubActivityClient client = new GitHubActivityClient();
		String jsonPayload = client.getUserActivity(username);

		if (jsonPayload != null) {
			ObjectMapper mapper = new ObjectMapper();
			List<GitHubEvent> events = mapper.readValue(jsonPayload, new TypeReference<List<GitHubEvent>>() {
			});
			System.out.println("GitHub Activity for user: " + username);
			printEventSummary(events);
		} else {
			System.out.println("No activity found for user: " + username);
		}
	}

	private static void printEventSummary(List<GitHubEvent> events) {

		events.forEach(event -> {
			String repo = event.getRepo() != null ? event.getRepo().path("name").asString("unknown") : "unknown";
			String formatted = switch (event.getType()) {
				case "PushEvent" -> "Pushed to " + repo;
				case "IssuesEvent" -> {
					String action = event.getPayload() != null ? event.getPayload().path("action").asString("unknown") : "unknown";
					yield (action.equals("opened") ? "Opened" : "Updated") + " an issue in " + repo;
				}
				case "WatchEvent" -> "Starred " + repo;
				case "ForkEvent" -> "Forked " + repo;
				case "CreateEvent" -> "Created a repository or branch in " + repo;
				case "PullRequestEvent" -> {
					String action = event.getPayload() != null ? event.getPayload().path("action").asString("unknown") : "unknown";
					yield (action.equals("opened") ? "Opened" : "Updated") + " a pull request in " + repo;
				}
				case "IssueCommentEvent" -> "Commented on an issue in " + repo;
				case "DeleteEvent" -> "Deleted a branch or tag in " + repo;
				default -> "Performed " + event.getType() + " in " + repo;
			};

			Instant createdAt = Instant.parse(event.getCreated_at());
			DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("yyyy-MM-dd HH:mm:ss")
				.withZone(java.time.ZoneId.systemDefault());
			String formattedTime = formatter.format(createdAt);

			System.out.println("- " + formatted + " at " + formattedTime);
		});
	}
}
