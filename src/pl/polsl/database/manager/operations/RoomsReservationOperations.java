package pl.polsl.database.manager.operations;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
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

/**
 *
 * @author matis
 */
public class RoomsReservationOperations implements IOperate {

    EntityManager em;

    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public Transactions createEntity(Object... args) throws ArgsLengthNotCorrectException {
        if (args.length != 6) {
            throw new ArgsLengthNotCorrectException("Args count are not correct");
        } else {
            Transactions transaction = new Transactions((String) args[0], (Integer)args[1],
                    (Time) args[2], (Date) args[3], (boolean) args[4], (Double)args[5]);
            return transaction;
        }
    }

    @Override
    public void addEntity(IEntity entity) {
        Transactions transaction = (Transactions) entity;
        em.getTransaction().begin();
        em.persist(transaction);
        em.getTransaction().commit();
    }

    @Override
    public void modifyEntity(IEntity entity, ArrayList<String> argNames, Object... args) {
        if (findEntity(entity) && args.length == 6) {
            Transactions transaction = (Transactions) entity;
            em.getTransaction().begin();
            transaction = em.find(Transactions.class, transaction);
            int i = 0;
            for (String name : argNames) {
                switch (name.toUpperCase()) {
                    case "COMPANY_NAME":
                        transaction.setCompanyName((String)args[i]);
                        i++;
                        break;
                    case "ROOM_NUMBER":
                        transaction.setRoomNumber((Integer)args[i]);
                        i++;
                        break;
                    case "TIME_":
                        transaction.setTime((Time)args[i]);
                        i++;
                        break;
                    case "DEADLINE":
                        transaction.setDeadline((Date)args[i]);
                        i++;
                        break;
                    case "ACCEPTED":
                        transaction.setAccepted((boolean)args[i]);
                        break;
                    case "RENT_PRICE":
                        transaction.setRentPrice((Double)args[i]);
                        break;
                    default:
                        break;
                }
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean findEntity(IEntity entity) {
        Transactions transation = (Transactions) entity;
        return em.contains(transation);
    }

    @Override
    public List isEntityExists(ArrayList<String> argsNames, Object... args) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Transactions> criteriaQuery = cb.createQuery(Transactions.class);
        Root<Transactions> transaction  = criteriaQuery.from(Transactions.class);
        List<Predicate> predicates = new ArrayList<>();
        int i = 0;
        for (String name : argsNames) {
            predicates.add(cb.equal(transaction.get(name), args[i]));
            i++;
        }
        criteriaQuery.select(transaction).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Transactions> query = em.createQuery(criteriaQuery);
        List<Transactions> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public void deleteEntity(IEntity entity) {
        if (findEntity(entity)) {
            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
        }
    }

}