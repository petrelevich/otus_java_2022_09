package ru.otus.table_ineritance;

import org.h2.tools.Console;
import org.hibernate.SessionFactory;
import ru.otus.core.HibernateUtils;
import ru.otus.table_ineritance.model.A;
import ru.otus.table_ineritance.model.B;
import ru.otus.table_ineritance.model.C;

import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;

public class InheritanceDemo {

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) throws SQLException {
        SessionFactory sf = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_FILE, A.class, B.class, C.class);
        System.out.println("\n\n-------------------------------------------\n\n");
        System.out.println("Начинаем вставку сущностей A/B/C: ");
        try (var session = sf.openSession()) {

            var a = new A(0, "aaaaaa1");
            var b = new B(0, "aaaaaa2", "bbbbbbb");
            var c = new C(0, "aaaaaa3", "ccccccc");

            session.getTransaction().begin();
            session.persist(a);
            session.persist(b);
            session.persist(c);
            session.getTransaction().commit();

            System.out.println("\n\n-------------------------------------------\n\n");
            System.out.println("Загружаем все сущности A (в т.ч. наследников):");
            session.getTransaction().begin();
            TypedQuery<A> query = session.createQuery("select a from A a", A.class);
            List<A> resultList = query.getResultList();

            System.out.println("\n\nРезультат:");
            System.out.println(resultList);
            session.getTransaction().commit();
        }
        Console.main();
    }
}
