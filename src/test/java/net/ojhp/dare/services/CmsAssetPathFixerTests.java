package net.ojhp.dare.services;

import net.ojhp.dare.configuration.CmsSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CmsAssetPathFixerTests {
    private CmsSettings mockCmsSettings;
    private CmsAssetPathFixer sut;

    @BeforeEach
    public void setUp() {
        this.mockCmsSettings = mock(CmsSettings.class);
        this.sut = new CmsAssetPathFixer(this.mockCmsSettings);
    }

    @Test
    public void fixAssetPath_WhenPathStartsWithPrefix_ReturnsAbsoluteUri() {
        String stubAssetRoot = "https://assets.example.com";
        String stubAssetPrefix = "/assets/";
        String stubAssetPath = stubAssetPrefix + "filename.txt";

        when(this.mockCmsSettings.getAssetRootUri()).thenReturn(stubAssetRoot);
        when(this.mockCmsSettings.getAssetPrefix()).thenReturn(stubAssetPrefix);

        String result = this.sut.fixAssetPath(stubAssetPath);

        assertEquals(stubAssetRoot + stubAssetPath, result);
    }

    @Test
    public void fixAssetPath_WhenPathDoesNotStartWithPrefix_ReturnsPathUnchanged() {
        String stubAssetPrefix = "/assets/";
        String stubAssetPath = "/not-an-asset/image.jpg";

        when(this.mockCmsSettings.getAssetPrefix()).thenReturn(stubAssetPrefix);

        String result = this.sut.fixAssetPath(stubAssetPath);

        assertEquals(stubAssetPath, result);
    }
}
