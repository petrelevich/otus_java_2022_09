package ru.otus.solution2;

import ru.otus.core.HibernateUtils;
import ru.otus.solution2.model.*;

public class Main {
    public static void main(String[] args) {
        HibernateUtils.buildSessionFactory(OtusStudent.class, Avatar.class, EMail.class, Course.class);
    }
}
