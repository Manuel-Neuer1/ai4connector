package prompt;

import java.nio.file.Path;

public class PromptArm {
    private final Path promptPath;
    private int timesTried = 0;
    private double totalReward = 0.0;
    private double lastReward = 0.0;
    private final double explorationFactor;

    public PromptArm(Path promptPath) {
        this(promptPath, Math.sqrt(2));
    }

    public PromptArm(Path promptPath, double explorationFactor) {
        this.promptPath = promptPath;
        this.explorationFactor = explorationFactor;
    }

    /**
     * 记录一次尝试及其 reward，返回新的平均奖励
     */
    public double recordReward(double reward) {
        lastReward = reward;
        totalReward += reward;
        timesTried++;
        return averageReward();
    }

    /**
     * 计算平均奖励
     */
    public double averageReward() {
        return timesTried == 0 ? 0.0 : totalReward / timesTried;
    }

    /**
     * 计算 UCB1 分数
     * @param totalPlays 所有臂的总尝试次数
     */
    public double ucbScore(int totalPlays) {
        if (timesTried == 0) {
            return Double.POSITIVE_INFINITY;
        }
        double avg = totalReward / timesTried;
        return avg + explorationFactor * Math.sqrt(Math.log(totalPlays) / timesTried);
    }

    public Path getPromptPath() {
        return promptPath;
    }

    public int getTimesTried() {
        return timesTried;
    }

    public double getTotalReward() {
        return totalReward;
    }

    public double getLastReward() {
        return lastReward;
    }

    @Override
    public String toString() {
        return String.format(
                "PromptArm[path=%s, tried=%d, avgReward=%.3f, lastReward=%.3f]",
                promptPath.getFileName(),
                timesTried,
                averageReward(),
                lastReward
        );
    }
}
