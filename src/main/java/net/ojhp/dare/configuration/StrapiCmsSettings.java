package net.ojhp.dare.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class StrapiCmsSettings implements CmsSettings {
    private static final String DARE_CMS_ROOT_URI = "DARE_CMS_ROOT_URI";

    private final Environment environment;

    @Autowired
    public StrapiCmsSettings(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String getRootUri() {
        return this.environment.getProperty(DARE_CMS_ROOT_URI);
    }

    @Override
    public String getAssetRootUri() {
        return this.getRootUri();
    }

    @Override
    public String getSingletonPath(String name) {
        return String.format("/%s", name);
    }

    @Override
    public String getCollectionPath(String name) {
        return String.format("/%s", name);
    }
}
