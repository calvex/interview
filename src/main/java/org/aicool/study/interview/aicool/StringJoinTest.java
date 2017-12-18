package org.aicool.study.interview.aicool;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Warmup(iterations = 10)
@Measurement(iterations = 20)
@Fork(1)
public class StringJoinTest {

    public static List<String> stringList = new ArrayList<>();

    @Setup
    public void prepare() {
        for (int i = 0; i < 1000000; i++) {
            stringList.add(UUID.randomUUID().toString());
        }
    }

    @Benchmark
    public void testStream() {
        String collect = stringList.stream().collect(Collectors.joining(","));
    }

    @Benchmark
    public void testStringJoin() {
        String join = String.join(",", stringList);

    }

    @Benchmark
    public void testStringBuilder() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String anInt : stringList) {
            stringBuilder.append(anInt).append(",");
        }
        if (stringBuilder.length() > 0) {
            String substring = stringBuilder.substring(0, stringBuilder.length() - 1);
        }
    }

    @Benchmark
    public void testStringJoiner() {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (String anInt : stringList) {
            stringJoiner.add(anInt);
        }
        String s = stringJoiner.toString();

    }


    public static void main(String[] args) throws RunnerException {
        Options build = new OptionsBuilder().include(StringJoinTest.class.getSimpleName()).build();
        new Runner(build).run();
    }
}
