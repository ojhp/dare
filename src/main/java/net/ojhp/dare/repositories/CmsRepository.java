package net.ojhp.dare.repositories;

import java.util.List;

public interface CmsRepository {
    <T> T getSingleton(Class<T> type);
    <T> List<T> getAll(Class<T> type);
}
