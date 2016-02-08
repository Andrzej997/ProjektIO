package pl.polsl.database.menager.operations;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import pl.polsl.database.entities.IEntity;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;

public interface IOperate {
    void setEntityManager(EntityManager em);
    IEntity createEntity(Object... args) throws ArgsLengthNotCorrectException;
    void addEntity(IEntity entity);
    void modifyEntity(IEntity entity,ArrayList<String> argNames, Object... args);
    boolean findEntity(IEntity entity);
    List findEntity(ArrayList<String> argsNames, Object... args);
    void deleteEntity(IEntity entity);
    List realizeQuery(String query);
}
