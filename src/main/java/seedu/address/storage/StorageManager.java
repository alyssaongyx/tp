package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyLocalCourseCatalogue;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private LocalCourseCatalogueStorage localCourseCatalogueStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(AddressBookStorage addressBookStorage,
                          LocalCourseCatalogueStorage localCourseCatalogueStorage,
                          UserPrefsStorage userPrefsStorage) {
        this.addressBookStorage = addressBookStorage;
        this.localCourseCatalogueStorage = localCourseCatalogueStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    // ================ LocalCourseCatalogue methods ==============================

    @Override
    public Path getLocalCourseCatalogueFilePath() {
        return localCourseCatalogueStorage.getLocalCourseCatalogueFilePath();
    }

    @Override
    public Optional<ReadOnlyLocalCourseCatalogue> readLocalCourseCatalogue() throws DataLoadingException {
        return readLocalCourseCatalogue(localCourseCatalogueStorage.getLocalCourseCatalogueFilePath());
    }

    @Override
    public Optional<ReadOnlyLocalCourseCatalogue> readLocalCourseCatalogue(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return localCourseCatalogueStorage.readLocalCourseCatalogue(filePath);
    }

    @Override
    public void saveLocalCourseCatalogue(ReadOnlyLocalCourseCatalogue localCourseCatalogue) throws IOException {
        saveLocalCourseCatalogue(localCourseCatalogue, localCourseCatalogueStorage.getLocalCourseCatalogueFilePath());
    }

    @Override
    public void saveLocalCourseCatalogue(ReadOnlyLocalCourseCatalogue localCourseCatalogue,
                                         Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        localCourseCatalogueStorage.saveLocalCourseCatalogue(localCourseCatalogue, filePath);
    }

}
