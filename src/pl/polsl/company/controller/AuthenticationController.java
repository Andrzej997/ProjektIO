package pl.polsl.company.controller;

import pl.polsl.company.model.ApplicationContext;
import pl.polsl.database.manager.DAOManager;
import pl.polsl.database.manager.PrivilegeLevels;

/**
 * Created by Krzysztof Stręk on 2016-02-09.
 */
public class AuthenticationController {

    private ManagementController managementController = null;

    private BusinessServiceController businessServiceController = null;

    private PrivilegeLevels userType;

    private final ApplicationContext applicationContext = new ApplicationContext();

    public PrivilegeLevels authenticateUser(String username, String password) throws UnauthorizedAccessException {

        DAOManager manager = DAOManager.getInstance("kino");
        if(!manager.authentificateUser(username, password)) {
            throw new UnauthorizedAccessException("Authentication failed");
        }

        switch (manager.getUserType(username)) {
            case "CHIEF":
                managementController = new ManagementController(applicationContext);
                userType = PrivilegeLevels.CHIEF;
                break;
            case "CONSULTANT":
                businessServiceController = new BusinessServiceController(applicationContext);
                userType = PrivilegeLevels.CONSULTANT;
                break;
        }

        return userType;
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

    public PrivilegeLevels getUserType() {
        return userType;
    }
}
