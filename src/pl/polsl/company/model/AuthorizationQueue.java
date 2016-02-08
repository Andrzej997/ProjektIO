package pl.polsl.company.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-01-29.
 */
public class AuthorizationQueue {

    private List<AuthorizableTransaction> orders;

    public AuthorizationQueue() {
        orders = new ArrayList<AuthorizableTransaction>();
    }

    public void add(AuthorizableTransaction order) {
        orders.add(order);
    }

    public Transaction authorize(AuthorizableTransaction order) {
        order.changeStatus();
        return order;
    }

    public AuthorizableTransaction getLastOrder() {
        if (orders != null && orders.size() > 0) {
            return orders.get(orders.size()-1);
        } else {
            return null;
        }
    }

    public List<AuthorizableTransaction> getAllOrders() {
        return orders;
    }
}
