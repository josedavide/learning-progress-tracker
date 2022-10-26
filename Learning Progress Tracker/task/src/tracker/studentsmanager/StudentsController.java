package tracker.studentsmanager;

import tracker.LearningPlatformRepository;
import tracker.model.Enrolment;
import tracker.model.Student;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StudentsController {
    private static final String BACK = "back";
    StudentsService studentsService;


    public StudentsController(LearningPlatformRepository learningRepository) {
        this.studentsService = new StudentsService(learningRepository);
    }

    public void addStudentsControl(Scanner scanner) {
        int initialStudentsRegistered = studentsService.numberOfRegisteredStudents();
        String[] newStudentCredentials;
        String userInput;

        System.out.println("Enter student credentials or 'back' to return");
        userInput = scanner.nextLine();

        while (!backCommand(userInput)) {
            newStudentCredentials = userInput.split("\\s+");
            studentsService.addNewStudentFromStudentCredentials(newStudentCredentials);
            userInput = scanner.nextLine();
        }

        int finalStudentsRegistered = studentsService.numberOfRegisteredStudents();
        int addedStudents = finalStudentsRegistered - initialStudentsRegistered;

        System.out.println("Total " + addedStudents + " students have been added.");
    }

    public void listStudents() {
        List<Integer> studentsIds = studentsService.getStudentsIds();
        if (studentsIds.size() == 0) {
            System.out.println("No students found");
        } else {
            System.out.println("Students:");
            for (int studentId : studentsIds) {
                System.out.println(studentId);
            }
        }
    }

    public void addStudentCredits(Scanner scanner) {
        String[] studentCredits;
        String userInput;

        System.out.println("Enter an id and points or 'back' to return");
        userInput = scanner.nextLine();

        while (!backCommand(userInput)) {
            studentCredits = userInput.split("\\s+");
            studentsService.addStudentCredits(studentCredits);
            userInput = scanner.nextLine();
        }
    }

    public void findStudent(Scanner scanner) {
        String userInput;

        System.out.println("Enter an id or 'back' to return");
        userInput = scanner.nextLine();

        while (!backCommand(userInput)) {
            Student student = studentsService.findStudent(userInput);
            if (student == null) {
                System.out.printf("No student is found for id=%s", userInput);
            } else {
                printStudentCredits(Integer.parseInt(userInput));
            }
            userInput = scanner.nextLine();
        }
    }

    private void printStudentCredits(int studentId) {
        System.out.println(studentId
                + " points: Java=" + studentsService.totalCreditsPerCourse(studentId, "Java")
                + "; DSA=" + studentsService.totalCreditsPerCourse(studentId, "DSA")
                + "; Databases=" + studentsService.totalCreditsPerCourse(studentId, "Databases")
                + "; Spring=" + studentsService.totalCreditsPerCourse(studentId, "Spring")
        );
    }

    public void sentNotifications() {
        List<Enrolment> enrollmentsToBeNotified = studentsService.enrolmentsFinishedToBeAcknowledged();
        for (Enrolment enrolment : enrollmentsToBeNotified) {
            System.out.print(notificationBuilder(
                    enrolment.getStudent().fullName(),
                    enrolment.getStudent().getEmail(),
                    enrolment.getCourse().getName()));
            enrolment.setAcknowledgmentLetterSent(true);
        }

        int distinctStudents = enrollmentsToBeNotified
                .stream()
                .map(enrolment -> enrolment.getStudent())
                .distinct()
                .collect( Collectors.toList())
                .size();

        System.out.println("Total " + distinctStudents + " students have been notified");
    }

    private String notificationBuilder(String fullName, String email, String course) {
        StringBuilder notification = new StringBuilder();
        notification.append("To: " + email + "\n");
        notification.append("Re: Your Learning Progress \n");
        notification.append("Hello, " + fullName + "! You have accomplished our " + course +" course!\n");

        return notification.toString();

    }

    private boolean backCommand(String command) {
        return command.toLowerCase().equals(BACK);
    }
}
