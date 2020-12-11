package net.ojhp.dare.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StrapiCmsSettingsTests {
    private Environment mockEnvironment;
    private StrapiCmsSettings sut;

    @BeforeEach
    public void setUp() {
        this.mockEnvironment = mock(Environment.class);
        this.sut = new StrapiCmsSettings(this.mockEnvironment);
    }

    @Test
    public void getRootUri_WhenCalled_FetchesSettingFromEnvironment() {
        String stubUri = "http://www.example.com/cms";

        when(this.mockEnvironment.getProperty("DARE_CMS_ROOT_URI"))
                .thenReturn(stubUri);

        assertEquals(stubUri, this.sut.getRootUri());
    }

    @Test
    public void getAssetRootUri_WhenCalled_ReturnsRootUri() {
        String stubUri = "http://www.example.com/cms";

        when(this.mockEnvironment.getProperty("DARE_CMS_ROOT_URI"))
                .thenReturn(stubUri);

        assertEquals(stubUri, this.sut.getAssetRootUri());
    }

    @Test
    public void getSingletonPath_WhenCalled_ReturnsCorrectPath() {
        String stubName = "singleton-type";

        assertEquals("/singleton-type", this.sut.getSingletonPath(stubName));
    }

    @Test
    public void getCollectionPath_WhenCalled_ReturnsCorrectPath() {
        String stubName = "collection-type";

        assertEquals("/collection-type", this.sut.getCollectionPath(stubName));
    }
}
