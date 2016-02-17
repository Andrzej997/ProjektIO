package pl.polsl.company.model;

import pl.polsl.database.entities.Transactions;


/**
 * Class to provide advertisment transaction module, extends Transaction abstract
 * 
 * Created by Krzysztof Stręk on 2016-02-08.
 */
public class AdvertisementTransaction extends Transaction {

    /**
     * Field with transaction entity in database
     */
    Transactions transactionEntity;

    /**
     * Constructor
     * 
     * @param t Transactions entity object
     */
    public AdvertisementTransaction(Transactions t) {
        super(t);
    }

    /**
     * accepts request
     */
    @Override
    public void accept() {

    }

    /**
     * refuse requests
     */
    @Override
    public void refuse() {

    }
}
