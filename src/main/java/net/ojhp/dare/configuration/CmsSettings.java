package net.ojhp.dare.configuration;

public interface CmsSettings {
    String getRootUri();

    String getAssetRootUri();

    String getAssetPrefix();

    String getSingletonPath(String name);

    String getCollectionPath(String name);
}
