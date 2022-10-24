package ru.otus.gc.bench;

import com.sun.management.GarbageCollectionNotificationInfo;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import javax.management.*;
import javax.management.openmbean.CompositeData;

/*
О формате логов
http://openjdk.java.net/jeps/158

Анализ лога GC
https://gceasy.io

Working directory:
Указать полный путь к папке otus_java_2022_09/L08-gc/demo

VM options:

-Xms512m
-Xmx512m
-verbose:gc
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/dump
-XX:+UseG1GC
*/

/*
Основная настройка:
    -XX:MaxGCPauseMillis=100000
    -XX:MaxGCPauseMillis=10
*/

public class GcDemo {
    private volatile int objectArraySize = 5 * 1000 * 1000;

    public static void main(String... args) throws Exception {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());

        GcDemo gcDemo = new GcDemo();

        switchOnMonitoring();
        switchOnMxBean(gcDemo);

        gcDemo.run();
    }

    public int getObjectArraySize() {
        return objectArraySize;
    }

    public void setObjectArraySize(int objectArraySize) {
        this.objectArraySize = objectArraySize;
    }

    private void run() throws InterruptedException {
        long beginTime = System.currentTimeMillis();
        createObject();
        System.out.println("time:" + (System.currentTimeMillis() - beginTime) / 1000);
    }

    private void createObject() throws InterruptedException {
        int loopCounter = 1000;
        for (int loopIdx = 0; loopIdx < loopCounter; loopIdx++) {
            int local = objectArraySize;
            Object[] array = new Object[local];
            for (int idx = 0; idx < local; idx++) {
                array[idx] = new String(new char[0]);
            }
            Thread.sleep(10); //Label_1
        }
    }

    private static void switchOnMxBean(GcDemo gcDemo) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, InterruptedException {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=gcDemo");

        GcDemoControlMBean mbean = new GcDemoControl(gcDemo);
        mbs.registerMBean(mbean, name);
    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo
                        .from((CompositeData) notification.getUserData());
                String gcName = info.getGcName();
                String gcAction = info.getGcAction();
                String gcCause = info.getGcCause();

                long startTime = info.getGcInfo().getStartTime();
                long duration = info.getGcInfo().getDuration();

                System.out.println("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
            };
            emitter.addNotificationListener(
                    listener,
                    notification -> notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION),
                    null);
        }
    }
}
