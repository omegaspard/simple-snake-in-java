public class SnakeGameController {
    public static void run(int boardSize) {
        // Create the board
        SnakeBoard snakeBoard = SnakeBoard.create(boardSize);

        // Create the snake
        Snake snake = Snake.create(boardSize);

        // Put the snake in the board
        snakeBoard.putElement(snake.getHead().getElementPosition(), BoardElement.SNAKE_HEAD);
        snakeBoard.putElement(snake.getTail().getElementPosition(), BoardElement.SNAKE_BODY);
        snakeBoard.putFood(snake.getAllSnakePositions());

        Drawer drawer = new Drawer(snakeBoard);
        SnakeMovementManager snakeMovementManager = new SnakeMovementManager();

        // While loop that run the game
        // Draw the board
        while (true) {
            drawer.drawBoard();
            ElementPosition movement = snakeMovementManager.getMovement(snake.getHeadDirection());

            snake.setHeadDirection(Movement.fromElementPosition(movement));

            int currentHeadX = snake.getHead().getElementPosition().getX() + movement.getX();
            int currentHeadY = snake.getHead().getElementPosition().getY() + movement.getY();

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
            snakeBoard.putElements(snake.getBody());
        }
    }
}
