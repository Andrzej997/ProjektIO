package pl.polsl.database.menager;

import java.util.ArrayList;
import java.util.HashMap;
import pl.polsl.database.entities.Users;

/**
 *
 * @author matis
 */
public enum PrivilegeLevels {
    CHIEF(1, "CHIEF") {
        @Override
        public HashMap<String, ArrayList<String>> getPriviligeLevel() {
            ArrayList<String> tableNames = new ArrayList<>();
            for (DatabaseWord tables : DatabaseWord.values()) {
                tableNames.add(tables.getTableName());
            }
            HashMap<String, ArrayList<String>> privilegeLevels = new HashMap<>();
            privilegeLevels.put("allPriv", tableNames);
            return privilegeLevels;
        }
    },
    CONSULTANT(2, "CONSULTANT") {
        @Override
        public HashMap<String, ArrayList<String>> getPriviligeLevel() {
            ArrayList<String> privTypesArray = Users.getPrivilegesTypes();
            ArrayList<String> tableNames = new ArrayList<>();
            for (DatabaseWord tables : DatabaseWord.values()) {
                String table = tables.getTableName();
                if (!table.equals("USERS")) {
                    tableNames.add(table);
                }
            }
            HashMap<String, ArrayList<String>> privilegeLevels = new HashMap<>();
            for (String privilege : privTypesArray) {
                if (!privilege.equals("createPriv") && !privilege.equals("allPriv")) {
                    privilegeLevels.put(privilege, tableNames);
                }
            }
            return privilegeLevels;
        }
    },
    CASHIER(3, "CASHIER") {
        @Override
        public HashMap<String, ArrayList<String>> getPriviligeLevel() {
            ArrayList<String> privTypesArray = Users.getPrivilegesTypes();
            ArrayList<String> tableNames = new ArrayList<>();
            for (DatabaseWord tables : DatabaseWord.values()) {
                String table = tables.getTableName();
                if (table.equals("ROOMS") || table.equals("TICKETS") || table.equals("SEANCES")
                        || table.equals("FILMS")) {
                    tableNames.add(table);
                }
            }
            HashMap<String, ArrayList<String>> privilegeLevels = new HashMap<>();
            for (String privilege : privTypesArray) {
                if (!privilege.equals("createPriv") && !privilege.equals("allPriv")) {
                    privilegeLevels.put(privilege, tableNames);
                }
            }
            return privilegeLevels;
        }
    };

    int privilegeLevel;
    String type;

    private PrivilegeLevels() {
    }

    PrivilegeLevels(int privilegeLevel, String type) {
        this.privilegeLevel = privilegeLevel;
        this.type = type;
    }

    public abstract HashMap<String, ArrayList<String>> getPriviligeLevel();
}
