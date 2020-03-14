package com.service;

import java.util.List;

/**
 * @Creator Ming
 * @Time 2020/3/11
 * @Other
 */
public interface SearchService {

    public List<String> getProductList(String name, String sort, int page, int num);

    public long getProductListCount(String name);
}
