import java.util.*;

public class Main {

    public static void main(String[] args) {

        int boardSize = 5;

        // Create the board
        BoardElement[][] board = new BoardElement[boardSize][boardSize];

        // Fill the board with 0
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = BoardElement.EMPTY;
            }
        }

        // Create the snake
        // Put the snake in the board
        int currentHeadX = 4;
        int currentHeadY = 4;

        ElementPosition[] snake = {
                new ElementPosition(currentHeadX, currentHeadY),
                new ElementPosition(currentHeadX, currentHeadY - 1)
        };

        board[snake[0].getX()][snake[0].getX()] = BoardElement.SNAKE_HEAD;
        board[snake[1].getX()][snake[1].getY()] = BoardElement.SNAKE_BODY;

        // TODO: The position of the head can be calculated therefore this one is useless
        String previousMovement = "d";

        // Generate random number to put the first food
        // TODO: The available position could be put in a Set and we make a random on an index of this Set.
        Random random = new Random();

        int randomX = random.nextInt(boardSize);
        int randomY = random.nextInt(boardSize);

        while (board[randomX][randomY] != BoardElement.EMPTY) {
            randomX = random.nextInt(boardSize);
            randomY = random.nextInt(boardSize);
        }

        board[randomX][randomY] = BoardElement.FOOD;

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
                    switch (board[i][j]) {
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
                        default:
                            throw new IllegalStateException("Unexpected value: " + board[i][j]);
                    }
                }
                System.out.print("\n");
            }

            // TODO: Put the movement management in a method/object
            // TODO: Use enum to name the different movement.
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

            switch (Movement.getEnum(movement)) {
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
                    currentHeadY == boardSize || board[currentHeadX][currentHeadY] == BoardElement.SNAKE_BODY) {
                System.out.println("GAME OVER");
                break;
            } else if (board[currentHeadX][currentHeadY] == BoardElement.FOOD) {

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

                // TODO : Put the food spawning in a method.
                while (board[randomX][randomY] != BoardElement.EMPTY) {
                    randomX = random.nextInt(boardSize);
                    randomY = random.nextInt(boardSize);
                }

                board[randomX][randomY] = BoardElement.FOOD;
            } else {
                board[snake[snake.length - 1].getX()][snake[snake.length - 1].getY()] = BoardElement.EMPTY;
            }
            // Move the snake
            for (int i = snake.length - 1; i >= 1; i--) {
                snake[i] = new ElementPosition(snake[i - 1].getX(), snake[i - 1].getY());
                board[snake[i].getX()][snake[i].getY()] = BoardElement.SNAKE_BODY;
            }
            snake[0] = new ElementPosition(currentHeadX, currentHeadY);
            board[snake[0].getX()][snake[0].getY()] = BoardElement.SNAKE_HEAD;
        }
    }
}
