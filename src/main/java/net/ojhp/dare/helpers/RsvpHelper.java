package net.ojhp.dare.helpers;

import net.ojhp.dare.configuration.CmsSettings;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service("rsvp")
public class RsvpHelper {
    private final CmsSettings cmsSettings;

    public RsvpHelper(CmsSettings cmsSettings) {
        this.cmsSettings = cmsSettings;
    }

    public String getAuthenticationUrl() throws MalformedURLException {
        return getPath(this.cmsSettings.getAuthenticationPath());
    }

    public String getRepliesUrl() throws MalformedURLException {
        return getPath(this.cmsSettings.getCollectionPath("replies"));
    }

    private String getPath(String path) throws MalformedURLException {
        return new URL(new URL(this.cmsSettings.getAssetRootUri()), path).toString();
    }
}
