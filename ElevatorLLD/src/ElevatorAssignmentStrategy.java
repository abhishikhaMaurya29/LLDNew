import java.util.List;

public interface ElevatorAssignmentStrategy {
    ElevatorController getElevator(int floor, List<ElevatorController> elevatorControllerList, Direction requestedDirection);
}
