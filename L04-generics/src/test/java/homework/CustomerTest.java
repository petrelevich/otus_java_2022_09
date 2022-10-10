package homework;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CustomerTest {

    // Все тесты должны проходить, менять тесты не надо.

    @Test
    @DisplayName("Проверяем, что класс Customer не сломан")
    void setterCustomerTest() {
        //given
        String expectedName = "updatedName";
        String name = "nameVas";
        Customer customer = new Customer(1, name, 2);

        //when
        customer.setName(expectedName);

        //then
        assertThat(customer.getName()).isEqualTo(expectedName);
    }

    @Test
    @Disabled //надо удалить
    @DisplayName("Объект Customer как ключ в карте")
    void customerAsKeyTest() {
        //given
        final long customerId = 1L;
        Customer customer = new Customer(customerId, "Ivan", 233);
        Map<Customer, String> map = new HashMap<>();

        String expectedData = "data";
        map.put(customer, expectedData);

        //when
        long newScore = customer.getScores() + 10;
        String factData = map.get(new Customer(customerId, "IvanChangedName", newScore));

        //then
        assertThat(factData).isEqualTo(expectedData);

        //when
        long newScoreSecond = customer.getScores() + 20;
        customer.setScores(newScoreSecond);
        String factDataSecond = map.get(customer);

        //then
        assertThat(factDataSecond).isEqualTo(expectedData);
    }

    @Test
    @Disabled //надо удалить
    @DisplayName("Сортировка по полю score, итерация по возрастанию")
    void scoreSortingTest() {
        //given
        Customer customer1 = new Customer(1, "Ivan", 233);
        Customer customer2 = new Customer(2, "Petr", 11);
        Customer customer3 = new Customer(3, "Pavel", 888);

        CustomerService customerService = new CustomerService();
        customerService.add(customer1, "Data1");
        customerService.add(customer2, "Data2");
        customerService.add(customer3, "Data3");

        //when
        Map.Entry<Customer, String> smallestScore = customerService.getSmallest();
        //then
        assertThat(smallestScore.getKey()).isEqualTo(customer2);

        //when
        // подсказка:
        // a key-value mapping associated with the least key strictly greater than the given key, or null if there is no such key.
        Map.Entry<Customer, String> middleScore = customerService.getNext(new Customer(10, "Key", 20));
        //then
        assertThat(middleScore.getKey()).isEqualTo(customer1);
        middleScore.getKey().setScores(10000);
        middleScore.getKey().setName("Vasy");

        //when
        Map.Entry<Customer, String> biggestScore = customerService.getNext(customer1);
        //then
        assertThat(biggestScore.getKey()).isEqualTo(customer3);

        //when
        Map.Entry<Customer, String> notExists = customerService.getNext(new Customer(100, "Not exists", 20000));
        //then
        assertThat(notExists).isNull();

    }

    @Test
    @Disabled //надо удалить
    @DisplayName("Модификация коллекции")
    void mutationTest() {
        //given
        Customer customer1 = new Customer(1, "Ivan", 233);
        Customer customer2 = new Customer(2, "Petr", 11);
        Customer customer3 = new Customer(3, "Pavel", 888);

        CustomerService customerService = new CustomerService();
        customerService.add(customer1, "Data1");
        customerService.add(new Customer(customer2.getId(), customer2.getName(), customer2.getScores()), "Data2");
        customerService.add(customer3, "Data3");

        //when
        Map.Entry<Customer, String> smallestScore = customerService.getSmallest();
        smallestScore.getKey().setName("Vasyl");

        //then
        assertThat(customerService.getSmallest().getKey().getName()).isEqualTo(customer2.getName());
    }

    @Test
    @Disabled //надо удалить
    @DisplayName("Возвращание в обратном порядке")
    void reverseOrderTest() {
        //given
        Customer customer1 = new Customer(1, "Ivan", 233);
        Customer customer2 = new Customer(3, "Petr", 11);
        Customer customer3 = new Customer(2, "Pavel", 888);

        CustomerReverseOrder customerReverseOrder = new CustomerReverseOrder();
        customerReverseOrder.add(customer1);
        customerReverseOrder.add(customer2);
        customerReverseOrder.add(customer3);

        //when
        Customer customerLast = customerReverseOrder.take();
        //then
        assertThat(customerLast).usingRecursiveComparison().isEqualTo(customer3);

        //when
        Customer customerMiddle = customerReverseOrder.take();
        //then
        assertThat(customerMiddle).usingRecursiveComparison().isEqualTo(customer2);

        //when
        Customer customerFirst = customerReverseOrder.take();
        //then
        assertThat(customerFirst).usingRecursiveComparison().isEqualTo(customer1);
    }
}