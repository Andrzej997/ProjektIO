package pl.polsl.database.entities;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Temporal;

@Entity
@Table(name="TRANSACTIONS")
public class Transactions implements Serializable, IEntity{

    private static final long serialVersionUID = 6L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, unique = false)
    private Long id;
    
    @Column(name = "START_DATE_AND_TIME")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar startDateAndTime;
    
    @Column(name = "END_DATE_AND_TIME")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar endDateAndTime;
    
    @Column(name = "PRICE")
    private Double price;
    
    @Column(name = "COMPANY_NAME")
    private String companyName;
    
    @Column(name = "ROOM_NUMBER")
    private Integer roomNumber;
    
    @Column(name = "RENTING_OR_ADDS_SELLING")
    private Boolean rentingOrAddsSelling;
    
    @Column(name = "ACCEPTED", nullable = false)
    private Boolean accepted;
    
    protected Transactions(){}

    public Transactions(Calendar startDateAndTime, Calendar endDateAndTime
            , Double price, String companyName, Integer roomNumber
            , Boolean rentingOrAddsSelling, Boolean accepted){
        this.startDateAndTime=startDateAndTime;
        this.endDateAndTime=endDateAndTime;
        this.price=price;
        this.companyName=companyName;
        this.roomNumber=roomNumber;
        this.rentingOrAddsSelling=rentingOrAddsSelling;
        this.accepted=accepted;
    }
    /**
     * @return the startDateAndTime
     */
    public Calendar getStartDateAndTime() {
        return startDateAndTime;
    }

    /**
     * @param startDateAndTime the startDateAndTime to set
     */
    public void setStartDateAndTime(Calendar startDateAndTime) {
        this.startDateAndTime = startDateAndTime;
    }

    /**
     * @return the endDateAndTime
     */
    public Calendar getEndDateAndTime() {
        return endDateAndTime;
    }

    /**
     * @param endDateAndTime the endDateAndTime to set
     */
    public void setEndDateAndTime(Calendar endDateAndTime) {
        this.endDateAndTime = endDateAndTime;
    }

    /**
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
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
    public Integer getRoomNumber() {
        return roomNumber;
    }

    /**
     * @param roomNumber the roomNumber to set
     */
    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * @return the rentingOrAddsSelling
     */
    public Boolean isRentingOrAddsSelling() {
        return rentingOrAddsSelling;
    }

    /**
     * @param rentingOrAddsSelling the rentingOrAddsSelling to set
     */
    public void setRentingOrAddsSelling(Boolean rentingOrAddsSelling) {
        this.rentingOrAddsSelling = rentingOrAddsSelling;
    }

    /**
     * @return the accepted
     */
    public Boolean isAccepted() {
        return accepted;
    }

    /**
     * @param accepted the accepted to set
     */
    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }
    
}
