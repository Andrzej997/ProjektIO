package pl.polsl.company.controller;

import pl.polsl.company.model.ApplicationContext;
import pl.polsl.company.model.AuthorizableTransaction;

import java.util.List;

/**
 * Management controler to handle manager gui requests
 *
 * Created by Krzysztof Stręk on 2016-02-08.
 */
public class ManagementController {

    /**
     * ApplicationContext object
     */
    private ApplicationContext applicationContext;

    /**
     * Constructor
     *
     * @param applicationContext new application context
     */
    public ManagementController(ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;
    }

    /**
     * Method to get all unauthorized transactions
     *
     * @return List of AutorizableTransactions
     */
    public List<AuthorizableTransaction> getUnauthorizedTransactions() {
        return applicationContext.getAuthorizationQueue().getAllTransactions();
    }

    /**
     * Method to find AuthorizableTransaction by id
     *
     * @param id Integer with AutorizableTransaction id
     * @return AutorizableTransaction object
     */
    private AuthorizableTransaction find(int id) {
        for (AuthorizableTransaction at : applicationContext.getAuthorizationQueue().getAllTransactions()) {
            if (at.getID() == id) {
                return at;
            }
        }
        return null; //TODO dodać exception
    }

    /**
     * Method to refuse transaction by AutorizableTransaction id
     *
     * @param id Integer with AutorizableTransaction id
     */
    public void refuseTransaction(int id) {
        AuthorizableTransaction transaction = find(id);
        transaction.getTransaction().refuse();
        applicationContext.getAuthorizationQueue().remove(id);
        applicationContext.getTransactionList().remove(id);
    }

    /**
     * Method to accept transaction by AutorizableTransaction id
     *
     * @param id Integer with AutorizableTransaction id
     */
    public void acceptTransaction(int id) {
        AuthorizableTransaction transaction = find(id);
        transaction.getTransaction().accept();
        applicationContext.getAuthorizationQueue().remove(id);
        applicationContext.getTransactionList().remove(id);
    }

    /**
     * @return the applicationContext
     */
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @param applicationContext the applicationContext to set
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

}
