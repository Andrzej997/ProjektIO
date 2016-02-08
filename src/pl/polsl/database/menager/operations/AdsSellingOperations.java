package pl.polsl.database.menager.operations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pl.polsl.database.entities.IEntity;
import pl.polsl.database.entities.Transactions;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;

/**
 *
 * @author matis
 */
public class AdsSellingOperations implements IOperate{
    
    EntityManager em;

    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public Transactions createEntity(Object... args) throws ArgsLengthNotCorrectException {
        if (args.length != 3) {
            throw new ArgsLengthNotCorrectException("Args count are not correct");
        } else {
            Transactions transaction = new Transactions((Date)args[0]
                    , (Date)args[1], (Double)args[2]);
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
        if (findEntity(entity) && args.length == 3) {
            Transactions transaction = (Transactions) entity;
            em.getTransaction().begin();
            transaction = em.find(Transactions.class, transaction);
            int i = 0;
            for (String name : argNames) {
                switch (name) {
                    case "endDate":
                        transaction.setEndDate((Date)(args[i]));
                        i++;
                        break;
                    case "startDate":
                        transaction.setStartDate((Date)args[i]);
                        i++;
                        break;
                    case "price":
                        transaction.setPrice((Double)args[i]);
                        i++;
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
    public List findEntity(ArrayList<String> argsNames, Object... args) {
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

    @Override
    public List realizeQuery(String query) {
        Query q = em.createQuery(query);
        List result = q.getResultList();
        return result;
    }
    
}
