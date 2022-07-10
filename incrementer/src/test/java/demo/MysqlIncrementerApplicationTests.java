package demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MysqlIncrementerApplicationTests {

  private static final Random RANDOM  = new Random();

  private static final int INVOCATIONS = 10000;
  private static final int THREADS = 10;

  @Autowired
  MysqlIdentityColumnMaxValueIncrementer incrementer1;

  @Autowired
  MysqlIdentityColumnMaxValueIncrementer incrementer2;

  @Test
  void incrementersWorkIndependently() throws InterruptedException {
    var executor = Executors.newFixedThreadPool(THREADS);
    var completionService = new ExecutorCompletionService<Long>(executor);

    IntStream.range(0, INVOCATIONS)
        .mapToObj(i -> (Callable<Long>) () -> {
          Thread.sleep(RANDOM.nextLong(300));
          return i % 2 == 0 ? incrementer1.nextLongValue() : incrementer2.nextLongValue();
        })
        .forEach(completionService::submit);

    List<Long> actual = new ArrayList<>();
    int errors = 0;

    long currentMillis = System.currentTimeMillis();
    for (int i = 0; i < INVOCATIONS; i++) {
      if ((i+1) % 100 == 0) {
        var spent = System.currentTimeMillis() - currentMillis;
        System.out.println("Checking future number " + (i+1) + ". Spent time in ms: " + spent);
        currentMillis = System.currentTimeMillis();
      }
      try {
        actual.add(completionService.take().get());
      } catch (ExecutionException e) {
        errors++;
      }
    }

    executor.shutdown();
    boolean terminated = executor.awaitTermination(10, TimeUnit.SECONDS);
    assertThat(terminated).isTrue();

    var min = actual.stream().mapToLong(i -> i).min().orElseThrow();
    var max = actual.stream().mapToLong(i -> i).max().orElseThrow();
    System.out.println("Min is " + min);
    System.out.println("Max is " + max);

    assertThat(errors).isZero();
    assertThat(actual).hasSize(INVOCATIONS);
    assertThat(actual).doesNotHaveDuplicates();
  }

}