package az.code.carlada.enums;

public enum Status {
    FREE(0),
    STANDARD (12),
    VIP(15);

    private final double amount;

    private Status(double amount) {
        this.amount = amount;
    }

    public double getStatusAmount(){
        return amount;
    }
}
