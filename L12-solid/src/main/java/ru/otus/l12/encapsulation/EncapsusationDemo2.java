package ru.otus.l12.encapsulation;

import java.util.*;

// Неизменяемые коллекции
class CompanyUnmodifiableDepartments {
    private List<Department> departments = new ArrayList<>();

    public List<Department> getDepartments() {
        return Collections.unmodifiableList(departments);
    }
}

// Сужаем интерфейс
class CompanyIterableDepartments {
    private List<Department> departments = new ArrayList<>();

    public Iterable<Department> getDepartments() {
        return departments;
    }

    public void addDepartment(Department department) {
        // ... какая-то логика
        departments.add(department);
        // ... какая-то логика
    }
}

public class EncapsusationDemo2 {

    public static void main(String[] args) {
        var company1 = new CompanyUnmodifiableDepartments();
        var departments1 = company1.getDepartments();

        try {
            departments1.add(new Department());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        var company2 = new CompanyIterableDepartments();
        company2.addDepartment(new Department());
        company2.addDepartment(new Department());
        var departments2 = company2.getDepartments();

        for (Department department : departments2) {
            System.out.println(department);
        }
    }

}
