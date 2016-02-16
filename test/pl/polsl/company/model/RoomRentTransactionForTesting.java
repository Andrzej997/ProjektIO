package pl.polsl.company.model;

import pl.polsl.database.entities.Transactions;

public class RoomRentTransactionForTesting extends RoomRentTransaction {

    public RoomRentTransactionForTesting(Transactions t) {
        super(t);
    }

    @Override
    public int getRoomNumber() {
        return 0;
    }
    
    @Override
    public long getID() {
        return 0;
    }

    @Override
    public void accept() {
        
    }

    @Override
    public void refuse() {
        
    }
}