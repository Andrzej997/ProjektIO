package pl.polsl.company.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class contains transactions list
 *
 * Created by Krzysztof Stręk on 2016-01-29.
 */
public class TransactionList {

    /**
     * Field with transactions list
     */
    private final List<Transaction> transactions;

    /**
     * Constructor
     */
    public TransactionList() {

        transactions = new ArrayList<>();
    }

    /**
     * Method to get transactions list
     *
     * @return List of Transaction
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Method to add Transaction to list
     *
     * @param t Transaction object
     * @return true when succesfully added
     */
    public boolean add(Transaction t) {
        return transactions.add(t);
    }

    /**
     * Method to remove transaction from list by id
     *
     * @param id transaction id
     * @return true when succesfully removed
     */
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
