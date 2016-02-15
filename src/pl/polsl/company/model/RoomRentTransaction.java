package pl.polsl.company.model;

import pl.polsl.database.entities.Transactions;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Krzysztof Stręk on 2016-01-29.
 */
public class RoomRentTransaction extends Transaction {

    public RoomRentTransaction(Transactions t) {
        super(t);
    }

    public int getRoomNumber() {
        return transactionEntity.getRoomNumber();
    }

    @Override
    public void accept() {

    }

    @Override
    public void reject() {

    }
}
