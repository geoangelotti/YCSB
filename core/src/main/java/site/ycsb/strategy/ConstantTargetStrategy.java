package site.ycsb.strategy;

import site.ycsb.ClientThread;

/**
 * Constant Target Strategy.
 **/

public class ConstantTargetStrategy implements TargetStrategy {
  private final ClientThread clientThread;

  public ConstantTargetStrategy(ClientThread clientThread) {
    this.clientThread = clientThread;
  }

  @Override
  public long calculate() {
    return clientThread.getTargetOpsTickNs();
  }
}
