public enum Movement {
    UP("z"), DOWN("s"), LEFT("q"), RIGHT("d");

    private final String value;

    private Movement(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static Movement getEnum(String value) {
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
