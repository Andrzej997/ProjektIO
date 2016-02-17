package pl.polsl.company.controller;

import pl.polsl.company.model.ApplicationContext;
import pl.polsl.database.manager.DAOManager;
import pl.polsl.database.manager.PrivilegeLevels;

/**
 * User autentification controller
 *
 * Created by Krzysztof Stręk on 2016-02-09.
 */
public class AuthenticationController {

    /**
     * Field with managementcontroller
     */
    private ManagementController managementController = null;

    /**
     * Field with business controler
     */
    private BusinessServiceController businessServiceController = null;

    /**
     * Field with user privilege levels
     */
    private PrivilegeLevels userType;

    /**
     * Aplication context object
     */
    private final ApplicationContext applicationContext = new ApplicationContext();

    /**
     * Method to authetificate user on server
     *
     * @param username String with username
     * @param password String with password
     * @return PrivilegeLevels object with user privileges type
     * @throws UnauthorizedAccessException when authentification fails
     */
    public PrivilegeLevels authenticateUser(String username, String password) throws UnauthorizedAccessException {

        DAOManager manager = DAOManager.getInstance("kino");
        if (!manager.authentificateUser(username, password)) {
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

    /**
     * Mehtod to get management controller
     *
     * @return ManagementController object
     * @throws UnauthorizedAccessException when access denied
     */
    public ManagementController getManagementController() throws UnauthorizedAccessException {
        if (managementController == null) {
            throw new UnauthorizedAccessException("Unauthorized access to ManagementController");
        }
        return managementController;
    }

    /**
     * Method to get BusinessServiceController
     *
     * @return BusinessServiceController object
     * @throws UnauthorizedAccessException when access denied
     */
    public BusinessServiceController getBusinessServiceController() throws UnauthorizedAccessException {
        if (businessServiceController == null) {
            throw new UnauthorizedAccessException("Unauthorized access to BusinessServiceController");
        }
        return businessServiceController;
    }

    /**
     * Method to get user privilege type
     *
     * @return PrivilegeLevels object with privilege type
     */
    public PrivilegeLevels getUserType() {
        return userType;
    }
}
