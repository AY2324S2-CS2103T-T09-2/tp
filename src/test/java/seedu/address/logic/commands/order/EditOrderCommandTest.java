package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.PersonBuilder;

public class EditOrderCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    // TODO: implement tests for successful editing of orders
    // @Test
    // public void execute_editOrderByModel_editSuccessful() throws Exception {
    //     PersonBuilder personBuilder = new PersonBuilder();
    //     Person person = personBuilder.build();
    //     OrderBuilder orderBuilder = new OrderBuilder();
    //     Order originalOrder = orderBuilder.build();
    //     ModelStubEditingOrder modelStub = new ModelStubEditingOrder(originalOrder, person);
    //     Order editedOrder = orderBuilder.withStatus("completed").build();
    //     EditOrderCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(editedOrder).build();
    //     EditOrderCommand editOrderCommand = new EditOrderCommand(INDEX_FIRST_PERSON, descriptor);
    //     String expectedMessage = String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder);
    //     assertCommandSuccess(editOrderCommand, modelStub, expectedMessage, modelStub);
    // }

    // TODO: implement tests for successful indexing and correct description
    // @Test
    // public void execute_validIndexAndDescriptor_success() {
    //     Order editedOrder = new OrderBuilder().withStatus("PENDING").build();
    //     EditOrderCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(editedOrder).build();
    //     EditOrderCommand editOrderCommand = new EditOrderCommand(INDEX_FIRST_ORDER, descriptor);
    //
    //     String expectedMessage = String.format(MESSAGE_EDIT_ORDER_SUCCESS, editedOrder);
    //     assertCommandSuccess(editOrderCommand, model, expectedMessage, model);
    // }

    // TODO: implement test for createEditedOrder
    // @Test
    // public void testExecute_createEditedOrder() throws Exception {
    //     PersonBuilder personBuilder = new PersonBuilder();
    //     Person person = personBuilder.build();
    //
    //     // Create an original order
    //     Order originalOrder = new Order(new OrderId(), new OrderDate("01-02-2024 10:09"),
    //             new Deadline("22-02-2024 23:59"), new Price("100.00"), new Remark("Original remark"),
    //             new Status("PENDING"));
    //
    //     // Set up edit descriptor with new values
    //     EditOrderCommand.EditOrderDescriptor editDescriptor = new EditOrderCommand.EditOrderDescriptor();
    //     editDescriptor.setOrderDate(new OrderDate("01-02-2024 10:09"));
    //     editDescriptor.setDeadline(new Deadline("01-03-2024 23:59"));
    //     editDescriptor.setPrice(new Price("150.00"));
    //     editDescriptor.setRemark(new Remark("Updated remark"));
    //     editDescriptor.setStatus(new Status("COMPLETED"));
    //
    //     // Create EditOrderCommand with target index and edit descriptor
    //     EditOrderCommand editOrderCommand = new EditOrderCommand(Index.fromZeroBased(0), editDescriptor);
    //
    //     // Create a model with the original order and associated person
    //     Model model = new ModelManager();
    //     model.addPerson(person); // Add the person to the model
    //     model.setPersonAndAddOrder(person, person, originalOrder); // Pass same person for target and editedPerson
    //
    //     // Execute the edit command
    //     editOrderCommand.execute(model);
    //
    //     // Retrieve the edited order from the model
    //     Order editedOrder = model.getFilteredOrderList().get(0);
    //
    //     // Verify that the edited order details match the expected values
    //     assertNotNull(editedOrder);
    //     assertEquals(new OrderDate("01-02-2024 10:09"), editedOrder.getOrderDate());
    //     assertEquals(new Deadline("01-03-2024 23:59"), editedOrder.getDeadline());
    //     assertEquals(new Price("150.00"), editedOrder.getPrice());
    //     assertEquals(new Remark("Updated remark"), editedOrder.getRemark());
    //     assertEquals(new Status("COMPLETED"), editedOrder.getStatus());
    // }


    @Test
    public void execute_orderAcceptedByModel_indexError() throws Exception {
        PersonBuilder personBuilder = new PersonBuilder();
        Person person = personBuilder.build();
        OrderBuilder orderBuilder = new OrderBuilder();
        Order order = orderBuilder.build();
        ModelStubEditingOrder modelStub = new ModelStubEditingOrder(order, person);
        Index targetIndex = INDEX_SECOND_ORDER;
        EditOrderCommand editOrderCommand = new EditOrderCommand(targetIndex,
                new EditOrderCommand.EditOrderDescriptor());
        assertThrows(CommandException.class, () -> editOrderCommand.execute(modelStub));
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
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPersonAndAddOrder(Person target, Person editedPerson, Order order) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPersonAndDeleteOrder(Person target, Person editedPerson, Order order) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPersonAndEditOrder(Person target, Person editedPerson, Order order, Order editedOrder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Order> getFilteredOrderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredOrderList(Predicate<Order> predicate) {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that always accepts the order being added.
     */
    private class ModelStubEditingOrder extends seedu.address.logic.commands.order.EditOrderCommandTest.ModelStub {
        private Order order;
        private Person person;

        ModelStubEditingOrder(Order order, Person person) {
            requireNonNull(order);
            this.order = order;
            this.person = person;
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            requireAllNonNull(target, editedPerson);
            this.person = editedPerson;
        }

        @Override
        public void setPersonAndAddOrder(Person target, Person editedPerson, Order order) {
            requireAllNonNull(target, editedPerson);
            this.person = editedPerson;
        }

        @Override
        public void setPersonAndDeleteOrder(Person target, Person editedPerson, Order order) {
            requireAllNonNull(target, editedPerson, order);
            this.person = editedPerson;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            List<Person> sampleList = new ArrayList<>();
            sampleList.add(this.person);
            ObservableList<Person> personList = FXCollections.observableArrayList(sampleList);
            return personList;
        }

        @Override
        public ObservableList<Order> getFilteredOrderList() {
            List<Order> sampleList = new ArrayList<>();
            sampleList.add(this.order);
            ObservableList<Order> orderList = FXCollections.observableArrayList(sampleList);
            return orderList;
        }

        private ObservableList<Order> getOrderList() {
            ObservableList<Order> orderList = FXCollections.observableArrayList(this.person.getOrders());
            return orderList;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            AddressBook addressBook = new AddressBook();
            addressBook.addPerson(this.person);
            return addressBook;
        }

        @Override
        public void updateFilteredOrderList(Predicate<Order> predicate) {
            requireNonNull(predicate);
        }
    }
}
