package pl.polsl.database.manager.operations;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import pl.polsl.database.entities.IEntity;
import pl.polsl.database.entities.Tickets;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;
import pl.polsl.database.exceptions.ArgsNotCorrectException;

/**
 * Tickets operations handler class
 * 
 * @author Mateusz Sojka
 * @version 1.5
 */
public class TicketsOperations implements IOperate {

    EntityManager em;

    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public Tickets createEntity(Object... args) 
            throws ArgsLengthNotCorrectException, ArgsNotCorrectException {
        if (args.length != 7) {
            throw new ArgsLengthNotCorrectException("Args count are not correct");
        } else {
            Tickets ticket = null; 
            try{
            ticket = new Tickets((Double)args[0], 
                    (Integer)args[1], (Integer)args[2],
                    (Integer)args[3], (Integer)args[4], 
                    (Time) args[5], (Date) args[6]);
            } catch(NullPointerException | NumberFormatException | ClassCastException ex){
                throw new ArgsNotCorrectException("WRONG ARGS IN TICKETS CREATE ENTITY METHOD" + ex.getMessage());
            }
            return ticket;
        }
    }

    @Override
    public void addEntity(IEntity entity) {
        Tickets ticket = (Tickets) entity;
        em.getTransaction().begin();
        em.persist(ticket);
        em.getTransaction().commit();
    }

    @Override
    public void modifyEntity(IEntity entity, ArrayList<String> argNames, Object... args) 
            throws ArgsNotCorrectException{
        if (findEntity(entity) && args.length == 7) {
            Tickets ticket = (Tickets) entity;
            try{
            em.getTransaction().begin();
            ticket = em.find(Tickets.class, ticket);
            int i = 0;
            for (String name : argNames) {
                switch (name.toUpperCase()) {
                    case "PRICE":
                        ticket.setPrice((Double)args[i]);
                        i++;
                        break;
                    case "CHAIR_NUMBER":
                        ticket.setChairNumber((Integer)args[i]);
                        i++;
                        break;
                    case "ROW_NUMBER":
                        ticket.setRowNumber((Integer)args[i]);
                        i++;
                        break;
                    case "STATE_":
                        ticket.setState((Integer)args[i]);
                        i++;
                        break;
                    case "ROOM_NUMBER":
                        ticket.setRoomNumber((Integer)args[i]);
                        i++;
                        break;
                    case "TIME_":
                        ticket.setTime((Time) args[i]);
                        i++;
                        break;
                    case "DATE_":
                        ticket.setDate((Date) args[i]);
                        i++;
                        break;
                    default:
                        break;
                }
            }
            em.getTransaction().commit();
            } catch(NullPointerException | NumberFormatException | ClassCastException ex){
                em.getTransaction().rollback();
                throw new ArgsNotCorrectException("WRONG ARGS IN TICKETS MODIFY ENTITY METHOD" + ex.getMessage());
            }
        }
    }

    @Override
    public boolean findEntity(IEntity entity) {
        Tickets ticket = (Tickets) entity;
        return em.contains(ticket);
    }

    @Override
    public List isEntityExists(ArrayList<String> argsNames, Object... args) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tickets> criteriaQuery = cb.createQuery(Tickets.class);
        Root<Tickets> ticket = criteriaQuery.from(Tickets.class);
        List<Predicate> predicates = new ArrayList<>();
        int i = 0;
        for (String name : argsNames) {
            predicates.add(cb.equal(ticket.get(name), args[i].toString()));
            i++;
        }
        criteriaQuery.select(ticket).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Tickets> query = em.createQuery(criteriaQuery);
        List<Tickets> resultList = query.getResultList();
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
