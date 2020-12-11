package net.ojhp.dare.services;

import net.ojhp.dare.configuration.CmsSettings;
import net.ojhp.dare.models.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service("style")
public class StyleService {
    private final CmsSettings cmsSettings;

    @Autowired
    public StyleService(CmsSettings cmsSettings) {
        this.cmsSettings = cmsSettings;
    }

    public String setBackgroundImage(String selector, Asset image) throws MalformedURLException {
        return String.format("%s{background-image:url('%s');}",
                selector,
                new URL(new URL(this.cmsSettings.getAssetRootUri()), image.getUrl()).toString());
    }

    public String getIconClasses(String iconName) {
        return "icon fa-" + iconName.replace('_', '-');
    }
}
