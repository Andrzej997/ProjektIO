package pl.polsl.company.model;

import pl.polsl.database.entities.Transactions;
import pl.polsl.database.manager.DAOManager;
import pl.polsl.database.manager.operations.TransactionsOperations;

import java.util.Calendar;

/**
 * Created by Krzysztof Stręk on 2016-01-29.
 */
public abstract class Transaction {

    protected Transactions transactionEntity;

    public Transaction(Transactions t) {
        transactionEntity = t;
    }

    public long getID() {
        return transactionEntity.getId();
    }

    public Calendar getStartDate() {
        return transactionEntity.getStartDateAndTime();
    }

    public Calendar getEndDate() {
        return transactionEntity.getEndDateAndTime();
    }

    public String getCompanyName() {
        return transactionEntity.getCompanyName();
    }

    public double getPrice() {
        return transactionEntity.getPrice();
    }

    //0 - RoomRent      1-reklama
    public int getType() {
        return transactionEntity.getType();
    }

    public abstract void accept();

    public abstract void refuse();

    public void persist() {
        TransactionsOperations t = new TransactionsOperations();
        t.setEntityManager(DAOManager.getInstance("kino").getEntityManager());
        t.addEntity(transactionEntity);
    }

    public void modify() {
        transactionEntity.setAccepted(true);
        TransactionsOperations t = new TransactionsOperations();
        t.setEntityManager(DAOManager.getInstance("kino").getEntityManager());
        t.modifyEntity(transactionEntity);
    }

    public void delete() {
        TransactionsOperations t = new TransactionsOperations();
        t.setEntityManager(DAOManager.getInstance("kino").getEntityManager());
        t.deleteEntity(transactionEntity);
    }

}
