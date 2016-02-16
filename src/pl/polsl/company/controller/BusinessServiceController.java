package pl.polsl.company.controller;

import pl.polsl.company.model.ApplicationContext;
import pl.polsl.company.model.RoomRentTransaction;
import pl.polsl.company.model.Transaction;
import pl.polsl.database.entities.Seances;
import pl.polsl.database.entities.Transactions;
import pl.polsl.database.manager.DAOManager;
import pl.polsl.database.manager.operations.TransactionsOperations;

import java.util.ArrayList;
import java.util.Calendar;
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
    
    public void createNewRoomRentTransaction(Calendar startDate, Calendar endDate, Double price, String contractorName, Integer roomNumber, Integer type) {
        try {
            Transactions reservation = transactionsOperations.createEntity(startDate, 
                    endDate, price, contractorName, roomNumber, type, false);
            transactionsOperations.addEntity(reservation);
            applicationContext.getTransactionList().add(new RoomRentTransaction(reservation));
        } catch (ArgsLengthNotCorrectException ex) {
            System.err.print(ex.getMessage());
        }
    }


    public List<Transaction> getAllTransactions() {
        return applicationContext.getTransactionList().getTransactions();
    }

    public List<RoomRentTransaction> getAllRoomRentTransactions() {
        List<RoomRentTransaction> result = new ArrayList<>();

        for (Transaction transaction : applicationContext.getTransactionList().getTransactions()) {
            if (transaction.getType() == 0) {
                result.add((RoomRentTransaction)(transaction));
            }
        }
        return result;
    }

    public List<Seances> getAllSeances() {
        String query = "SELECT e FROM Seances e";

        return DAOManager.getInstance("kino").realizeQuery(query);
    }

    public void cancelRoomReservation(int id) {
        List<Transaction> transactions = applicationContext.getTransactionList().getTransactions();

        for (Transaction t : transactions) {
            if (t.getID() == id) {
                t.delete();
                transactions.remove(t);
                break;
            }
        }
    }

    
}