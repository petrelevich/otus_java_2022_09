package ru.otus.testing.example;

import org.junit.jupiter.api.*;

class LifeCycleTest {

    // Подготовительные мероприятия. Метод выполнится один раз, перед всеми тестами
    @BeforeAll
    public static void globalSetUp() {
        System.out.println("@BeforeAll");
    }

    // Подготовительные мероприятия. Метод выполнится перед каждым тестом
    @BeforeEach
    public void setUp() {
        System.out.print("\n@BeforeEach. ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
    }

    // Сам тест
    @Test
    void anyTest1() {
        System.out.print("@Test: anyTest1. ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
    }

    // Еще тест
    @Test
    void anyTest2() {
        System.out.print("@Test: anyTest2. ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
    }

    // Завершающие мероприятия. Метод выполнится после каждого теста
    @AfterEach
    public void tearDown() {
        System.out.print("@AfterEach. ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
    }

    // Завершающие мероприятия. Метод выполнится один раз, после всех тестов
    @AfterAll
    public static void globalTearDown() {
        System.out.println("\n@AfterAll");
    }
}
