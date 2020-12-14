package net.ojhp.dare.helpers;

import net.ojhp.dare.models.Asset;
import net.ojhp.dare.models.SiteSettings;
import net.ojhp.dare.repositories.CmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service("siteSettings")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class SiteSettingsHelper {
    private final CmsRepository cmsRepository;
    private SiteSettings siteSettings;

    @Autowired
    public SiteSettingsHelper(CmsRepository cmsRepository) {
        this.cmsRepository = cmsRepository;
    }

    public String getTitle() {
        return this.getSettings().getTitle();
    }

    public String getHeader() {
        return this.getSettings().getHeader();
    }

    public String getIcon() {
        return this.getSettings().getIcon();
    }

    public Asset getBackgroundImage() {
        return this.getSettings().getBackgroundImage();
    }

    public String getFooter() {
        return this.getSettings().getFooter();
    }

    private SiteSettings getSettings() {
        if (this.siteSettings == null) {
            this.siteSettings = this.cmsRepository.getSingleton(SiteSettings.class);
        }

        return this.siteSettings;
    }
}
