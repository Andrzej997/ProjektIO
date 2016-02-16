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

    public boolean add(AuthorizableTransaction order) {
        return transactions.add(order);
    }

    public boolean accept(AuthorizableTransaction order) {
        order.accept();
        return transactions.remove(order);
    }

    public List<AuthorizableTransaction> getAllTransactions() {
        return transactions;
    }

    public boolean remove(long id) {
        boolean succeed = false;
        for (AuthorizableTransaction t : transactions) {
            if (t.getID() == id) {
                succeed = transactions.remove(t);
                break;
            }
        }
        return succeed;
    }
}
