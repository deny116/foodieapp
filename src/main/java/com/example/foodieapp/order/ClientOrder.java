package com.example.foodieapp.order;

import com.example.foodieapp.iteam.Item;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class ClientOrder {

    Order order;

    public ClientOrder() {
        clear();
    }

    Order getOrder() {
        return order;
    }

    void add(Item item) {
        order.getItems().add(item);
    }

    void clear() {
        order = new Order();
        order.setStatus(OrderStatus.NEW);
    }
}
