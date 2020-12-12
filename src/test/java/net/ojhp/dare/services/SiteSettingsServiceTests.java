package net.ojhp.dare.services;

import net.ojhp.dare.models.Asset;
import net.ojhp.dare.models.SiteSettings;
import net.ojhp.dare.repositories.CmsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SiteSettingsServiceTests {
    private static final String CONFIGURED_TITLE = "Site Name";
    private static final String CONFIGURED_HEADER = "Header text";
    private static final String CONFIGURED_ICON = "icon-name";
    private static final Asset CONFIGURED_BACKGROUND_IMAGE = new Asset();
    private static final String CONFIGURED_FOOTER = "Footer text";

    private CmsRepository mockRepository;
    private SiteSettingsService sut;

    @BeforeEach
    public void setUp() {
        this.mockRepository = mock(CmsRepository.class);
        this.sut = new SiteSettingsService(this.mockRepository);

        SiteSettings stubSiteSettings = new SiteSettings();
        stubSiteSettings.setTitle(CONFIGURED_TITLE);
        stubSiteSettings.setHeader(CONFIGURED_HEADER);
        stubSiteSettings.setIcon(CONFIGURED_ICON);
        stubSiteSettings.setBackgroundImage(CONFIGURED_BACKGROUND_IMAGE);
        stubSiteSettings.setFooter(CONFIGURED_FOOTER);
        when(this.mockRepository.getSingleton(SiteSettings.class)).thenReturn(stubSiteSettings);
    }

    @Test
    public void getTitle_WhenCalled_ReturnsConfiguredValue() {
        assertEquals(CONFIGURED_TITLE, this.sut.getTitle());
    }

    @Test
    public void getHeader_WhenCalled_ReturnsConfiguredValue() {
        assertEquals(CONFIGURED_HEADER, this.sut.getHeader());
    }

    @Test
    public void getIcon_WhenCalled_ReturnsConfiguredValue() {
        assertEquals(CONFIGURED_ICON, this.sut.getIcon());
    }

    @Test
    public void getBackgroundImage_WhenCalled_ReturnsConfiguredValue() {
        assertSame(CONFIGURED_BACKGROUND_IMAGE, this.sut.getBackgroundImage());
    }

    @Test
    public void getFooter_WhenCalled_ReturnsConfiguredValue() {
        assertSame(CONFIGURED_FOOTER, this.sut.getFooter());
    }

    @Test
    public void getters_WhenCalledRepeatedly_CachesSettings() {
        this.sut.getTitle();
        this.sut.getHeader();
        this.sut.getIcon();
        this.sut.getBackgroundImage();
        this.sut.getFooter();

        verify(this.mockRepository, times(1)).getSingleton(SiteSettings.class);
    }
}
