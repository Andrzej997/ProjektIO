package pl.polsl.company.controller;

import pl.polsl.company.model.AdvertisementTransaction;
import pl.polsl.company.model.RoomRentTransaction;
import pl.polsl.company.model.Transaction;
import pl.polsl.database.entities.Transactions;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by Krzysztof Stręk on 2016-02-08.
 */
public class BusinessServiceController {

    private DateFormat dateFormatter = DateFormat.getDateInstance();

    public boolean authenticateUser(int id, String password) {
        //pobieranie usera i sprawdzanie czy hasło jest takie samo

        return false;   //tmp
    }

    public void logout() {
        //zwalnianie zasobów, możliwe że nie będzie potrzebna
    }

    public void createNewRoomRentTransaction(String duration, String contractorName, String roomNumber, String date) throws ParseException {
        int durationMinutes = Integer.parseInt(duration);
        int room = Integer.parseInt(roomNumber);
        Date rentDate = dateFormatter.parse(date);

        Transaction transaction = new RoomRentTransaction(durationMinutes, contractorName, room, rentDate);
    }

    public void createNewAdvertisementTransaction(String price, String dateFrom, String dateTo) throws ParseException {
        double advPrice = Double.valueOf(price);
        Date startDate = dateFormatter.parse(dateFrom);
        Date endDate = dateFormatter.parse(dateTo);

        Transaction transaction = new AdvertisementTransaction(advPrice, startDate, endDate);
    }
}
