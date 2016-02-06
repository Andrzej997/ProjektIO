package pl.polsl.company.protocol;

import java.io.IOException;
import java.util.ArrayList;
import pl.polsl.company.protocol.msgWords.ActionWord;
import pl.polsl.company.protocol.msgWords.DatabaseWord;
import pl.polsl.company.server.Services;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;
import pl.polsl.database.exceptions.OperatorNotFoundException;
import pl.polsl.database.menager.DAOMenager;
import pl.polsl.database.menager.operations.OperationHandler;

public class ClientServerProtocolDef {

    Services services;

    public ClientServerProtocolDef(Services services) {
        this.services = services;
    }

    public void authentication() throws IOException {
        services.sendMessageToClient("USER_REQ");
        String user = services.reciveAnswer();
        if (user != null) {
            services.sendMessageToClient("PASS_REQ");
            String pass = services.reciveAnswer();
            if (pass != null) {
                services.setDAOMenager(DAOMenager.getInstance("kino", user, pass));
            } else {
                services.sendMessageToClient("ERROR");
                services.sendMessageToClient("NULL_PASS");
            }
        } else {
            services.sendMessageToClient("ERROR");
            services.sendMessageToClient("NULL_USER");
        }
    }

    public String idetifyAction(String message) {
        for (ActionWord action : ActionWord.values()) {
            if (action.getAction().equals(message)) {
                return action.getActionCode();
            }
        }
        return null;
    }

    public String idetifyDatabase(String message) {
        for (DatabaseWord database : DatabaseWord.values()) {
            if (database.getDatabaseName().equals(message)) {
                return database.getDatabaseName();
            }
        }
        return null;
    }

    public ArrayList<String> getParameters(String databaseName, String action, ArrayList<String> clientParams) throws ArgsLengthNotCorrectException, OperatorNotFoundException {
        ArrayList<String> colNames = services.getDAOMenager().getTableColumnNames(databaseName);
        ArrayList<String> params = null;
        for (ActionWord act : ActionWord.values()) {
            if (act.getAction().equalsIgnoreCase(action)) {
                params = act.getParameters(databaseName, clientParams, colNames);
            }
        }
        return params;
    }

    public void handleRequest() throws IOException, ArgsLengthNotCorrectException, OperatorNotFoundException {
        String message = services.reciveAnswer();
        String action_code = this.idetifyAction(message);
        if (action_code == null) {
            return;
        }
        message = services.reciveAnswer();
        String tableName = idetifyDatabase(message);
        if (tableName == null) {
            return;
        }
        ArrayList<String> clientParameters = new ArrayList<>();
        while (!(message = services.reciveAnswer()).equalsIgnoreCase("END")) {
            clientParameters.add(message);
        }
        if (clientParameters.isEmpty()) {
            return;
        }
        ArrayList<String> parameters = getParameters(tableName, action_code, clientParameters);
        OperationHandler operationHandler = new OperationHandler(null);
        ArrayList<String> array = new ArrayList<>();
        ArrayList<String> array2 = new ArrayList<>();
        ArrayList<String> array3 = new ArrayList<>();
        ArrayList<String> array4 = new ArrayList<>();
        switch (action_code) {
            case "REALIZE_QUERY":
                break;
            case "MODIFY_ENTITY":
                for (int i = 0; i < parameters.indexOf("NEW"); i += 2) {
                    array.add(parameters.get(i));
                    array2.add(parameters.get(i+1));
                }
                for(int i = parameters.indexOf("NEW")+1; i < parameters.size(); i+=2){
                    array3.add(parameters.get(i));
                    array4.add(parameters.get(i+1));
                }
                operationHandler.handleRequest(tableName, action_code, array, 
                        array2.toArray(), array3, array4.toArray());
                break;
            case "DELETE_ENTITY":
                for (int i = 0; i < parameters.size(); i += 2) {
                    array.add(parameters.get(i));
                    array2.add(parameters.get(i + 1));
                }
                operationHandler.handleRequest(tableName, action_code, array, array2.toArray());
                break;
            case "ADD_ENTITY":
                operationHandler.handleRequest(action_code, action_code, parameters.toArray());
                break;
            default:
                break;
        }
    }
}
