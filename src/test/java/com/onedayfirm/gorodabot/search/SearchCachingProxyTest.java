package com.onedayfirm.gorodabot.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SearchCachingProxyTest {

    @Test
    @SuppressWarnings("unchecked")
    void calledOnlyOnce() {
        var result = "result";
        var service = (SearchService<String, String>) mock(SearchService.class);
        when(service.search(anyString())).thenReturn(result);
        var proxy = new SearchCachingProxy<>(service);

        for (var i = 0; i < 3; i++)
            assertEquals(result, proxy.search("query"));
        verify(service, times(1)).search(anyString());
    }
}
