package seedu.duke.logic.parser;

import seedu.duke.logic.commands.DeleteCommand;
import seedu.duke.logic.parser.exceptions.ParseException;

import static seedu.duke.logic.parser.ParserUtil.parseJobApplicationIndex;

public class DeleteCommandParser implements Parser<DeleteCommand>{
    /**
     * Parses the delete command. Delete takes in index of job application, and deletes it.
     * @param indexString Index to delete it if valid
     * @return DeleteCommand object
     * @throws ParseException
     */
    @Override
    public DeleteCommand parse(String indexString) throws ParseException {
        int targetIndex = parseJobApplicationIndex(indexString);
        return new DeleteCommand(targetIndex);
    }
}
