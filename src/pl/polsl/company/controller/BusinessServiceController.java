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
import pl.polsl.database.manager.operations.OperationHandler;

/**
 * Class to handler request from consultant gui
 *
 * Created by Krzysztof Stręk on 2016-02-08.
 */
public class BusinessServiceController {

    /**
     * Field with aplication context
     */
    private ApplicationContext applicationContext;

    /**
     * Field with transactionsOperations object
     */
    private final TransactionsOperations transactionsOperations;

    /**
     * Constructor
     *
     * @param applicationContext ApliactionContext object
     */
    public BusinessServiceController(ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;

        EntityManager em = DAOManager.getInstance("kino").getEntityManager();

        transactionsOperations = new TransactionsOperations();
        transactionsOperations.setEntityManager(em);

    }

    /**
     * Method to create new room rent transaction
     *
     * @param startDate Calendar with startDate
     * @param endDate Calendar with endDate
     * @param price Double with price
     * @param contractorName String with company name
     * @param roomNumber Integer with room number
     * @param type Integer with transaction type
     */
    public void createNewRoomRentTransaction(Calendar startDate, Calendar endDate, Double price, String contractorName, Integer roomNumber, Integer type) {
        try {
            OperationHandler handler = new OperationHandler(DAOManager.getInstance("kino").getEntityManager());
            List<Transactions> list = handler.handleRequest("TRANSACTIONS", "ADD_ENTITY", startDate, endDate, price, contractorName, roomNumber, type, false);
            applicationContext.getTransactionList().add(new RoomRentTransaction(list.get(0)));
        } catch (ArgsLengthNotCorrectException ex) {
            System.err.print(ex.getMessage());
        }
    }

    /**
     * Method to ger all transactions list
     *
     * @return List of Transactions
     */
    public List<Transaction> getAllTransactions() {
        return applicationContext.getTransactionList().getTransactions();
    }

    /**
     * Method to get all room rent transactions list
     *
     * @return List of room rent transactions
     */
    public List<RoomRentTransaction> getAllRoomRentTransactions() {
        List<RoomRentTransaction> result = new ArrayList<>();

        for (Transaction transaction : applicationContext.getTransactionList().getTransactions()) {
            if (transaction.getType() == 0) {
                result.add((RoomRentTransaction) (transaction));
            }
        }
        return result;
    }

    /**
     * Method to get all seances
     *
     * @return List of seances
     */
    public List<Seances> getAllSeances() {
        String query = "SELECT e FROM Seances e";

        return DAOManager.getInstance("kino").realizeQuery(query);
    }

    /**
     * Method to cancel room reservation
     *
     * @param id Integer with reservation id
     */
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

    /**
     * @param applicationContext the applicationContext to set
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * @return the applicationContext
     */
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
