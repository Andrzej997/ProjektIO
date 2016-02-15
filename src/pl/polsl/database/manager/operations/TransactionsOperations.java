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

    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public void addEntity(IEntity entity) {
        Transactions transaction = (Transactions) entity;
        em.getTransaction().begin();
        em.persist(transaction);
        em.getTransaction().commit();
    }

    @Override
    public boolean findEntity(IEntity entity) {
        Transactions transaction = (Transactions) entity;
        return em.contains(transaction);
    }

    @Override
    public void deleteEntity(IEntity entity) {
        if (findEntity(entity)) {
            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
        }
    }

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

    @Override
    public void modifyEntity(IEntity entity, ArrayList<String> argNames, Object... args) 
            throws ArgsNotCorrectException{
        if (findEntity(entity) && args.length == 2) {
            Transactions transaction = (Transactions) entity;
            try {
                em.getTransaction().begin();
                transaction = em.find(Transactions.class, transaction);
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
