package pl.polsl.database.menager.operations;

import java.util.ArrayList;
import java.util.HashMap;
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
    }

    public void handleRequest(String database, String action, Object... args) {
        if (operationMap.containsKey(database)) {
            IOperate operation = operationMap.get(database);
            operation.setEntityManager(em);
            handleOperation(action, operation, args);
        }
    }

    public void handleOperation(String action, IOperate operate, Object... args) {
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
                operate.deleteEntity((operate.findEntity((ArrayList<String>)args[0], args[1])));
                break;
            case "FIND_ENTITY":
                operate.findEntity((ArrayList<String>)args[0], args[1]);
                break;
            case "MODIFY_ENTITY":
                IEntity entity = operate.findEntity((ArrayList<String>)args[0], args[1]);
                operate.modifyEntity(entity,(ArrayList<String>) args[2], args[3]);
                break;
            case "REALIZE_QUERY":
                operate.realizeQuery((String)args[0]);
                break;
            default:
                break;
        }
    }
}
