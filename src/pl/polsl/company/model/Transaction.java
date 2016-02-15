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

    public abstract void accept();//może będzie niepotrzebne
    public abstract void reject();

    public void persist() {
        TransactionsOperations t = new TransactionsOperations();
        t.setEntityManager(DAOManager.getInstance("kino").getEntityManager());
        t.addEntity(transactionEntity);
    }

}
