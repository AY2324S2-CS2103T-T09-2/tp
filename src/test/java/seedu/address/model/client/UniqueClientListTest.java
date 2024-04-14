package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.client.exceptions.DuplicatePersonException;
import seedu.address.model.client.exceptions.PersonNotFoundException;
import seedu.address.model.order.Order;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.PersonBuilder;

public class UniqueClientListTest {

    private final UniquePersonList uniquePersonList = new UniquePersonList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniquePersonList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        assertTrue(uniquePersonList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        Client editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniquePersonList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniquePersonList.add(ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniquePersonList.add(ALICE);
        uniquePersonList.setPerson(ALICE, ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(ALICE);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniquePersonList.add(ALICE);
        Client editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniquePersonList.setPerson(ALICE, editedAlice);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(editedAlice);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniquePersonList.add(ALICE);
        uniquePersonList.setPerson(ALICE, BOB);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniquePersonList.add(ALICE);
        uniquePersonList.add(BOB);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniquePersonList.add(ALICE);
        uniquePersonList.remove(ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void addOrder_existingPerson_addOrder() {
        Order order = new OrderBuilder().build();
        Client client = new PersonBuilder().build();
        uniquePersonList.add(client);
        Client editedClient = client.addOrder(order);
        uniquePersonList.setPersonAndAddOrder(client, editedClient, order);
        assertEquals(1, uniquePersonList.asUnmodifiableObservableListOrders().size());
    }

    @Test
    public void removeOrder_existingPersonAndOrder_removeOrder() {
        Order order = new OrderBuilder().build();
        Client client = new PersonBuilder().build();
        uniquePersonList.add(client);
        Client editedClient = client.addOrder(order);
        uniquePersonList.setPersonAndAddOrder(client, editedClient, order);
        uniquePersonList.setPersonAndDeleteOrder(editedClient, client, order);
        assertEquals(0, uniquePersonList.asUnmodifiableObservableListOrders().size());
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((UniquePersonList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniquePersonList.add(ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        uniquePersonList.setPersons(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((List<Client>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniquePersonList.add(ALICE);
        List<Client> clientList = Collections.singletonList(BOB);
        uniquePersonList.setPersons(clientList);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Client> listWithDuplicateClients = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPersons(listWithDuplicateClients));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniquePersonList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniquePersonList.asUnmodifiableObservableList().toString(), uniquePersonList.toString());
    }

    @Test
    public void setPersonsAndAddOrder_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersonAndAddOrder(ALICE, null,
                null));
    }

    @Test
    public void setPersonsAndAddOrder_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersonAndAddOrder(null,
                null, null));
    }

    @Test
    public void setPersonAndAddOrder_validPersonAndOrder_success() {
        UniquePersonList uniquePersonList = new UniquePersonList();
        Client originalClient = new PersonBuilder(ALICE).build();
        Client editedClient = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        OrderBuilder orderBuilder = new OrderBuilder();
        Order newOrder = orderBuilder.build();

        uniquePersonList.add(originalClient);
        assertDoesNotThrow(() -> uniquePersonList.setPersonAndAddOrder(originalClient, editedClient, newOrder));

        assertTrue(uniquePersonList.contains(editedClient));
    }

    @Test
    public void setPersonsAndDeleteOrder_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersonAndDeleteOrder(BOB, null,
                null));
    }

    @Test
    public void setPersonsAndDeleteOrder_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersonAndDeleteOrder(null,
                null, null));
    }

    @Test
    public void setPersonAndDeleteOrder_orderDoesNotExist_personUnchanged() {
        UniquePersonList uniquePersonList = new UniquePersonList();
        Client originalClient = new PersonBuilder(ALICE).build();
        Client editedClient = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        OrderBuilder orderBuilder = new OrderBuilder();
        Order newOrder = orderBuilder.build();

        uniquePersonList.add(originalClient);

        assertDoesNotThrow(() -> uniquePersonList.setPersonAndDeleteOrder(originalClient, editedClient, newOrder));
    }

    @Test
    public void testEquals_sameInput() {
        UniquePersonList uniquePersonList = new UniquePersonList();
        assertEquals(uniquePersonList, uniquePersonList);
    }

    @Test
    public void testEquals_sameOutput() {
        UniquePersonList list1 = new UniquePersonList();
        UniquePersonList list2 = new UniquePersonList();
        list1.add(ALICE);
        list2.add(ALICE);
        assertEquals(list1.equals(list2), list2.equals(list1));
    }

    @Test
    public void testHashCode() {
        UniquePersonList uniquePersonList = new UniquePersonList();
        int initialHashCode = uniquePersonList.hashCode();
        for (int i = 0; i < 10; i++) {
            assertEquals(initialHashCode, uniquePersonList.hashCode());
        }
    }

    public void equals() {
        assertTrue(uniquePersonList.equals(uniquePersonList));
        UniquePersonList uniquePersonList2 = new UniquePersonList();
        uniquePersonList2.add(new PersonBuilder().build());
        assertFalse(uniquePersonList.equals(uniquePersonList2));
    }
}
