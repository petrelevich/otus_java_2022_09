package ru.otus.collections.demo;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;

//TODO please FIXME with BlockingQueue
// Вопросы:
// - Что делает это многопоточное приложение?
// - Какие есть проблемы в данном многопоточном приложении?
// - Запустим приложение прямо сейчас!
// - Фиксим тест сейчас!
// - *Для какого сценария по нагрузке больше всего подходит BlockingQueue?
class FixMe5WithBlockingQueueTest {
    private static final int ITERATIONS_COUNT = 1000;

    @Test
    void testBlockingQueueWorksGreat() throws InterruptedException {

        List<Integer> list = new ArrayList<>();
        final CountDownLatch latch = new CountDownLatch(2);
        List<Exception> exceptions = new CopyOnWriteArrayList<>();

        Thread t1 = new Thread(() -> {
            try {
                latch.countDown();
                latch.await();
                for (int i = 0; i < ITERATIONS_COUNT; i++) {
                    list.remove(0);
                }
            } catch (Exception ex) {
                exceptions.add(ex);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                latch.countDown();
                latch.await();
                for (int i = 0; i < ITERATIONS_COUNT; i++) {
                    list.add(list.size(), 5);
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
