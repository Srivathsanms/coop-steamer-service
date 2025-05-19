package no.coop.task.streamer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "federated")
public class FederatedPlatformProperties {
    private List<Platform> platforms;

    public List<Platform> getPlatforms() { return platforms; }
    public void setPlatforms(List<Platform> platforms) { this.platforms = platforms; }

    public static class Platform {
        private String name;
        private String liveUrl;
        private String bufferedUrl;
        private String authToken;

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getLiveUrl() { return liveUrl; }
        public void setLiveUrl(String liveUrl) { this.liveUrl = liveUrl; }

        public String getBufferedUrl() { return bufferedUrl; }
        public void setBufferedUrl(String bufferedUrl) { this.bufferedUrl = bufferedUrl; }

        public String getAuthToken() { return authToken; }
        public void setAuthToken(String authToken) { this.authToken = authToken; }
    }
}
