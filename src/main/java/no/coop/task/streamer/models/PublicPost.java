package no.coop.task.streamer.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicPost {


    String id;
    @JsonProperty("created_at")
    String createdAt;
    String content;
    AuthorProfile profile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AuthorProfile getProfile() {
        return profile;
    }

    public void setProfile(AuthorProfile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "PublicPost{" +
                "id='" + id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", content='" + content + '\'' +
                ", profile=" + profile +
                '}';
    }
}
