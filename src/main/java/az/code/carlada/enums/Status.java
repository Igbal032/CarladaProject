package az.code.carlada.enums;

public enum Status {
    DEFAULT(3),
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
