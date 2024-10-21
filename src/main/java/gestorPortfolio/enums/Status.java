package gestorPortfolio.enums;

public enum Status {
    UNDER_REVIEW(1),
    ANALYSIS_PERFORMED(2),
    ANALYSIS_APPROVED(3),
    STARTED(4),
    PLANNED(5),
    IN_PROGRESS(6),
    TERMINATED(7),
    CANCELED(8);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Status fromValue(int value) {
        for (Status status : Status.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + value);
    }
}
