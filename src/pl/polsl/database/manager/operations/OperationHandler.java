package pl.polsl.database.manager.operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import pl.polsl.database.entities.IEntity;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;

/**
 *
 * @author matis
 */
public class OperationHandler {

    private final Map<String, IOperate> operationMap;
    private final EntityManager em;

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

    public List handleRequest(String tableName, String action, Object... args)
            throws ArgsLengthNotCorrectException {
        if (operationMap.containsKey(tableName)) {
            IOperate operation = operationMap.get(tableName);
            operation.setEntityManager(em);
            return handleOperation(action, operation, args);
        }
        return null;
    }

    public List handleOperation(String action, IOperate operate, Object... args)
            throws ArgsLengthNotCorrectException {
        switch (action) {
            case "ADD_ENTITY": {
                operate.addEntity(operate.createEntity((args)));
            }
            break;
            case "DELETE_ENTITY":
                operate.deleteEntity((IEntity) (operate.isEntityExists((ArrayList<String>) args[0], args[1])).get(0));
                break;
            case "FIND_ENTITY":
                List results = operate.isEntityExists((ArrayList<String>) args[0], args[1]);
                return results;
            case "MODIFY_ENTITY":
                IEntity entity = (IEntity) (operate.isEntityExists((ArrayList<String>) args[0], args[1])).get(0);
                operate.modifyEntity(entity, (ArrayList<String>) args[2], args[3]);
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
