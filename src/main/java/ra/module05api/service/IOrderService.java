package ra.module05api.service;

import ra.module05api.entity.Order;

public interface IOrderService {
    Order getOrderById(Long id);
}
