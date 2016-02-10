package pl.polsl.database.entities;

import java.io.Serializable;
import java.sql.Time;
import java.util.Arrays;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

@Entity
@Table(name = "GROUP_PROMOTIONS")
@SecondaryTable(name = "TIME_PROMOTIONS")
public class Promotions implements Serializable, IEntity {

    private static final long serialVersionUID = 5L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, unique = false)
    private Long id;

    @Column(table = "GROUP_PROMOTIONS", name = "MINIMAL_AMOUNT", nullable = false)
    private int minimalAmount;

    @Column(table = "GROUP_PROMOTIONS", name = "SALE", nullable = false)
    private int sale;

    @Column(table = "TIME_PROMOTIONS", name = "DAYS_OF_WEEK", nullable = false)
    private Date[] daysOfWeek;

    @Column(table = "TIME_PROMOTIONS", name = "HOURS", nullable = false)
    private Time[] hours;

    @Column(table = "TIME_PROMOTIONS", name = "TIME_SALE", nullable = false)
    private int timeSale;

    protected Promotions() {
    }

    public Promotions(int minimalAmount, int sale) {
        this.minimalAmount = minimalAmount;
        this.sale = sale;
    }

    public Promotions(Date[] daysOfWeek, Time[] hours, int timeSale) {
        this.daysOfWeek = daysOfWeek.clone();
        this.hours = hours.clone();
        this.timeSale = timeSale;
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
        if (!(object instanceof Promotions)) {
            return false;
        }
        Promotions other = (Promotions) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    /**
     * @return the minimalAmount
     */
    public int getMinimalAmount() {
        return minimalAmount;
    }

    /**
     * @param minimalAmount the minimalAmount to set
     */
    public void setMinimalAmount(int minimalAmount) {
        this.minimalAmount = minimalAmount;
    }

    /**
     * @return the sale
     */
    public int getSale() {
        return sale;
    }

    /**
     * @param sale the sale to set
     */
    public void setSale(int sale) {
        this.sale = sale;
    }

    /**
     * @return the daysOfWeek
     */
    public Date[] getDaysOfWeek() {
        return daysOfWeek.clone();
    }

    /**
     * @param daysOfWeek the daysOfWeek to set
     */
    public void setDaysOfWeek(Date[] daysOfWeek) {
        this.daysOfWeek = daysOfWeek.clone();
    }

    /**
     * @return the hours
     */
    public Time[] getHours() {
        return hours.clone();
    }

    /**
     * @param hours the hours to set
     */
    public void setHours(Time[] hours) {
        this.hours = hours.clone();
    }

    /**
     * @return the timeSale
     */
    public int getTimeSale() {
        return timeSale;
    }

    /**
     * @param timeSale the timeSale to set
     */
    public void setTimeSale(int timeSale) {
        this.timeSale = timeSale;
    }

    @Override
    public String toString() {
        return "pl.polsl.database.entities.Reservations[ id=" + id + " minimalAmount=" 
                +minimalAmount + " sale=" + sale + " daysOfWeek=" + Arrays.toString(daysOfWeek) 
                + "hours=" + Arrays.toString(hours) + " timeSale= " + timeSale + " ]";
    }

}
