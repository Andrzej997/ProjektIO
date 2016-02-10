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
import pl.polsl.database.entities.Clients;
import pl.polsl.database.entities.IEntity;
import pl.polsl.database.entities.Promotions;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;

/**
 *
 * @author matis
 */
public class ClientsOperations implements IOperate{

    EntityManager em;
    
    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public IEntity createEntity(Object... args) throws ArgsLengthNotCorrectException {
        if (args.length != 3) {
            throw new ArgsLengthNotCorrectException("Args count are not correct");
        } else {
            Clients client = new Clients((String)args[0], (Double)args[1], (Promotions)args[2]);
            return client;
        }
    }

    @Override
    public void addEntity(IEntity entity) {
        Clients clients = (Clients) entity;
        em.getTransaction().begin();
        em.persist(clients);
        em.getTransaction().commit();
    }

    @Override
    public void modifyEntity(IEntity entity, ArrayList<String> argNames, Object... args) {
        if (findEntity(entity) && args.length == 3) {
            Clients client = (Clients) entity;
            em.getTransaction().begin();
            client = em.find(Clients.class, client);
            int i = 0;
            for (String name : argNames) {
                switch (name.toUpperCase()) {
                    case "CLIENT_OR_COMPANY_NAME":
                        client.setClientOrCompanyName((String)args[i]);
                        i++;
                        break;
                    case "PRICE":
                        client.setPrice(Double.parseDouble((String)args[i]));
                        i++;
                        break;
                    case "RESERVATION":
                        client.setReservations((Promotions)args[i]);
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
        Clients client = (Clients) entity;
        return em.contains(client);
    }

    @Override
    public List findEntity(ArrayList<String> argsNames, Object... args) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Clients> criteriaQuery = cb.createQuery(Clients.class);
        Root<Clients> client  = criteriaQuery.from(Clients.class);
        List<Predicate> predicates = new ArrayList<>();
        int i = 0;
        for (String name : argsNames) {
            predicates.add(cb.equal(client.get(name), args[i]));
            i++;
        }
        criteriaQuery.select(client).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Clients> query = em.createQuery(criteriaQuery);
        List<Clients> resultList = query.getResultList();
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

    @Override
    public List realizeQuery(String query) {
        Query q = em.createQuery(query);
        List result = q.getResultList();
        return result;
    }
    
}
