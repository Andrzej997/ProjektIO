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
import pl.polsl.database.entities.Promotions;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;

public class GroupPromotionOperations implements IOperate{
    EntityManager em;

    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public Promotions createEntity(Object... args) throws ArgsLengthNotCorrectException {
        if (args.length != 2) {
            throw new ArgsLengthNotCorrectException("Args count are not correct");
        } else {
            Promotions reservation = new Promotions((Integer)args[0],
                   (Integer)args[1]);
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
                    case "MINIMAL_AMOUNT":
                        reservation.setMinimalAmount((Integer)args[i]);
                        i++;
                        break;
                    case "SALE":
                        reservation.setSale((Integer)args[i]);
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
            predicates.add(cb.equal(reservation.get(name), Integer.parseInt((String)args[i])));
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
