package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.BookKeeper;
import seedu.address.model.ReadOnlyBookKeeper;
import seedu.address.model.client.Address;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.order.Deadline;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderDate;
import seedu.address.model.order.OrderId;
import seedu.address.model.order.Price;
import seedu.address.model.order.Remark;
import seedu.address.model.order.Status;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code BookKeeper} with sample data.
 */
public class SampleDataUtil {
    private SampleDataUtil() {
    } //

    public static Client[] getSamplePersons() {
        Set<Order> s1 = Set.of(
                new Order(new OrderId("69c25c8d-9e34-4d9d-8bad-e378f203ae73"),
                        new OrderDate("01-03-2024 23:59"), new Deadline("01-03-2024 23:59"),
                        new Price("50"), new Remark("No remark"), new Status("PENDING")),
                new Order(new OrderId("b7d063c5-f803-4f75-b2ad-777ec679b75e"),
                        new OrderDate("10-02-2024 11:33"), new Deadline("14-02-2024 10:59"),
                        new Price("20"), new Remark("No remark"), new Status("COMPLETED")));
        Set<Order> s2 = Set.of(
                new Order(new OrderId("fc64826c-369b-4f45-97c0-f98e2edfa006"),
                        new OrderDate("10-10-2024 01:50"), new Deadline("15-10-2024 13:50"),
                        new Price("30"), new Remark("No remark"), new Status("CANCELED")),
                new Order(new OrderId("cd7e3cb4-c310-4692-ba68-a779f6e09d68"),
                        new OrderDate("10-02-2024 11:33"), new Deadline("14-02-2024 10:59"),
                        new Price("20"), new Remark("No remark"), new Status("CANCELED")));
        Client p1 = new Client(new Name("Alex Yeoh"), new Phone("87438807"),
                new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"), s1);
        p1.getOrders().forEach(order -> order.setClient(p1));
        Client p2 = new Client(new Name("Bernice Yu"), new Phone("99272758"),
                new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"), s2);
        p2.getOrders().forEach(order -> order.setClient(p2));
        Client p3 = new Client(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), Set.of());
        Client p4 = new Client(new Name("David Li"), new Phone("91031282"),
                new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), Set.of());
        Client p5 = new Client(new Name("Irfan Ibrahim"), new Phone("92492021"),
                new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"), Set.of());
        Client p6 = new Client(new Name("Roy Balakrishnan"), new Phone("92624417"),
                new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), Set.of());
        return new Client[]{p1, p2, p3, p4, p5, p6};
    }

    public static ReadOnlyBookKeeper getSampleAddressBook() {
        BookKeeper sampleAb = new BookKeeper();
        for (Client sampleClient : getSamplePersons()) {
            sampleAb.addClient(sampleClient);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
