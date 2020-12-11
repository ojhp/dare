package net.ojhp.dare.repositories;

import net.ojhp.dare.configuration.CmsSettings;
import net.ojhp.dare.models.Collection;
import net.ojhp.dare.models.Singleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CmsRepositoryImpl implements CmsRepository {
    private final RestTemplate restTemplate;
    private final CmsSettings cmsSettings;

    @Autowired
    public CmsRepositoryImpl(
            RestTemplateBuilder restTemplateBuilder,
            CmsSettings cmsSettings) {
        this.restTemplate = restTemplateBuilder
                .rootUri(cmsSettings.getRootUri())
                .build();
        this.cmsSettings = cmsSettings;
    }

    @Override
    public <T> T getSingleton(Class<T> type) {
        String name = type.getAnnotation(Singleton.class).value();
        String path = this.cmsSettings.getSingletonPath(name);

        return this.executeGet(path, type);
    }

    @Override
    public <T> List<T> getAll(Class<T> type) {
        String name = type.getAnnotation(Collection.class).value();
        String path = this.cmsSettings.getCollectionPath(name);
        Class<T[]> arrayType = (Class<T[]>) type.arrayType();

        T[] items = this.executeGet(path, arrayType);
        if (items == null) {
            return null;
        }

        return Arrays.asList(items);
    }

    private <T> T executeGet(String path, Class<T> type) {
        try {
            return this.restTemplate.getForObject(path, type);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }

            throw e;
        }
    }
}
