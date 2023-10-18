package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.partnercourse.PartnerCourse;

public interface ReadOnlyPartnerCourseCatalogue {
    /**
     * Returns an unmodifiable view of the partner course list.
     * This list will not contain any duplicate partner courses.
     * ObservableList will aid interoperability.
     */
    ObservableList<PartnerCourse> getPartnerCourseList();

}