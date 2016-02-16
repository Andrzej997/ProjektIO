package pl.polsl.company.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-01-29.
 */
public class TransactionList {

    private final List<Transaction> transactions;

    public TransactionList() {

        transactions = new ArrayList<>();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public boolean add(Transaction t) {
        return transactions.add(t);
    }

    public boolean remove(long id) {
        boolean succeed = false;
        for (Transaction t : transactions) {
            if (t.getID() == id) {
                succeed = transactions.remove(t);
                break;
            }
        }
        return succeed;
    }
}