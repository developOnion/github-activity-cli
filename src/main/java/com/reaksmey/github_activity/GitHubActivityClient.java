package com.reaksmey.github_activity;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GitHubActivityClient {

	private final String BASE_URL = "https://api.github.com/users/%s/events?per_page=%d&page=%d";
	private final HttpClient httpClient;

	public GitHubActivityClient() {
		httpClient = HttpClient.newHttpClient();
	}

	public String getUserActivity(final String username) {
		// Default to 30 events per page
		final int eventsPerPage = 30;
		final int currentPage = 1;
		return getUserActivity(username, eventsPerPage, currentPage);
	}

	public String getUserActivity(final String username, final int eventsPerPage, final int page) {

		final String url = String.format(BASE_URL, username, eventsPerPage, page);

		try {
			HttpRequest request = HttpRequest.newBuilder()
				.uri(new URI(url))
				.GET()
				.build();
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 200) {
				return response.body();
			} else {
				System.err.println("Failed to fetch data: " + response.statusCode());
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
