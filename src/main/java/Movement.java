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
                throw new IllegalArgumentException("Argument can only be one of the values of the Movement Enum.");
        }
    }
}
