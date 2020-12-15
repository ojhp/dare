package net.ojhp.dare.helpers;

import net.ojhp.dare.configuration.CmsSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RsvpHelperTests {
    private CmsSettings mockCmsSettings;
    private RsvpHelper sut;

    @BeforeEach
    public void setUp() {
        this.mockCmsSettings = mock(CmsSettings.class);
        this.sut = new RsvpHelper(this.mockCmsSettings);
    }

    @Test
    public void getAuthenticationUrl_WhenCalled_ConstructsAbsoluteUrl() throws MalformedURLException {
        String stubAssetRoot = "https://cms.website.com";
        String stubAuthenticationPath = "/auth/path";

        when(this.mockCmsSettings.getAssetRootUri()).thenReturn(stubAssetRoot);
        when(this.mockCmsSettings.getAuthenticationPath()).thenReturn(stubAuthenticationPath);

        assertEquals(stubAssetRoot + stubAuthenticationPath, this.sut.getAuthenticationUrl());
    }

    @Test
    public void getRepliesUrl_WhenCalled_ConstructsAbsoluteUrl() throws MalformedURLException {
        String stubAssetRoot = "http://assets.net";
        String stubRepliesPath = "/path/to/replies";

        when(this.mockCmsSettings.getAssetRootUri()).thenReturn(stubAssetRoot);
        when(this.mockCmsSettings.getCollectionPath("replies")).thenReturn(stubRepliesPath);

        assertEquals(stubAssetRoot + stubRepliesPath, this.sut.getRepliesUrl());
    }
}
