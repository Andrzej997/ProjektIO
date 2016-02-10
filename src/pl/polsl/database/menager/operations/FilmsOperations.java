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
import pl.polsl.database.entities.Films;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;

/**
 *
 * @author matis
 */
public class FilmsOperations implements IOperate {

    EntityManager em;

    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public void addEntity(IEntity entity) {
        Films film = (Films) entity;
        em.getTransaction().begin();
        em.persist(film);
        em.getTransaction().commit();
    }

    @Override
    public boolean findEntity(IEntity entity) {
        Films film = (Films) entity;
        return em.contains(film);
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
    public Films createEntity(Object... args) throws ArgsLengthNotCorrectException{
        if (args.length != 2) {
            throw new ArgsLengthNotCorrectException("Arguments are not correct");
        } else {
            Films film = new Films((String) args[0], (String) args[1]);
            return film;
        }
    }

    @Override
    public void modifyEntity(IEntity entity, ArrayList<String> argNames, Object... args) {
        if (findEntity(entity) && args.length == 2) {
            Films film = (Films) entity;
            em.getTransaction().begin();
            film = em.find(Films.class, film);
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
        }
    }

    @Override
    public List isEntityExists(ArrayList<String> argsNames, Object... args) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Films> criteriaQuery = cb.createQuery(Films.class);
        Root<Films> films = criteriaQuery.from(Films.class);
        List<Predicate> predicates = new ArrayList<>();
        int i =0;
        for(String name : argsNames){
            predicates.add(cb.equal(films.get(name), args[i]));
            i++;
        }
        criteriaQuery.select(films).where(predicates.toArray(new Predicate[] {}));
        TypedQuery<Films> query = em.createQuery(criteriaQuery);
        List<Films> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List realizeQuery(String query) {
        Query q = em.createQuery(query);
        List result = q.getResultList();
        return result;
        
    }
    
}
