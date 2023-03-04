package homework;


import java.util.*;

public class CustomerService {

    private final TreeMap<Customer, String> map = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        return cloneKey(map.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return map.higherEntry(customer) != null ? cloneKey(map.higherEntry(customer)) : null;
    }

    private Map.Entry<Customer, String> cloneKey(Map.Entry<Customer, String> entry) {
        return Map.entry(new Customer(entry.getKey()), entry.getValue());
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }
}
