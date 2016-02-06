package pl.polsl.database.menager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pl.polsl.company.protocol.msgWords.DatabaseWord;

/**
 *
 * @author MateuszSojka
 *
 */
public class DAOMenager {

    private final EntityManagerFactory emf;
    private final EntityManager entityManager;

    private static DAOMenager menager;

    private DAOMenager(String databaseName, String user, String password) {
        emf = Persistence.createEntityManagerFactory(databaseName);
        Map properties = new HashMap();
        properties.put("javax.persistence.jdbc.user", user);
        properties.put("javax.persistence.jdbc.password", password);
        entityManager = emf.createEntityManager(properties);
    }

    public static synchronized DAOMenager getInstance(String databaseName, String user, String password) {
        if (menager != null) {
            return menager;
        } else {
            return new DAOMenager(databaseName, user, password);
        }
    }

    public ArrayList<String> getTableColumnNames(String tableName) {
        ArrayList<String> colNames = new ArrayList<>();
        Field[] declaredFields = null;
        for (DatabaseWord data : DatabaseWord.values()) {
            if (data.getDatabaseName().equals(tableName)) {
                declaredFields = data.getDatabaseClass().getDeclaredFields();
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

}
