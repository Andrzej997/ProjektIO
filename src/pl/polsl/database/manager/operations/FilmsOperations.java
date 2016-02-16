package pl.polsl.database.manager.operations;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import pl.polsl.database.entities.IEntity;
import pl.polsl.database.entities.Films;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;
import pl.polsl.database.exceptions.ArgsNotCorrectException;

/**
 * Films operations handler class
 * 
 * @author Mateusz Sojka
 * @version 1.5
 */
public class FilmsOperations implements IOperate {

    EntityManager em;

    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public List addEntity(IEntity entity) {
        Films film = (Films) entity;
        em.getTransaction().begin();
        em.persist(film);
        em.getTransaction().commit();
                List<IEntity> val = new ArrayList<>();
        val.add(entity);
        return val;
    }

    @Override
    public boolean findEntity(IEntity entity) {
        Films film = (Films) entity;
        Films find = em.getReference(Films.class, film.getId());
        return find != null;
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
    public Films createEntity(Object... args)
            throws ArgsLengthNotCorrectException, ArgsNotCorrectException {
        if (args.length != 2) {
            throw new ArgsLengthNotCorrectException("Arguments are not correct");
        } else {
            Films film = null;
            try {
                film = new Films((String) args[0], (String) args[1]);
            } catch (NullPointerException | NumberFormatException | ClassCastException ex) {
                throw new ArgsNotCorrectException("WRONG ARGS IN FILMS CREATE ENTITY METHOD" + ex.getMessage());
            }
            return film;
        }
    }

    @Override
    public void modifyEntity(IEntity entity, ArrayList<String> argNames, Object... args) {
        if (findEntity(entity)) {
            Films film = (Films) entity;
            try{
            em.getTransaction().begin();
            film = em.find(Films.class, film.getId());
            int i = 0;
            for (String name : argNames) {
                if ("TITLE".equalsIgnoreCase(name)) {
                    film.setTitle((String) args[i]);
                    i++;
                } else if ("DURATION_TIME".equalsIgnoreCase(name)) {
                    film.setDurationTime((String) args[i]);
                    i++;
                }
            }
            em.getTransaction().commit();
            } catch(NullPointerException | NumberFormatException | ClassCastException ex){
                em.getTransaction().rollback();
                throw new ArgsNotCorrectException("WRONG ARGS IN FILMS CREATE ENTITY METHOD" + ex.getMessage());
            }
        }
    }

    @Override
    public List isEntityExists(ArrayList<String> argsNames, Object... args) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Films> criteriaQuery = cb.createQuery(Films.class);
        Root<Films> films = criteriaQuery.from(Films.class);
        List<Predicate> predicates = new ArrayList<>();
        int i = 0;
        for (String name : argsNames) {
            predicates.add(cb.equal(films.get(name), args[i].toString()));
            i++;
        }
        criteriaQuery.select(films).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Films> query = em.createQuery(criteriaQuery);
        List<Films> resultList = query.getResultList();
        return resultList;
    }

}
