package site.ycsb.strategy;

import site.ycsb.ClientThread;

import java.util.Properties;

/**
 * Sine wave Strategy.
 **/

public class SineTargetStrategy implements TargetStrategy {
  private final int period;
  private final int baseTarget;
  private final int amplitude;
  private final long startTime;

  public SineTargetStrategy(Properties properties, long startTime) {
    this.startTime = startTime;
    period = Integer.parseInt(properties.getProperty("period", "60"));
    baseTarget = Integer.parseInt(properties.getProperty("baseTarget", "50"));
    amplitude = Integer.parseInt(properties.getProperty("amplitude", "25"));
    if (baseTarget <= 0 || baseTarget - amplitude <= 0) {
      throw new IllegalArgumentException(
          String.format("baseTarget %d and amplitude %d will lead to stuck strategy.", baseTarget, amplitude));
    }
  }

  @Override
  public long calculate() {
    long now = System.currentTimeMillis();
    double radians = (2 * Math.PI * now - startTime) / (period * 1000);
    double newTarget = baseTarget + amplitude * Math.sin(radians);
    double newTargetPerThreadPerMs = newTarget / 1_000.0;
    return (long) (1_000_000 / newTargetPerThreadPerMs);
  }
}
