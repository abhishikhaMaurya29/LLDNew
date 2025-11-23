import java.util.List;

public class NearestElevatorStrategy implements ElevatorAssignmentStrategy {
    @Override
    public ElevatorController getElevator(int floor, List<ElevatorController> elevatorControllerList,
                                          Direction requestedDirection) {
        ElevatorController best = null;
        int leastCost = Integer.MAX_VALUE;

        for (ElevatorController ec : elevatorControllerList) {
            Elevator elevator = ec.getElevator();
            int cost = Math.abs(floor - elevator.getCurrentFloor());

            if (!Direction.IDLE.equals(elevator.getDirection()) &&
                    elevator.getDirection() != requestedDirection) {
                cost += 1000;
            }

            if (Direction.IDLE.equals(elevator.getDirection())) {
                cost -= 10;
            }

            if (cost < leastCost) {
                leastCost = cost;
                best = ec;
            }
        }

        return best;
    }
}
