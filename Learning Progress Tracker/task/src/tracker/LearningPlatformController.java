package tracker;

import tracker.model.Enrolment;
import tracker.studentsmanager.StudentsController;
import tracker.statisticsmanager.StatisticsController;

import java.util.Scanner;

public class LearningPlatformController {

    private static final String ADD_STUDENTS = "add students";
    private static final String LIST = "list";
    private static final String ADD_POINTS = "add points";
    private static final String FIND = "find";
    private static final String STATISTICS = "statistics";
    private static final String NOTIFY = "notify";

    private static final String BACK = "back";
    private static final String EXIT = "exit";
    private static final String VOID = "";

    private StudentsController studentsController;
    private StatisticsController statisticsController;

    public LearningPlatformController(LearningPlatformRepository learningRepository) {

        studentsController = new StudentsController(learningRepository);
        statisticsController = new StatisticsController(learningRepository);
    }

    public void start() {
        System.out.println("Learning Progress Tracker");
        Scanner scanner = new Scanner(System.in);
        processMainMenu(scanner);
    }

    private void processMainMenu(Scanner scanner) {
        String command = "";
        while (!command.equals(EXIT)) {
            command = scanner.nextLine().strip().toLowerCase();

            switch (command) {
                case ADD_STUDENTS:
                    studentsController.addStudentsControl(scanner);
                    break;
                case LIST:
                    studentsController.listStudents();
                    command = "";
                    break;
                case ADD_POINTS:
                    studentsController.addStudentCredits(scanner);
                    break;
                case FIND:
                    studentsController.findStudent(scanner);
                    break;
                case STATISTICS:
                    statisticsController.statisticsControl(scanner);
                    break;
                case NOTIFY:
                    studentsController.sentNotifications();
                    break;
                case BACK:
                    System.out.println("Enter 'exit' to exit the program");
                    break;
                case EXIT:
                    System.out.println("Bye!");
                    break;
                case VOID:
                    System.out.println("No input");
                    break;
                default:
                    System.out.println("Unknown command!");
                    break;
            }
        }
    }


}
