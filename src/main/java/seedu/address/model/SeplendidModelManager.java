package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.SeplendidLogsCenter;
import seedu.address.model.localcourse.LocalCourse;
import seedu.address.model.notes.Note;
import seedu.address.model.notes.NoteList;
import seedu.address.model.partnercourse.PartnerCourse;
import seedu.address.model.university.University;

/**
 * Represents the in-memory model of SEPlendid.
 */
public class SeplendidModelManager implements SeplendidModel {
    private static final Logger logger = SeplendidLogsCenter.getLogger(SeplendidModelManager.class);

    // Add all data models here
    private final LocalCourseCatalogue localCourseCatalogue;
    private final UserPrefs userPrefs;
    private final FilteredList<LocalCourse> filteredLocalCourseCatalogue;
    private final PartnerCourseCatalogue partnerCourseCatalogue;
    private final FilteredList<PartnerCourse> filteredPartnerCourseCatalogue;

    private final NoteList noteList;

    /**
     * Initializes a SeplendidModelManager with the given localCourseCatalogue, userPrefs,
     * partnerCourseCatalogue, universityCatalogue, mappingCatalogue, noteCatalogue.
     */
    public SeplendidModelManager(ReadOnlyLocalCourseCatalogue localCourseCatalogue, ReadOnlyUserPrefs userPrefs,
                                 ReadOnlyPartnerCourseCatalogue partnerCourseCatalogue) {
        requireAllNonNull(localCourseCatalogue, userPrefs, partnerCourseCatalogue);

        logger.fine("Initializing with local course catalogue: " + localCourseCatalogue
                + " and user prefs " + userPrefs);

        this.localCourseCatalogue = new LocalCourseCatalogue(localCourseCatalogue);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredLocalCourseCatalogue = new FilteredList<>(this.localCourseCatalogue.getLocalCourseList());
        this.partnerCourseCatalogue = new PartnerCourseCatalogue(partnerCourseCatalogue);
        filteredPartnerCourseCatalogue = new FilteredList<>(this.partnerCourseCatalogue.getPartnerCourseList());
        this.noteList = new NoteList();
    }

    public SeplendidModelManager() {
        this(new LocalCourseCatalogue(), new UserPrefs(), new PartnerCourseCatalogue());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getLocalCourseCatalogueFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setLocalCourseCatalogueFilePath(Path localCourseCatalogueFilePath) {
        requireNonNull(localCourseCatalogueFilePath);
        userPrefs.setAddressBookFilePath(localCourseCatalogueFilePath);
    }

    //=========== LocalCourseCatalogue ================================================================================

    @Override
    public void setLocalCourseCatalogue(ReadOnlyLocalCourseCatalogue localCourseCatalogue) {
        this.localCourseCatalogue.resetData(localCourseCatalogue);
    }

    @Override
    public ReadOnlyLocalCourseCatalogue getLocalCourseCatalogue() {
        return localCourseCatalogue;
    }

    @Override
    public boolean hasLocalCourse(LocalCourse localCourse) {
        requireNonNull(localCourse);
        return localCourseCatalogue.hasLocalCourse(localCourse);
    }

    @Override
    public void deleteLocalCourse(LocalCourse target) {
        localCourseCatalogue.removeLocalCourse(target);
    }

    @Override
    public void addLocalCourse(LocalCourse localCourse) {
        localCourseCatalogue.addLocalCourse(localCourse);
        updateFilteredLocalCourseList(PREDICATE_SHOW_ALL_LOCAL_COURSES);
    }

    @Override
    public void setLocalCourse(LocalCourse target, LocalCourse editedLocalCourse) {
        requireAllNonNull(target, editedLocalCourse);

        localCourseCatalogue.setLocalCourse(target, editedLocalCourse);
    }

    //=========== FilteredLocalCourseList Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code LocalCourse} backed by the internal list of
     * {@code versionedLocalCourseCatalogue}
     */
    @Override
    public ObservableList<LocalCourse> getFilteredLocalCourseList() {
        return filteredLocalCourseCatalogue;
    }

    @Override
    public void updateFilteredLocalCourseList(Predicate<LocalCourse> predicate) {
        requireNonNull(predicate);
        filteredLocalCourseCatalogue.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SeplendidModelManager)) {
            return false;
        }

        SeplendidModelManager otherSeplendidModelManager = (SeplendidModelManager) other;
        return localCourseCatalogue.equals(otherSeplendidModelManager.localCourseCatalogue)
                && userPrefs.equals(otherSeplendidModelManager.userPrefs)
                && filteredLocalCourseCatalogue.equals(otherSeplendidModelManager.filteredLocalCourseCatalogue);
    }

    //=========== PartnerCourseCatalouge ============================================================================
    @Override
    public ReadOnlyPartnerCourseCatalogue getPartnerCourseCatalogue() {
        return partnerCourseCatalogue;
    }
    @Override
    public boolean hasPartnerCourse(PartnerCourse partnerCourse) {
        requireNonNull(partnerCourse);
        return partnerCourseCatalogue.hasPartnerCourse(partnerCourse);
    }

    @Override
    public void addPartnerCourse(PartnerCourse partnerCourse) {
        partnerCourseCatalogue.addPartnerCourse(partnerCourse);
        updateFilteredPartnerCourseList(PREDICATE_SHOW_ALL_PARTNER_COURSES);
    }

    /**
     * Returns an unmodifiable view of the list of {@code LocalCourse} backed by the internal list of
     * {@code versionedLocalCourseCatalogue}
     */
    @Override
    public ObservableList<PartnerCourse> getFilteredPartnerCourseList() {
        return filteredPartnerCourseCatalogue;
    }

    @Override
    public void updateFilteredPartnerCourseList(Predicate<PartnerCourse> predicate) {
        requireNonNull(predicate);
        filteredPartnerCourseCatalogue.setPredicate(predicate);
    }


    //=========== NoteCatalogue ================================================================================
    @Override
    public void addNote(Note note) {
        noteList.addNote(note);
    }

    //=========== UniversityCatalogue ================================================================================
    @Override
    public void updateUniversityList(Predicate<University> predicate) {

    }

}