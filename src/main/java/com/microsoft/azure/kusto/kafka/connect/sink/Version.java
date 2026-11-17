package com.microsoft.azure.kusto.kafka.connect.sink;

import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Version {
    public static final String CLIENT_NAME = "Kusto.Kafka.Sink";

    private static final Logger log = LoggerFactory.getLogger(Version.class);
    private static final String VERSION_FILE = "/azure-kusto-kafka-sink-version.properties";
    private static String version = "unknown";
    private static String pluginVersion = null;

    static {
        try {
            Properties props = new Properties();
            try (InputStream versionFileStream = Version.class.getResourceAsStream(VERSION_FILE)) {
                props.load(versionFileStream);
                version = props.getProperty("version", version).trim();
                String plugin = props.getProperty("plugin.version");
                if (plugin != null && !plugin.trim().isEmpty() && !plugin.trim().startsWith("${")) {
                    pluginVersion = plugin.trim();
                }
            }
        } catch (Exception e) {
            log.warn("Error while loading version:", e);
        }
    }

    /**
     * The version of this connector library (its pom version). Reported to Kusto as the client
     * version in connector details.
     */
    public static String getVersion() {
        return version;
    }

    /**
     * The version reported by the Kafka Connect plugin (connector and task {@code version()}).
     * Defaults to {@link #getVersion()}; a build can override it with
     * {@code -Dconnector.plugin.version=<version>}.
     */
    public static String getPluginVersion() {
        return pluginVersion != null ? pluginVersion : version;
    }
}
