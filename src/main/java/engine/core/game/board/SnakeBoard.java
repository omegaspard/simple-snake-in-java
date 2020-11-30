package engine.core.game.board;

import engine.core.game.BoardElement;
import engine.core.game.ElementPosition;
import engine.core.game.snake.SnakePart;

import java.util.*;

public class SnakeBoard {
    private final int boardSize;
    private final BoardElement[][] board;
    private final Random random = new Random();

    private SnakeBoard(int boardSize, BoardElement[][] board) {
        this.boardSize = boardSize;
        this.board = board;
    }

    public static SnakeBoard create(int boardSize) {
        BoardElement[][] board = new BoardElement[boardSize][boardSize];

        for (BoardElement[] row : board) {
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

    public boolean putFood() {
        Set<ElementPosition> currentAvailableBoardPositions = this.getCurrentAvailablePosition();

        if (currentAvailableBoardPositions.isEmpty()) {
            return false;
        }

        ArrayList<ElementPosition> indexedAvailableBoardPositions = new ArrayList<>(currentAvailableBoardPositions);
        ElementPosition randomElementPosition =
                indexedAvailableBoardPositions.get(random.nextInt(indexedAvailableBoardPositions.size()));
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

    private Set<ElementPosition> getCurrentAvailablePosition() {
        HashSet<ElementPosition> elementPositions = new HashSet<>();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (this.board[i][j] == BoardElement.EMPTY) {
                    elementPositions.add(new ElementPosition(i, j));
                }
            }
        }
        return elementPositions;
    }
}
