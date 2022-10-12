package ru.otus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;


@State(Scope.Thread)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JMHexample {
    private static final int ARRAY_SIZE_MAX = 100_000_000;
    private static final int ARRAY_SIZE_INIT = 10;
    private MyArrayInt myArr;
    private List<Integer> arrayList;

    public static void main(String[] args) throws RunnerException {
        var opt = new OptionsBuilder().include(JMHexample.class.getSimpleName()).forks(1).build();
        new Runner(opt).run();
    }

    @Setup
    public void setup() throws Exception {
        myArr = new MyArrayInt(ARRAY_SIZE_INIT);
        arrayList = new ArrayList<>(ARRAY_SIZE_INIT);
    }

    @Benchmark
    public long myArrayIntTest() {
        for (var idx = 0; idx < ARRAY_SIZE_MAX; idx++) {
            myArr.setValue(idx, idx);
        }

        long summ = 0;
        for (var idx = 0; idx < ARRAY_SIZE_MAX; idx++) {
            summ += myArr.getValue(idx);
        }
        return summ;
    }

    @Benchmark
    public long ArrayListTest() {
        for (var idx = 0; idx < ARRAY_SIZE_MAX; idx++) {
            arrayList.add(idx, idx);
        }

        long summ = 0;
        for (var idx = 0; idx < ARRAY_SIZE_MAX; idx++) {
            summ += arrayList.get(idx);
        }
        return summ;
    }
}
