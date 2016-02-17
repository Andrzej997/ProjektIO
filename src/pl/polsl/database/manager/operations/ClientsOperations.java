package pl.polsl.database.manager.operations;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import pl.polsl.database.entities.Clients;
import pl.polsl.database.entities.IEntity;
import pl.polsl.database.entities.Promotions;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;
import pl.polsl.database.exceptions.ArgsNotCorrectException;

/**
 * Clients operation handler class
 *
 * @author Mateusz Sojka
 * @version 1.5
 */
public class ClientsOperations implements IOperate {

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
    public IEntity createEntity(Object... args)
            throws ArgsLengthNotCorrectException, ArgsNotCorrectException {
        if (args.length != 3) {
            throw new ArgsLengthNotCorrectException("Args count are not correct");
        } else {
            Clients client = null;
            try {
                client = new Clients((String) args[0], (Double) args[1], (List<Promotions>) args[2]);
            } catch (NullPointerException | NumberFormatException | ClassCastException ex) {
                throw new ArgsNotCorrectException("WRONG ARGS IN CLIENTS CREATE ENTITY METHOD" + ex.getMessage());
            }
            return client;
        }
    }

    /**
     * Add entity to table method
     *
     * @param entity IEntity typed object to add
     */
    @Override
    public List addEntity(IEntity entity) {
        Clients clients = (Clients) entity;
        em.getTransaction().begin();
        em.persist(clients);
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
            Clients client = (Clients) entity;
            try {
                em.getTransaction().begin();
                client = em.find(Clients.class, client.getId());
                int i = 0;
                for (String name : argNames) {
                    switch (name.toUpperCase()) {
                        case "CLIENT_OR_COMPANY_NAME":
                            client.setClientOrCompanyName((String) args[i]);
                            i++;
                            break;
                        case "PRICE":
                            client.setPrice(Double.parseDouble((String) args[i]));
                            i++;
                            break;
                        case "RESERVATION":
                            client.setReservations((List<Promotions>) args[i]);
                            i++;
                            break;
                        default:
                            break;
                    }
                }
                em.getTransaction().commit();
            } catch (NullPointerException | NumberFormatException | ClassCastException ex) {
                em.getTransaction().rollback();
                throw new ArgsNotCorrectException("WRONG ARGS IN CLIENTS MODIFY ENTITY METHOD" + ex.getMessage());
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
        Clients client = (Clients) entity;
        Clients find = em.getReference(Clients.class, client.getId());
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
        CriteriaQuery<Clients> criteriaQuery = cb.createQuery(Clients.class);
        Root<Clients> client = criteriaQuery.from(Clients.class);
        List<Predicate> predicates = new ArrayList<>();
        int i = 0;
        for (String name : argsNames) {
            predicates.add(cb.equal(client.get(name), args[i].toString()));
            i++;
        }
        criteriaQuery.select(client).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Clients> query = em.createQuery(criteriaQuery);
        List<Clients> resultList = query.getResultList();
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
