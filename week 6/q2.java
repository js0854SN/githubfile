import java.util.*;

class EventTicket {
    protected String attendeeId;
    protected double basePrice;
    protected double amountPaid;

    public EventTicket(String attendeeId, double basePrice) {

        if (attendeeId == null || attendeeId.trim().length() < 4) {
            throw new IllegalArgumentException(
                "Attendee ID must have at least 4 characters"
            );
        }

        if (basePrice <= 0) {
            throw new IllegalArgumentException(
                "Base price must be positive"
            );
        }

        this.attendeeId = attendeeId;
        this.basePrice = basePrice;
        this.amountPaid = 0.0;
    }

    public void pay(double amount) {
        amountPaid += amount;
    }

    public double getBalanceDue() {
        return Math.max(0, basePrice - amountPaid);
    }

    public void printTicket() {
        System.out.println(
            "Standard Event Ticket | Balance Due: "
            + getBalanceDue()
        );
    }
}


class WorkshopTicket extends EventTicket {

    protected String track;

    public WorkshopTicket(
        String attendeeId,
        double basePrice,
        String track
    ) {
        super(attendeeId, basePrice);
        this.track = track;
    }

    public String getTrack() {
        return track;
    }

    @Override
    public void printTicket() {
        System.out.println(
            "Workshop Ticket | Track: "
            + track
            + " | Balance Due: "
            + getBalanceDue()
        );
    }
}


class PremiumWorkshopTicket extends WorkshopTicket {

    private double kitFee;

    public PremiumWorkshopTicket(
        String attendeeId,
        double basePrice,
        String track,
        double kitFee
    ) {
        super(attendeeId, basePrice, track);
        this.kitFee = kitFee;
    }

    @Override
    public void printTicket() {
        System.out.println(
            "Premium Workshop Ticket | Track: "
            + track
            + " | Kit Fee: "
            + kitFee
            + " | Balance Due: "
            + getBalanceDue()
        );
    }
}


class HackathonTicket extends EventTicket {

    private String teamName;

    public HackathonTicket(
        String attendeeId,
        double basePrice,
        String teamName
    ) {
        super(attendeeId, basePrice);
        this.teamName = teamName;
    }

    @Override
    public void printTicket() {
        System.out.println(
            "Hackathon Ticket | Team: "
            + teamName
            + " | Balance Due: "
            + getBalanceDue()
        );
    }
}


public class Main {

    // Classify the ticket based only on instanceof
    public static String classifyGeneration(
        EventTicket ticket
    ) {

        if (ticket instanceof PremiumWorkshopTicket) {
            return "Multilevel descendant (3 generations deep)";
        }

        if (ticket instanceof HackathonTicket) {
            return "Hierarchical sibling (independent branch)";
        }

        if (ticket instanceof WorkshopTicket) {
            return "Single-level descendant";
        }

        return "Base generation";
    }


    // Calculate total balance using polymorphism
    public static double getTotalBalanceDue(
        EventTicket[] tickets
    ) {

        double total = 0.0;

        for (EventTicket ticket : tickets) {
            total += ticket.getBalanceDue();
        }

        return total;
    }


    public static void main(String[] args) {

        EventTicket standard =
            new EventTicket("STU1", 500);

        WorkshopTicket workshop =
            new WorkshopTicket(
                "STU2",
                1200,
                "AI/ML"
            );

        PremiumWorkshopTicket premium =
            new PremiumWorkshopTicket(
                "STU3",
                2000,
                "Cloud Native",
                300
            );

        HackathonTicket hackathon =
            new HackathonTicket(
                "STU4",
                800,
                "Byte Force"
            );


        // Print each ticket
        standard.printTicket();
        workshop.printTicket();
        premium.printTicket();
        hackathon.printTicket();


        // Classification
        System.out.println(
            classifyGeneration(premium)
        );

        System.out.println(
            classifyGeneration(hackathon)
        );


        // Store all four types in one EventTicket array
        EventTicket[] tickets = {
            standard,
            workshop,
            premium,
            hackathon
        };


        // Calculate total balance
        System.out.println(
            getTotalBalanceDue(tickets)
        );
    }
}