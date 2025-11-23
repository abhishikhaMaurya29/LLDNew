import java.util.List;

public class ElevatorManager {

    ElevatorAssignmentStrategy elevatorAssignmentStrategy;
    List<ElevatorController> elevatorControllerList;

    public ElevatorManager(ElevatorAssignmentStrategy assignmentStrategy,
                           List<ElevatorController> elevatorControllerList) {
        this.elevatorAssignmentStrategy = assignmentStrategy;
        this.elevatorControllerList = elevatorControllerList;
    }

    public void addExternalRequest(int floor, Direction direction) {
        ElevatorController ec = elevatorAssignmentStrategy.getElevator(floor, elevatorControllerList, direction);
        ec.addExternalRequest(floor, direction);
    }

    public void addInternalRequest(int elevatorId, int floor) {
        elevatorControllerList.get(elevatorId).addInternalRequest(floor);
    }

    public void stepAll() {
        for (ElevatorController ec : elevatorControllerList) {
            ec.step();
        }
    }
}