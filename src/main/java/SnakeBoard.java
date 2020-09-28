import java.util.*;
import java.util.function.Function;

public class SnakeBoard {
    private final int boardSize;
    private final BoardElement[][] board;
    private final Function<Integer, Set<ElementPosition>> allPositions = (Integer size) -> {
        HashSet<ElementPosition> boardElementPositions = new HashSet<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardElementPositions.add(new ElementPosition(i, j));
            }
        }

        return boardElementPositions;
    };
    private final Random random = new Random();

    private SnakeBoard(int boardSize, BoardElement[][] board) {
        this.boardSize = boardSize;
        this.board = board;
    }

    public static SnakeBoard create(int boardSize) {
        BoardElement[][] board = new BoardElement[boardSize][boardSize];

        // Fill the board with empty element.
        for(BoardElement[] row : board) {
            Arrays.fill(row, BoardElement.EMPTY);
        }

        return new SnakeBoard(boardSize, board);
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

    // TODO: When putting a new food, the current food location need to be taken into consideration.
    public boolean putFood(ElementPosition[] snake) {
        Set<ElementPosition> snakeBodyPositions = new HashSet<>(Arrays.asList(snake));
        Set<ElementPosition> availableBoardPositions = this.allPositions.apply(this.boardSize);

        if(availableBoardPositions.isEmpty()) {
            return false;
        }

        availableBoardPositions.removeAll(snakeBodyPositions);
        ArrayList<ElementPosition> indexedAvailableBoardPositions = new ArrayList<>(availableBoardPositions);

        ElementPosition randomElementPosition = indexedAvailableBoardPositions.get(random.nextInt(boardSize));

        this.board[randomElementPosition.getX()][randomElementPosition.getY()] = BoardElement.FOOD;

        return true;
    }

}
