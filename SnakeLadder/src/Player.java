import java.util.UUID;

public class Player {
    String id;
    String name;
    int currentPosition;

    public Player(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.currentPosition = 0;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}
