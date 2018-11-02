package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;
import seedu.address.model.student.Attendance;

/**
 * UPDATES ATTENDANCE OF A STUDENT IN THE ADDRESS BOOK.
 */
public class AttendanceCommand extends Command {

    public static final String COMMAND_WORD = "attendance";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the attendance of a student. "
            + "by the index number used in the displayed student list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ATTENDANCE + "[ATTENDANCE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ATTENDANCE + "Present.";

    public static final String MESSAGE_ADD_ATTENDANCE_SUCCESS = "Added attendance for student: %1$s";
    public static final String MESSAGE_REMOVE_ATTENDANCE_SUCCESS = "Removed attendance for student: %1$s";

    private final Index index;
    private final Attendance attendance;

    /**
     * @param index of the student to mark the attendance of
     * @param attendance of the student
     */
    public AttendanceCommand(Index index, Attendance attendance) {
        requireAllNonNull(index, attendance);

        this.index = index;
        this.attendance = attendance;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToEdit = lastShownList.get(index.getZeroBased());
        Student editedStudent = new Student(studentToEdit.getName(), studentToEdit.getStudentNumber(), studentToEdit.getEmail(),
                studentToEdit.getFaculty(), attendance, studentToEdit.getTags());

        model.updateStudent(studentToEdit, editedStudent);
        model.commitAddressBook();

        return new CommandResult(generateSuccessMessage(editedStudent));
    }

    /**
     * Generates a command execution success message based on whether the attendance is updated to or removed from
     * {@code studentToEdit}.
     */
    private String generateSuccessMessage(Student studentToEdit) {
        String message = !attendance.value.toString().isEmpty()
                ? MESSAGE_ADD_ATTENDANCE_SUCCESS : MESSAGE_REMOVE_ATTENDANCE_SUCCESS;
        return String.format(message, studentToEdit);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof AttendanceCommand)) {
            return false;
        }

        AttendanceCommand a = (AttendanceCommand) object;
        return index.equals(a.index)
                && attendance.equals(a.attendance);
    }

}
