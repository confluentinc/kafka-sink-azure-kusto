package com.microsoft.azure.kusto.kafka.connect.sink;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class VersionTest {

    @Test
    void versionIsFilteredFromPom() {
        assertNotEquals("unknown", Version.getVersion());
        assertFalse(Version.getVersion().startsWith("${"));
    }

    @Test
    void pluginVersionDefaultsToPomVersion() {
        // connector.plugin.version defaults to ${project.version}; a test run doesn't override it.
        assertEquals(System.getProperty("connector.plugin.version", Version.getVersion()), Version.getPluginVersion());
    }

    @Test
    void connectorAndTaskReportPluginVersion() {
        assertEquals(Version.getPluginVersion(), new KustoSinkConnector().version());
        assertEquals(Version.getPluginVersion(), new KustoSinkTask().version());
    }
}
