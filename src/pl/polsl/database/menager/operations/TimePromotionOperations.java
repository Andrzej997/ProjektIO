package pl.polsl.database.menager.operations;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import pl.polsl.database.entities.IEntity;
import pl.polsl.database.entities.Promotions;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;

/**
 *
 * @author matis
 */
public class TimePromotionOperations implements IOperate {

    EntityManager em;

    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public IEntity createEntity(Object... args) throws ArgsLengthNotCorrectException {
        if (args.length != 2) {
            throw new ArgsLengthNotCorrectException("Args count are not correct");
        } else {
            Promotions reservation = new Promotions((Date[]) args[0], (Time[]) args[1],
                    (Integer)args[2]);
            return reservation;
        }
    }

    @Override
    public void addEntity(IEntity entity) {
        Promotions reservation = (Promotions) entity;
        em.getTransaction().begin();
        em.persist(reservation);
        em.getTransaction().commit();
    }

    @Override
    public void modifyEntity(IEntity entity, ArrayList<String> argNames, Object... args) {
        if (findEntity(entity) && args.length == 3) {
            Promotions reservation = (Promotions) entity;
            em.getTransaction().begin();
            reservation = em.find(Promotions.class, reservation);
            int i = 0;
            for (String name : argNames) {
                switch (name.toUpperCase()) {
                    case "DAYS_OF_WEEK":
                        reservation.setDaysOfWeek((Date[])args[i]);
                        i++;
                        break;
                    case "HOURS":
                        reservation.setHours((Time[])args[i]);
                        i++;
                        break;
                    case "TIME_SALE":
                        reservation.setTimeSale((Integer)args[i]);
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
        Promotions reservation = (Promotions) entity;
        return em.contains(reservation);
    }

    @Override
    public List isEntityExists(ArrayList<String> argsNames, Object... args) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Promotions> criteriaQuery = cb.createQuery(Promotions.class);
        Root<Promotions> reservation  = criteriaQuery.from(Promotions.class);
        List<Predicate> predicates = new ArrayList<>();
        int i = 0;
        for (String name : argsNames) {
            predicates.add(cb.equal(reservation.get(name), args[i]));
            i++;
        }
        criteriaQuery.select(reservation).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Promotions> query = em.createQuery(criteriaQuery);
        List<Promotions> resultList = query.getResultList();
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
