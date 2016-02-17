package pl.polsl.database.manager.operations;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import pl.polsl.database.entities.IEntity;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;
import pl.polsl.database.exceptions.ArgsNotCorrectException;

/**
 * Interface to provide strategy pattern
 * 
 * @author Mateusz Sojka
 * @version 1.0
 */
public interface IOperate {
    
    /**
     * Method to set Entity Manager to entity class
     * @param em EntityManager object
     */
    void setEntityManager(EntityManager em);
    
    /**
     * Method to create entity
     * @param args variable array of args, depedant to entity
     * @return IEntity typed Object
     * @throws ArgsLengthNotCorrectException when varargs count are not correct
     * @throws ArgsNotCorrectException when varargs data are not correct
     */
    IEntity createEntity(Object... args) 
            throws ArgsLengthNotCorrectException, ArgsNotCorrectException;
    
    /**
     * Add entity to table method
     * 
     * @param entity IEntity typed object to add
     * @return added Entity
     */
    List addEntity(IEntity entity);
    
    /**
     * Modify entity
     * 
     * @param entity - Entity to modify
     * @param argNames - Array List with column names, depedant to modified values
     * @param args - Varargs array with values to modify
     * @throws ArgsLengthNotCorrectException when args count are not correct
     */
    void modifyEntity(IEntity entity,ArrayList<String> argNames, Object... args) 
            throws ArgsLengthNotCorrectException;
    
    /**
     * Find entity method
     * 
     * @param entity Object to find
     * @return return true if entity exists, otherwise return false
     */
    boolean findEntity(IEntity entity);
    
    /**
     * Method to find entity in table
     * 
     * @param argsNames Column names, used to find by column
     * @param args Varargs array with values, dependent to argsNames
     * @return List of found entities
     */
    List isEntityExists(ArrayList<String> argsNames, Object... args);
    
    /**
     * Method to delete entity from table
     * @param entity found Entity
     */
    void deleteEntity(IEntity entity);
}
