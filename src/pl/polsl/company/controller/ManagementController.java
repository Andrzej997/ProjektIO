package pl.polsl.company.controller;

import pl.polsl.database.menager.operations.OperationHandler;

/**
 * Created by Krzysztof Stręk on 2016-02-08.
 */
public class ManagementController {

    private final OperationHandler operationHandler;

    ManagementController(OperationHandler operationHandler) {
        this.operationHandler = operationHandler;
    }
}
