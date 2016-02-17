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
import pl.polsl.database.entities.Promotions;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;
import pl.polsl.database.exceptions.ArgsNotCorrectException;

/**
 * Group promotions operations handler class
 *
 * @author Mateusz Sojka
 * @version 1.5
 */
public class GroupPromotionOperations implements IOperate {

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
    public Promotions createEntity(Object... args)
            throws ArgsLengthNotCorrectException, ArgsNotCorrectException {
        if (args.length != 2) {
            throw new ArgsLengthNotCorrectException("Args count are not correct");
        } else {
            Promotions reservation = null;
            try {
                reservation = new Promotions((Integer) args[0],
                        (Integer) args[1]);
            } catch (NullPointerException | NumberFormatException | ClassCastException ex) {
                throw new ArgsNotCorrectException("WRONG ARGS IN GROUP_PROMOTIONS CREATE ENTITY METHOD" + ex.getMessage());
            }
            return reservation;
        }
    }

    /**
     * Add entity to table method
     *
     * @param entity IEntity typed object to add
     */
    @Override
    public List addEntity(IEntity entity) {
        Promotions reservation = (Promotions) entity;
        em.getTransaction().begin();
        em.persist(reservation);
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
            Promotions reservation = (Promotions) entity;
            try {
                em.getTransaction().begin();
                reservation = em.find(Promotions.class, reservation.getId());
                int i = 0;
                for (String name : argNames) {
                    switch (name.toUpperCase()) {
                        case "MINIMAL_AMOUNT":
                            reservation.setMinimalAmount((Integer) args[i]);
                            i++;
                            break;
                        case "SALE":
                            reservation.setSale((Integer) args[i]);
                            i++;
                            break;
                        default:
                            break;
                    }
                }
                em.getTransaction().commit();
            } catch (NullPointerException | NumberFormatException | ClassCastException ex) {
                em.getTransaction().rollback();
                throw new ArgsNotCorrectException("WRONG ARGS IN GROUP_PROMOTIONS MODIFY ENTITY METHOD" + ex.getMessage());
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
        Promotions reservation = (Promotions) entity;
        Promotions find = em.find(Promotions.class, reservation.getId());
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
        CriteriaQuery<Promotions> criteriaQuery = cb.createQuery(Promotions.class);
        Root<Promotions> reservation = criteriaQuery.from(Promotions.class);
        List<Predicate> predicates = new ArrayList<>();
        int i = 0;
        for (String name : argsNames) {
            predicates.add(cb.equal(reservation.get(name), args[i].toString()));
            i++;
        }
        criteriaQuery.select(reservation).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Promotions> query = em.createQuery(criteriaQuery);
        List<Promotions> resultList = query.getResultList();
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
