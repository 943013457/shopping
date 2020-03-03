package com.mapper;

import com.pojo.OrderTableAndItem;

/**
 * @Creator Ming
 * @Time 2019/10/28
 * @Other
 */
public interface OrderTableAndItemMapper {

    public int insertTable(OrderTableAndItem orderTableAndItem);

    public int insertItem(OrderTableAndItem orderTableAndItem);
}
