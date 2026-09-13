import java.util.Arrays;

class EventTicket {
    protected double basePrice;
    protected double amountPaid;

    private double[] lateFeeHistory;
    private int lateFeeCount;

    public EventTicket(double basePrice) {
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Base price must be positive");
        }

        this.basePrice = basePrice;
        this.amountPaid = 0;

        lateFeeHistory = new double[10];
        lateFeeCount = 0;
    }

    public EventTicket(String attendeeId, double basePrice) {
        this(basePrice);

        if (attendeeId == null || attendeeId.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid attendee ID");
        }
    }

    public void pay(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment must be positive");
        }

        amountPaid += amount;
    }

    public double getBalanceDue() {
        return Math.max(0, basePrice - amountPaid);
    }

    protected void applyLateFee(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException("Late fee must be positive");
        }

        // Penalty increases the amount due.
        basePrice += amount;

        // Record the penalty.
        if (lateFeeCount < lateFeeHistory.length) {
            lateFeeHistory[lateFeeCount] = amount;
            lateFeeCount++;
        }
    }

    public double[] getLateFeeHistory() {

        return Arrays.copyOf(
            lateFeeHistory,
            lateFeeCount
        );
    }
}

class WorkshopTicket extends EventTicket {

    private String track;

    public WorkshopTicket(
        String attendeeId,
        double basePrice,
        String track
    ) {
        super(attendeeId, basePrice);
        this.track = track;
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}

public class Main {

    public static void main(String[] args) {

        WorkshopTicket w =
            new WorkshopTicket(
                "STU1",
                1200,
                "AI/ML"
            );

        w.pay(1200);

        w.applyLateFee(100);

        System.out.println(
            w.getBalanceDue()
        );

        double[] history =
            w.getLateFeeHistory();

        System.out.println(
            Arrays.toString(history)
        );

        // Try to tamper with returned array
        history[0] = 999;

        System.out.println(
            Arrays.toString(
                w.getLateFeeHistory()
            )
        );
    }
}
