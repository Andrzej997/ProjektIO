package pl.polsl.company.controller;

import pl.polsl.company.model.ApplicationContext;
import pl.polsl.company.model.AuthorizableTransaction;

import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-02-08.
 */
public class ManagementController {

    private final ApplicationContext applicationContext;

    public ManagementController(ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;
    }

    public List<AuthorizableTransaction> getUnauthorizedTransactions() {
        return applicationContext.getAuthorizationQueue().getAllTransactions();
    }

    private AuthorizableTransaction find(int id) {
        for (AuthorizableTransaction at : applicationContext.getAuthorizationQueue().getAllTransactions()) {
            if (at.getID() == id) {
                return at;
            }
        }
        return null; //TODO dodać exception
    }

    public void refuseTransaction(int id) {
        AuthorizableTransaction transaction = find(id);
        transaction.getTransaction().refuse();
        applicationContext.getAuthorizationQueue().remove(id);
        applicationContext.getTransactionList().remove(id);
    }

    public void acceptTransaction(int id) {
        AuthorizableTransaction transaction = find(id);
        transaction.getTransaction().accept();
        applicationContext.getAuthorizationQueue().remove(id);
        applicationContext.getTransactionList().remove(id);
    }


}
