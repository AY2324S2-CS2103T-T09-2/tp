package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ORDERS;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.Pair;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.Deadline;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderDate;
import seedu.address.model.order.Price;
import seedu.address.model.order.Remark;
import seedu.address.model.order.Status;
import seedu.address.model.person.Person;


/**
 * Edits the details of an existing order in the address book.
 */
public class EditOrderCommand extends Command {
    public static final String COMMAND_WORD = "editOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the order identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + "DATE] "
            + "[" + PREFIX_BY + "DEADLINE] "
            + "[" + PREFIX_PRICE + "PRICE] "
            + "[" + PREFIX_DETAILS + "REMARK] "
            + "[" + PREFIX_STATUS + "STATUS]...\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_EDIT_ORDER_SUCCESS = "Edited Order: %1$s";

    private final Index targetIndex;
    private final EditOrderDescriptor editOrderDescriptor;

    /**
     * @param targetIndex         of the order in the filtered order list to edit
     * @param editOrderDescriptor details to edit the order with
     */
    public EditOrderCommand(Index targetIndex, EditOrderDescriptor
            editOrderDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(editOrderDescriptor);

        this.targetIndex = targetIndex;
        this.editOrderDescriptor = new EditOrderCommand.EditOrderDescriptor(editOrderDescriptor);
    }

    /**
     * Creates and returns a {@code Order} with the details of {@code orderToEdit}
     * edited with {@code editOrderDescriptor}.
     */
    private static Order createEditedOrder(Order orderToEdit, EditOrderDescriptor editOrderDescriptor) {
        assert orderToEdit != null;

        OrderDate updatedOrderDate = editOrderDescriptor.getOrderDate().orElse(orderToEdit.getOrderDate());
        Deadline updatedDeadline = editOrderDescriptor.getDeadline().orElse(orderToEdit.getDeadline());
        Price updatedPrice = editOrderDescriptor.getPrice().orElse(orderToEdit.getPrice());
        Remark updatedRemark = editOrderDescriptor.getRemark().orElse(orderToEdit.getRemark());
        Status updatedStatus = editOrderDescriptor.getStatus().orElse(orderToEdit.getStatus());

        if (orderToEdit.getStatus().getStatusEnum() == Status.StatusEnum.PENDING
                && updatedStatus.getStatusEnum() == Status.StatusEnum.COMPLETED) {
            updatedStatus = new Status(Status.StatusEnum.COMPLETED.name());
        }

        return new Order(orderToEdit.getOrderId(), updatedOrderDate, updatedDeadline, updatedPrice, updatedRemark,
                updatedStatus, orderToEdit.getPerson());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownOrderList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownOrderList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToEdit = lastShownOrderList.get(targetIndex.getZeroBased());
        Order editedOrder = createEditedOrder(orderToEdit, editOrderDescriptor);

        List<Person> personList = model.getFilteredPersonList();
        Pair<Person, Person> personPair = getEditedPerson(personList, orderToEdit, editedOrder);
        Person personToEdit = personPair.getFirst();
        Person editedPerson = personPair.getSecond();

        model.setPersonAndEditOrder(personToEdit, editedPerson, orderToEdit, editedOrder);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        return new CommandResult(String.format(MESSAGE_EDIT_ORDER_SUCCESS, Messages.format(editedOrder)));
    }

    private Pair<Person, Person> getEditedPerson(List<Person> personList, Order orderToEdit, Order editedOrder) throws
            CommandException {
        for (Person person : personList) {
            if (person.getOrders().contains(orderToEdit)) {
                Set<Order> newOrders = new HashSet<>(person.getOrders());
                newOrders.remove(orderToEdit);
                newOrders.add(editedOrder);

                Person editedPerson = new Person(
                        person.getName(), person.getPhone(), person.getEmail(),
                        person.getAddress(), person.getTags(), newOrders);

                return new Pair<>(person, editedPerson);
            }
        }
        throw new CommandException("Failed to find the client associated with this order!");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditOrderCommand)) {
            return false;
        }

        EditOrderCommand otherEditOrderCommand = (EditOrderCommand) other;
        return targetIndex.equals(otherEditOrderCommand.targetIndex)
                && editOrderDescriptor.equals(otherEditOrderCommand.editOrderDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("editOrderDescriptor", editOrderDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the order with. Each non-empty field value will replace the
     * corresponding field value of the order.
     */
    public static class EditOrderDescriptor {
        private OrderDate orderDate;
        private Deadline deadline;
        private Price price;
        private Remark remark;
        private Status status;

        public EditOrderDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditOrderDescriptor(EditOrderDescriptor toCopy) {
            setOrderDate(toCopy.orderDate);
            setDeadline(toCopy.deadline);
            setPrice(toCopy.price);
            setRemark(toCopy.remark);
            setStatus(toCopy.status);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(orderDate, deadline, price, remark, status);
        }

        public Optional<OrderDate> getOrderDate() {
            return Optional.ofNullable(orderDate);
        }

        public void setOrderDate(OrderDate orderDate) {
            this.orderDate = orderDate;
        }

        public Optional<Deadline> getDeadline() {
            return Optional.ofNullable(deadline);
        }

        public void setDeadline(Deadline deadline) {
            this.deadline = deadline;
        }

        public Optional<Price> getPrice() {
            return Optional.ofNullable(price);
        }

        public void setPrice(Price price) {
            this.price = price;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }

        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditOrderDescriptor)) {
                return false;
            }

            EditOrderDescriptor otherEditOrderDescriptor = (EditOrderDescriptor) other;
            return Objects.equals(orderDate, otherEditOrderDescriptor.orderDate)
                    && Objects.equals(deadline, otherEditOrderDescriptor.deadline)
                    && Objects.equals(price, otherEditOrderDescriptor.price)
                    && Objects.equals(remark, otherEditOrderDescriptor.remark)
                    && Objects.equals(status, otherEditOrderDescriptor.status);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("orderDate", orderDate)
                    .add("deadline", deadline)
                    .add("price", price)
                    .add("remark", remark)
                    .add("status", status)
                    .toString();
        }
    }
}
