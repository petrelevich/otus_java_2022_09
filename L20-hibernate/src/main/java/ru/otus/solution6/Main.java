package ru.otus.solution6;

import ru.otus.core.HibernateUtils;
import ru.otus.solution6.model.Avatar;
import ru.otus.solution6.model.Course;
import ru.otus.solution6.model.EMail;
import ru.otus.solution6.model.OtusStudent;

public class Main {
    public static void main(String[] args) {
        HibernateUtils.buildSessionFactory(OtusStudent.class, Avatar.class, EMail.class, Course.class);
    }
}
