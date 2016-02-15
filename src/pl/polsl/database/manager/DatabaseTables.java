package pl.polsl.database.manager;

/**
 * Enum with all database table names and class
 * 
 * @author Mateusz Sojka
 * @version 1.4
 */
public enum DatabaseTables {

    Films("FILMS", 1, pl.polsl.database.entities.Films.class),
    GroupPromotions("GROUP_PROMOTIONS", 2, pl.polsl.database.entities.Promotions.class),
    TimePromotions("TIME_PROMOTIONS", 3, pl.polsl.database.entities.Promotions.class),
    Rooms("ROOMS", 4, pl.polsl.database.entities.Rooms.class),
    Seances("SEANCES", 5, pl.polsl.database.entities.Seances.class),
    Tickets("TICKETS", 6, pl.polsl.database.entities.Tickets.class),
    AddsSelling("ADDS_SELLING", 7, pl.polsl.database.entities.Transactions.class),
    RoomsReservations("ROOMS_RENTING", 8, pl.polsl.database.entities.Transactions.class),
    Clients("CLIENTS", 9, pl.polsl.database.entities.Clients.class),
    Users("USERS", 9, pl.polsl.database.entities.Users.class);

    /**
     * String with table name
     */
    private final String tableName;
    
    /**
     * Integer with table code
     */
    private final Integer tableCode;
    
    /**
     * Class path object
     */
    private final Class<?> tableClass;

    /**
     * Constructor
     * 
     * @param tableName String with table name
     * @param tableCode Integer with table code
     * @param object Class path object
     */
    DatabaseTables(String tableName, Integer tableCode, Class<?> object) {
        this.tableName = tableName;
        this.tableCode = tableCode;
        this.tableClass = object;
    }

    /**
     * Method to get table name
     * @return String with table name
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Method to get table code
     * @return Integer with table code
     */
    public Integer getTableCode() {
        return tableCode;
    }
    
    /**
     * Method to get class path object
     * @return Class path object
     */
    public Class<?> getTableClass(){
        return tableClass;
    }
    
}