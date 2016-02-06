/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import pl.polsl.database.entities.Tickets;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;

/**
 *
 * @author matis
 */
public class TicketsOperations implements IOperate {

    EntityManager em;

    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public Tickets createEntity(Object... args) throws ArgsLengthNotCorrectException {
        if (args.length != 7) {
            throw new ArgsLengthNotCorrectException("Args count are not correct");
        } else {
            Tickets ticket = new Tickets(Double.parseDouble((String)args[0]), 
                    Integer.parseInt((String)args[1]), Integer.parseInt((String)args[2]),
                    Integer.parseInt((String)args[3]), Integer.parseInt((String)args[4]), 
                    (Time) args[5], (Date) args[6]);
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
    public void modifyEntity(IEntity entity, ArrayList<String> argNames, Object... args) {
        if (findEntity(entity) && args.length == 7) {
            Tickets ticket = (Tickets) entity;
            em.getTransaction().begin();
            ticket = em.find(Tickets.class, ticket);
            int i = 0;
            for (String name : argNames) {
                switch (name) {
                    case "price":
                        ticket.setPrice(Double.parseDouble((String)args[i]));
                        i++;
                        break;
                    case "chairNumber":
                        ticket.setChairNumber(Integer.parseInt((String)args[i]));
                        i++;
                        break;
                    case "rowNumber":
                        ticket.setRowNumber(Integer.parseInt((String)args[i]));
                        i++;
                        break;
                    case "state":
                        ticket.setState(Integer.parseInt((String)args[i]));
                        i++;
                        break;
                    case "roomNumber":
                        ticket.setRoomNumber(Integer.parseInt((String)args[i]));
                        i++;
                        break;
                    case "time":
                        ticket.setTime((Time) args[i]);
                        i++;
                        break;
                    case "date":
                        ticket.setDate((Date) args[i]);
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
        Tickets ticket = (Tickets) entity;
        return em.contains(ticket);
    }

    @Override
    public IEntity findEntity(ArrayList<String> argsNames, Object... args) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tickets> criteriaQuery = cb.createQuery(Tickets.class);
        Root<Tickets> ticket = criteriaQuery.from(Tickets.class);
        List<Predicate> predicates = new ArrayList<>();
        int i = 0;
        for (String name : argsNames) {
            predicates.add(cb.equal(ticket.get(name), args[i]));
            i++;
        }
        criteriaQuery.select(ticket).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Tickets> query = em.createQuery(criteriaQuery);
        List<Tickets> resultList = query.getResultList();
        return resultList.get(0);
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
    public List realizeQuery(String query) {
        Query q = em.createQuery(query);
        List result = q.getResultList();
        return result;
    }

}
