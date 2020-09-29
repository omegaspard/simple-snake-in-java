public class Drawer {
    private final SnakeBoard snakeBoard;

    public Drawer(SnakeBoard snakeBoard) {
        this.snakeBoard = snakeBoard;
    }

    public void drawBoard() {
        for (int i = 0; i < this.snakeBoard.getBoardSize(); i++) {
            for (int j = 0; j < this.snakeBoard.getBoardSize(); j++) {
                switch (snakeBoard.getBoardElementAt(i, j)) {
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
    }
}
