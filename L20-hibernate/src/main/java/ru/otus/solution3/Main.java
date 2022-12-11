package ru.otus.solution3;

import ru.otus.core.HibernateUtils;
import ru.otus.solution3.model.*;

public class Main {
    public static void main(String[] args) {
        HibernateUtils.buildSessionFactory(OtusStudent.class, Avatar.class, EMail.class, Course.class);
    }
}
