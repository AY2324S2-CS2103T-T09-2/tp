package seedu.address.model.client;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.order.Order;
import seedu.address.model.tag.Tag;


/**
 * Represents a Client in bookkeeper.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Client {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Order> orders = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Client(Name name, Phone phone, Email email, Address address,
                  Set<Tag> tags, Set<Order> orders) {
        requireAllNonNull(name, phone, email, address, tags, orders);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.orders.addAll(orders);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable order set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Order> getOrders() {
        return Collections.unmodifiableSet(this.orders);
    }

    /**
     * Returns a new Client object, with the specified Order added to the orders object.
     *
     * @param order the order to be added
     * @return new Client object
     */
    public Client addOrder(Order order) {
        Set<Order> newOrders = new HashSet<>(orders);
        newOrders.add(order);
        return new Client(this.name, this.phone, this.email, this.address, this.getTags(), newOrders);
    }

    /**
     * Returns a new Client object, with the specified Order removed to the orders object.
     *
     * @param order the order to be removed
     * @return new Client object
     */
    public Client removeOrder(Order order) {
        Set<Order> newOrders = new HashSet<>(orders);
        newOrders.remove(order);
        return new Client(this.name, this.phone, this.email, this.address, this.getTags(), newOrders);
    }

    /**
     * Returns true if both clients have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameClient(Client otherClient) {
        if (otherClient == this) {
            return true;
        }

        return otherClient != null
                && otherClient.getName().equals(getName());
    }

    /**
     * The list of orders in this client.
     */
    public List<Order> getOrdersList() {
        return new ArrayList<>(orders);
    }

    /**
     * Returns true if both clients have the same identity and data fields.
     * This defines a stronger notion of equality between two clients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Client)) {
            return false;
        }

        Client otherClient = (Client) other;
        return name.equals(otherClient.name)
                && phone.equals(otherClient.phone)
                && email.equals(otherClient.email)
                && address.equals(otherClient.address)
                && tags.equals(otherClient.tags)
                && orders.equals(otherClient.orders);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, orders);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("orders", orders)
                .toString();
    }

}
