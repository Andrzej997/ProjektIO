package pl.polsl.company.controller;

import java.sql.Time;
import pl.polsl.company.model.ApplicationContext;
import pl.polsl.company.model.Transaction;
import pl.polsl.database.entities.Transactions;
import pl.polsl.database.manager.DAOManager;
import pl.polsl.database.manager.operations.AdsSellingOperations;
import pl.polsl.database.manager.operations.RoomsReservationOperations;

import java.util.Date;
import java.util.List;

import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;

import javax.persistence.EntityManager;

/**
 * Created by Krzysztof Stręk on 2016-02-08.
 */
public class BusinessServiceController {

    private final ApplicationContext applicationContext;
    private final RoomsReservationOperations roomsReservationOperations;
    private final AdsSellingOperations adsSellingOperations;
    
    public BusinessServiceController(ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;

        EntityManager em = DAOManager.getInstance("kino").getEntityManager();

        roomsReservationOperations = new RoomsReservationOperations();
        roomsReservationOperations.setEntityManager(em);

        adsSellingOperations = new AdsSellingOperations();
        adsSellingOperations.setEntityManager(em);
    }
    
    public void logout() {
        //TODO zwalnianie zasobów, możliwe że nie będzie potrzebne
    }
    
    public void createNewRoomRentTransaction(int duration, String contractorName, int roomNumber, Date date, Time time) {
        try {
            Transactions reservation = roomsReservationOperations.createEntity(contractorName, time, date, roomNumber, false);
            roomsReservationOperations.addEntity(reservation);
        } catch (ArgsLengthNotCorrectException ex) {
            System.err.print(ex.getMessage());
        }
        //TODO dodawanie odrazu do listy albo odświerzanie z bazy
    }
    
    public void createNewAdvertisementTransaction(double price, Date dateFrom, Date dateTo) {
        try {
            adsSellingOperations.addEntity(adsSellingOperations.createEntity(price, dateFrom, dateTo, false));
        } catch (ArgsLengthNotCorrectException ex) {
            System.err.print(ex.getMessage());
        }
    }

    public List<Transaction> getAllTransactions() {
        return applicationContext.getTransactions();
    }
    
}