package seedu.address.logic.commands.clients;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class SortCommandTest {

    @Test
    public void execute_sortList_success() {
        // Create a list of persons in unsorted order
        List<Person> unsortedList = List.of(
                new Person(new Name("Charlie"), new Phone("12345678"), new Email("charlie@example.com"),
                        new Address("C Lane"), Set.of(new Tag("friend")), Set.of()),
                new Person(new Name("Alice"), new Phone("98765432"), new Email("alice@example.com"),
                        new Address("A Lane"), Set.of(new Tag("friend")), Set.of()),
                new Person(new Name("Bob"), new Phone("87654321"), new Email("bob@example.com"),
                        new Address("B Lane"), Set.of(new Tag("friend")), Set.of())
        );

        // Create a model with unsorted list
        Model model = new ModelManager();
        model.updateFilteredPersonList(person -> true); // Ensure all persons are initially shown
        unsortedList.forEach(model::addPerson);

        // Execute the SortCommand
        SortCommand sortCommand = new SortCommand();
        sortCommand.execute(model);

        // Retrieve the filtered person list after sorting
        List<Person> sortedList = model.getFilteredPersonList();

        // Create a list of names in sorted order
        List<String> sortedNames = sortedList.stream()
                .map(person -> person.getName().fullName)
                .collect(Collectors.toList());

        // Create a list of expected names in sorted order
        List<String> expectedNames = List.of("Alice", "Bob", "Charlie");

        // Assert that the names are sorted correctly
        assertEquals(expectedNames, sortedNames);
    }
}
