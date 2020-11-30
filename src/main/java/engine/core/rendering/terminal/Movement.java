package engine.core.rendering.terminal;

import engine.core.game.ElementPosition;

public enum Movement {
    UP, DOWN, LEFT, RIGHT;

    public static Movement fromString(String value) {
        switch (value) {
            case "z":
                return UP;
            case "s":
                return DOWN;
            case "q":
                return LEFT;
            case "d":
                return RIGHT;
            default:
                throw new IllegalArgumentException("Argument can only be one of z q s or d.");
        }
    }

    public static Movement fromElementPosition(ElementPosition elementPosition) {
        if (new ElementPosition(-1, 0).equals(elementPosition)) {
            return UP;
        } else if (new ElementPosition(1, 0).equals(elementPosition)) {
            return DOWN;
        } else if (new ElementPosition(0, 1).equals(elementPosition)) {
            return RIGHT;
        } else if (new ElementPosition(0, -1).equals(elementPosition)) {
            return LEFT;
        }
        throw new IllegalStateException("Unexpected value: " + elementPosition);
    }
}
