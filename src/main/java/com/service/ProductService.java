package com.service;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Creator Ming
 * @Time 2019/8/15
 * @Other
 */
public interface ProductService {
    public String getProductJson(int id);

    public boolean hasProduct(int id);

    public String getNameAndPrice(int id);
}
