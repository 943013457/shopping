package com.service;

import java.util.List;

/**
 * @Creator Ming
 * @Time 2019/8/20
 * @Other
 */
public interface MyTrolleyService {
    public List<String> getTrolleyJson(String username);

    public int deleteItem(String username, int productId);

    public int insertItem(String username, int productID, int number);

    public boolean isAddTrolley(String username,int productID);
}
