package gestorPortfolio.enums;

public enum Risk {
    LOW(1),
    MEDIUM(2),
    HIGH(3);

    private final int value;

    Risk(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Risk fromValue(int value) {
        for (Risk risk : Risk.values()) {
            if (risk.value == value) {
                return risk;
            }
        }
        throw new IllegalArgumentException("Invalid risk value: " + value);
    }
}
