package pl.polsl.company.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-01-29.
 */
public class AuthorizationQueue {

    private List<AuthorizableTransaction> transactions;

    public AuthorizationQueue(List<AuthorizableTransaction> unauthorizedTransactions) {
        transactions = unauthorizedTransactions;
    }

    public void add(AuthorizableTransaction order) {
        transactions.add(order);
    }

    public Transaction authorize(AuthorizableTransaction order) {
        order.changeStatus();
        return order;
    }

    public AuthorizableTransaction getLastOrder() {
        if (transactions != null && transactions.size() > 0) {
            return transactions.get(transactions.size()-1);
        } else {
            return null;
        }
    }

    public List<AuthorizableTransaction> getAllTransactions() {
        return transactions;
    }
}
