package pl.polsl.company.protocol.msgWords;

import java.util.ArrayList;
import pl.polsl.database.exceptions.ArgsLengthNotCorrectException;
import pl.polsl.database.exceptions.OperatorNotFoundException;

/**
 *
 * @author matis
 */
public enum ActionWord {

    Select("SELECT", "REALIZE_QUERY") {
        @Override
        public ArrayList<String> getParameters(String tableName,
                ArrayList<String> clientParams, ArrayList<String> colNames)
                throws ArgsLengthNotCorrectException, OperatorNotFoundException {
            ArrayList<String> parameters = new ArrayList<>();
            for (String name : colNames) {
                if (!name.equalsIgnoreCase("ID")) {
                    for (String param : clientParams) {
                        String operator = getUsedOperator(param);
                        String id = getParamID(param, operator);
                        if (id.equals(name)) {
                            String value = getParamValue(param, "=");
                            parameters.add(id);
                            parameters.add(operator);
                            parameters.add(value);
                        }
                    }
                }
            }
            if (parameters.size() > 0) {
                return parameters;
            } else {
                throw new ArgsLengthNotCorrectException("ARGS COUNT IS NOT CORRECT TO DELETE ENTITY");
            }
        }
    },
    Add("ADD", "ADD_ENTITY") {
        @Override
        public ArrayList<String> getParameters(String tableName,
                ArrayList<String> clientParams, ArrayList<String> colNames)
                throws ArgsLengthNotCorrectException {
            ArrayList<String> parameters = new ArrayList<>();
            colNames.stream().filter((name) -> (!name.equals("ID"))).forEach((name) -> {
                clientParams.stream().forEach((param) -> {
                    String id = getParamID(param, "=");
                    if (id.equals(name)) {
                        String value = getParamValue(param, "=");
                        parameters.add(value);
                    }
                });
            });
            if (parameters.size() == colNames.size() - 1) {
                return parameters;
            } else {
                throw new ArgsLengthNotCorrectException("ARGS COUNT IS NOT CORRECT TO ADD ENTITY");
            }
        }
    },
    Modify("MODIFY", "MODIFY_ENTITY") {
        @Override
        public ArrayList<String> getParameters(String tableName,
                ArrayList<String> clientParams, ArrayList<String> colNames)
                throws ArgsLengthNotCorrectException, OperatorNotFoundException {
            ArrayList<String> parameters = new ArrayList<>();
            ArrayList<String> oldParameters = modifyGetOldParams(clientParams);
            ArrayList<String> newParameters = modifyGetNewParams(clientParams);
            for (String name : colNames) {
                if (!name.equalsIgnoreCase("ID")) {
                    for (String param : oldParameters) {
                        String operator = getUsedOperator(param);
                        String id = getParamID(param, operator);
                        if (id.equals(name)) {
                            String value = getParamValue(param, "=");
                            parameters.add(id);
                            parameters.add(operator);
                            parameters.add(value);
                        }
                    }
                }
            }
            parameters.add("NEW");
            colNames.stream().filter((name) -> (!name.equalsIgnoreCase("ID"))).forEach((name) -> {
                newParameters.stream().forEach((param) -> {
                    String id = getParamID(param,  "=");
                    if (id.equals(name)) {
                        String value = getParamValue(param, "=");
                        parameters.add(id);
                        parameters.add("=");
                        parameters.add(value);
                    }
                });
            });
            if (parameters.size() > 0) {
                return parameters;
            } else {
                throw new ArgsLengthNotCorrectException("ARGS COUNT IS NOT CORRECT TO DELETE ENTITY");
            }
        }
    },
    Delete("DELETE", "DELETE_ENTITY") {
        @Override
        public ArrayList<String> getParameters(String tableName,
                ArrayList<String> clientParams, ArrayList<String> colNames)
                throws ArgsLengthNotCorrectException, OperatorNotFoundException {
            ArrayList<String> parameters = new ArrayList<>();
            for (String name : colNames) {
                if (!name.equalsIgnoreCase("ID")) {
                    for (String param : clientParams) {
                        String operator = getUsedOperator(param);
                        String id = getParamID(param, operator);
                        if (id.equals(name)) {
                            String value = getParamValue(param, "=");
                            parameters.add(id);
                            parameters.add(value);
                        }
                    }
                }
            }
            if (parameters.size() > 0) {
                return parameters;
            } else {
                throw new ArgsLengthNotCorrectException("ARGS COUNT IS NOT CORRECT TO DELETE ENTITY");
            }
        }
    };

    private final String action;

    private final String actionCode;

    ActionWord(String action, String actionCode) {
        this.action = action;
        this.actionCode = actionCode;
    }

    public String getAction() {
        return action;
    }

    public String getActionCode() {
        return actionCode;
    }

    public abstract ArrayList<String> getParameters(String tableName,
            ArrayList<String> clientParams, ArrayList<String> colNames)
            throws ArgsLengthNotCorrectException,
            OperatorNotFoundException;

    public String getParamID(String param, String operator) {
        Integer index = param.indexOf(operator);
        return param.substring(0, index - 1);
    }

    public String getParamValue(String param, String operator) {
        Integer index = param.indexOf(operator);
        return param.substring(index + operator.length());
    }

    public String getUsedOperator(String message) throws OperatorNotFoundException {
        for (Operators operator : Operators.values()) {
            if (message.contains(operator.getOperatorText())) {
                return operator.getOperatorText();
            }
        }
        throw new OperatorNotFoundException("OPERATOR WAS NOT FOUND IN GIVEN PARAMETER");
    }
    
    public ArrayList<String> modifyGetOldParams(ArrayList<String> array) throws ArgsLengthNotCorrectException{
        int i =0;
        for(String str : array){
            if(str.equalsIgnoreCase("NEW")){
                return new ArrayList<>(array.subList(0, i-1));
            }
            i++;
        }
        throw new ArgsLengthNotCorrectException("OPERATOR NEW NOT FOUND");
    }
    
    public ArrayList<String> modifyGetNewParams(ArrayList<String> array) throws ArgsLengthNotCorrectException{
        int i = 0;
        for(String str: array){
            if(str.equalsIgnoreCase("NEW")){
                return new ArrayList<>(array.subList(i+1, array.size()));
            }
            i++;
        }
        throw new ArgsLengthNotCorrectException("OPERATOR NEW NOT FOUND");
    }
}
