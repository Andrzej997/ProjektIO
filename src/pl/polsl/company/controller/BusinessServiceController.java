package pl.polsl.company.controller;

import java.sql.Time;
import pl.polsl.company.model.AdvertisementTransaction;
import pl.polsl.company.model.RoomRentTransaction;
import pl.polsl.company.model.Transaction;
import pl.polsl.database.manager.DAOManager;
import pl.polsl.database.menager.operations.OperationHandler;
import pl.polsl.database.menager.operations.RoomsReservationOperations;

import java.util.Date;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;

/**
 * Created by Krzysztof Stręk on 2016-02-08.
 */
public class BusinessServiceController {
    
    private final OperationHandler operationHandler;
    
    private final RoomsReservationOperations roomsReservationOperations;
    
    BusinessServiceController(OperationHandler operationHandler) {
        this.operationHandler = operationHandler;
        roomsReservationOperations = new RoomsReservationOperations();
        roomsReservationOperations.setEntityManager(DAOManager.getInstance("kino").getEntityManager());
    }
    
    public void logout() {
        //TODO zwalnianie zasobów, możliwe że nie będzie potrzebne
    }
    
    public void createNewRoomRentTransaction(int duration, String contractorName, int roomNumber, Date date, Time time) {
        try {
            operationHandler.handleOperation("ADD_ENTITY", roomsReservationOperations, contractorName, time, date, roomNumber, false);
        } catch (ArgsLengthNotCorrectException ex) {
            System.err.print(ex.getMessage());
        }
        // mozesz tez to zrobić tak:
        //operationHandler.handleRequest("ROOMS_RENTING", "ADD_ENTITY", contractorName, time, date, roomNumber, false);
        //TODO dodawanie odrazu do listy albo odświerzanie z bazy
    }
    
    public void createNewAdvertisementTransaction(double price, Date dateFrom, Date dateTo) {
        
        Transaction transaction = new AdvertisementTransaction(price, dateFrom, dateTo);
    }
    
}
