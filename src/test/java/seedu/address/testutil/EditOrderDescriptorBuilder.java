package seedu.address.testutil;

import seedu.address.logic.commands.order.EditOrderCommand.EditOrderDescriptor;
import seedu.address.model.order.Deadline;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderDate;
import seedu.address.model.order.Price;
import seedu.address.model.order.Remark;
import seedu.address.model.order.Status;

/**
 * A utility class to help with building EditOrderDescriptor objects.
 */
public class EditOrderDescriptorBuilder {

    private EditOrderDescriptor descriptor;

    public EditOrderDescriptorBuilder() {
        descriptor = new EditOrderDescriptor();
    }

    public EditOrderDescriptorBuilder(EditOrderDescriptor descriptor) {
        this.descriptor = new EditOrderDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditOrderDescriptor} with fields containing {@code order}'s details.
     */
    public EditOrderDescriptorBuilder(Order order) {
        descriptor = new EditOrderDescriptor();
        descriptor.setOrderDate(order.getOrderDate());
        descriptor.setDeadline(order.getDeadline());
        descriptor.setPrice(order.getPrice());
        descriptor.setRemark(order.getRemark());
        descriptor.setStatus(order.getStatus());
    }

    /**
     * Sets the {@code OrderDate} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withOrderDate(String orderDate) {
        descriptor.setOrderDate(new OrderDate(orderDate));
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withDeadline(String deadline) {
        descriptor.setDeadline(new Deadline(deadline));
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withPrice(String price) {
        descriptor.setPrice(new Price(price));
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withRemark(String remark) {
        descriptor.setRemark(new Remark(remark));
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withStatus(String status) {
        descriptor.setStatus(new Status(status));
        return this;
    }

    public EditOrderDescriptor build() {
        return descriptor;
    }
}
