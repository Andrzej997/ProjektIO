package pl.polsl.database.menager;

/**
 *
 * @author matis
 */
public enum DatabaseWord {

    Films("FILMY", 1, pl.polsl.database.entities.Films.class),
    GroupPromotions("PROMOCJE_GRUPOWE", 2, pl.polsl.database.entities.Reservations.class),
    TimePromotions("PROMOCJE_CZASOWE", 3, pl.polsl.database.entities.Reservations.class),
    Rooms("SALE", 4, pl.polsl.database.entities.Rooms.class),
    Seances("SEANSE", 5, pl.polsl.database.entities.Seances.class),
    Tickets("BILETY", 6, pl.polsl.database.entities.Tickets.class),
    AddsSelling("ZLECENIA_SPRZEDAŻ_REKLAM", 7, pl.polsl.database.entities.Transactions.class),
    RoomsReservations("ZLECENIA_WYNAJEM_SALI", 8, pl.polsl.database.entities.Transactions.class),
    Clients("KLIENCI", 9, pl.polsl.database.entities.Clients.class),
    Users("USERS", 9, pl.polsl.database.entities.Users.class);

    private final String databaseName;
    private final Integer databaseCode;
    private final Class<?> databaseClass;

    DatabaseWord(String databaseName, Integer databaseCode, Class<?> object) {
        this.databaseName = databaseName;
        this.databaseCode = databaseCode;
        this.databaseClass = object;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public Integer getDatabaseCode() {
        return databaseCode;
    }
    
    public Class<?> getDatabaseClass(){
        return databaseClass;
    }
    
}