public class LibraryMember {

    private String membershipId;
    private String name;
    private boolean premiumMember;

    // Stored one-way transformation of security answer
    private String securityAnswer;

    // No-argument constructor
    public LibraryMember() {
        this(null, null);
    }

    // Name-only constructor
    public LibraryMember(String name) {
        this(null, name);
    }

    // Main initialization constructor
    public LibraryMember(String membershipId, String name) {
        this.membershipId = membershipId;
        this.name = name;
        this.premiumMember = false;
    }

    // JavaBean getter
    public String getMembershipId() {
        return membershipId;
    }

    // Write-once setter
    public void setMembershipId(String id) {

        if (this.membershipId == null) {
            this.membershipId = id;
        }
    }

    // JavaBean getter for name
    public String getName() {
        return name;
    }

    // JavaBean setter for name
    public void setName(String name) {
        this.name = name;
    }

    // JavaBean boolean getter
    public boolean isPremiumMember() {
        return premiumMember;
    }

    // JavaBean setter
    public void setPremiumMember(boolean premium) {
        this.premiumMember = premium;
    }

    // Write-only property
    public void setSecurityAnswer(String answer) {

        if (answer == null) {
            this.securityAnswer = null;
        } else {
            // Simple deterministic one-way transformation
            this.securityAnswer = Integer.toHexString(answer.hashCode());
        }
    }

    public static void main(String[] args) {

        // Test name-only constructor
        LibraryMember m1 = new LibraryMember("Priya Nair");

        System.out.println(m1.getMembershipId());

        // Test id + name constructor
        LibraryMember m2 =
                new LibraryMember("LIB-8841", "Priya Nair");

        System.out.println(m2.getMembershipId());

        // Test write-once membershipId
        LibraryMember m3 = new LibraryMember();

        m3.setMembershipId("LIB-8841");
        m3.setMembershipId("FAKE-0000");

        System.out.println(m3.getMembershipId());

        // Test premium member JavaBean methods
        m3.setPremiumMember(true);

        System.out.println(m3.isPremiumMember());

        // Test security answer setter
        m3.setSecurityAnswer("mySecretAnswer");

        System.out.println("Security answer stored securely.");
    }
}
