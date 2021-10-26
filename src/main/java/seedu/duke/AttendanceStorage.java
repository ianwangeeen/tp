package seedu.duke;

import seedu.duke.attendance.Attendance;
import seedu.duke.attendance.AttendanceList;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class AttendanceStorage {

    /**
     * This method searches the current directory for a folder named DukeAttendance.
     * If the folder exists, it will load the attendances into the attendance list.
     * Else it will create an empty folder called DukeAttendance
     */
    public static void setUpAttendanceStorage(AttendanceList attendanceList) {
        File currentDir = new File("");
        try {
            //is / instead of \\ to make it OS independent. need to test it tho on UNIX
            String dukeAttendanceFolderPath = currentDir.getCanonicalPath() + "/DukeAttendance";

            //System.out.println(DukeAttendanceFolderPath);
            File dukeAttendanceFolder = new File(dukeAttendanceFolderPath);
            if (dukeAttendanceFolder.isDirectory()) {
                System.out.println("attendance file exists");
                loadAttendanceFiles(dukeAttendanceFolder, attendanceList);
            } else {
                System.out.println("attendance file does not exists");
                new File(dukeAttendanceFolderPath).mkdirs();
                System.out.println("attendance file created");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will load all attendances into the list.
     *
     * @param dukeAttendanceFolder the folder path containing all the attendances
     */
    public static void loadAttendanceFiles(File dukeAttendanceFolder, AttendanceList attendanceList) {
        File[] dukeAttendanceFiles = dukeAttendanceFolder.listFiles();

        for (File file : dukeAttendanceFiles) {
            //System.out.println(file.getName()); //this can show all the names of attendance files
            loadIndividualAttendanceFile(file, attendanceList);
        }
    }

    public static void loadIndividualAttendanceFile(File attendanceCsvFile, AttendanceList attendanceList) {
        String fileName = attendanceCsvFile.getName();

        String name;
        String trainingName = getFileTrainingName(fileName);
        String attended;
        try {
            Scanner dukeAttendanceScanner = new Scanner(attendanceCsvFile);
            dukeAttendanceScanner.nextLine(); //skips the first header row
            while (dukeAttendanceScanner.hasNextLine()) {
                String fullAttendanceDetails = dukeAttendanceScanner.nextLine();
                //System.out.println(fullAttendanceDetails);
                String[] attendanceDetails = fullAttendanceDetails.split("\\,", 2);

                name = attendanceDetails[0];
                attended = attendanceDetails[1];

                Attendance attendance = new Attendance(name, trainingName, attended);
                attendanceList.addAttendance(attendance);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method adds to the csv file if it already exists. If the csv file does not exist, it will create a new csv
     * file and add to it.
     *
     * @param attendance the attendance entry to be added
     */
    //assumption is that the attendance folder is created already
    public static void writeToAttendance(AttendanceList attendanceList, Attendance attendance) {
        String trainingName = attendance.getTrainingName();
        File currentDir = new File("");
        try {
            String dukeAttendanceFilePath = currentDir.getCanonicalPath() + "/DukeAttendance/" + trainingName + ".csv";

            System.out.println(dukeAttendanceFilePath);

            File dukeSpecificAttendanceFile = new File(dukeAttendanceFilePath);
            if (dukeSpecificAttendanceFile.exists()) {
                rewriteAttendanceCsv(attendanceList, dukeSpecificAttendanceFile, trainingName);
            } else {
                //create new file and write to it
                initializeAttendanceCsv(attendanceList, attendance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initializeAttendanceCsv(AttendanceList attendanceList, Attendance attendance) {
        String trainingName = attendance.getTrainingName();
        File currentDir = new File("");
        try {
            String dukeAttendanceFilePath = currentDir.getCanonicalPath() + "/DukeAttendance/" + trainingName + ".csv";
            File attendanceCsvFile = new File(dukeAttendanceFilePath);
            attendanceCsvFile.createNewFile();
            writeFirstAttendance(attendanceCsvFile, attendance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeFirstAttendance(File attendanceCsvFile, Attendance attendance) {
        try (PrintWriter dukeAttendanceWriter = new PrintWriter(attendanceCsvFile)) {
            dukeAttendanceWriter.write("name");
            dukeAttendanceWriter.write(',');
            dukeAttendanceWriter.write("attendance");
            dukeAttendanceWriter.write(',');
            dukeAttendanceWriter.write('\n');
            dukeAttendanceWriter.write(attendance.getMemberName());
            dukeAttendanceWriter.write(',');
            dukeAttendanceWriter.write(attendance.getAttended());
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * this method rewrites the entire specific csv file.
     */
    public static void rewriteAttendanceCsv(AttendanceList attendanceList, File currentAttendanceFile,
            String trainingName) {
        int attendanceListSize = attendanceList.getAttendanceListSize();
        try (PrintWriter dukeAttendanceWriter = new PrintWriter(currentAttendanceFile)) {
            dukeAttendanceWriter.write("name");
            dukeAttendanceWriter.write(',');
            dukeAttendanceWriter.write("attendance");
            dukeAttendanceWriter.write(',');
            dukeAttendanceWriter.write('\n');
            for (int i = 1; i <= attendanceListSize; i++) {
                if (attendanceList.getAttendanceTrainingName(i).equals(trainingName)) {
                    dukeAttendanceWriter.write(attendanceList.getAttendanceMemberName(i));
                    dukeAttendanceWriter.write(',');
                    dukeAttendanceWriter.write(attendanceList.getAttendancePresentOrLate(i));
                    dukeAttendanceWriter.write('\n');
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }


    /**
     * This method removes the extension from the file name.
     *
     * @param filename full file name including extension
     */
    public static String getFileTrainingName(String filename) {
        int fileNameLength = filename.length();
        int numberToRemove = fileNameLength - 4;
        String trainingName = filename.substring(0, numberToRemove);
        return trainingName;
    }

    //this function cheesing cos it just goes into the attendance folder and read the csv names
    //haven't done

    /**
     * This method will list all the attendance training names.
     */
    public static String getAllTrainingNames() {
        return "kk";
    }


}





