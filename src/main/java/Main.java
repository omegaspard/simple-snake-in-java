import java.util.*;

public class Main {

    public static void main(String[] args) {

        // Create the board
        Integer[][] board = new Integer[10][10];

        // Fill the board with 0

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = 0;
            }
        }

        // Create the snake
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

        board[snakeX[0]][snakeY[0]] = 1;
        board[snakeX[1]][snakeY[1]] = 2;

        String previousMovement = "d";

        // Generate random number to put the first food
        Random random = new Random();

        int randomX = random.nextInt(10);
        int randomY = random.nextInt(10);

        while (board[randomX][randomY] != 0) {
            randomX = random.nextInt(10);
            randomY = random.nextInt(10);
        }

        board[randomX][randomY] = 3;

        Map<String, String> inverseMovement = new HashMap<String, String>();
        inverseMovement.put("z", "s");
        inverseMovement.put("s", "z");
        inverseMovement.put("q", "d");
        inverseMovement.put("d", "q");

        // While loop that run the game
        // Draw the board
        while (true) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    switch (board[i][j]) {
                        case 0:
                            System.out.print("[ ]");
                            break;
                        case 1:
                            System.out.print("[*]");
                            break;
                        case 2:
                            System.out.print("[o]");
                            break;
                        case 3:
                            System.out.print("[#]");
                            break;
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
            if (currentHeadX == -1 || currentHeadX == 10 || currentHeadY == -1 || currentHeadY == 10 || board[currentHeadX][currentHeadY] == 2) {
                System.out.println("GAME OVER");
                break;
            } else if (board[currentHeadX][currentHeadY] == 3) {

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

                while (board[randomX][randomY] != 0) {
                    randomX = random.nextInt(10);
                    randomY = random.nextInt(10);
                }

                board[randomX][randomY] = 3;
            } else {
                board[snakeX[snakeX.length - 1]][snakeY[snakeX.length - 1]] = 0;
            }
            // Move the snake
            for (int i = snakeX.length - 1; i >= 1; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
                board[snakeX[i]][snakeY[i]] = 2;
            }
            snakeX[0] = currentHeadX;
            snakeY[0] = currentHeadY;
            board[snakeX[0]][snakeY[0]] = 1;
        }
    }
}
