import java.util.*;

public class Snake {
    // TODO: The position of a body part of the snake and it's type are always coupled, maybe put it in an object ?
    private ElementPosition[] body;
    private final ElementPosition head;
    private final ElementPosition tail;
    private final Movement headDirection = Movement.RIGHT;

    private Snake(ElementPosition[] body, ElementPosition head, ElementPosition tail) {
        this.body = body;
        this.head = head;
        this.tail = tail;
    }

    public static Snake create(int boardSize) {
        ElementPosition head = new ElementPosition(boardSize / 2, boardSize / 2);
        ElementPosition tail = new ElementPosition(boardSize / 2, boardSize / 2 - 1);
        ElementPosition[] body = {head, tail};

        return new Snake(body, head, tail);
    }

    public List<ElementPosition> getBody() {
        return Arrays.asList(body);
    }

    public List<BoardElement> getBodyBoardElement() {
        BoardElement[] boardElements = new BoardElement[this.body.length];
        Arrays.fill(boardElements, BoardElement.SNAKE_BODY);
        boardElements[0] = BoardElement.SNAKE_HEAD;

        return Arrays.asList(boardElements);
    }

    public ElementPosition getHead() {
        return head;
    }

    public ElementPosition getTail() {
        return tail;
    }

    public void moveForward(int newHeadX, int newHeadY) {
        for (int i = body.length - 1; i >= 1; i--) {
            body[i] = new ElementPosition(body[i - 1].getX(), body[i - 1].getY());
        }
        body[0] = new ElementPosition(newHeadX, newHeadY);
    }

    public void eatFood() {
        this.body = Arrays.copyOf(this.body, this.body.length + 1);

        int directionTailX = this.body[this.body.length - 3].getX() - this.body[this.body.length - 2].getX();
        if (directionTailX > 0) {
            this.body[this.body.length - 1] =
                    new ElementPosition(this.body[this.body.length - 2].getX() - 1, this.body[this.body.length - 2].getY());
        } else if (directionTailX < 0) {
            this.body[this.body.length - 1] =
                    new ElementPosition(this.body[this.body.length - 2].getX() + 1, this.body[this.body.length - 2].getY());
        } else {
            int directionTailY = this.body[this.body.length - 3].getY() - this.body[this.body.length - 2].getY();
            if (directionTailY > 0) {
                this.body[this.body.length - 1] =
                        new ElementPosition(this.body[this.body.length - 2].getX(),
                                this.body[this.body.length - 2].getY() + 1);
            } else if (directionTailY < 0) {
                this.body[this.body.length - 1] =
                        new ElementPosition(this.body[this.body.length - 2].getX(),
                                this.body[this.body.length - 2].getY() - 1);
            }
        }
    }

    public Set<ElementPosition> getAllSnakePositions() {
        return new HashSet<>(Arrays.asList(body));
    }

}
