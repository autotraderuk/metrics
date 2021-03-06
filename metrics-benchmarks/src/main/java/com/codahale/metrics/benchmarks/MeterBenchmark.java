package com.codahale.metrics.benchmarks;

import com.codahale.metrics.Meter;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import static com.codahale.metrics.MeasurementPublisher.DO_NOT_PUBLISH;

@State(Scope.Benchmark)
public class MeterBenchmark {

    private final Meter meter = new Meter("meter", DO_NOT_PUBLISH);

    // It's intentionally not declared as final to avoid constant folding
    private long nextValue = 0xFBFBABBA;

    @Benchmark
    public Object perfMark() {
        meter.mark(nextValue);
        return meter;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + MeterBenchmark.class.getSimpleName() + ".*")
                .warmupIterations(3)
                .measurementIterations(5)
                .threads(4)
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
