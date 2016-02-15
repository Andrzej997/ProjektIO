package pl.polsl.company.controller;

import java.sql.Time;
import pl.polsl.company.model.ApplicationContext;
import pl.polsl.company.model.Transaction;
import pl.polsl.database.entities.Transactions;
import pl.polsl.database.manager.DAOManager;
import pl.polsl.database.manager.operations.TransactionsOperations;

import java.util.Date;
import java.util.List;

import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;

import javax.persistence.EntityManager;

/**
 * Created by Krzysztof Stręk on 2016-02-08.
 */
public class BusinessServiceController {

    private final ApplicationContext applicationContext;
    private final TransactionsOperations transactionsOperations;
    
    public BusinessServiceController(ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;

        EntityManager em = DAOManager.getInstance("kino").getEntityManager();

        transactionsOperations = new TransactionsOperations();
        transactionsOperations.setEntityManager(em);

    }
    
    public void logout() {
        //TODO zwalnianie zasobów, możliwe że nie będzie potrzebne
    }
    
    public void createNewRoomRentTransaction(int duration, String contractorName, int roomNumber, Date date, Time time) {
        try {
            Transactions reservation = transactionsOperations.createEntity(contractorName, time, date, roomNumber, false);
            transactionsOperations.addEntity(reservation);
        } catch (ArgsLengthNotCorrectException ex) {
            System.err.print(ex.getMessage());
        }
        //TODO dodawanie odrazu do listy albo odświerzanie z bazy
    }
    
    public void createNewAdvertisementTransaction(double price, Date dateFrom, Date dateTo) {
    }

    public List<Transaction> getAllTransactions() {
        return applicationContext.getTransactions();
    }
    
}