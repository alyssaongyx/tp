package seedu.address.model.partnercourse;

public enum PartnerCourseAttribute {
    PARTNERCODE,
    PARTNERNAME;

    public static final String MESSAGE_CONSTRAINTS =
            "There are only 2 attributes: partnercode and partnername.";

    /**
     * Returns true if the given attribute is valid.
     * @param test String to be tested
     * @return true if attribute is valid.
     */
    public static boolean isValidAttribute(String test) {
        switch(test) {
            case ("PARTNERCODE"):
            case ("PARTNERNAME"):
                return true;
            default:
                return false;
        }
    }
}
