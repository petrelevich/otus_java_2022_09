package ru.otus.cassandrademo.db;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.session.Session;
import lombok.RequiredArgsConstructor;
import ru.otus.cassandrademo.model.Phone;
import ru.otus.cassandrademo.model.SmartPhone;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PhoneRepositoryImpl implements PhoneRepository {
    private final CassandraConnection cassandraConnection;

    @Override
    public <T> void insert(T value, Class<T> tClass) {
        CqlSession session = cassandraConnection.getSession();
        if (tClass.equals(Phone.class)) {

            Phone phone = (Phone) value;
            session.execute("INSERT INTO Products.Phones (id, model, color, serialNumber) " +
                            "VALUES (?, ?, ?, ?)", phone.getId(), phone.getModel(), phone.getColor(),
                    phone.getSerialNumber());

        } else if (tClass.equals(SmartPhone.class)) {

            SmartPhone phone = (SmartPhone) value;
            session.execute("INSERT INTO Products.Phones (id, model, color, serialNumber, operatingSystem) " +
                            "VALUES (?, ?, ?, ?, ?)", phone.getId(), phone.getModel(), phone.getColor(),
                    phone.getSerialNumber(), phone.getOperatingSystem());

        } else {
            throw new RuntimeException("Unsupported object class");
        }
    }

    @Override
    public <T> Optional<T> findOne(UUID id, Class<T> tClass) {
        assertClass(tClass);

        CqlSession session = cassandraConnection.getSession();
        ResultSet resultSet = session.execute("SELECT * FROM Products.Phones WHERE id = :id", id);
        return Optional.ofNullable(resultSet.one()).map(row -> mapRow(row, tClass));
    }


    @Override
    public <T> List<T> findAll(Class<T> tClass) {
        assertClass(tClass);

        CqlSession session = cassandraConnection.getSession();
        ResultSet resultSet = session.execute("SELECT * FROM Products.Phones");

        return resultSet.all().stream()
                .map(row -> mapRow(row, tClass)).collect(Collectors.toList());
    }

    private void assertClass(Class<?> tClass) {
        if (!(tClass.equals(Phone.class) || tClass.equals(SmartPhone.class))) {
            throw new RuntimeException("Unsupported object class");
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T mapRow(Row row, Class<T> tClass) {
        UUID id = row.getUuid("id");
        String model = row.getString("model");
        String color = row.getString("color");
        String serialNumber = row.getString("serialNumber");
        String operatingSystem = row.getString("operatingSystem");

        if (tClass.equals(Phone.class)) {
            return (T) new Phone(id, model, color, serialNumber);
        }
        return (T) new SmartPhone(id, model, color, serialNumber, operatingSystem);
    }

    
}
