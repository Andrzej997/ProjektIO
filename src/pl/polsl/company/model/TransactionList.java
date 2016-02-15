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

    public void add(Transaction t) {
        transactions.add(t);
    }

    public void remove(long id) {
        for (Transaction t : transactions) {
            if (t.getID() == id) {
                transactions.remove(t);
                break;
            }
        }
    }
}