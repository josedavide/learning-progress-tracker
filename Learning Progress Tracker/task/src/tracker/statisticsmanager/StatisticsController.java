package tracker.statisticsmanager;

import tracker.LearningPlatformRepository;
import tracker.model.Course;
import tracker.model.Enrolment;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class StatisticsController {

    private static final String BACK = "back";

    StatisticsService statisticsService;

    public StatisticsController(LearningPlatformRepository learningRepository) {
        this.statisticsService = new StatisticsService(learningRepository);
    }

    public void statisticsControl(Scanner scanner) {
        System.out.println("Type the name of a course to see details or 'back' to quit:");
        System.out.println("Most popular: " + courseListToString(statisticsService.mostPopularCourse()));
        System.out.println("Least popular: " + courseListToString(statisticsService.lessPopularCourse()));
        System.out.println("Highest activity: " + courseListToString(statisticsService.mostActiveCourse()));
        System.out.println("Lowest activity: " + courseListToString(statisticsService.lessActiveCourse()));
        System.out.println("Easiest course: " + courseListToString(statisticsService.lessDifficultCourse()));
        System.out.println("Hardest course: " + courseListToString(statisticsService.mostDifficultCourse()));

        String userInput = scanner.nextLine();

        while (!backCommand(userInput)) {
            courseStatisticsDetails(userInput);
            userInput = scanner.nextLine();
        }

    }

    private String courseListToString(List<Course> courses) {
        String ret = "";
        if (courses == null || courses.size() == 0) {
            ret += "n/a";
        } else {
            for (int i = 0; i < (courses.size() - 1); i++) {
                ret += (courses.get(i).getName() + ", ");
            }
            ret += (courses.get(courses.size() - 1).getName());
        }
        return ret;
    }

    private void courseStatisticsDetails(String userInput) {
        Course course = statisticsService.findCourse(userInput);
        if (course == null) {
            System.out.println("Unknown course");
        } else {
            showOrderedByCompletionEnrolledStudents(course);
        }

    }

    private void showOrderedByCompletionEnrolledStudents(Course course) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

        System.out.println("Course name: " + course.getName());
        System.out.println("id \t points \t completed");
        for (Enrolment enrolment :
                statisticsService.getEnrolmentsByCourseSorted(course.getCode())) {
            System.out.print(enrolment.getStudent().getStudentId() + "\t");
            System.out.print(enrolment.getTotalAchievedCredits() + "\t");
            System.out.println(decimalFormat.format(enrolment.completedPercentage()) + "%");
        }
    }

    private boolean backCommand(String command) {
        return command.equalsIgnoreCase(BACK);
    }
}
