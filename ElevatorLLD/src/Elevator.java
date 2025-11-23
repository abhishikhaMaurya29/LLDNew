public class Elevator {
    private int currentFloor;
    private Direction direction;

    public Elevator() {
        this.currentFloor = 0;
        this.direction = Direction.IDLE;
    }

    public int getCurrentFloor() {
        return this.currentFloor;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void openDoor(int floor) {
        System.out.println(System.currentTimeMillis() + ", Door is opening at " + floor);
    }

    public void closeDoor(int floor) {
        System.out.println(System.currentTimeMillis() + ", Door is closed at " + floor);
    }

    public void moveUp() {
        currentFloor++;
        direction = Direction.UP;
    }

    public void moveDown() {
        currentFloor--;
        direction = Direction.DOWN;
    }
}
