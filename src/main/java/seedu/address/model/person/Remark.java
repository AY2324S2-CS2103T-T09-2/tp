package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Remark {
    public final String value;

    /**
     * Constructs a {@code Remark}.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    /**
     * Returns the remark value.
     *
     * @return The remark value.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Checks if this remark is equal to another object.
     *
     * @param other The other object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && value.equals(((Remark) other).value)); // state check
    }

    /**
     * Generates a hash code for this remark.
     *
     * @return The hash code value for this remark.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
