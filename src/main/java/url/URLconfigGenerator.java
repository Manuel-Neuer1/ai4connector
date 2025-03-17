package url;

import java.util.Random;

public abstract class URLconfigGenerator {
    protected Random r;

    public abstract Object generateRandomUrlConfig();

    public abstract Object generateRandomBoolUrlConfig();

    protected int selectRandomValue(int[] values) {
        int randomIndex = r.nextInt(values.length);
        return values[randomIndex];
    }

    public URLconfigGenerator(Random r) {
        this.r = r;
    }
}
