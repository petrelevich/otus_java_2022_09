package ru.otus.exercise;

import ru.otus.core.HibernateUtils;
import ru.otus.exercise.model.*;

public class Main {
    public static void main(String[] args) {
        HibernateUtils.buildSessionFactory(OtusStudent.class, Avatar.class, EMail.class, Course.class);
    }
}
