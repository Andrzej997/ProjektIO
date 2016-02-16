package pl.polsl.company.model;

import java.util.Calendar;

/**
 * Created by Krzysztof Stręk on 2016-01-29.
 */

public class AuthorizableTransaction {

    private RoomRentTransaction transaction;

    public AuthorizableTransaction(RoomRentTransaction transaction) {
        this.transaction = transaction;
    }

    public void accept() {
        transaction.persist();
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public long getID() {
        return transaction.getID();
    }

    public String getCompanyName() {
        return transaction.getCompanyName();
    }

    public int getRoomNumber() {
        return transaction.getRoomNumber();
    }

    public Calendar getStartDate() {
        return transaction.getStartDate();
    }

    public Calendar getEndDate() {
        return transaction.getEndDate();
    }

    public double getPrice() {
        return transaction.getPrice();
    }


}
