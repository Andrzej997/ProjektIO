package pl.polsl.company.controller;

import pl.polsl.company.model.AdvertisementTransaction;
import pl.polsl.company.model.RoomRentTransaction;
import pl.polsl.company.model.Transaction;
import pl.polsl.database.menager.DAOMenager;
import pl.polsl.database.menager.operations.OperationHandler;
import pl.polsl.database.menager.operations.RoomsReservationOperations;

import java.util.Date;

/**
 * Created by Krzysztof Stręk on 2016-02-08.
 */
public class BusinessServiceController {

    private final OperationHandler operationHandler;

    private final RoomsReservationOperations roomsReservationOperations;

    BusinessServiceController(OperationHandler operationHandler) {
        this.operationHandler = operationHandler;
        roomsReservationOperations = new RoomsReservationOperations();
        roomsReservationOperations.setEntityManager(DAOMenager.getInstance("kino").getEntityManager());
    }

    public void logout() {
        //TODO zwalnianie zasobów, możliwe że nie będzie potrzebne
    }

    public void createNewRoomRentTransaction(int duration, String contractorName, int roomNumber, Date date) {

        operationHandler.handleOperation("ADD_ENTITY", roomsReservationOperations, duration, contractorName, roomNumber, date);
        //TODO dodawanie odrazu do listy albo odświerzanie z bazy
    }

    public void createNewAdvertisementTransaction(double price, Date dateFrom, Date dateTo) {

        Transaction transaction = new AdvertisementTransaction(price, dateFrom, dateTo);
    }


}