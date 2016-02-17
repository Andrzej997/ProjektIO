package pl.polsl.company.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class creates autorization queue with transactions to autorize
 *
 * Created by Krzysztof Stręk on 2016-01-29.
 */
public class AuthorizationQueue {

    /**
     * Field with List of autorizable transactions
     */
    private List<AuthorizableTransaction> transactions;

    /**
     * Constructor
     */
    public AuthorizationQueue() {
        transactions = new ArrayList<>();
    }

    /**
     * Add autorisable transaction to queue
     *
     * @param order AutorisableTransaction object
     * @return true when added correctly
     */
    public boolean add(AuthorizableTransaction order) {
        return transactions.add(order);
    }

    /**
     * Method autorise the transaction and removes it from queue
     *
     * @param order AutorisableTransaction object
     * @return true when removed correctly
     */
    public boolean accept(AuthorizableTransaction order) {
        order.accept();
        return transactions.remove(order);
    }

    /**
     * Method to get all autorisable transactions list
     *
     * @return List of AutorizableTransactions
     */
    public List<AuthorizableTransaction> getAllTransactions() {
        return transactions;
    }

    /**
     * Method to remove the transaction from queue by id
     *
     * @param id Transaction id
     * @return true when succeded
     */
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
