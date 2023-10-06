package seedu.address.logic.commands;

import seedu.address.model.Model;
public class RemarkCommand {

    public static final String COMMAND_WORD = "remark";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Hello from remark");
    }
}
