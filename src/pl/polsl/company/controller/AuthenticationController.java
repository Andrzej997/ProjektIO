package pl.polsl.company.controller;

import pl.polsl.database.menager.DAOMenager;
import pl.polsl.database.menager.operations.OperationHandler;

/**
 * Created by Krzysztof Stręk on 2016-02-09.
 */
public class AuthenticationController {

    private final OperationHandler operationHandler = new OperationHandler(DAOMenager.getInstance("kino").getEntityManager());

    private ManagementController managementController = null;

    private BusinessServiceController businessServiceController = null;

    public boolean authenticateUser(int id, String password) {
        //operationHandler.handleRequest("", "FIND_ENTITY")

        switch (1 /***pobieranie typu użytkownika****/) {
            case 1: //kierownik
                managementController = new ManagementController(operationHandler);
                break;
            case 2: //konsultant/sprzedawca/ktośtam
                businessServiceController = new BusinessServiceController(operationHandler);
                break;
            case 3:  //invalid

        }

        return false;   //tmp
    }

    public ManagementController getManagementController() throws UnauthorizedAccessException {
        if (managementController == null) {
            throw new UnauthorizedAccessException("Unauthorized access to ManagementController");
        }
        return managementController;
    }

    public BusinessServiceController getBusinessServiceController() throws UnauthorizedAccessException {
        if (businessServiceController == null) {
            throw new UnauthorizedAccessException("Unauthorized access to BusinessServiceController");
        }
        return businessServiceController;
    }

}
