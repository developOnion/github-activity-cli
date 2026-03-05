package com.reaksmey.github_activity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import tools.jackson.databind.JsonNode;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubEvent {
	private String type;
	private JsonNode actor;
	private JsonNode repo;
	private JsonNode payload;
	private boolean isPublic;
	private String created_at;
}
