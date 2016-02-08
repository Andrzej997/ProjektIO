package pl.polsl.database.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Time;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name="BILETY")
public class Tickets implements Serializable, IEntity{

    private static final long serialVersionUID = 3L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, unique = false)
    private Long id;
    
    @Column(name="CENA", precision = 2, nullable = false)
    private double price;
    
    @Column(name="NUMER_MIEJSCA", nullable = false)
    private int chairNumber;
    
    @Column(name="NUMER_RZĘDU", nullable = false)
    private int rowNumber;
    
    @Column(name="STAN", nullable = false)
    private int state;
    
    @Column(name="NUMER_SALI", nullable = false)
    private int roomNumber;
    
    @Column(name="CZAS", nullable = false)
    private Time time;
    
    @Column(name="DATA_", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    
    protected Tickets(){}
    
    public Tickets(double price,int chairNumber,int rowNumber,int state, int roomNumber, Time time, Date date ){
        this.price=price;
        this.chairNumber=chairNumber;
        this.rowNumber=rowNumber;
        this.roomNumber=roomNumber;
        this.time=time;
        this.date=(Date)date.clone();
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
        if (!(object instanceof Tickets)) {
            return false;
        }
        Tickets other = (Tickets) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "pl.polsl.database.entities.Tickets[ id=" + id + " ]";
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
     * @return the chairNumber
     */
    public int getChairNumber() {
        return chairNumber;
    }

    /**
     * @param chairNumber the chairNumber to set
     */
    public void setChairNumber(int chairNumber) {
        this.chairNumber = chairNumber;
    }

    /**
     * @return the rowNumber
     */
    public int getRowNumber() {
        return rowNumber;
    }

    /**
     * @param rowNumber the rowNumber to set
     */
    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    /**
     * @return the state
     */
    public int getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(int state) {
        this.state = state;
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
     * @return the date
     */
    public Date getDate() {
        return (Date)date.clone();
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = (Date)date.clone();
    }
    
}
