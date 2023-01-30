package ru.otus.collections.demo;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static java.lang.System.out;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;

//TODO please FIXME with CopyOnWriteArrayList
// Вопросы:
// - Какую коллекцию будем менять и на какую?
// - Фиксим тест сейчас!
// - *Для какого сценария по нагрузке больше всего подходит CopyOnWriteArrayList?
class FixMe3WithCopyOnWriteArrayListUnitTest {
    private static final Logger log = LoggerFactory.getLogger(FixMe3WithCopyOnWriteArrayListUnitTest.class);

    private static final int ITERATIONS_COUNT = 1000;

    @Test
    void testCopyOnWriteArrayListWorksGreat() throws InterruptedException {

        final List<String> list = new ArrayList<>();
        final CountDownLatch latch = new CountDownLatch(2);
        List<Exception> exceptions = new ArrayList<>();

        Thread t1 = new Thread(() -> {
            try {
                latch.countDown();
                latch.await();
                for (int i = 0; i < ITERATIONS_COUNT; i++) {
                    out.println("starting adding email " + i);
                    list.add(randomAlphabetic(10) + "@gmail.com");
                    out.println("finishing adding email " + i);
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                latch.countDown();
                latch.await();
                for (int i = 0; i < ITERATIONS_COUNT; i++) {
                    out.println("starting read iteration " + i);
                    for (String email : list) {
                        out.println(email);
                    }
                    out.println("finishing read iteration " + i);
                }
            } catch (Exception ex) {
                exceptions.add(ex);
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        assertThat(exceptions).withFailMessage(exceptions.toString()).isEmpty();
    }
}
