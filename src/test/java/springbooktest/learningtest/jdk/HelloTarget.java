package springbooktest.learningtest.jdk;

/**
 * Created by adaeng on 17/03/2019.
 */
public class HelloTarget implements Hello {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

    @Override
    public String sayHi(String name) {
        return "Hi " + name;
    }

    @Override
    public String sayThankU(String name) {
        return "Thank u " + name;
    }
}
