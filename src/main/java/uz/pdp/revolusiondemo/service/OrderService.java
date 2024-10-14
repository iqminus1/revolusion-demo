package uz.pdp.revolusiondemo.service;

import uz.pdp.revolusiondemo.enums.OrderStatus;
import uz.pdp.revolusiondemo.payload.ApiResultDto;
import uz.pdp.revolusiondemo.payload.in.OrderCrudDto;

public interface OrderService {
    ApiResultDto<?> readAll(int page,int size);

    ApiResultDto<?> read(Integer id);

    ApiResultDto<?> readMyOrders();

    ApiResultDto<?> create(OrderCrudDto crudDto);

    ApiResultDto<?> changeStatus(OrderStatus status, Integer id);
}
