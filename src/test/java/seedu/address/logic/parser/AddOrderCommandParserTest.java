package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COST_DESC_ROSES;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_ROSES;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_ROSES;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.DateTimeUtil;
import seedu.address.logic.commands.orders.AddOrderCommand;

public class AddOrderCommandParserTest {
    private final AddOrderCommandParser parser = new AddOrderCommandParser();

    @Test
    public void parse_optionalFieldsMissing_failure() {
        // Missing cost field
        assertParseFailure(parser, INDEX_FIRST_PERSON + DESCRIPTION_DESC_ROSES + DEADLINE_DESC_ROSES,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));

        // Missing deadline field
        assertParseFailure(parser, INDEX_FIRST_PERSON + DESCRIPTION_DESC_ROSES + COST_DESC_ROSES,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // missing description prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON + COST_DESC_ROSES + DEADLINE_DESC_ROSES,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));

        // missing cost prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON + DESCRIPTION_DESC_ROSES + DEADLINE_DESC_ROSES,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));

        // missing deadline prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON + DESCRIPTION_DESC_ROSES + COST_DESC_ROSES,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));
    }

    @Test
    public void check_sameCurrentTime_success() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        DateTimeUtil.getCurrentTime();
        assertEquals(DateTimeUtil.getCurrentTime(), now.format(formatter));


    }
}
