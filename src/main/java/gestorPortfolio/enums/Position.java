package gestorPortfolio.enums;

public enum Position {
    ADMIN(1),
    MANAGER(2),
    MEMBER(3);

    private final int value;

    Position(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Position fromValue(int value) {
        for (Position position : Position.values()) {
            if (position.value == value) {
                return position;
            }
        }
        throw new IllegalArgumentException("Invalid position value: " + value);
    }
}
