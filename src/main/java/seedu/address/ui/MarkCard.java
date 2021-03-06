package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.mark.Mark;

/**
 * An UI component that displays information of a {@code Mark}.
 */
public class MarkCard extends UiPart<Region> {

    private static final String FXML = "MarkListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Mark mark;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label studentCount;

    public MarkCard(Mark mark, int displayedIndex) {
        super(FXML);
        this.mark = mark;
        name.setText(mark.getName());
        studentCount.setText(String.valueOf(mark.getSet().size()) + " students");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkCard)) {
            return false;
        }

        // state check
        MarkCard card = (MarkCard) other;
        return mark.equals(card.mark);
    }
}
