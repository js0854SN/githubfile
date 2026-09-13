class EventTicket {

    protected double basePrice;
    protected double amountPaid;

    private static int ticketsIssued = 0;

    public final String ticketId;

    public EventTicket(double basePrice) {

        if (basePrice <= 0) {
            throw new IllegalArgumentException(
                "Base price must be positive"
            );
        }

        ticketsIssued++;

        ticketId = "TCK-" + (1000 + ticketsIssued);

        this.basePrice = basePrice;
        this.amountPaid = 0;
    }

    public void pay(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Payment must be positive"
            );
        }

        amountPaid += amount;
    }

    public void pay(double amount, String mode) {

        System.out.println(
            "Payment mode: " + mode
        );

        // Reuse the one-argument method
        pay(amount);
    }

    public double getBalanceDue() {
        return Math.max(0, basePrice - amountPaid);
    }

    public static boolean isValidPromoCode(String code) {

        // Exact format: F + 3 digits + 1 uppercase letter
        if (code == null || code.length() != 5) {
            return false;
        }

        if (code.charAt(0) != 'F') {
            return false;
        }

        if (!Character.isDigit(code.charAt(1))) {
            return false;
        }

        if (!Character.isDigit(code.charAt(2))) {
            return false;
        }

        if (!Character.isDigit(code.charAt(3))) {
            return false;
        }

        if (!Character.isUpperCase(code.charAt(4))) {
            return false;
        }

        return true;
    }

    public static int getTicketsIssued() {
        return ticketsIssued;
    }
}

class GroupTicket extends EventTicket {

    private int groupSize;

    public GroupTicket(
        double basePrice,
        int groupSize
    ) {
        super(basePrice);

        if (groupSize <= 0) {
            throw new IllegalArgumentException(
                "Group size must be positive"
            );
        }

        this.groupSize = groupSize;
    }

    public int getGroupSize() {
        return groupSize;
    }
}

public class Main {

    public static String processNightlySettlement(
        EventTicket[] tickets
    ) {

        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;

        for (EventTicket ticket : tickets) {

            if (ticket == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (ticket instanceof GroupTicket) {
                group++;
            } else {
                individual++;
            }
        }

        return processed + " processed | " +
               nullSkipped + " null skipped | " +
               group + " group | " +
               individual + " individual";
    }

    public static void main(String[] args) {

        // Ticket ID
        EventTicket t1 =
            new EventTicket(500);

        System.out.println(t1.ticketId);

        System.out.println(
            EventTicket.getTicketsIssued()
        );

        // Promo codes
        System.out.println(
            EventTicket.isValidPromoCode("F123A")
        );

        System.out.println(
            EventTicket.isValidPromoCode("F12A")
        );

        System.out.println(
            EventTicket.isValidPromoCode("X123A")
        );

        // Payments
        t1.pay(200);
        t1.pay(200, "UPI");

        System.out.println(
            t1.getBalanceDue()
        );

        // Nightly settlement
        EventTicket[] tickets = {
            new GroupTicket(2000, 5),
            null,
            new EventTicket(500)
        };

        System.out.println(
            processNightlySettlement(tickets)
        );
    }
}
