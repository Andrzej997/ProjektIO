package pl.polsl.company.model;

/**
 * Created by Krzysztof Stręk on 2016-01-29.
 */
public interface AuthorizableOrder extends Order {
    void changeStatus();
}
