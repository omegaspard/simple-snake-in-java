package engine.core.game.snake;

import engine.core.game.BoardElement;
import engine.core.game.ElementPosition;

public class SnakePart {
    private ElementPosition elementPosition;
    private final BoardElement boardElement;

    public SnakePart(ElementPosition elementPosition, BoardElement boardElement) {
        this.elementPosition = elementPosition;
        this.boardElement = boardElement;
    }

    public ElementPosition getElementPosition() {
        return elementPosition;
    }

    public BoardElement getBoardElement() {
        return boardElement;
    }

    public void setElementPosition(ElementPosition elementPosition) {
        this.elementPosition = elementPosition;
    }

}
