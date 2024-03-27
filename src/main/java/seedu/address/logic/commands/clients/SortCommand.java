package seedu.address.logic.commands.clients;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts all clients in the address book by alphabetical order of the first alphabet of their names.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS = "Clients sorted by the first alphabet of their names";

    @Override
    public CommandResult execute(Model model) {
        // Retrieve all persons from the model
        // List<Person> allPersons = model.getAddressBook().getPersonList();
        List<Person> allPersons = model.getFilteredPersonList();

        // Sort the list by the first alphabet of the name using Comparator
        List<Person> sortedPersons = allPersons.stream()
                .sorted(Comparator.comparing(person -> person.getName().fullName.substring(0, 1)))
                .collect(Collectors.toList());

        // Update the filtered list with sorted list
        model.updateFilteredPersonList(person -> sortedPersons.contains(person));

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
