package pl.polsl.company.model;

import pl.polsl.database.entities.Transactions;
import pl.polsl.database.manager.DAOManager;
import pl.polsl.database.manager.operations.TransactionsOperations;

import java.util.Calendar;

/**
 * Abstract class to provide some methods
 *
 * Created by Krzysztof Stręk on 2016-01-29.
 */
public abstract class Transaction {

    /**
     * Transactions entity object
     */
    protected Transactions transactionEntity;

    /**
     * Construntor
     *
     * @param t Transactions entity object
     */
    public Transaction(Transactions t) {
        transactionEntity = t;
    }

    /**
     * Method to get transaction id
     *
     * @return Long with id
     */
    public long getID() {
        return transactionEntity.getId();
    }

    /**
     * Method to get transaction start date
     *
     * @return Calendar with start date
     */
    public Calendar getStartDate() {
        return transactionEntity.getStartDateAndTime();
    }

    /**
     * Method to get transaction end date
     *
     * @return Calendar with end date
     */
    public Calendar getEndDate() {
        return transactionEntity.getEndDateAndTime();
    }

    /**
     * Method to get transaction company name
     *
     * @return String with company name
     */
    public String getCompanyName() {
        return transactionEntity.getCompanyName();
    }

    /**
     * Method to get transaction price
     *
     * @return Double with price
     */
    public double getPrice() {
        return transactionEntity.getPrice();
    }

    /**
     * Method to get transaction type
     *
     * @return integer with type
     */
    public int getType() {
        return transactionEntity.getType();
    }

    /**
     * Abstract method
     */
    public abstract void accept();

    /**
     * Abstract method
     */
    public abstract void refuse();

    /**
     * Adds entity to database
     */
    public void persist() {
        TransactionsOperations t = new TransactionsOperations();
        t.setEntityManager(DAOManager.getInstance("kino").getEntityManager());
        t.addEntity(transactionEntity);
    }

    /**
     * Modify entity in database
     */
    public void modify() {
        transactionEntity.setAccepted(true);
        TransactionsOperations t = new TransactionsOperations();
        t.setEntityManager(DAOManager.getInstance("kino").getEntityManager());
        t.modifyEntity(transactionEntity);
    }

    /**
     * Deletes entity from database
     */
    public void delete() {
        TransactionsOperations t = new TransactionsOperations();
        t.setEntityManager(DAOManager.getInstance("kino").getEntityManager());
        t.deleteEntity(transactionEntity);
    }

}
