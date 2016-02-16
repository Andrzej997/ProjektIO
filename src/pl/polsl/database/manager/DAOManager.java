package pl.polsl.database.manager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import pl.polsl.database.entities.Users;

/**
 * Database manager class
 * 
 * @author MateuszSojka
 * @version 1.3
 */
public class DAOManager {

    /**
     * @return the DAOManager object
     */
    public static DAOManager getMenager() {
        return menager;
    }

    /**
     * Field contains entity factory object
     */
    private final EntityManagerFactory emf;
    
    /**
     * Field contains entity manager object
     */
    private final EntityManager entityManager;

    
    /**
     * Singleton
     */
    private static DAOManager menager;

    
    /**
     * private constructor
     * @param databaseName String with database name
     */
    private DAOManager(String databaseName) {
        emf = Persistence.createEntityManagerFactory(databaseName);
        entityManager = emf.createEntityManager();
    }

    /**
     * Method to get singleton object
     * 
     * @param databaseName String with database name
     * @return DAOManager object
     */
    public static synchronized DAOManager getInstance(String databaseName) {
        if (menager != null) {
            return menager;
        } else {
            return new DAOManager(databaseName);
        }
    }

    /**
     * Method to autentificate user in database
     * 
     * @param user String with user name
     * @param password String with password
     * @return true if user exists, otherwise false
     */
    public boolean authentificateUser(String user, String password) {
        CriteriaBuilder cb = emf.getCriteriaBuilder();
        CriteriaQuery<Users> criteriaQuery = cb.createQuery(Users.class);
        Root<Users> users = criteriaQuery.from(Users.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(users.get("password"), password));
        predicates.add(cb.equal(users.get("username"), user));
        criteriaQuery.select(users).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Users> query = entityManager.createQuery(criteriaQuery);
        List<Users> resultList = query.getResultList();
        return !resultList.isEmpty();
    }

    /**
     * Method to get table column names
     * @param tableName String with table name
     * @return ArrayList of String with column names
     */
    public ArrayList<String> getTableColumnNames(String tableName) {
        ArrayList<String> colNames = new ArrayList<>();
        Field[] declaredFields = null;
        for (DatabaseTables data : DatabaseTables.values()) {
            if (data.getTableName().equals(tableName)) {
                declaredFields = data.getTableClass().getDeclaredFields();
            }
        }
        for (Field field : declaredFields) {
            Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
            for (Annotation annotation : declaredAnnotations) {
                if (annotation instanceof Column) {
                    Column myAnnotation = (Column) annotation;
                    colNames.add(myAnnotation.name());
                }
            }
        }
        return colNames;
    }

    /**
     * Method to get user type
     * 
     * @param username String with user name
     * @return String with user type
     */
    public String getUserType(String username) {
        CriteriaBuilder cb = emf.getCriteriaBuilder();
        CriteriaQuery<Users> criteriaQuery = cb.createQuery(Users.class);
        Root<Users> users = criteriaQuery.from(Users.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(users.get("username"), username));
        criteriaQuery.select(users).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Users> query = entityManager.createQuery(criteriaQuery);
        List<Users> resultList = query.getResultList();
        return resultList.get(0).getType();
    }

    /**
     * Method to realize query in sql on database
     * @param query String with query
     * @return List of results
     */
    public List realizeQuery(String query) {
        Query q = entityManager.createQuery(query);
        List result = q.getResultList();
        return result;
    }

    /**
     * @return the entityManager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

}
