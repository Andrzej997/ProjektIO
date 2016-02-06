package pl.polsl.database.entities;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.SecondaryTable;
import javax.persistence.Temporal;

@Entity
@Table(name="ZLECENIA_SPRZEDAŻ_REKLAM")
@SecondaryTable(name="ZLECENIA_WYNAJEM_SALI")
public class Transactions implements Serializable, IEntity{

    private static final long serialVersionUID = 6L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, unique = false)
    private Long id;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(table="ZLECENIA_SPRZEDAŻ_REKLAM", name="DATA_ZAKOŃCZENIA", nullable = false)
    private Date endDate;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(table="ZLECENIA_SPRZEDAŻ_REKLAM", name="DATA_ROZPOCZĘCIA", nullable = false)
    private Date startDate;
    
    @Column(table="ZLECENIA_SPRZEDAŻ_REKLAM", name="CENA", nullable = false)
    private double price;
    
    @Column(table="ZLECENIA_WYNAJEM_SALI", name="NAZWA_FIRMY", nullable = false)
    private String companyName;
    
    @Column(table="ZLECENIA_WYNAJEM_SALI", name="NUMER_SALI", nullable = false)
    private int roomNumber;
    
    @Column(table="ZLECENIA_WYNAJEM_SALI", name="CZAS", nullable = false)
    private Time time;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(table="ZLECENIA_WYNAJEM_SALI", name="TERMIN", nullable = false)
    private Date deadline;
    
    protected Transactions(){}
    
    public Transactions(Date endDate, Date startDate, double price){
        this.endDate=endDate;
        this.startDate=startDate;
        this.price=price;
    }
    
    public Transactions(String companyName, int roomNumber, Time time, Date deadline){
        this.companyName=companyName;
        this.roomNumber=roomNumber;
        this.time=time;
        this.deadline=deadline;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Transactions)) {
            return false;
        }
        Transactions other = (Transactions) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "pl.polsl.database.entities.Transactions[ id=" + id + " ]";
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the roomNumber
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * @param roomNumber the roomNumber to set
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * @return the time
     */
    public Time getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Time time) {
        this.time = time;
    }

    /**
     * @return the deadline
     */
    public Date getDeadline() {
        return deadline;
    }

    /**
     * @param deadline the deadline to set
     */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
    
}
