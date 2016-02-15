package pl.polsl.company.model;

import pl.polsl.database.entities.Transactions;

import java.util.Date;

/**
 * Created by Krzysztof Stręk on 2016-02-08.
 */
public class AdvertisementTransaction extends Transaction {

    Transactions transactionEntity;

    public AdvertisementTransaction(Transactions t) {
        super(t);
    }

    @Override
    public void accept() {

    }

    @Override
    public void reject() {

    }
}
