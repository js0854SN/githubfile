class EventTicket {
    protected double basePrice;
    protected double amountPaid;

    public EventTicket(double basePrice) {
        if (basePrice <= 0) {
            throw new IllegalArgumentException(
                "Base price must be positive"
            );
        }

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

    public double getBalanceDue() {
        return Math.max(0, basePrice - amountPaid);
    }

    public String printTicket() {
        return "Standard | Balance: " + getBalanceDue();
    }
}

class WorkshopTicket extends EventTicket {

    private String track;

    public WorkshopTicket(
        double basePrice,
        String track
    ) {
        super(basePrice);
        this.track = track;
    }

    public String getTrack() {
        return track;
    }

    @Override
    public String printTicket() {
        return "Workshop | Track: " + track +
               " | Balance: " + getBalanceDue();
    }
}

public class Main {

    public static String batchPrint(EventTicket[] tickets) {

        StringBuilder report = new StringBuilder();

        for (EventTicket ticket : tickets) {

            // Polymorphic method call
            report.append(ticket.printTicket());

            // Safe downcast
            if (ticket instanceof WorkshopTicket) {

                WorkshopTicket workshop =
                    (WorkshopTicket) ticket;

                report.append(
                    " [Track via downcast: "
                    + workshop.getTrack()
                    + "]"
                );
            }

            report.append(" | ");
        }

        return report.toString();
    }

    public static void main(String[] args) {

        EventTicket[] tickets = {
            new EventTicket(500),
            new WorkshopTicket(1200, "AI/ML")
        };

        System.out.println(
            batchPrint(tickets)
        );

        // Demonstration of an unsafe cast:
        EventTicket plain = new EventTicket(500);

        try {
            WorkshopTicket bad =
                (WorkshopTicket) plain;
        } catch (ClassCastException e) {
            System.out.println(
                "ClassCastException at runtime"
            );
        }
    }
}
