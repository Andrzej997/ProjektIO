package pl.polsl.database.manager.operations;

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
import pl.polsl.database.entities.Rooms;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;

public class RoomsOperations implements IOperate {

    EntityManager em;

    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public Rooms createEntity(Object... args) throws ArgsLengthNotCorrectException {
        if (args.length != 1) {
            throw new ArgsLengthNotCorrectException("Arguments are not correct");
        } else {
            Rooms room = new Rooms((Integer)args[0]);
            return room;
        }
    }

    @Override
    public void addEntity(IEntity entity) {
        Rooms room = (Rooms) entity;
        em.getTransaction().begin();
        em.persist(room);
        em.getTransaction().commit();
    }

    @Override
    public void modifyEntity(IEntity entity, ArrayList<String> argNames, Object... args) {
        if (findEntity(entity) && args.length == 1) {
            Rooms room = (Rooms) entity;
            em.getTransaction().begin();
            room = em.find(Rooms.class, room);
            for (String name : argNames) {
                if ("CAPACITY".equalsIgnoreCase(name)) {
                    room.setCapacity((Integer)args[0]);
                }
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean findEntity(IEntity entity) {
        Rooms room = (Rooms) entity;
        return em.contains(room);
    }

    @Override
    public List isEntityExists(ArrayList<String> argsNames, Object... args) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rooms> criteriaQuery = cb.createQuery(Rooms.class);
        Root<Rooms> rooms = criteriaQuery.from(Rooms.class);
        List<Predicate> predicates = new ArrayList<>();
        int i = 0;
        for (String name : argsNames) {
            predicates.add(cb.equal(rooms.get(name), Integer.parseInt((String)args[i])));
            i++;
        }
        criteriaQuery.select(rooms).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Rooms> query = em.createQuery(criteriaQuery);
        List<Rooms> resultList = query.getResultList();
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
