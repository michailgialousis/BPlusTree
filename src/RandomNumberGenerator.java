import java.util.Random;

public class RandomNumberGenerator {
    public static int generateRandomNumber(int N) {
        Random random = new Random();
        return random.nextInt(N);
    }
}