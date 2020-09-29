import java.util.*;
import java.util.stream.Collectors;

public class Snake {
    // TODO: The position of a body part of the snake and it's type are always coupled, maybe put it in an object ?
    private SnakePart[] body;
    private final SnakePart head;
    private final SnakePart tail;
    private final Movement headDirection = Movement.RIGHT;

    private Snake(SnakePart[] body, SnakePart head, SnakePart tail) {
        this.body = body;
        this.head = head;
        this.tail = tail;
    }

    public static Snake create(int boardSize) {
        SnakePart head = new SnakePart(new ElementPosition(boardSize / 2, boardSize / 2),
                BoardElement.SNAKE_HEAD);
        SnakePart tail = new SnakePart(new ElementPosition(boardSize / 2, boardSize / 2 - 1),
                BoardElement.SNAKE_BODY);
        SnakePart[] body = {head, tail};

        return new Snake(body, head, tail);
    }

    public List<SnakePart> getBody() {
        return Arrays.asList(body);
    }

    public List<BoardElement> getBodyBoardElement() {
        BoardElement[] boardElements = new BoardElement[this.body.length];
        Arrays.fill(boardElements, BoardElement.SNAKE_BODY);
        boardElements[0] = BoardElement.SNAKE_HEAD;

        return Arrays.asList(boardElements);
    }

    public SnakePart getHead() {
        return head;
    }

    public SnakePart getTail() {
        return tail;
    }

    public void moveForward(int newHeadX, int newHeadY) {
        for (int i = body.length - 1; i >= 1; i--) {
            body[i].setElementPosition(new ElementPosition(body[i - 1].getElementPosition().getX(),
                    body[i - 1].getElementPosition().getY()));
        }
        body[0].setElementPosition(new ElementPosition(newHeadX, newHeadY));
    }

    public void eatFood() {
        this.body = Arrays.copyOf(this.body, this.body.length + 1);

        int directionTailX = this.body[this.body.length - 3].getElementPosition().getX()
                - this.body[this.body.length - 2].getElementPosition().getX();
        if (directionTailX > 0) {
            this.body[this.body.length - 1].setElementPosition(
                    new ElementPosition(this.body[this.body.length - 2].getElementPosition().getX() - 1,
                            this.body[this.body.length - 2].getElementPosition().getY()));
        } else if (directionTailX < 0) {
            this.body[this.body.length - 1].setElementPosition(
                    new ElementPosition(this.body[this.body.length - 2].getElementPosition().getX() + 1,
                            this.body[this.body.length - 2].getElementPosition().getY()));
        } else {
            int directionTailY = this.body[this.body.length - 3].getElementPosition().getY()
                    - this.body[this.body.length - 2].getElementPosition().getY();
            if (directionTailY > 0) {
                this.body[this.body.length - 1].setElementPosition(
                        new ElementPosition(this.body[this.body.length - 2].getElementPosition().getX(),
                                this.body[this.body.length - 2].getElementPosition().getY() + 1));
            } else if (directionTailY < 0) {
                this.body[this.body.length - 1].setElementPosition(
                        new ElementPosition(this.body[this.body.length - 2].getElementPosition().getX(),
                                this.body[this.body.length - 2].getElementPosition().getY() - 1));
            }
        }
    }

    public Set<ElementPosition> getAllSnakePositions() {
        return Arrays.stream(body).map(SnakePart::getElementPosition).collect(Collectors.toSet());
    }

}
