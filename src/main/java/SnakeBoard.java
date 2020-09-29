import java.util.*;

public class SnakeBoard {
    private final int boardSize;
    private final BoardElement[][] board;
    private final Set<ElementPosition> allPositions;
    private final Random random = new Random();
    private ElementPosition lastFoodPosition;

    private SnakeBoard(int boardSize, BoardElement[][] board, Set<ElementPosition> allPositions) {
        this.boardSize = boardSize;
        this.board = board;
        this.allPositions = allPositions;
    }

    public static SnakeBoard create(int boardSize) {
        BoardElement[][] board = new BoardElement[boardSize][boardSize];

        for (BoardElement[] row : board) {
            Arrays.fill(row, BoardElement.EMPTY);
        }

        HashSet<ElementPosition> boardElementPositions = new HashSet<>();

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                boardElementPositions.add(new ElementPosition(i, j));
            }
        }

        return new SnakeBoard(boardSize, board, Collections.unmodifiableSet(boardElementPositions));
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void putElement(ElementPosition elementPosition, BoardElement boardElement) {
        this.board[elementPosition.getX()][elementPosition.getY()] = boardElement;
    }

    public void putElements(List<SnakePart> snakeParts) {
        for (SnakePart snakePart : snakeParts) {
            putElement(snakePart.getElementPosition(), snakePart.getBoardElement());
        }
    }

    public BoardElement getBoardElementAt(ElementPosition elementPosition) {
        return this.board[elementPosition.getX()][elementPosition.getY()];
    }

    public BoardElement getBoardElementAt(int x, int y) {
        return this.board[x][y];
    }

    public boolean putFood(Set<ElementPosition> snakeBodyPositions) {
        Set<ElementPosition> currentAvailableBoardPositions = new HashSet<>(this.allPositions);

        if (currentAvailableBoardPositions.isEmpty()) {
            return false;
        }

        currentAvailableBoardPositions.removeAll(snakeBodyPositions);
        if (lastFoodPosition != null) currentAvailableBoardPositions.remove(this.lastFoodPosition);
        ArrayList<ElementPosition> indexedAvailableBoardPositions = new ArrayList<>(currentAvailableBoardPositions);
        ElementPosition randomElementPosition = indexedAvailableBoardPositions.get(random.nextInt(boardSize));

        this.lastFoodPosition = randomElementPosition;

        this.board[randomElementPosition.getX()][randomElementPosition.getY()] = BoardElement.FOOD;

        return true;
    }

    public void cleanSnake() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (this.board[i][j] == BoardElement.SNAKE_HEAD || this.board[i][j] == BoardElement.SNAKE_BODY) {
                    this.board[i][j] = BoardElement.EMPTY;
                }
            }
        }
    }
}
