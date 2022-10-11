package homework;


import java.util.*;

public class CustomerReverseOrder {
    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"
    private final Stack<Customer> stack = new Stack<>();
    public void add(Customer customer) {
        stack.add(customer);
    }

    public Customer take() {
        return stack.pop();
    }
}
