package pl.polsl.database.manager.operations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import pl.polsl.database.entities.IEntity;
import pl.polsl.database.entities.Transactions;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;
import pl.polsl.database.exceptions.ArgsNotCorrectException;

/**
 * Transaction operations handler class
 *
 * @author Mateusz Sojka
 * @version 1.5
 */
public class TransactionsOperations implements IOperate {

    EntityManager em;

    /**
     * Method to set Entity Manager to entity class
     *
     * @param em EntityManager object
     */
    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    /**
     * Add entity to table method
     *
     * @param entity IEntity typed object to add
     */
    @Override
    public List addEntity(IEntity entity) {
        Transactions transaction = (Transactions) entity;
        em.getTransaction().begin();
        em.persist(transaction);
        em.getTransaction().commit();
        List<IEntity> val = new ArrayList<>();
        val.add(entity);
        return val;
    }

    /**
     * Method to modify found entity
     * 
     * @param entity IEntity object with found entity
     */
    public void modifyEntity(IEntity entity) {
        Transactions transaction = (Transactions) entity;
        if (em.find(Transactions.class, transaction.getId()) != null) {
            Transactions t = em.find(Transactions.class, transaction.getId());
            em.getTransaction().begin();
            t.setAccepted(transaction.isAccepted());
            t.setCompanyName(transaction.getCompanyName());
            t.setEndDateAndTime(transaction.getEndDateAndTime());
            t.setPrice(transaction.getPrice());
            t.setRoomNumber(transaction.getRoomNumber());
            t.setStartDateAndTime(transaction.getStartDateAndTime());
            t.setType(transaction.getType());
            em.getTransaction().commit();
        }
    }

    /**
     * Find entity method
     *
     * @param entity Object to find
     * @return return true if entity exists, otherwise return false
     */
    @Override
    public boolean findEntity(IEntity entity) {
        Transactions transaction = (Transactions) entity;
        Transactions find = em.find(Transactions.class, transaction.getId());
        return find != null;
    }

    /**
     * Method to delete entity from table
     *
     * @param entity found Entity
     */
    @Override
    public void deleteEntity(IEntity entity) {
        if (findEntity(entity)) {
            em.getTransaction().begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            em.getTransaction().commit();
        }
    }

    /**
     * Method to create entity
     *
     * @param args variable array of args, depedant to entity
     * @return IEntity typed Object
     * @throws ArgsLengthNotCorrectException when varargs count are not correct
     * @throws ArgsNotCorrectException when varargs data are not correct
     */
    @Override
    public Transactions createEntity(Object... args)
            throws ArgsLengthNotCorrectException, ArgsNotCorrectException {
        if (args.length != 7) {
            throw new ArgsLengthNotCorrectException("Arguments are not correct");
        } else {
            Transactions transaction = null;
            try {
                transaction = new Transactions((Calendar) args[0],
                        (Calendar) args[1], (Double) args[2], (String) args[3],
                        (Integer) args[4], (Integer) args[5], (Boolean) args[6]);
            } catch (NullPointerException | NumberFormatException | ClassCastException ex) {
                throw new ArgsNotCorrectException("WRONG ARGS IN TRANSACTIONS CREATE ENTITY METHOD" + ex.getMessage());
            }
            return transaction;
        }
    }

    /**
     * Modify entity
     *
     * @param entity - Entity to modify
     * @param argNames - Array List with column names, depedant to modified
     * values
     * @param args - Varargs array with values to modify
     * @throws ArgsNotCorrectException when args count are not correct
     */
    @Override
    public void modifyEntity(IEntity entity, ArrayList<String> argNames, Object... args)
            throws ArgsNotCorrectException {
        if (findEntity(entity)) {
            Transactions transaction = (Transactions) entity;
            try {
                em.getTransaction().begin();
                transaction = em.find(Transactions.class, transaction.getId());
                int i = 0;
                for (String name : argNames) {
                    if (name.equalsIgnoreCase("START_DATE_AND_TIME")) {
                        transaction.setStartDateAndTime((Calendar) args[i]);
                        i++;
                    } else if (name.equalsIgnoreCase("END_DATE_AND_TIME")) {
                        transaction.setEndDateAndTime((Calendar) args[i]);
                        i++;
                    } else if (name.equalsIgnoreCase("PRICE")) {
                        transaction.setPrice((Double) args[i]);
                        i++;
                    } else if (name.equalsIgnoreCase("COMPANY_NAME")) {
                        transaction.setCompanyName((String) args[i]);
                        i++;
                    } else if (name.equalsIgnoreCase("ROOM_NUMBER")) {
                        transaction.setRoomNumber((Integer) args[i]);
                        i++;
                    } else if (name.equalsIgnoreCase("TYPE")) {
                        transaction.setType((Integer) args[i]);
                        i++;
                    } else if (name.equalsIgnoreCase("ACCEPTED")) {
                        transaction.setAccepted((Boolean) args[i]);
                        i++;
                    }
                }
                em.getTransaction().commit();
            } catch (NullPointerException | NumberFormatException | ClassCastException ex) {
                em.getTransaction().rollback();
                throw new ArgsNotCorrectException("WRONG ARGS IN TRANSACTIONS MODIFY ENTITY METHOD" + ex.getMessage());
            }
        }
    }

    /**
     * Method to find entity in table
     *
     * @param argsNames Column names, used to find by column
     * @param args Varargs array with values, dependent to argsNames
     * @return List of found entities
     */
    @Override
    public List isEntityExists(ArrayList<String> argsNames, Object... args) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Transactions> criteriaQuery = cb.createQuery(Transactions.class);
        Root<Transactions> transactions = criteriaQuery.from(Transactions.class);
        List<Predicate> predicates = new ArrayList<>();
        int i = 0;
        for (String name : argsNames) {
            predicates.add(cb.equal(transactions.get(name), args[i].toString()));
            i++;
        }
        criteriaQuery.select(transactions).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Transactions> query = em.createQuery(criteriaQuery);
        List<Transactions> resultList = query.getResultList();
        return resultList;
    }

}
