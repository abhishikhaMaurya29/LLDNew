import java.util.Collections;
import java.util.TreeSet;

public class ElevatorController {
    private final Elevator elevator;
    private final SchedulerStrategy schedulerStrategy;
    private final TreeSet<Integer> upQueue;
    private final TreeSet<Integer> downQueue;

    public ElevatorController(Elevator elevator, SchedulerStrategy schedulerStrategy) {
        this.elevator = elevator;
        this.schedulerStrategy = schedulerStrategy;
        this.upQueue = new TreeSet<>();
        this.downQueue = new TreeSet<>(Collections.reverseOrder());
    }

    public void addExternalRequest(int floor, Direction direction) {
        addRequest(floor, direction);
    }

    public void addInternalRequest(int floor) {
        addRequest(floor, null);
    }

    private void addRequest(int floor, Direction direction) {
        if (floor == elevator.getCurrentFloor()) {
            elevator.openDoor(floor);
        } else if (Direction.UP.equals(direction)) {
            upQueue.add(floor);
        } else if (Direction.DOWN.equals(direction)) {
            downQueue.add(floor);
        } else {
            if (floor < elevator.getCurrentFloor()) {
                downQueue.add(floor);
            } else if (floor > elevator.getCurrentFloor()) {
                upQueue.add(floor);
            }
        }
    }

    public Elevator getElevator(){
        return this.elevator;
    }

    public void step() {
        int nextStep = schedulerStrategy.getNextFloor(elevator.getCurrentFloor(),
                elevator.getDirection(), upQueue, downQueue);

        if (nextStep == -1) {
            elevator.setDirection(Direction.IDLE);
            return;
        }

        if (nextStep < elevator.getCurrentFloor()) {
            elevator.moveDown();
        } else if (nextStep > elevator.getCurrentFloor()) {
            elevator.moveUp();
        } else {
            elevator.openDoor(elevator.getCurrentFloor());
            elevator.closeDoor(elevator.getCurrentFloor());
            upQueue.remove(nextStep);
            downQueue.remove(nextStep);
        }
    }
}
