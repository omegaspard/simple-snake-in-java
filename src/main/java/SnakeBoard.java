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

        for(BoardElement[] row : board) {
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

    public BoardElement getBoardElementAt(ElementPosition elementPosition) {
        return this.board[elementPosition.getX()][elementPosition.getY()];
    }

    public boolean putFood(ElementPosition[] snake) {
        Set<ElementPosition> snakeBodyPositions = new HashSet<>(Arrays.asList(snake));
        Set<ElementPosition> currentAvailableBoardPositions = new HashSet<>(this.allPositions);

        if(currentAvailableBoardPositions.isEmpty()) {
            return false;
        }

        currentAvailableBoardPositions.removeAll(snakeBodyPositions);
        if(lastFoodPosition != null) {
            currentAvailableBoardPositions.remove(this.lastFoodPosition);
        }
        ArrayList<ElementPosition> indexedAvailableBoardPositions = new ArrayList<>(currentAvailableBoardPositions);

        ElementPosition randomElementPosition = indexedAvailableBoardPositions.get(random.nextInt(boardSize));

        this.lastFoodPosition = randomElementPosition;

        this.board[randomElementPosition.getX()][randomElementPosition.getY()] = BoardElement.FOOD;

        return true;
    }
}
