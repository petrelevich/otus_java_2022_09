package homework;


import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private final SortedMap<Customer, String> map = new TreeMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        Map.Entry<Customer, String> mapEntry = map.entrySet().iterator().next();
        return Map.entry(mapEntry.getKey().clone(), mapEntry.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> mapEntry = map.entrySet().stream()
                .filter(e -> e.getKey().getScores() > customer.getScores())
                .findFirst().orElse(null);
        return mapEntry == null ? null : Map.entry(mapEntry.getKey().clone(), mapEntry.getValue());
    }

    public void add(Customer customer, String data) {
        map.put(customer.clone(), data);
    }
}
