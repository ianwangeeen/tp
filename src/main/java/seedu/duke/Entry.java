package seedu.duke;

import java.util.ArrayList;

public class Entry {
    private static final ArrayList<TrainingSchedule> trainings = new ArrayList<>();
    private static final ArrayList<Member> members = new ArrayList<>();

    /**
     * Returns void. Function is responsible for adding different Tasks to the task list.
     *
     * @param entry user raw data input
     * @throws IndexOutOfBoundsException if user keys in done [number] when there is no such task.
     */
    public static void addEntry(String entry) throws NullPointerException {
        Keyword keyword = Parser.getKeywordStatus(entry);
        switch (keyword) {
        case LIST_MEMBER_KEYWORD:
            Ui.printMemberList(members);
            break;
        case LIST_TRAINING_KEYWORD:
            Ui.printTrainingList(trainings);
            break;
        case ADD_MEMBER_KEYWORD:
            Parser.makeMemberEntry(members, entry);
            break;
        case ADD_TRAINING_KEYWORD:
            Parser.makeTrainingEntry(trainings, entry);
        case DELETE_MEMBER_KEYWORD:
            Parser.deleteMember(members, entry);
            break;
        case DELETE_TRAINING_KEYWORD:
            Parser.deleteTraining(trainings, entry);
        case FIND_MEMBER_KEYWORD:
            Parser.findInMembers(members, entry);
            break;
        case FIND_TRAINING_KEYWORD:
            Parser.findInTraining(trainings, entry);
        case NO_KEYWORD:
            break;
        case EXIT_KEYWORD:
            Ui.printExitMessage();
            break;
        }
    }
}
