package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.DateTimeUtil;
import seedu.address.logic.commands.orders.AddOrderCommand;

public class AddOrderCommandParserTest {
    private AddOrderCommandParser parser = new AddOrderCommandParser();

    public static String getFutureDateForTest() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String futureDateString = futureDate.format(formatter);
        return futureDateString;
    }

    @Test
    public void parse_missingPrefixUserIndex_throwsParseException() {
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "abc", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void check_sameCurrentTime_success() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        DateTimeUtil.getCurrentTime();
        assertEquals(DateTimeUtil.getCurrentTime(), now.format(formatter));
    }


}
