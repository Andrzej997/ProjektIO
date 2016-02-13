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
@Table(name="ADDS_SELLING")
@SecondaryTable(name="ROOMS_RENTING")
public class Transactions implements Serializable, IEntity{

    private static final long serialVersionUID = 6L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, unique = false)
    private Long id;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(table="ADDS_SELLING", name="END_DATE", nullable = false)
    private Date endDate;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(table="ADDS_SELLING", name="START_DATE", nullable = false)
    private Date startDate;
    
    @Column(table="ADDS_SELLING", name="PRICE", nullable = false)
    private double price;
    
    @Column(table="ROOMS_RENTING", name="COMPANY_NAME", nullable = false)
    private String companyName;
    
    @Column(table="ROOMS_RENTING", name="ROOM_NUMBER", nullable = false)
    private int roomNumber;
    
    @Column(table="ROOMS_RENTING", name="TIME_", nullable = false)
    private Time time;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(table="ROOMS_RENTING", name="DEADLINE", nullable = false)
    private Date deadline;
    
    @Column(table="ROOMS_RENTING", name="ACCEPTED")
    private boolean accepted;
    
    @Column(table="ROOMS_RENTING", name="RENT_PRICE")
    private double rentPrice;
    
    protected Transactions(){}
    
    public Transactions(Date endDate, Date startDate, double price){
        this.endDate=(Date)endDate.clone();
        this.startDate=(Date)startDate.clone();
        this.price=price;
    }
    
    public Transactions(String companyName, int roomNumber, Time time, Date deadline,
            boolean accpeted, double rentPrice){
        this.companyName=companyName;
        this.roomNumber=roomNumber;
        this.time=time;
        this.deadline=(Date)deadline.clone();
        this.accepted=accpeted;
        this.rentPrice=rentPrice;
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
        return "pl.polsl.database.entities.Transactions[ id=" + id + " endDate=" 
                + endDate.toString() + " startDate=" + startDate.toString() + 
                " price=" + price + " companyName=" + companyName+ " roomNumber="
                + roomNumber + " time=" + time.toString() + " deadline=" 
                + deadline.toString() + " accepted= "+ accepted + " ]";
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return (Date)endDate.clone();
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = (Date)endDate.clone();
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return (Date)startDate.clone();
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = (Date)startDate.clone();
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
        return (Date)deadline.clone();
    }

    /**
     * @param deadline the deadline to set
     */
    public void setDeadline(Date deadline) {
        this.deadline = (Date)deadline.clone();
    }

    /**
     * @return the accepted
     */
    public boolean isAccepted() {
        return accepted;
    }

    /**
     * @param accepted the accepted to set
     */
    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    /**
     * @return the rentPrice
     */
    public double getRentPrice() {
        return rentPrice;
    }

    /**
     * @param rentPrice the rentPrice to set
     */
    public void setRentPrice(double rentPrice) {
        this.rentPrice = rentPrice;
    }
    
}
