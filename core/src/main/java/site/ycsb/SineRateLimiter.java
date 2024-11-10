package site.ycsb;

import java.util.Properties;

/**
 * A SineRateLimiter that changes the current target simulating a sine wave.
 */

public class SineRateLimiter {
  private double baseTarget;
  private double amplitudeTarget;
  private double periodSeconds;
  private long startTime;

  public void init(Properties props) {
    baseTarget = Double.parseDouble(props.getProperty("baseTarget", "1000"));
    amplitudeTarget = Double.parseDouble(props.getProperty("amplitudeTarget", "500"));
    periodSeconds = Double.parseDouble(props.getProperty("periodSeconds", "60"));
    startTime = System.currentTimeMillis();
  }

  public void acquire(int permits) {
    long currentTime = System.currentTimeMillis();
    double elapsedSeconds = (currentTime - startTime) / 1000.0;
    
    // Calculate current target rate based on sine wave
    double currentTarget = baseTarget + amplitudeTarget * Math.sin(2 * Math.PI * elapsedSeconds / periodSeconds);
    
    // Convert to microseconds per operation
    double sleepMicros = 1000000.0 / currentTarget;
    
    try {
      Thread.sleep((long)(sleepMicros / 1000));
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
