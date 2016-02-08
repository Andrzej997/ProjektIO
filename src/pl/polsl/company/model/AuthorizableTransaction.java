package pl.polsl.company.model;

/**
 * Created by Krzysztof Stręk on 2016-01-29.
 */

//dekorator dla tranzakcji umożliwiający autoryzację
public class AuthorizableTransaction implements Transaction {

    private Transaction transaction;

    public AuthorizableTransaction(Transaction transaction) {

    }

    void changeStatus() {

    }

    @Override
    public void accept() {

    }

    @Override
    public void reject() {

    }
}
