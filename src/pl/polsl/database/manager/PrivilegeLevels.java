package pl.polsl.database.manager;

import java.util.ArrayList;
import java.util.HashMap;
import pl.polsl.database.entities.Users;

/**
 * Enum with privilege levels
 * 
 * @author Mateusz Sojka
 * @version 1.5
 */
public enum PrivilegeLevels {
    CHIEF(1, "CHIEF") {
        @Override
        public HashMap<String, ArrayList<String>> getPriviligeLevel() {
            ArrayList<String> tableNames = new ArrayList<>();
            for (DatabaseTables tables : DatabaseTables.values()) {
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
            for (DatabaseTables tables : DatabaseTables.values()) {
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
            for (DatabaseTables tables : DatabaseTables.values()) {
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

    /**
     * Integer with privilege level code
     */
    int privilegeLevel;
    
    /**
     * String with privilage level type
     */
    String type;

    /**
     * private constructor
     */
    private PrivilegeLevels() {
    }

    /**
     * Constructor
     * 
     * @param privilegeLevel Integer with privilege Level
     * @param type String with type
     */
    PrivilegeLevels(int privilegeLevel, String type) {
        this.privilegeLevel = privilegeLevel;
        this.type = type;
    }

    /**
     * Abstract method to get HashMap with diferent privilege level on diferent tables
     * @return HashMap <String , ArrayList<String>>  
     */
    public abstract HashMap<String, ArrayList<String>> getPriviligeLevel();
    
    /**
     * Method to get privilege type
     * @return String with privilege type
     */
    public String getType(){
        return this.type;
    }
    
    /**
     * Method to get privilege level
     * @return Integer with privilege level
     */
    public int getPrivilegeLevel(){
        return privilegeLevel;
    }
}
