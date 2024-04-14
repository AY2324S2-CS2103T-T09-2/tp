package seedu.address.logic.commands.orders;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ORDERS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.Pair;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;


/**
 * Removes an existing order in the address book.
 */
public class DeleteOrderCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Deleted Order: %1$s";

    public static final String COMMAND_WORD = "deleteOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the order identified by the index number used in the displayed order list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " index";

    public static final String MESSAGE_DELETE_ORDER_FAILURE = "Failed to delete Order!";

    private final Index targetIndex;


    /**
     * Creates an DeleteOrderCommand to delete the specified {@code Order}.
     */
    public DeleteOrderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    private Pair<Person, Person> getEditedPerson(List<Person> personList, Order orderToDelete) throws CommandException {

        for (Person person : personList) {
            if (person.getOrders().contains(orderToDelete)) {
                Person editedPerson = person.removeOrder(orderToDelete);
                return new Pair<>(person, editedPerson);
            }
        }
        throw new CommandException(MESSAGE_DELETE_ORDER_FAILURE);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Order> lastShownOrderList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownOrderList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToDelete = lastShownOrderList.get(targetIndex.getZeroBased());

        List<Person> personList = model.getFilteredPersonList();
        Pair<Person, Person> pair = getEditedPerson(personList, orderToDelete);
        Person person = pair.getFirst();
        Person editedPerson = pair.getSecond();

        model.setPersonAndDeleteOrder(person, editedPerson, orderToDelete);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(orderToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteOrderCommand)) {
            return false;
        }

        DeleteOrderCommand otherAddCommand = (DeleteOrderCommand) other;
        return targetIndex.equals(otherAddCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", targetIndex)
                .toString();
    }
}
