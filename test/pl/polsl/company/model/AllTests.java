package pl.polsl.company.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    ApplicationContextTest.class,
    AuthorizationQueueTest.class,
    TransactionListTest.class
})

public class AllTests {

}
