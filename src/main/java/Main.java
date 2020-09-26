import java.util.*;

public class Main {

    public static void main(String[] args) {

        int boardSize = 10;

        // Create the board
        BoardElement[][] board = new BoardElement[boardSize][boardSize];

        // Fill the board with 0
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = BoardElement.EMPTY;
            }
        }

        // Create the snake
        // TODO: The snake could be represented by a single array of Pair object (x, y).
        // TODO: The element of the board could be represented by an Enum instead of an int.
        Integer[] snakeX = new Integer[2];
        Integer[] snakeY = new Integer[2];

        // Put the snake in the board
        int currentHeadX = 4;
        int currentHeadY = 4;
        int currentTailX = 4;
        int currentTailY = currentHeadX - 1;

        snakeX[0] = currentHeadX;
        snakeY[0] = currentHeadY;
        snakeX[1] = currentHeadX;
        snakeY[1] = currentHeadY - 1;

        board[snakeX[0]][snakeY[0]] = BoardElement.SNAKE_HEAD;
        board[snakeX[1]][snakeY[1]] = BoardElement.SNAKE_BODY;

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

        Map<String, String> inverseMovement = new HashMap();
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

            // Movmement listener (In terminal mode)
            Scanner scanner = new Scanner(System.in);
            HashSet<String> availableMovements = new HashSet<>(Arrays.asList("z", "q", "s", "d"));
            String movement;

            do {
                System.out.println("Enter movement (z, q, s, d): ");
                movement = scanner.nextLine();
            }
            while (!availableMovements.contains(movement) && !movement.equals(inverseMovement.getOrDefault(previousMovement, "a")));

            int previousHeadX = currentHeadX;
            int previousHeadY = currentHeadY;

            switch (movement) {
                case "z":
                    currentHeadX -= 1;
                    break;
                case "s":
                    currentHeadX += 1;
                    break;
                case "d":
                    currentHeadY += 1;
                    break;
                case "q":
                    currentHeadY -= 1;
                    break;
                default:
                    break;
            }

            // Check collision with Food, Tail, Wall
            // If Tail or Wall -> Game over
            if (currentHeadX == -1 || currentHeadX == boardSize || currentHeadY == -1 || currentHeadY == boardSize || board[currentHeadX][currentHeadY] == BoardElement.SNAKE_BODY) {
                System.out.println("GAME OVER");
                break;
            } else if (board[currentHeadX][currentHeadY] == BoardElement.FOOD) {

                // If Food increase snake by one, Spawn new food
                snakeX = Arrays.copyOf(snakeX, snakeX.length + 1);
                snakeY = Arrays.copyOf(snakeY, snakeY.length + 1);

                int directionTailX = snakeX[snakeX.length - 3] - snakeX[snakeX.length - 2];
                if(directionTailX > 0) {
                    snakeX[snakeX.length - 1] = snakeX[snakeX.length - 2] - 1;
                    snakeY[snakeY.length - 1] = snakeY[snakeY.length - 2];
                }
                else if(directionTailX < 0) {
                    snakeX[snakeX.length - 1] = snakeX[snakeX.length - 2] + 1;
                    snakeY[snakeY.length - 1] = snakeY[snakeY.length - 2];
                }
                else {
                    int directionTailY = snakeY[snakeY.length - 3] - snakeY[snakeY.length - 2];
                    if(directionTailY > 0) {
                        snakeX[snakeX.length - 1] = snakeX[snakeX.length - 2];
                        snakeY[snakeY.length - 1] = snakeY[snakeY.length - 2] + 1;
                    }
                    else if(directionTailY < 0) {
                        snakeX[snakeX.length - 1] = snakeX[snakeX.length - 2];
                        snakeY[snakeY.length - 1] = snakeY[snakeY.length - 2] - 1;
                    }
                }

                // TODO : Put the food spawning in a method.
                while (board[randomX][randomY] != BoardElement.EMPTY) {
                    randomX = random.nextInt(boardSize);
                    randomY = random.nextInt(boardSize);
                }

                board[randomX][randomY] = BoardElement.FOOD;
            } else {
                board[snakeX[snakeX.length - 1]][snakeY[snakeX.length - 1]] = BoardElement.EMPTY;
            }
            // Move the snake
            for (int i = snakeX.length - 1; i >= 1; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
                board[snakeX[i]][snakeY[i]] = BoardElement.SNAKE_BODY;
            }
            snakeX[0] = currentHeadX;
            snakeY[0] = currentHeadY;
            board[snakeX[0]][snakeY[0]] = BoardElement.SNAKE_HEAD;
        }
    }
}
