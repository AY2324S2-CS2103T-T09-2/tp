package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.client.exceptions.DuplicatePersonException;
import seedu.address.model.client.exceptions.PersonNotFoundException;
import seedu.address.model.order.Order;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A client is considered unique by comparing using {@code Client#isSamePerson(Client)}. As such, adding and updating of
 * persons uses Client#isSamePerson(Client) for equality so as to ensure that the client being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a client uses Client#equals(Object) so
 * as to ensure that the client with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Client#isSamePerson(Client)
 */
public class UniquePersonList implements Iterable<Client> {

    private final ObservableList<Client> internalList = FXCollections.observableArrayList();
    private final ObservableList<Client> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    private final ObservableList<Order> internalOrderList = FXCollections.observableArrayList();
    private final ObservableList<Order> internalUnmodifiableListOrder =
            FXCollections.unmodifiableObservableList(internalOrderList);

    /**
     * Returns true if the list contains an equivalent client as the given argument.
     */
    public boolean contains(Client toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a client to the list.
     * The client must not already exist in the list.
     */
    public void add(Client toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the client {@code target} in the list with {@code editedClient}.
     * {@code target} must exist in the list.
     * The client identity of {@code editedClient} must not be the same as another existing client in the list.
     */
    public void setPerson(Client target, Client editedClient) {
        requireAllNonNull(target, editedClient);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedClient) && contains(editedClient)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedClient);
    }

    /**
     * Replaces the client {@code target} in the list with {@code editedClient}.
     * Removes the respective Order object from the ObservableList as well.
     *
     * @param target       client to be removed.
     * @param editedClient client to be added.
     * @param order        order to be added.
     */
    public void setPersonAndDeleteOrder(Client target, Client editedClient, Order order) {
        setPerson(target, editedClient);
        internalOrderList.remove(order);
        sortOrders();
    }

    /**
     * Replaces the client {@code target} in the list with {@code editedClient}.
     * Adds the Order object to the ObservableList as well.
     *
     * @param target       client to be removed.
     * @param editedClient client to be added.
     * @param order        order to be removed.
     */
    public void setPersonAndAddOrder(Client target, Client editedClient, Order order) {
        setPerson(target, editedClient);
        internalOrderList.add(order);
        sortOrders();
    }

    /**
     * Replaces the client {@code target} in the list with {@code editedClient}.
     *
     * @param client        client to be removed.
     * @param editedClient  client to be added.
     * @param orderToDelete order to be removed.
     * @param orderToAdd    order to be added.
     */

    public void setPersonAndEditOrder(Client client, Client editedClient, Order orderToDelete, Order orderToAdd) {
        requireAllNonNull(client, orderToDelete, orderToAdd);
        setPerson(client, editedClient);
        int index = internalOrderList.indexOf(orderToDelete);
        internalOrderList.set(index, orderToAdd);
        sortOrders();
    }

    /**
     * Removes the equivalent client from the list.
     * The client must exist in the list.
     */
    public void remove(Client toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
        internalOrderList.removeAll(toRemove.getOrdersList());
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code clients}.
     * {@code clients} must not contain duplicate clients.
     */
    public void setPersons(List<Client> clients) {
        requireAllNonNull(clients);
        if (!personsAreUnique(clients)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(clients);
        setOrders();
    }


    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Client> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }


    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Order> asUnmodifiableObservableListOrders() {
        return internalUnmodifiableListOrder;
    }

    private void setOrders() {
        List<Order> creationOrderList = new ArrayList<>();
        for (Client client : internalList) {
            creationOrderList.addAll(client.getOrdersList());
        }
        internalOrderList.setAll(creationOrderList);
    }

    private void sortOrders() {
        FXCollections.sort(internalOrderList, (order1, order2) ->
                order1.getDeadline().compareTo(order2.getDeadline()));
    }

    @Override
    public Iterator<Client> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniquePersonList)) {
            return false;
        }

        UniquePersonList otherUniquePersonList = (UniquePersonList) other;
        return internalList.equals(otherUniquePersonList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code clients} contains only unique clients.
     */
    private boolean personsAreUnique(List<Client> clients) {
        for (int i = 0; i < clients.size() - 1; i++) {
            for (int j = i + 1; j < clients.size(); j++) {
                if (clients.get(i).isSamePerson(clients.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
