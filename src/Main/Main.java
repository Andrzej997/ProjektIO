package Main;

import java.util.ArrayList;
import pl.polsl.database.menager.operations.OperationHandler;

public class Main {

    public static void request(String action_code, String tableName, ArrayList<String> parameters) {
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
                    array2.add(parameters.get(i + 1));
                }
                for (int i = parameters.indexOf("NEW") + 1; i < parameters.size(); i += 2) {
                    array3.add(parameters.get(i));
                    array4.add(parameters.get(i + 1));
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
                operationHandler.handleRequest(tableName, action_code, parameters.toArray());
                break;
            default:
                break;
        }
    }

    public static void main(String[] Args) {
        String c = "ADD_ENTITY";
        String tableName = "SALE";
        ArrayList<String> parameters = new ArrayList<>();
        parameters.add("100");
        request(c, tableName, parameters);
    }
}
