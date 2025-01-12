package site.ycsb.strategy;

import site.ycsb.ClientThread;

import java.util.Properties;

/**
 * Sine wave Strategy.
 **/

public class SineTargetStrategy implements TargetStrategy {
  private final ClientThread clientThread;
  private final int period;
  private final int baseTarget;
  private final int amplitude;

  public SineTargetStrategy(ClientThread clientThread, Properties properties) {
    this.clientThread = clientThread;
    period = Integer.parseInt(properties.getProperty("period", "60"));
    baseTarget = Integer.parseInt(properties.getProperty("baseTarget", "50"));
    amplitude = Integer.parseInt(properties.getProperty("amplitude", "25"));
    if (baseTarget <= 0 || baseTarget - amplitude <= 0) {
      throw new IllegalArgumentException(String.format("baseTarget %d and amplitude %d will lead to stuck strategy.", baseTarget, amplitude));
    }
  }

  @Override
  public long calculate() {
    double radians = (2 * Math.PI * clientThread.getOpsDone()) / period;
    double newTarget = baseTarget + amplitude * Math.sin(radians);
    //System.out.println("newTarget: " + newTarget);
    double newTargetPerThreadPerMs = newTarget / 1_000.0;
    return (long) (1_000_000 / newTargetPerThreadPerMs);
  }
}
