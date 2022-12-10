package stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StudentExample {


    public static void main(String[] args) {
        var students = List.of(new Student("Alex", 22, 5, 4.5),
                new Student("Maria", 22, 5, 3.5),
                new Student("John", 12, 4, 4.7),
                new Student("Bob", 22, 5, 4.8));

        var result = new ArrayList<Student>();
        // Напечатать имена топ-студентов 5го курса с оценкой больше 4, по убыванию
        for (Student student : students) {
            if (student.avgMark() > 4 && student.course() == 5) {
                result.add(student);
            }
        }
        result.sort((o1, o2) -> Double.compare(o2.avgMark(), o1.avgMark()));

        for (Student student : result) {
            System.out.println(student.name());
        }

        //обратите внимание: это чистая функция получения результата
        var resultNew = students.stream()
                .filter(student -> student.avgMark() > 4)
                .filter(student -> student.course() == 5)
                .sorted(Comparator.comparingDouble(Student::avgMark).reversed())
                .toList();

        //обратите внимание: побочный эффект в виде вывода на консоль отделен от чистого кода
        resultNew.forEach(student -> System.out.println(student.name()));

        //идеологически так лучше
        for (var student : resultNew) {
            System.out.println(student);
        }

        //пример гнусного применения. Изменяем другой объект из лямбда-функции.
        var resultNew2 = new ArrayList<Student>();
        students.stream()
                .filter(student -> student.avgMark() > 4)
                .filter(student -> student.course() == 5)
                .sorted(Comparator.comparingDouble(Student::avgMark).reversed())
                .forEach(resultNew2::add);

        resultNew2.forEach(student -> System.out.println(student.name()));
    }
}
