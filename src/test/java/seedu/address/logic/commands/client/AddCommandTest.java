package seedu.address.logic.commands.client;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.BookKeeper;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyBookKeeper;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.order.Order;
import seedu.address.testutil.ClientBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Client validClient = new ClientBuilder().build();

        CommandResult commandResult = new AddCommand(validClient).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validClient)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validClient), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Client validClient = new ClientBuilder().build();
        AddCommand addCommand = new AddCommand(validClient);
        ModelStub modelStub = new ModelStubWithPerson(validClient);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_CLIENT, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Client alice = new ClientBuilder().withName("Alice").build();
        Client bob = new ClientBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertEquals(addAliceCommand, addAliceCommand);

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertEquals(addAliceCommand, addAliceCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addAliceCommand);

        // null -> returns false
        assertNotEquals(null, addAliceCommand);

        // different client -> returns false
        assertNotEquals(addAliceCommand, addBobCommand);
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
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
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addClient(Client client) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyBookKeeper getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyBookKeeper newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasClient(Client client) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteClient(Client target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClient(Client target, Client editedClient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClientAndAddOrder(Client target, Client editedClient, Order order) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClientAndDeleteOrder(Client target, Client editedClient, Order order) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClientAndEditOrder(Client target, Client editedClient, Order order, Order editedOrder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Client> getFilteredClientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Order> getFilteredOrderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredClientList(Predicate<Client> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredOrderList(Predicate<Order> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single client.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Client client;

        ModelStubWithPerson(Client client) {
            requireNonNull(client);
            this.client = client;
        }

        @Override
        public boolean hasClient(Client client) {
            requireNonNull(client);
            return this.client.isSameClient(client);
        }
    }

    /**
     * A Model stub that always accept the client being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Client> personsAdded = new ArrayList<>();

        @Override
        public boolean hasClient(Client client) {
            requireNonNull(client);
            return personsAdded.stream().anyMatch(client::isSameClient);
        }

        @Override
        public void addClient(Client client) {
            requireNonNull(client);
            personsAdded.add(client);
        }

        @Override
        public ReadOnlyBookKeeper getAddressBook() {
            return new BookKeeper();
        }
    }

}
