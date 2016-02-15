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

    public void accept(AuthorizableTransaction order) {
        order.accept();
        transactions.remove(order);
    }

    public List<AuthorizableTransaction> getAllTransactions() {
        return transactions;
    }

    public void remove(long id) {
        for (AuthorizableTransaction t : transactions) {
            if (t.getID() == id) {
                transactions.remove(t);
                break;
            }
        }
    }
}
