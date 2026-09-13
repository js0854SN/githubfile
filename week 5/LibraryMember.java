public class LibraryMember {

    private String membershipId;
    String branchCode;                 
    protected double finesOwed;
    public String displayName;

    // Parameterized constructor — no usable no-arg constructor
    public LibraryMember(String membershipId, String branchCode,
                         double finesOwed, String displayName) {

        String trimmedId = membershipId == null ? "" : membershipId.trim();

        if (trimmedId.isEmpty() || trimmedId.length() < 4) {
            throw new IllegalArgumentException("Invalid membershipId");
        }

        this.membershipId = trimmedId;
        this.branchCode = branchCode;
        this.finesOwed = finesOwed;
        this.displayName = displayName;
    }

    static String classifyAccess(String fieldModifier, String accessorContext) {

        switch (fieldModifier) {

            case "private":
                if (accessorContext.equals("SAME_CLASS")) {
                    return "ALLOWED";
                }
                return "DENIED";

            case "default":
                if (accessorContext.equals("SAME_CLASS")
                        || accessorContext.equals("SAME_PACKAGE")) {
                    return "ALLOWED";
                }
                return "DENIED";

            case "protected":
                if (accessorContext.equals("SAME_CLASS")
                        || accessorContext.equals("SAME_PACKAGE")) {
                    return "ALLOWED";
                }
                return "DENIED";

            case "public":
                return "ALLOWED";

            default:
                return "DENIED";
        }
    }


    static String summarizeByModifier(String[][] attempts) {

        String[] modifiers = {"private", "default", "protected", "public"};

        StringBuilder result = new StringBuilder();

        for (String modifier : modifiers) {

            int allowed = 0;
            int denied = 0;

            for (String[] attempt : attempts) {

                if (attempt[0].equals(modifier)) {

                    String access = classifyAccess(
                            attempt[0],
                            attempt[1]
                    );

                    if (access.equals("ALLOWED")) {
                        allowed++;
                    } else {
                        denied++;
                    }
                }
            }

            if (allowed + denied > 0) {

                if (result.length() > 0) {
                    result.append(" | ");
                }

                result.append(modifier)
                      .append(": ")
                      .append(allowed)
                      .append(" allowed / ")
                      .append(denied)
                      .append(" denied");
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {

        // Test classifyAccess()
        System.out.println(
                classifyAccess("private", "SAME_CLASS")
        );

        System.out.println(
                classifyAccess("protected", "DIFFERENT_PACKAGE")
        );

        // Test summarizeByModifier()
        String[][] attempts = {
                {"private", "SAME_CLASS"},
                {"private", "SAME_PACKAGE"},
                {"default", "SAME_PACKAGE"},
                {"default", "DIFFERENT_PACKAGE"},
                {"protected", "SAME_PACKAGE"},
                {"protected", "SAME_CLASS"},
                {"public", "DIFFERENT_PACKAGE"}
        };

        System.out.println(
                summarizeByModifier(attempts)
        );


        try {
            new LibraryMember(
                    "LB9",
                    "BR1",
                    0,
                    "Priya Nair"
            );

            System.out.println("construction succeeded");

        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        try {
            new LibraryMember(
                    "LB94",
                    "BR1",
                    0,
                    "Priya Nair"
            );

            System.out.println("construction succeeded");

        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }
    }
}
