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
import pl.polsl.database.entities.Rooms;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;
import pl.polsl.database.exceptions.ArgsNotCorrectException;

/**
 * Rooms operations handler class
 *
 * @author Mateusz Sojka
 * @version 1.5
 */
public class RoomsOperations implements IOperate {

    EntityManager em;

    /**
     * Method to set Entity Manager to entity class
     *
     * @param em EntityManager object
     */
    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    /**
     * Method to create entity
     *
     * @param args variable array of args, depedant to entity
     * @return IEntity typed Object
     * @throws ArgsLengthNotCorrectException when varargs count are not correct
     * @throws ArgsNotCorrectException when varargs data are not correct
     */
    @Override
    public Rooms createEntity(Object... args)
            throws ArgsLengthNotCorrectException, ArgsNotCorrectException {
        if (args.length != 1) {
            throw new ArgsLengthNotCorrectException("Arguments are not correct");
        } else {
            Rooms room = null;
            try {
                room = new Rooms((Integer) args[0]);
            } catch (NullPointerException | NumberFormatException | ClassCastException ex) {
                throw new ArgsNotCorrectException("WRONG ARGS IN ROOMS CREATE ENTITY METHOD" + ex.getMessage());
            }
            return room;
        }
    }

    /**
     * Add entity to table method
     *
     * @param entity IEntity typed object to add
     */
    @Override
    public List addEntity(IEntity entity) {
        Rooms room = (Rooms) entity;
        em.getTransaction().begin();
        em.persist(room);
        em.getTransaction().commit();
        List<IEntity> val = new ArrayList<>();
        val.add(entity);
        return val;
    }

    /**
     * Modify entity
     *
     * @param entity - Entity to modify
     * @param argNames - Array List with column names, depedant to modified
     * values
     * @param args - Varargs array with values to modify
     * @throws ArgsNotCorrectException when args count are not correct
     */
    @Override
    public void modifyEntity(IEntity entity, ArrayList<String> argNames, Object... args)
            throws ArgsNotCorrectException {
        if (findEntity(entity)) {
            Rooms room = (Rooms) entity;
            try {
                em.getTransaction().begin();
                room = em.find(Rooms.class, room.getId());
                for (String name : argNames) {
                    if ("CAPACITY".equalsIgnoreCase(name)) {
                        room.setCapacity((Integer) args[0]);
                    }
                }
                em.getTransaction().commit();
            } catch (NullPointerException | NumberFormatException | ClassCastException ex) {
                em.getTransaction().rollback();
                throw new ArgsNotCorrectException("WRONG ARGS IN ROOMS MODIFY ENTITY METHOD" + ex.getMessage());
            }
        }
    }

    /**
     * Find entity method
     *
     * @param entity Object to find
     * @return return true if entity exists, otherwise return false
     */
    @Override
    public boolean findEntity(IEntity entity) {
        Rooms room = (Rooms) entity;
        Rooms find = em.find(Rooms.class, room.getId());
        return find != null;
    }

    /**
     * Method to find entity in table
     *
     * @param argsNames Column names, used to find by column
     * @param args Varargs array with values, dependent to argsNames
     * @return List of found entities
     */
    @Override
    public List isEntityExists(ArrayList<String> argsNames, Object... args) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rooms> criteriaQuery = cb.createQuery(Rooms.class);
        Root<Rooms> rooms = criteriaQuery.from(Rooms.class);
        List<Predicate> predicates = new ArrayList<>();
        int i = 0;
        for (String name : argsNames) {
            predicates.add(cb.equal(rooms.get(name), args[i].toString()));
            i++;
        }
        criteriaQuery.select(rooms).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Rooms> query = em.createQuery(criteriaQuery);
        List<Rooms> resultList = query.getResultList();
        return resultList;
    }

    /**
     * Method to delete entity from table
     *
     * @param entity found Entity
     */
    @Override
    public void deleteEntity(IEntity entity) {
        if (findEntity(entity)) {
            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
        }
    }

}
