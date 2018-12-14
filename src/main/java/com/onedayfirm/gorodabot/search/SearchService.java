package com.onedayfirm.gorodabot.search;

public interface SearchService<Q, R> {

    R search(Q query);
}
