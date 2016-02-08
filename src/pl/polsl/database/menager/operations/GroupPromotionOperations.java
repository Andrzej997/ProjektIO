package pl.polsl.database.menager.operations;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import pl.polsl.database.entities.IEntity;
import pl.polsl.database.entities.Reservations;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;

public class GroupPromotionOperations implements IOperate{
    EntityManager em;

    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public Reservations createEntity(Object... args) throws ArgsLengthNotCorrectException {
        if (args.length != 2) {
            throw new ArgsLengthNotCorrectException("Args count are not correct");
        } else {
            Reservations reservation = new Reservations(Integer.parseInt((String)args[0]),
                   Integer.parseInt((String)args[1]));
            return reservation;
        }
    }

    @Override
    public void addEntity(IEntity entity) {
        Reservations reservation = (Reservations) entity;
        em.getTransaction().begin();
        em.persist(reservation);
        em.getTransaction().commit();
    }

    @Override
    public void modifyEntity(IEntity entity, ArrayList<String> argNames, Object... args) {
        if (findEntity(entity) && args.length == 3) {
            Reservations reservation = (Reservations) entity;
            em.getTransaction().begin();
            reservation = em.find(Reservations.class, reservation);
            int i = 0;
            for (String name : argNames) {
                switch (name) {
                    case "minimalAmount":
                        reservation.setMinimalAmount(Integer.parseInt((String)args[i]));
                        i++;
                        break;
                    case "sale":
                        reservation.setSale(Integer.parseInt((String)args[i]));
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
        Reservations reservation = (Reservations) entity;
        return em.contains(reservation);
    }

    @Override
    public List findEntity(ArrayList<String> argsNames, Object... args) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Reservations> criteriaQuery = cb.createQuery(Reservations.class);
        Root<Reservations> reservation  = criteriaQuery.from(Reservations.class);
        List<Predicate> predicates = new ArrayList<>();
        int i = 0;
        for (String name : argsNames) {
            predicates.add(cb.equal(reservation.get(name), Integer.parseInt((String)args[i])));
            i++;
        }
        criteriaQuery.select(reservation).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Reservations> query = em.createQuery(criteriaQuery);
        List<Reservations> resultList = query.getResultList();
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
