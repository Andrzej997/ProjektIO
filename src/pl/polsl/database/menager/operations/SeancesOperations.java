package pl.polsl.database.menager.operations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import pl.polsl.database.entities.Films;
import pl.polsl.database.entities.IEntity;
import pl.polsl.database.entities.Rooms;
import pl.polsl.database.entities.Seances;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;

/**
 *
 * @author matis
 */
public class SeancesOperations implements IOperate {

    EntityManager em;

    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public Seances createEntity(Object... args) throws ArgsLengthNotCorrectException {
        if (args.length != 4) {
            throw new ArgsLengthNotCorrectException("Args count are not correct");
        } else {
            Seances seance = new Seances((Films) args[0], (Rooms) args[1], 
                    (Calendar) args[2], (Double)args[0]);
            return seance;
        }
    }

    @Override
    public void addEntity(IEntity entity) {
        Seances seance = (Seances) entity;
        em.getTransaction().begin();
        em.persist(seance);
        em.getTransaction().commit();
    }

    @Override
    public void modifyEntity(IEntity entity, ArrayList<String> argNames, Object... args) {
        if (findEntity(entity) && args.length == 4) {
            Seances seance = (Seances) entity;
            em.getTransaction().begin();
            seance = em.find(Seances.class, seance);
            int i = 0;
            for (String name : argNames) {
                switch (name.toUpperCase()) {
                    case "FILM":
                        seance.setFilm((Films) args[i]);
                        i++;
                        break;
                    case "ROOM":
                        seance.setRoom((Rooms) args[i]);
                        i++;
                        break;
                    case "SEANCE_DATE":
                        seance.setDate((Calendar) args[i]);
                        i++;
                        break;
                    case "BASIC_TICKET_PRICE":
                        seance.setBasicTicketPrice((Double)args[i]);
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
        Seances seance = (Seances) entity;
        return em.contains(seance);
    }

    @Override
    public List isEntityExists(ArrayList<String> argsNames, Object... args) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Seances> criteriaQuery = cb.createQuery(Seances.class);
        Root<Seances> seance = criteriaQuery.from(Seances.class);
        List<Predicate> predicates = new ArrayList<>();
        int i = 0;
        for (String name : argsNames) {
            predicates.add(cb.equal(seance.get(name), args[i]));
            i++;
        }
        criteriaQuery.select(seance).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Seances> query = em.createQuery(criteriaQuery);
        List<Seances> resultList = query.getResultList();
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
