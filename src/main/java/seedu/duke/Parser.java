package seedu.duke;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Parser {

    public static boolean hasListMemberKeyword(String arg) {
        return arg.trim().matches("^list /m");
    }

    public static boolean hasListTrainingKeyword(String arg) {
        return arg.trim().toLowerCase().contains("list /t");
    }

    public static boolean hasAddMemberKeyword(String arg) {
        return arg.trim().toLowerCase().contains("add /m");
    }

    public static boolean hasAddTrainingKeyword(String arg) {
        return arg.trim().toLowerCase().contains("add /t");
    }

    public static boolean hasDeleteMemberKeyword(String arg) {
        return arg.trim().toLowerCase().contains("delete /m");
    }

    public static boolean hasDeleteTrainingKeyword(String arg) {
        return arg.trim().toLowerCase().contains("delete /t");
    }

    public static boolean hasFindMemberKeyword(String arg) {
        return arg.trim().toLowerCase().contains("find /m");
    }

    public static boolean hasFindTrainingKeyword(String arg) {
        return arg.trim().toLowerCase().contains("find /t");
    }


    /**
     * Returns the required value for keyword which is the first word keyed in by user.
     *
     * @param query user raw data input.
     * @return keyword value from Keyword enum class.
     */
    public static Keyword getKeywordStatus(String query) {
        Keyword keyword;
        if (hasAddMemberKeyword(query)) {
            keyword = Keyword.ADD_MEMBER_KEYWORD;
        } else if (hasAddTrainingKeyword(query)) {
            keyword = Keyword.ADD_TRAINING_KEYWORD;
        } else if (hasListMemberKeyword(query)) {
            keyword = Keyword.LIST_MEMBER_KEYWORD;
        } else if (hasListTrainingKeyword(query)) {
            keyword = Keyword.LIST_TRAINING_KEYWORD;
        } else if (query.trim().equals("bye")) {
            keyword = Keyword.EXIT_KEYWORD;
        } else if (hasDeleteMemberKeyword(query)) {
            keyword = Keyword.DELETE_MEMBER_KEYWORD;
        } else if (hasDeleteTrainingKeyword(query)) {
            keyword = Keyword.DELETE_TRAINING_KEYWORD;
        } else if (hasFindMemberKeyword(query)) {
            keyword = Keyword.FIND_MEMBER_KEYWORD;
        } else if (hasFindTrainingKeyword(query)) {
            keyword = Keyword.FIND_TRAINING_KEYWORD;
        } else {
            keyword = Keyword.NO_KEYWORD;
        }
        return keyword;
    }

    /**
     * Returns the list number which is assigned to the task.
     *
     * @param arg user input that contains word [done].
     * @return task number.
     */
    public static int getNum(String arg) {
        String[] words = arg.trim().split("[\\s]+");
        return Integer.parseInt(words[1]);
    }

    /**
     * Returns the description of the task only, without the date or the keyword.
     *
     * @param query user raw data input.
     * @return description of task.
     */
    public static String getTrainingDescription(String query) {
        String[] words = query.trim().split("[\\s]+");
        String[] allButFirstWord = Arrays.copyOfRange(words, 1, words.length);
        StringBuilder sentenceAfterDeletion = new StringBuilder();
        for (String word : allButFirstWord) {
            if (word.contains("/")) {
                break;
            } else {
                sentenceAfterDeletion.append(word).append(" ");
            }
        }
        return sentenceAfterDeletion.toString();
    }

    public static String getMemberDescription(String query) {
        String[] words = query.trim().split("[\\s]+");
        String[] allButFirstWord = Arrays.copyOfRange(words, 1, words.length);
        StringBuilder sentenceAfterDeletion = new StringBuilder();
        for (String word : allButFirstWord) {
            if (word.contains("/")) {
                break;
            } else {
                sentenceAfterDeletion.append(word).append(" ");
            }
        }
        return sentenceAfterDeletion.toString();
    }


    /**
     * Function creates a new Todo task to be input in tasks.
     *
     * @param members ArrayList of members
     * @param query user input
     */
    public static void makeMemberEntry(ArrayList<Member> members, String query) {
        members.add(new Members(Parser.getMemberDescription(query)));
        System.out.println("Added a Member: " + Parser.getMemberDescription(query));
    }

    /**
     * Function creates a new Event task to be input in tasks.
     *
     * @param trainings ArrayList of trainings
     * @param query user input
     */
    public static void makeTrainingEntry(ArrayList<TrainingSchedule> trainings, String query) {
        trainings.add(new TrainingSchedule(Parser.getTrainingDescription(query)));
        System.out.println("Added a Training entry: " + Parser.getTrainingDescription(query));
    }

    /**
     * Function finds tasks with descriptions matching the user's query and adds them to a new ArrayList. If no matching
     * words are found, the user will be notified.
     *
     * @param trainings ArrayList of trainings
     * @param query user input
     */
    public static void findInTraining(ArrayList<TrainingSchedule> trainings, String query) {
        ArrayList<TrainingSchedule> matchingTrainings = new ArrayList<>();
        for (TrainingSchedule training : trainings) {
            if (training.getDescription().toLowerCase().contains(Parser.getTrainingDescription(query))) {
                matchingTrainings.add(training);
            }
        }
        Ui.printMatchingTrainingList(matchingTrainings, query);
    }

    /**
     * Function finds tasks with descriptions matching the user's query and adds them to a new ArrayList. If no matching
     * words are found, the user will be notified.
     *
     * @param members ArrayList of members
     * @param query user input
     */
    public static void findInMembers(ArrayList<Member> members, String query) {
        ArrayList<Member> matchingMembers = new ArrayList<>();
        for (Member member : members) {
            if (member.getDescription().toLowerCase().contains(Parser.getTrainingDescription(query))) {
                matchingMembers.add(member);
            }
        }
        Ui.printMatchingMemberList(matchingMembers, query);
    }

    /**
     * Function deletes an item from the ArrayList task.
     *
     * @param members ArrayList of members
     * @param query user input
     */
    public static void deleteMember(ArrayList<Member> members, String query) {
        try {
            int memberNumber = Parser.getNum(query) - 1;
            Member referencedMember = members.get(name);
            members.remove(memberNumber);
            Ui.printDeletedMemberMessage(referencedMember);
        } catch (IndexOutOfBoundsException exception) {
            System.out.println("There is no such member number...");
        }
    }

    public static void deleteTraining(ArrayList<TrainingSchedule> trainings, String query) {
        try {
            TrainingSchedule referencedTraining = trainings.get(trainingNumber);
            trainings.remove(trainingNumber);
            Ui.printDeletedTrainingMessage(referencedTraining);
        } catch (IndexOutOfBoundsException exception) {
            System.out.println("There is no such training entry...");
        }
    }

    /**
     * Function waits for user input, or takes input from ./list.txt.
     */
    public static void waitForQuery() {
        String query = "";
        Scanner userInput = new Scanner(System.in);
        while (!query.equals("bye")) {
            System.out.print("=>");
            if (userInput.hasNextLine()) {
                query = userInput.nextLine();
            }
            Entry.addEntry(query);
        }
    }
}

