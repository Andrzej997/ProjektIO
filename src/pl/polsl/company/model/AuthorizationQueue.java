package pl.polsl.company.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-01-29.
 */
public class AuthorizationQueue {

    private List<AuthorizableTransaction> transactions;

    public AuthorizationQueue() {
        transactions = new ArrayList<>();
    }

    public void add(AuthorizableTransaction order) {
        transactions.add(order);
    }

    public void authorize(AuthorizableTransaction order) {
        order.accept();
        transactions.remove(order);
    }

    public List<AuthorizableTransaction> getAllTransactions() {
        return transactions;
    }
}
