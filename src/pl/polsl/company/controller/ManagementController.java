package pl.polsl.company.controller;

import pl.polsl.company.model.ApplicationContext;
import pl.polsl.company.model.AuthorizableTransaction;

import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-02-08.
 */
public class ManagementController {

    private final ApplicationContext applicationContext;

    public ManagementController(ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;
    }

    public List<AuthorizableTransaction> getUnauthorizedTransactions() {
        return applicationContext.getAuthorizationQueue().getAllTransactions();
    }


}
