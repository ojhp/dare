package net.ojhp.dare.models;

import lombok.Data;

@Data
@Singleton("site-settings")
public class SiteSettings {
    private String title;
    private String header;
    private String icon;
    private Asset backgroundImage;
    private String footer;
}
