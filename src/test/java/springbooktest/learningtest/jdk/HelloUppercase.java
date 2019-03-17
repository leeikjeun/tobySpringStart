package springbooktest.learningtest.jdk;

/**
 * Created by adaeng on 17/03/2019.
 */
public class HelloUppercase implements Hello {
    private Hello hello;

    public HelloUppercase(Hello hello){
        this.hello = hello;
    }

    @Override
    public String sayHello(String name) {
        return hello.sayHello(name).toUpperCase();
    }

    @Override
    public String sayHi(String name) {
        return hello.sayHi(name).toUpperCase();
    }

    @Override
    public String sayThankU(String name) {
        return hello.sayThankU(name).toUpperCase();
    }
}
