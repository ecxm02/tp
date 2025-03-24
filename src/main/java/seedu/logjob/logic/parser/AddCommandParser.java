package seedu.logjob.logic.parser;

import static seedu.logjob.logic.parser.CliSyntax.FLAG_COMPANY_NAME;
import static seedu.logjob.logic.parser.CliSyntax.FLAG_JOB_TITLE;
import static seedu.logjob.logic.parser.CliSyntax.FLAG_APPLICATION_DATE;
import static seedu.logjob.logic.parser.CliSyntax.FLAG_APPLICATION_STATUS;
import static seedu.logjob.model.ApplicationStatus.APPLIED;

import java.time.LocalDate;
import java.util.function.Predicate;

import seedu.logjob.logic.commands.AddCommand;
import seedu.logjob.logic.parser.exceptions.ParseException;
import seedu.logjob.model.ApplicationStatus;

/**
 * Parses input arguments and creates a new {@code AddCommand} Object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses input arguments string and returns an {@code AddCommand} object.
     * Requires company name and job title flags, and optionally accepts an application status flag.
     *
     * @param args full user input arguments
     * @return a new AddCommand with parsed arguments
     * @throws ParseException if required flags are missing or duplicated, or if arguments are invalid
     */
    @Override
    public AddCommand parse(String args) throws ParseException {
        ArgumentMap argMap =
                ArgumentTokenizer.tokenize(
                        args, FLAG_COMPANY_NAME, FLAG_JOB_TITLE, FLAG_APPLICATION_DATE, FLAG_APPLICATION_STATUS);

        containsAllFlags(argMap, FLAG_COMPANY_NAME, FLAG_JOB_TITLE);
        containsNoDuplicateFlags(
                argMap, FLAG_COMPANY_NAME, FLAG_JOB_TITLE, FLAG_APPLICATION_DATE, FLAG_APPLICATION_STATUS);

        String companyName = ParserUtil.parseCompanyName(argMap.get(FLAG_COMPANY_NAME));
        String jobTitle = ParserUtil.parseJobTitle(argMap.get(FLAG_JOB_TITLE));

        // Optional Application Status Flag
        LocalDate applicationDate;
        ApplicationStatus applicationStatus;

        if (argMap.containsKey(FLAG_APPLICATION_STATUS)) {
            applicationStatus = ParserUtil.parseStatus(argMap.get(FLAG_APPLICATION_STATUS));
        } else {
            applicationStatus = APPLIED;
        }

        if (argMap.containsKey(FLAG_APPLICATION_DATE)) {
            applicationDate = ParserUtil.parseApplicationDate(argMap.get(FLAG_APPLICATION_DATE));
        } else {
            applicationDate = LocalDate.now();
        }

        return new AddCommand(companyName, jobTitle, applicationDate, applicationStatus);

    }

    private static void containsAllFlags(ArgumentMap argMap, Flag... flags)
            throws ParseException {
        validateFlags(flag -> !argMap.containsKey(flag),
                "Missing flag(s): ", flags);
    }

    private static void containsNoDuplicateFlags(ArgumentMap argMap, Flag... flags)
            throws ParseException {
        validateFlags(argMap::containsMultipleValues,
                "Duplicate flag(s): ", flags);
    }

    private static void validateFlags(Predicate<Flag> condition,
                                      String errorMessagePrefix,
                                      Flag... flags) throws ParseException {
        StringBuilder errorMessage = new StringBuilder();
        for (Flag flag : flags) {
            if (condition.test(flag)) {
                errorMessage.append(flag.toString()).append(" ");
            }
        }
        if (!errorMessage.isEmpty()) {
            throw new ParseException(errorMessagePrefix + errorMessage);
        }
    }
}
