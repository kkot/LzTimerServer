package kkot.lztimer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to JHipster.
 *
 * <p>
 *     Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    /** Default time after user is regarded as inactive. */
    private int defaultMinIdleTime;

    public int getDefaultMinIdleTime() {
        return defaultMinIdleTime;
    }

    public void setDefaultMinIdleTime(int defaultMinIdleTime) {
        this.defaultMinIdleTime = defaultMinIdleTime;
    }
}
