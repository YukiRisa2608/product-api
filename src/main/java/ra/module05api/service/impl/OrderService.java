package ra.module05api.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ra.module05api.entity.Order;
import ra.module05api.exception.NotFoundException;
import ra.module05api.repository.OrderRepository;
import ra.module05api.service.IOrderService;

@Service
@AllArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;

    @Override
    public Order getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            throw new NotFoundException("Can not find order by id = " + id);
        }
        return order;
    }
}
