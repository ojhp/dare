package net.ojhp.dare.repositories;

import net.ojhp.dare.configuration.CmsSettings;
import net.ojhp.dare.models.Collection;
import net.ojhp.dare.models.Singleton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CmsRepositoryImplTests {
    private static final String SINGLETON_TYPE_NAME = "a-singleton";
    private static final String COLLECTION_TYPE_NAME = "a-collection";

    private RestTemplate mockRestTemplate;
    private CmsSettings mockCmsSettings;
    private CmsRepositoryImpl sut;

    @BeforeEach
    public void setUp() {
        this.mockRestTemplate = mock(RestTemplate.class);
        this.mockCmsSettings = mock(CmsSettings.class);

        String stubRootUri = "http://cms.example.com";
        when(this.mockCmsSettings.getRootUri()).thenReturn(stubRootUri);
        RestTemplateBuilder mockRestTemplateBuilder = mock(RestTemplateBuilder.class, RETURNS_DEEP_STUBS);
        when(mockRestTemplateBuilder.rootUri(stubRootUri).build()).thenReturn(this.mockRestTemplate);

        this.sut = new CmsRepositoryImpl(mockRestTemplateBuilder, this.mockCmsSettings);
    }

    @Test
    public void getSingleton_WhenSuccessful_ReturnsSingletonData() {
        String stubPath = "/path/to/singleton";
        DummySingleton stubSingleton = new DummySingleton();

        when(this.mockCmsSettings.getSingletonPath(SINGLETON_TYPE_NAME))
                .thenReturn(stubPath);
        when(this.mockRestTemplate.getForObject(stubPath, DummySingleton.class))
                .thenReturn(stubSingleton);

        assertSame(stubSingleton, this.sut.getSingleton(DummySingleton.class));
    }

    @Test
    public void getSingleton_WhenNotFound_ReturnsNull() {
        String stubPath = "/path/to/singleton";

        when(this.mockCmsSettings.getSingletonPath(SINGLETON_TYPE_NAME))
                .thenReturn(stubPath);
        when(this.mockRestTemplate.getForObject(stubPath, DummySingleton.class))
                .thenThrow(HttpClientErrorException.NotFound.create(
                        HttpStatus.NOT_FOUND,
                        "Failed",
                        new HttpHeaders(),
                        new byte[] {},
                        null));

        assertNull(this.sut.getSingleton(DummySingleton.class));
    }

    @Test
    public void getAll_WhenSuccessful_ReturnsCollectionItems() {
        String stubPath = "/path/to/collection";
        DummyCollection[] stubItems = {
                new DummyCollection(),
                new DummyCollection(),
                new DummyCollection()
        };

        when(this.mockCmsSettings.getCollectionPath(COLLECTION_TYPE_NAME))
                .thenReturn(stubPath);
        when(this.mockRestTemplate.getForObject(stubPath, DummyCollection[].class))
                .thenReturn(stubItems);

        List<DummyCollection> result = this.sut.getAll(DummyCollection.class);

        assertNotNull(result);
        assertEquals(stubItems.length, result.size());
        for (DummyCollection item : stubItems) {
            assertTrue(result.contains(item));
        }
    }

    @Test
    public void getAll_WhenNotFound_ReturnsNull() {
        String stubPath = "/path/to/collection";

        when(this.mockCmsSettings.getSingletonPath(COLLECTION_TYPE_NAME))
                .thenReturn(stubPath);
        when(this.mockRestTemplate.getForObject(stubPath, DummyCollection.class))
                .thenThrow(HttpClientErrorException.NotFound.create(
                        HttpStatus.NOT_FOUND,
                        "Failed",
                        new HttpHeaders(),
                        new byte[] {},
                        null));

        assertNull(this.sut.getAll(DummyCollection.class));
    }

    @Singleton(SINGLETON_TYPE_NAME)
    private class DummySingleton {}

    @Collection(COLLECTION_TYPE_NAME)
    private class DummyCollection {}
}
