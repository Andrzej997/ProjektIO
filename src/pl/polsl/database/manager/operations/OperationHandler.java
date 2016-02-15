package pl.polsl.database.manager.operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import pl.polsl.database.entities.IEntity;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;
import pl.polsl.database.exceptions.ArgsNotCorrectException;

/**
 * Class to handle database requests
 *
 * @author Mateusz Sojka
 * @version 1.0
 */
public class OperationHandler {

    /**
     * Field contains hashmap with entity name and related operation handler
     */
    private final Map<String, IOperate> operationMap;

    /**
     * Field contains entity manager
     */
    private final EntityManager em;

    /**
     * Constructor
     *
     * @param em EntityManager object
     */
    public OperationHandler(EntityManager em) {
        this.em = em;
        this.operationMap = new HashMap<>();
        operationMap.put("FILMS", new FilmsOperations());
        operationMap.put("GROUP_PROMOTIONS", new GroupPromotionOperations());
        operationMap.put("TIME_PROMOTIONS", new TimePromotionOperations());
        operationMap.put("ROOMS", new RoomsOperations());
        operationMap.put("SEANCES", new SeancesOperations());
        operationMap.put("TICKETS", new TicketsOperations());
        operationMap.put("CLIENTS", new ClientsOperations());
        operationMap.put("TRANSACTIONS", new TransactionsOperations());
    }

    /**
     * Method used to handle all requests on database actions available:
     * ADD_ENTITY, DELETE_ENTITY, FIND_ENTITY, MODIFY_ENTITY, CREATE_ENTITY
     *
     * @param tableName String with table name
     * @param action String with required action
     * @param args varargs array, contains action arguments
     * @return List with results or null when action doesn't return any lists
     * @throws ArgsLengthNotCorrectException when given varargs are not correct
     */
    public List handleRequest(String tableName, String action, Object... args)
            throws ArgsLengthNotCorrectException, ArgsNotCorrectException {
        if (operationMap.containsKey(tableName)) {
            IOperate operation = operationMap.get(tableName);
            operation.setEntityManager(em);
            return handleOperation(action, operation, args);
        }
        return null;
    }

    /**
     * Method which handle requested actions
     *
     * @param action String with action name
     * @param operate IOperate object from hashmap
     * @param args varargs array with action args
     * @return List with results or null
     * @throws ArgsLengthNotCorrectException when varargs are not correct
     */
    public List handleOperation(String action, IOperate operate, Object... args)
            throws ArgsLengthNotCorrectException, ArgsNotCorrectException {
        IEntity entity = null;
        switch (action) {
            case "ADD_ENTITY": {
                operate.addEntity(operate.createEntity((args)));
            }
            break;
            case "DELETE_ENTITY":
                entity = (IEntity) (operate.isEntityExists((ArrayList<String>) args[0], args[1])).get(0);
                if (entity != null) {
                    operate.deleteEntity(entity);
                }
                break;
            case "FIND_ENTITY":
                List results = operate.isEntityExists((ArrayList<String>) args[0], args[1]);
                return results;
            case "MODIFY_ENTITY":
                entity = (IEntity) (operate.isEntityExists((ArrayList<String>) args[0], args[1])).get(0);
                if (entity != null) {
                    operate.modifyEntity(entity, (ArrayList<String>) args[2], args[3]);
                }
                break;
            case "CREATE_ENTITY": {
                List create = new ArrayList<>();
                create.add(operate.createEntity(args));
                return create;
            }
            default:
                break;
        }
        return null;
    }
}
