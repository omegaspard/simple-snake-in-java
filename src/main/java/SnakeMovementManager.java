import java.util.*;

public class SnakeMovementManager {
    private static final Set<String> availableInputs = new HashSet<>(Arrays.asList("z", "q", "s", "d"));
    private static final Map<Movement, Movement> inverseMovement = new HashMap<>();
    static {
        inverseMovement.put(Movement.UP, Movement.DOWN);
        inverseMovement.put(Movement.DOWN, Movement.UP);
        inverseMovement.put(Movement.LEFT, Movement.RIGHT);
        inverseMovement.put(Movement.RIGHT, Movement.LEFT);
    }
    private final Scanner scanner = new Scanner(System.in);

    public ElementPosition getMovement(Movement headDirection) {
        String movement;

        do {
            System.out.println("Enter movement (z, q, s, d): ");
            movement = scanner.nextLine();
        }
        while (!availableInputs.contains(movement) &&
                !Movement.fromString(movement).equals(inverseMovement.get(headDirection)));

        switch (Movement.fromString(movement)) {
            case UP:
                return new ElementPosition(-1 , 0);
            case DOWN:
                return new ElementPosition(1, 0);
            case RIGHT:
                return new ElementPosition(0, 1);
            case LEFT:
                return new ElementPosition(0, -1);
            default:
                throw new IllegalStateException("Unexpected value: " + Movement.fromString(movement));
        }
    }

}
