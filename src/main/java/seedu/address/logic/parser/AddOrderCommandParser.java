package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;

import java.util.NoSuchElementException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.logic.commands.orders.AddOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.Deadline;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderDate;
import seedu.address.model.order.OrderId;
import seedu.address.model.order.Price;
import seedu.address.model.order.Remark;
import seedu.address.model.order.Status;

/**
 * Parses input arguments and creates a new AddOrderCommand object.
 */
public class AddOrderCommandParser implements Parser<AddOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddOrderCommand
     * and returns an AddOrderCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_DETAILS, PREFIX_BY, PREFIX_PRICE);

        Index index;
        OrderDate orderDate = new OrderDate(DateTimeUtil.getCurrentTime());
        Deadline deadline;
        Remark remark;
        Price price;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get());
            deadline = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_BY).get());
            remark = new Remark(argMultimap.getValue(PREFIX_DETAILS).get());
        } catch (NoSuchElementException error) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE),
                    error);
        }
        Order order = new Order(new OrderId(), orderDate, deadline, price, remark, new Status("pending"));
        return new AddOrderCommand(index, order);
    }

}
