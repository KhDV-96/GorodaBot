package com.onedayfirm.gorodabot.search;

import java.util.Map;
import java.util.WeakHashMap;

public class SearchCachingProxy<Q, R> implements SearchService<Q, R> {

    private SearchService<Q, R> service;
    private Map<Q, R> cache;

    public SearchCachingProxy(SearchService<Q, R> service) {
        this.service = service;
        cache = new WeakHashMap<>();
    }

    @Override
    public R search(Q query) {
        var cachedQuery = cache.get(query);
        if (cachedQuery == null) {
            cachedQuery = service.search(query);
            cache.put(query, cachedQuery);
        }
        return cachedQuery;
    }
}
