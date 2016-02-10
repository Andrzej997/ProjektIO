package pl.polsl.database.manager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import pl.polsl.database.entities.Users;

/**
 *
 * @author MateuszSojka
 *
 */
public class DAOManager {

    /**
     * @return the menager
     */
    public static DAOManager getMenager() {
        return menager;
    }

    private final EntityManagerFactory emf;
    private final EntityManager entityManager;

    private static DAOManager menager;

    private DAOManager(String databaseName) {
        emf = Persistence.createEntityManagerFactory(databaseName);
        entityManager = emf.createEntityManager();
    }

    public static synchronized DAOManager getInstance(String databaseName) {
        if (menager != null) {
            return menager;
        } else {
            return new DAOManager(databaseName);
        }
    }

    public boolean authentificateUser(String user, String password) {
        CriteriaBuilder cb = emf.getCriteriaBuilder();
        CriteriaQuery<Users> criteriaQuery = cb.createQuery(Users.class);
        Root<Users> users = criteriaQuery.from(Users.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(users.get("USERNAME"), user));
        predicates.add(cb.equal(users.get("PASSWORD"), password));
        criteriaQuery.select(users).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Users> query = entityManager.createQuery(criteriaQuery);
        List<Users> resultList = query.getResultList();
        return !resultList.isEmpty();
    }

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
    
    public String getUserType(String username){
        CriteriaBuilder cb = emf.getCriteriaBuilder();
        CriteriaQuery<Users> criteriaQuery = cb.createQuery(Users.class);
        Root<Users> users = criteriaQuery.from(Users.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(users.get("USERNAME"), username));
        criteriaQuery.select(users).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Users> query = entityManager.createQuery(criteriaQuery);
        List<Users> resultList = query.getResultList();
        return resultList.get(0).getType();
    }

    /**
     * @return the entityManager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }
    
}
