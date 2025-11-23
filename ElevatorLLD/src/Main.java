import java.util.List;

public class Main {
    public static void main(String[] args) {
        SchedulerStrategy schedulerStrategy = new ScanSchedulerStrategy();
        ElevatorAssignmentStrategy assignmentStrategy = new NearestElevatorStrategy();

        ElevatorManager elevatorManager = getElevatorManager(schedulerStrategy, assignmentStrategy);

        elevatorManager.addExternalRequest(5, Direction.UP);
        elevatorManager.addExternalRequest(2, Direction.DOWN);
        elevatorManager.addInternalRequest(0, 7);

        for (int i = 0; i < 15; i++) {
            elevatorManager.stepAll();
        }
    }

    private static ElevatorManager getElevatorManager(SchedulerStrategy schedulerStrategy, ElevatorAssignmentStrategy assignmentStrategy) {
        ElevatorController elevatorController1 = new ElevatorController(new Elevator(), schedulerStrategy);
        ElevatorController elevatorController2 = new ElevatorController(new Elevator(), schedulerStrategy);
        ElevatorController elevatorController3 = new ElevatorController(new Elevator(), schedulerStrategy);

        List<ElevatorController> elevatorControllerList = List.of(elevatorController1, elevatorController2,
                elevatorController3);
        return new ElevatorManager(assignmentStrategy, elevatorControllerList);
    }
}