package net.ojhp.dare.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StrapiCmsSettingsTests {
    private Environment mockEnvironment;
    private StrapiCmsSettings sut;

    @BeforeEach
    public void setUp() {
        this.mockEnvironment = mock(Environment.class);
        this.sut = new StrapiCmsSettings(this.mockEnvironment);
    }

    @Test
    public void getRootUri_WhenCalled_ReturnsRootUri() {
        String stubUri = "http://www.example.com/cms";

        when(this.mockEnvironment.getProperty("DARE_CMS_ROOT_URI"))
                .thenReturn(stubUri);

        assertEquals(stubUri, this.sut.getRootUri());
    }

    @Test
    public void getAssetRootUri_WhenCalled_ReturnsAssetRootUri() {
        String stubUri = "http://www.example.com/cms";

        when(this.mockEnvironment.getProperty("DARE_ASSET_ROOT_URI"))
                .thenReturn(stubUri);

        assertEquals(stubUri, this.sut.getAssetRootUri());
    }

    @Test
    public void getAssetPrefix_WhenCalled_ReturnsExpectedValue() {
        assertEquals("/uploads/", this.sut.getAssetPrefix());
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

    @Test
    public void getAuthenticationPath_WhenCalled_ReturnsExpectedValue() {
        assertEquals("/auth/local", this.sut.getAuthenticationPath());
    }
}
