package ru.otus.processrunner.jobs;

import java.util.Scanner;
import java.util.stream.IntStream;

/*
from location: src/main/java
javac ru/otus/processrunner/jobs/Job.java
java ru.otus.processrunner.jobs.Job
 */
public class Job {
  public static void main(String[] args) {
    String endOfRangeEnvVar = System.getenv("endOfRange");
/*
    Scanner sc = new Scanner(System.in);
    System.out.println("Введите первую строку:");
    System.out.println("Первая строка: " + sc.nextLine());

    System.out.println("Введите вторую строку:");
    System.out.println("Вторая строка: " + sc.nextLine());
*/
    System.out.printf("EndOfRange environment variable: %s\n", endOfRangeEnvVar);
    int endOfRange = endOfRangeEnvVar == null? 100: Integer.parseInt(endOfRangeEnvVar);
    IntStream.range(1, endOfRange).forEach(System.out::println);
  }
}
