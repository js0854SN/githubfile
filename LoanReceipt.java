public final class LoanReceipt {

    private final String memberId;
    private final String[] bookIds;

    // Shared state initialized once
    private static final String BOOK_PREFIX;

    static {
        BOOK_PREFIX = "BK-";
    }

    public LoanReceipt(String memberId, String[] bookIds) {

        if (bookIds == null || bookIds.length > 20) {
            throw new IllegalArgumentException("Invalid bookIds");
        }

        for (String bookId : bookIds) {

            if (!isValidBookId(bookId)) {
                throw new IllegalArgumentException("Invalid book ID");
            }
        }

        this.memberId = memberId;

        // Defensive copy on the way in
        this.bookIds = bookIds.clone();
    }

    private static boolean isValidBookId(String bookId) {

        if (bookId == null || bookId.length() != 6) {
            return false;
        }

        if (!bookId.startsWith(BOOK_PREFIX)) {
            return false;
        }

        // Check exactly three digits
        for (int i = 3; i < 6; i++) {

            if (!Character.isDigit(bookId.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public String getMemberId() {
        return memberId;
    }

    public String[] getBookIds() {

        // Defensive copy on the way out
        return bookIds.clone();
    }

    public LoanReceipt withCorrectedBookId(
            int index,
            String newId) {

        if (index < 0 || index >= bookIds.length) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        if (!isValidBookId(newId)) {
            throw new IllegalArgumentException("Invalid book ID");
        }

        String[] correctedIds = bookIds.clone();

        correctedIds[index] = newId;

        // Return a brand-new object
        return new LoanReceipt(memberId, correctedIds);
    }

    public static String processNightlyCirculation(
            LoanReceipt[] receipts) {

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        if (receipts == null) {
            return "0 processed | 0 null skipped | 0 reference-only | 0 regular";
        }

        for (LoanReceipt receipt : receipts) {

            if (receipt == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + referenceOnly + " reference-only | "
                + regular + " regular";
    }

    public static void main(String[] args) {

        // Test invalid book ID
        try {

            LoanReceipt bad =
                    new LoanReceipt(
                            "LIB-8841",
                            new String[]{"BK-100", "bad"}
                    );

            System.out.println("construction succeeded");

        } catch (IllegalArgumentException e) {

            System.out.println("construction rejected");
        }

        // Test defensive copying
        LoanReceipt r =
                new LoanReceipt(
                        "LIB-8841",
                        new String[]{"BK-100", "BK-101"}
                );

        String[] ids = r.getBookIds();

        ids[0] = "HACKED";

        System.out.println(r.getBookIds()[0]);

        // Test nightly processing
        LoanReceipt[] receipts = {

            new ReferenceOnlyLoanReceipt(
                    "LIB-001",
                    new String[]{"BK-200"},
                    "Reading Room 3"
            ),

            null,

            new LoanReceipt(
                    "LIB-002",
                    new String[]{"BK-201"}
            )
        };

        System.out.println(
                LoanReceipt.processNightlyCirculation(receipts)
        );

        // Test withCorrectedBookId()
        LoanReceipt corrected =
                r.withCorrectedBookId(0, "BK-999");

        System.out.println(corrected.getBookIds()[0]);

        // Original remains unchanged
        System.out.println(r.getBookIds()[0]);
    }
}


// Reference-only variant
class ReferenceOnlyLoanReceipt extends LoanReceipt {

    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(
            String memberId,
            String[] bookIds,
            String roomNumber) {

        super(memberId, bookIds);
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}
