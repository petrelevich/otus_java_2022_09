package ru.otus.references;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * VM options: -Xmx256m -Xms256m -Xlog:gc=debug
 */
public class ReferenceDemo {
    private static final Logger logger = LoggerFactory.getLogger(ReferenceDemo.class);

    public static void main(String[] args) throws InterruptedException {
        // strong();
        // weak();
        // soft();
        // finalizeDemo();
        // phantom();
        // phantomDemo();
    }

    private static void strong() {
        var size = 1010;
        List<BigObject> references = new ArrayList<>(size);

        for (var idx = 0; idx < size; idx++) {
            references.add(new BigObject());
            logger.info("idx:{}", idx);
        }
        //OutOfMemoryError for -Xmx256m -Xms256m
        logger.info("Size: {}", references.size());
    }

    private static void weak() {
        var size = 1010;
        List<WeakReference<BigObject>> references = new ArrayList<>(size);

        for (var idx = 0; idx < size; idx++) {
            references.add(new WeakReference<>(new BigObject()));
        }

        //Если раскоментировать, то gc удалит все объекты
        // System.gc();

        var sum = 0;
        for (var idx = 0; idx < size; idx++) {
            if (references.get(idx).get() != null) {
                sum++;
            }
        }
        logger.info("Weak references: {}", sum);
    }

    private static void soft() {
        var size = 1010;
        List<SoftReference<BigObject>> references = new ArrayList<>(size);

        for (var idx = 0; idx < size; idx++) {
            references.add(new SoftReference<>(new BigObject()));
        }

        // System.gc();

        var sum = 0;
        for (var idx = 0; idx < size; idx++) {
            if (references.get(idx).get() != null) {
                sum++;
            }
        }

        logger.info("Soft references: {}", sum);
    }

    private static void finalizeDemo() throws InterruptedException {
        var immortal = new Immortal(false);
        logger.info("immortal_1:{}", immortal);

        immortal = null;
        System.gc();
        Thread.sleep(TimeUnit.SECONDS.toMillis(3));

        //как видим объект благополучно удален
        logger.info("immortal_2:{}", immortal);
        ////

        var immortalReal = new Immortal(true);
        logger.info("immortal next:{}", immortalReal);

        immortalReal = null;
        System.gc();
        Thread.sleep(TimeUnit.SECONDS.toMillis(3));

        immortalReal = revivalBackup;
        //объект воскрес
        logger.info("immortal next_2:{}", immortalReal);
    }

    private static void phantom() throws InterruptedException {
        var a = new BigObject();
        logger.info("a: {}", a);

        //создаем очередь ReferenceQueue
        ReferenceQueue<BigObject> refQueue = new ReferenceQueue<>();

        //создаем Phantom Reference на объект типа BigObject и "подвязываем" ее на переменную a.
        PhantomReference<BigObject> phantomA = new PhantomReference<>(a, refQueue);
        logger.info("Ref in pool before GC: {}", refQueue.poll());

        logger.info("phantomA.get: {}", phantomA.get()); //всегда null

        a = null;
        //теперь объект "a" может быть удален сборщиком мусора.
        //До того, как gc сработает в refQueue будет null.


        System.gc();
        Thread.sleep(100);
        Reference<? extends BigObject> aa = refQueue.poll();
        logger.info("Ref in pool after GC: {}", aa);

        //Однако получить объект назад не получится
        BigObject resObject = aa.get();
        logger.info("resObject: {}", resObject);
    }

    /*
        Внимание!
        Не используйте этот код в домашней работе.
        Это иллюстрация идеи применения ReferenceQueue, а не готовое решение.
     */
    private static void phantomDemo() throws InterruptedException {
        var a = new BigObject();
        ReferenceQueue<BigObject> refQueue = new ReferenceQueue<>();
        var phantomReference = new PhantomReference<>(a, refQueue);

        new Thread(
                () -> {
                    try {
                        logger.info("Waiting for object cleaning...");
                        Reference<? extends BigObject> removed = refQueue.remove();
                        logger.info("Object cleaned:{}", removed);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        ).start();
        Thread.sleep(TimeUnit.SECONDS.toMillis(3));
        logger.info("cleaning...");
        a = null;
        System.gc();
        Thread.sleep(100);
        logger.info("done");
    }

    static class BigObject {
        final byte[] array = new byte[1024 * 1024];
    }

    private static Immortal revivalBackup;
    static class Immortal {
        private final boolean doRevival;

        public Immortal(boolean doRevival) {
            this.doRevival = doRevival;
        }

        @Override
        protected void finalize() {
            logger.info("finalize it");
            if (doRevival) {
                revivalBackup = this;
            }
        }
    }
}
