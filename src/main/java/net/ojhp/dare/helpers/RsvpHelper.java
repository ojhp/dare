package net.ojhp.dare.helpers;

import net.ojhp.dare.configuration.CmsSettings;
import net.ojhp.dare.models.ReplyQuestion;
import net.ojhp.dare.models.SelectOption;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
