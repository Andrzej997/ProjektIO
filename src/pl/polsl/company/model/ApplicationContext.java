package pl.polsl.company.model;

import pl.polsl.database.entities.Transactions;
import pl.polsl.database.manager.DAOManager;

import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-02-10.
 */
public class ApplicationContext {

    private final TransactionList transactionList;

    private final AuthorizationQueue authorizationQueue;

    public ApplicationContext() {

        String query = "SELECT e FROM Transactions e";
        List<Transactions> transactionEntities = DAOManager.getInstance("kino").realizeQuery(query);

        transactionList = new TransactionList();
        authorizationQueue = new AuthorizationQueue();

        for (Transactions t: transactionEntities) {
            Transaction newTransaction;

            switch (t.getType()) {
                case 0:
                    newTransaction = new RoomRentTransaction(t);
                    break;
                case 1:
                    newTransaction = new AdvertisementTransaction(t);
                    break;
                default:
                    newTransaction = new AdvertisementTransaction(t);
            }
            transactionList.add(newTransaction);

            if (t.getType() == 0 && !t.isAccepted()) {
                authorizationQueue.add(new AuthorizableTransaction((RoomRentTransaction)newTransaction));
            }

        }
    }

    public AuthorizationQueue getAuthorizationQueue() {
        return authorizationQueue;
    }

    public TransactionList getTransactionList() {
        return transactionList;
    }
}
