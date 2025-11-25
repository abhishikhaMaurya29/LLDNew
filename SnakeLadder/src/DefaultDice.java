import java.util.Random;

public class DefaultDice implements Dice {
    private final int face;
    private final Random random;

    public DefaultDice(int face) {
        this.face = face;
        this.random = new Random();
    }

    @Override
    public int roll() {
        return random.nextInt(face);
    }
}
