package pl.polsl.database.menager.operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        operationMap.put("FILMY", new FilmsOperations());
        operationMap.put("PROMOCJE_GRUPOWE", new GroupPromotionOperations());
        operationMap.put("PROMOCJE_CZASOWE", new TimePromotionOperations());
        operationMap.put("SALE", new RoomsOperations());
        operationMap.put("SEANSE", new SeancesOperations());
        operationMap.put("BILETY", new TicketsOperations());
        operationMap.put("ZLECENIA_SPRZEDAŻ_REKLAM", new AdsSellingOperations());
        operationMap.put("ZLECENIA_WYNAJEM_SALI", new RoomsReservationOperations());
        operationMap.put("KLIENCI", new ClientsOperations());
    }

    public List handleRequest(String database, String action, Object... args) {
        if (operationMap.containsKey(database)) {
            IOperate operation = operationMap.get(database);
            operation.setEntityManager(em);
            return handleOperation(action, operation, args);
        }
        return null;
    }

    public List handleOperation(String action, IOperate operate, Object... args) {
        switch (action) {
            case "ADD_ENTITY": {
                try {
                    operate.addEntity(operate.createEntity((args)));
                } catch (ArgsLengthNotCorrectException ex) {
                    Logger.getLogger(OperationHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            case "DELETE_ENTITY":
                operate.deleteEntity((IEntity)(operate.findEntity((ArrayList<String>)args[0], args[1])).get(0));
                break;
            case "FIND_ENTITY":
                List results = operate.findEntity((ArrayList<String>)args[0], args[1]);
                return results;
            case "MODIFY_ENTITY":
                IEntity entity = (IEntity) (operate.findEntity((ArrayList<String>)args[0], args[1])).get(0);
                operate.modifyEntity(entity,(ArrayList<String>) args[2], args[3]);
                break;
            case "REALIZE_QUERY":
                List result = operate.realizeQuery((String)args[0]);
                return result;
            default:
                break;
        }
        return null;
    }
}
