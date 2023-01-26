package ru.otus.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolDemo {
    private static final Logger logger = LoggerFactory.getLogger(ForkJoinPoolDemo.class);

    public static void main(String[] args) {
        new ForkJoinPoolDemo().go();
    }

    private void go() {
        var forkJoinPool = new ForkJoinPool();

        var result = forkJoinPool.invoke(new TaskSumInt(new int[]{1, 2, 3, 4, 5, 6, 7}));
        logger.info("result:{}", result);
    }

    public static class TaskSumInt extends RecursiveTask<Integer> {
        private final int[] data;

        TaskSumInt(int[] data) {
            this.data = data;
            logger.info("data:{}", data);
        }

        @Override
        protected Integer compute() {
            if (data.length > 1) {
                var taskL = new TaskSumInt(Arrays.copyOfRange(data, 0, data.length / 2));
                taskL.fork();

                var taskR = new TaskSumInt(Arrays.copyOfRange(data, data.length / 2, data.length));
                taskR.fork();

                var result = taskL.join();
                result += taskR.join();
                return result;
            } else {
                return data[0];
            }
        }
    }
}
