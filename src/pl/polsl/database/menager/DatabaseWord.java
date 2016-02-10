package pl.polsl.database.menager;

/**
 *
 * @author matis
 */
public enum DatabaseWord {

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

    private final String tableName;
    private final Integer tableCode;
    private final Class<?> tableClass;

    DatabaseWord(String tableName, Integer tableCode, Class<?> object) {
        this.tableName = tableName;
        this.tableCode = tableCode;
        this.tableClass = object;
    }

    public String getTableName() {
        return tableName;
    }

    public Integer getTableCode() {
        return tableCode;
    }
    
    public Class<?> getTableClass(){
        return tableClass;
    }
    
}