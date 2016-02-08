package pl.polsl.company.model;

import pl.polsl.database.entities.Transactions;

import java.util.Date;

/**
 * Created by Krzysztof Stręk on 2016-01-29.
 */
public class RoomRentTransaction extends Transactions implements Transaction {

    private Date date = new Date();

    private String contractorName;

    private int roomNumber;

    public RoomRentTransaction(int duration, String contractorName, int roomNumber, Date date) {

    }

    @Override
    public void accept() {

    }

    @Override
    public void reject() {

    }
}
