package pl.polsl.company.model;

import pl.polsl.database.manager.DAOManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-02-10.
 */
public class ApplicationContext {

    private AuthorizationQueue authorizationQueue;

    private final List<Transaction> transactions;

    public ApplicationContext() {

        String query = "SELECT * FROM ADDS_SELLING UNION SELECT * FROM ROOMS_RENTING";

        transactions = DAOManager.getInstance("kino").realizeQuery(query);

        List<AuthorizableTransaction> unauthorizedTransactions = getUnauthorized(transactions);

        authorizationQueue = new AuthorizationQueue(unauthorizedTransactions);
    }

    private List<AuthorizableTransaction> getUnauthorized(List<Transaction> transactions) {
        ArrayList<AuthorizableTransaction> result = new ArrayList<AuthorizableTransaction>();

        for (Transaction t : transactions) {
            //TODO sprawdzanie czy czeka na autoryzacje i dodanie do listy
        }

        return result;
    }

    public AuthorizationQueue getAuthorizationQueue() {
        return authorizationQueue;
    }

    public List<Transaction> getTransactions() {
        //TODO do ogarnięcia w jakiej postaci lista ma być zwracana i może jakieś zmiany w strukturze tabel
        return transactions;
    }
}
