package pl.polsl.company.model;

import java.util.Date;

/**
 * Created by Krzysztof Stręk on 2016-01-29.
 */
public class RoomRenting implements Order {

    private Date date = new Date();

    private String contractorName;

    private int roomNumber;

    public void accept() {

    }

    public void reject() {

    }
}
