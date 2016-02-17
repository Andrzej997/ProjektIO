package pl.polsl.company.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class to create autorizable transaction object
 *
 * Created by Krzysztof Stręk on 2016-01-29.
 */
public class AuthorizableTransaction {

    /**
     * Field contains transaction entity with rooms renting transaction to
     * autorize
     */
    private RoomRentTransaction transaction;

    /**
     * Constructor
     *
     * @param transaction RoomRentTransaction object
     */
    public AuthorizableTransaction(RoomRentTransaction transaction) {
        this.transaction = transaction;
    }

    /**
     * Method to accpet transaction
     */
    public void accept() {
        transaction.persist();
    }

    /**
     * Method to get Room rent transaction
     *
     * @return RoomRentTransaction transaction
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * Method to get id from transaction
     *
     * @return Long with transaction id
     */
    public long getID() {
        return transaction.getID();
    }

    /**
     * Method to get company name from transaction
     *
     * @return String with transaction company name
     */
    public String getCompanyName() {
        return transaction.getCompanyName();
    }

    /**
     * Method to get room number from transaction
     *
     * @return integer with transaction room number
     */
    public int getRoomNumber() {
        return transaction.getRoomNumber();
    }

    /**
     * Method to get start date from transaction
     *
     * @return Calendar with transaction start date
     */
    public Calendar getStartDate() {
        return transaction.getStartDate();
    }

    /**
     * Method to get end date from transaction
     *
     * @return Calendar with transaction end date
     */
    public Calendar getEndDate() {
        return transaction.getEndDate();
    }

    /**
     * Method to get price from transaction
     *
     * @return Double with transaction price
     */
    public double getPrice() {
        return transaction.getPrice();
    }

    /**
     * Method to create string from transaction which can be sent to client
     *
     * @return String with transaction description
     */
    @Override
    public String toString() {
        //"ID;Nazwa firmy;Numer sali;Godzina;Data;Cena"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        return getID() + ";" + getCompanyName() + ";" + getRoomNumber() + ";"
                + sdf.format(getStartDate().getTime()) + ";"
                + sdf2.format(getStartDate().getTime()) + ";" + getPrice();
    }
}
