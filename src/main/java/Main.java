import java.util.*;

public class Main {

    public static void main(String[] args) {

        int boardSize = 5;

        // Create the board
        SnakeBoard snakeBoard = SnakeBoard.create(boardSize);

        // Create the snake
        // Put the snake in the board
        int currentHeadX = 4;
        int currentHeadY = 4;

        // TODO: The snake should be in an object to hide the implementation ?
        ElementPosition[] snake = {
                new ElementPosition(currentHeadX, currentHeadY),
                new ElementPosition(currentHeadX, currentHeadY - 1)
        };

        snakeBoard.putElement(snake[0], BoardElement.SNAKE_HEAD);
        snakeBoard.putElement(snake[1], BoardElement.SNAKE_BODY);

        // TODO: The position of the head can be calculated therefore this one is useless or it is not useless at all and keeping the track of the head position from the original movement is simplier ?
        String previousMovement = "d";

        snakeBoard.putFood(snake);

        // TODO: Put the movement management in a method/object

        Map<String, String> inverseMovement = new HashMap<>();
        inverseMovement.put("z", "s");
        inverseMovement.put("s", "z");
        inverseMovement.put("q", "d");
        inverseMovement.put("d", "q");

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

                // If Food, increase snake by one, Spawn new food
                // TODO: Should the snake be an array list ?
                snake = Arrays.copyOf(snake, snake.length + 1);

                int directionTailX = snake[snake.length - 3].getX() - snake[snake.length - 2].getX();
                if (directionTailX > 0) {
                    snake[snake.length - 1] =
                            new ElementPosition(snake[snake.length - 2].getX() - 1, snake[snake.length - 2].getY());
                } else if (directionTailX < 0) {
                    snake[snake.length - 1] =
                            new ElementPosition(snake[snake.length - 2].getX() + 1, snake[snake.length - 2].getY());
                } else {
                    int directionTailY = snake[snake.length - 3].getY() - snake[snake.length - 2].getY();
                    if (directionTailY > 0) {
                        snake[snake.length - 1] =
                                new ElementPosition(snake[snake.length - 2].getX(),
                                        snake[snake.length - 2].getY() + 1);
                    } else if (directionTailY < 0) {
                        snake[snake.length - 1] =
                                new ElementPosition(snake[snake.length - 2].getX(),
                                        snake[snake.length - 2].getY() - 1);
                    }
                }
                if(!snakeBoard.putFood(snake)) {
                    System.out.println("GAME WON ! CONGRATS !");
                }
            } else {
                snakeBoard.putElement(snake[snake.length - 1], BoardElement.EMPTY);
            }

            // TODO Put the snake Movement in a method, to add in a snake object ?
            // Move the snake
            for (int i = snake.length - 1; i >= 1; i--) {
                snake[i] = new ElementPosition(snake[i - 1].getX(), snake[i - 1].getY());
                snakeBoard.putElement(snake[i], BoardElement.SNAKE_BODY);
            }
            snake[0] = new ElementPosition(currentHeadX, currentHeadY);
            snakeBoard.putElement(snake[0], BoardElement.SNAKE_HEAD);
        }
    }
}
