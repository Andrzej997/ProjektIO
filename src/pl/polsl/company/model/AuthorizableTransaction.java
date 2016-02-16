package pl.polsl.company.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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

    @Override
    public String toString() {
        //"ID;Nazwa firmy;Numer sali;Godzina;Data;Cena"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");	
        return getID() + ";" + getCompanyName() + ";" + getRoomNumber() + ";" 
                + sdf.format(getStartDate().getTime()) + ";" 
                + sdf2.format(getStartDate().getTime()) + ";" + getPrice();
    }
}
