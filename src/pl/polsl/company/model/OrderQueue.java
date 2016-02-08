package pl.polsl.company.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-01-29.
 */
public class OrderQueue {

    private List<AuthorizableOrder> orders;

    public OrderQueue() {
        orders = new ArrayList<AuthorizableOrder>();
    }

    public void add(AuthorizableOrder order) {
        orders.add(order);
    }

    public Order authoriye(AuthorizableOrder order) {
        order.zmienStatus();
        return order;
    }

    public AuthorizableOrder getLastOrder() {
        if (orders != null && orders.size() > 0) {
            return orders.get(orders.size()-1);
        } else {
            return null;
        }
    }

    public List<AuthorizableOrder> getAllOrders() {
        return orders;
    }
}
