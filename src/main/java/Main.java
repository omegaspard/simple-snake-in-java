import java.util.*;

public class Main {

    public static void main(String[] args) {

        int boardSize = 5;

        // Create the board
        SnakeBoard snakeBoard = SnakeBoard.create(boardSize);

        // Create the snake
        Snake snake = Snake.create(boardSize);

        // TODO To transfer when managing the movement in an object.
        int currentHeadX = snake.getHead().getX();
        int currentHeadY = snake.getHead().getY();

        // Put the snake in the board

        snakeBoard.putElement(snake.getHead(), BoardElement.SNAKE_HEAD);
        snakeBoard.putElement(snake.getTail(), BoardElement.SNAKE_BODY);

        // TODO: The position of the head can be calculated therefore this one is useless or it is not useless at all
        //  and keeping the track of the head position from the original movement is simpler ?
        String previousMovement = "d";

        snakeBoard.putFood(snake.getAllSnakePositions());

        // TODO: Put the movement management in a method/object

        Map<String, String> inverseMovement = new HashMap<>();
        inverseMovement.put("z", "s");
        inverseMovement.put("s", "z");
        inverseMovement.put("q", "d");
        inverseMovement.put("d", "q");

        // TODO: Put the drawing in an object
        // While loop that run the game
        // Draw the board
        while (true) {
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    switch (snakeBoard.getBoardElementAt(new ElementPosition(i, j))) {
                        case EMPTY:
                            System.out.print("[ ]");
                            break;
                        case SNAKE_HEAD:
                            System.out.print("[*]");
                            break;
                        case SNAKE_BODY:
                            System.out.print("[o]");
                            break;
                        case FOOD:
                            System.out.print("[#]");
                            break;
                    }
                }
                System.out.print("\n");
            }

            // Movement listener (In terminal mode)
            Scanner scanner = new Scanner(System.in);
            HashSet<String> availableMovements = new HashSet<>(Arrays.asList("z", "q", "s", "d"));
            String movement;

            do {
                System.out.println("Enter movement (z, q, s, d): ");
                movement = scanner.nextLine();
            }
            while (!availableMovements.contains(movement) &&
                    !movement.equals(inverseMovement.getOrDefault(previousMovement, "a")));

            switch (Movement.fromString(movement)) {
                case UP:
                    currentHeadX -= 1;
                    break;
                case DOWN:
                    currentHeadX += 1;
                    break;
                case RIGHT:
                    currentHeadY += 1;
                    break;
                case LEFT:
                    currentHeadY -= 1;
                    break;
                default:
                    break;
            }

            // Check collision with Food, Tail, Wall
            // If Tail or Wall -> Game over
            if (currentHeadX == -1 || currentHeadX == boardSize || currentHeadY == -1 ||
                    currentHeadY == boardSize ||
                    snakeBoard.getBoardElementAt(new ElementPosition(currentHeadX, currentHeadY))
                            == BoardElement.SNAKE_BODY) {
                System.out.println("GAME OVER");
                break;
            } else if (snakeBoard.getBoardElementAt(new ElementPosition(currentHeadX, currentHeadY))
                    == BoardElement.FOOD) {

                snake.eatFood();

                if(!snakeBoard.putFood(snake.getAllSnakePositions())) {
                    System.out.println("GAME WON ! CONGRATS !");
                    break;
                }
            }

            // Move the snake
            snakeBoard.cleanSnake();
            snake.moveForward(currentHeadX, currentHeadY);
            snakeBoard.putElements(snake.getBody(), snake.getBodyBoardElement());
        }
    }
}
