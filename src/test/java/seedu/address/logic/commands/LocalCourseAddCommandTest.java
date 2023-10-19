package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalObjects.CS2030S;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.LocalCourseCatalogue;
import seedu.address.model.ReadOnlyLocalCourseCatalogue;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.SeplendidModel;
import seedu.address.model.localcourse.LocalCourse;
import seedu.address.model.notes.Note;
import seedu.address.testutil.LocalCourseBuilder;

/**
 * Unit testing of LocalCourseAddCommand, with stubs / dependency injection.
 */
public class LocalCourseAddCommandTest {

    @Test
    public void constructor_nullLocalCourse_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LocalCourseAddCommand(null));
    }

    @Test
    public void execute_localCourseAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingLocalCourseAdded modelStub = new ModelStubAcceptingLocalCourseAdded();
        LocalCourse validLocalCourse = new LocalCourseBuilder().build();
        // Tests interactions with model
        CommandResult commandResult = new LocalCourseAddCommand(validLocalCourse).execute(modelStub);

        assertEquals(String.format(LocalCourseAddCommand.MESSAGE_SUCCESS, Messages.format(validLocalCourse)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validLocalCourse), modelStub.localCoursesAdded);
    }

    @Test
    public void execute_duplicateLocalCourse_throwsCommandException() {
        LocalCourse validLocalCourse = new LocalCourseBuilder().build();
        LocalCourseAddCommand localCourseAddCommand = new LocalCourseAddCommand(validLocalCourse);
        SeplendidModelStub modelStub = new SeplendidModelStubWithLocalCourse(validLocalCourse);

        assertThrows(CommandException.class, LocalCourseAddCommand.MESSAGE_DUPLICATE_LOCAL_COURSE, () ->
                localCourseAddCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        LocalCourse cs2030s = new LocalCourseBuilder().withLocalCode("CS2030S").build();
        LocalCourse cs2040s = new LocalCourseBuilder().withLocalCode("CS2040S").build();
        LocalCourseAddCommand addCS2030SComamnd = new LocalCourseAddCommand(cs2030s);
        LocalCourseAddCommand addCS2040SCommand = new LocalCourseAddCommand(cs2040s);

        // same object -> returns true
        assertTrue(addCS2030SComamnd.equals(addCS2030SComamnd));

        // same values -> returns true
        LocalCourseAddCommand addCS2030SComamndCopy = new LocalCourseAddCommand(cs2030s);
        assertTrue(addCS2030SComamnd.equals(addCS2030SComamndCopy));

        // different types -> returns false
        assertFalse(addCS2030SComamnd.equals(1));

        // null -> returns false
        assertFalse(addCS2030SComamnd.equals(null));

        // different person -> returns false
        assertFalse(addCS2030SComamnd.equals(addCS2040SCommand));
    }

    @Test
    public void toStringMethod() {
        LocalCourseAddCommand localCourseAddCommand = new LocalCourseAddCommand(CS2030S);
        String expected = LocalCourseAddCommand.class.getCanonicalName() + "{localCourseToAdd=" + CS2030S + "}";
        assertEquals(expected, localCourseAddCommand.toString());
    }

    /**
     * A default model stub that have all methods failing.
     */
    private class SeplendidModelStub implements SeplendidModel {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getLocalCourseCatalogueFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLocalCourseCatalogueFilePath(Path localCourseCatalogueFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addLocalCourse(LocalCourse localCourse) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLocalCourseCatalogue(ReadOnlyLocalCourseCatalogue localCourseCatalogue) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyLocalCourseCatalogue getLocalCourseCatalogue() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasLocalCourse(LocalCourse localCourse) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteLocalCourse(LocalCourse localCourse) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLocalCourse(LocalCourse localCourse, LocalCourse editedLocalCourse) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<LocalCourse> getFilteredLocalCourseList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredLocalCourseList(Predicate<LocalCourse> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addNote(Note note) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single localCourse.
     */
    private class SeplendidModelStubWithLocalCourse extends SeplendidModelStub {
        private final LocalCourse localCourse;

        SeplendidModelStubWithLocalCourse(LocalCourse lc) {
            requireNonNull(lc);
            localCourse = lc;
        }

        @Override
        public boolean hasLocalCourse(LocalCourse lc) {
            requireNonNull(lc);
            return localCourse.isSameLocalCourse(lc);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingLocalCourseAdded extends SeplendidModelStub {
        final ArrayList<LocalCourse> localCoursesAdded = new ArrayList<>();

        @Override
        public boolean hasLocalCourse(LocalCourse localCourse) {
            requireNonNull(localCourse);
            return localCoursesAdded.stream().anyMatch(localCourse::isSameLocalCourse);
        }

        @Override
        public void addLocalCourse(LocalCourse localCourse) {
            requireNonNull(localCourse);
            localCoursesAdded.add(localCourse);
        }

        @Override
        public ReadOnlyLocalCourseCatalogue getLocalCourseCatalogue() {
            return new LocalCourseCatalogue();
        }
    }

}
