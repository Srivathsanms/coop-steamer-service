package no.coop.task.streamer.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorProfile {

    private String id;
    private String username;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("bot")
    private boolean isBot;
    @JsonProperty("created_at")
    private String createdAt;
    private String url;
    @JsonProperty("followers_count")
    private int followersCount;
    @JsonProperty("following_count")
    private int followingCount;
    @JsonProperty("statuses_count")
    private int statusesCount;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean isBot) {
        this.isBot = isBot;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public int getStatusesCount() {
        return statusesCount;
    }

    public void setStatusesCount(int statusesCount) {
        this.statusesCount = statusesCount;
    }

    @Override
    public String toString() {
        return "BlogAccount{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", displayName='" + displayName + '\'' +
                ", isBot=" + isBot +
                ", createdAt='" + createdAt + '\'' +
                ", url='" + url + '\'' +
                ", followersCount=" + followersCount +
                ", followingCount=" + followingCount +
                ", statusesCount=" + statusesCount +
                '}';
    }
}
