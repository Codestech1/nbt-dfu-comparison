package net.hypejet.nbt.dfu.comparison.test;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public final class ComparisonEnvironmentExtension implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        // Initialize the environment before any test touches anything related to Minecraft
        Class.forName("net.hypejet.nbt.dfu.comparison.test.TestUtil");
    }
}