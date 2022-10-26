package tracker.studentsmanager;

import tracker.LearningPlatformRepository;
import tracker.model.Enrolment;
import tracker.model.Student;

import java.util.*;

import static tracker.studentsmanager.StudentCredentialsParser.*;

public class StudentsService {

    private static final int STUDENT_ID_AND_CREDITS_VALUES = 5;
    private static final String REGEXP_NATURAL_NUMBER = "\\d+";
    LearningPlatformRepository learningPlatformRepository;

    StudentsService(LearningPlatformRepository learningRepository) {
        learningPlatformRepository = learningRepository;
    }

    List<Integer> getStudentsIds() {

        LinkedHashMap<Integer, Student> studentsById = learningPlatformRepository.getStudentsById();

        return studentsById.keySet().stream().toList();
    }

    public int numberOfRegisteredStudents() {
        return getStudentsIds().size();
    }

    public void addNewStudentFromStudentCredentials(String[] studentCredentials) {
        if (!hasMinimumCredentialsValues(studentCredentials)) {
            System.out.println("Incorrect credentials");
        } else {
            String firstName = getFirstNameFromCredentialsArray(studentCredentials);
            String lastName = getLastNameFromCredentialsArray(studentCredentials);
            String email = getEmailFromCredentialsArray(studentCredentials);

            if (!firstNameIsValid(firstName)) {
                System.out.println("Incorrect first name");
            } else if (!lastNameIsValid(lastName)) {
                System.out.println("Incorrect last name");
            } else if (!emailIsValid(email)) {
                System.out.println("Incorrect email");
            } else {
                addNewStudent(firstName, lastName, email);
            }
        }
    }

    public Student addNewStudent(String firstName, String lastName, String email) {

        Student student = null;

        if (learningPlatformRepository.isStudentEmailRegistered(email)) {
            System.out.println("This email is already taken.");
        } else {
            student = learningPlatformRepository.addNewStudent(firstName, lastName, email);
            System.out.println("The student has been added.");
        }

        return student;
    }

    public void addStudentCredits(String[] studentCredits) {
        if (studentCredits.length != STUDENT_ID_AND_CREDITS_VALUES) {
            System.out.println("Incorrect points format");
        } else if (!isStudentRegistered(studentCredits[0])) {
            System.out.println("No student is found for id=" + studentCredits[0]);
        } else if (!isNaturalNumber(studentCredits[1]) || !isNaturalNumber(studentCredits[2])
                || !isNaturalNumber(studentCredits[3]) || !isNaturalNumber(studentCredits[4])) {
            System.out.println("Incorrect points format");
        } else {
            Integer studentId = Integer.valueOf(studentCredits[0]);
            Map<String, Integer> creditsByCourse =
                mapStudentCreditsValuesToCoursesHashMap(
                    Integer.valueOf(studentCredits[1]),
                    Integer.valueOf(studentCredits[2]),
                    Integer.valueOf(studentCredits[3]),
                    Integer.valueOf(studentCredits[4])
                );
            learningPlatformRepository.registerNewActivityCredits(studentId, creditsByCourse);
            System.out.println("Points updated");
        }
    }

    public Student findStudent(String idStudent) {
        return learningPlatformRepository.getStudentById(Integer.valueOf(idStudent));
    }

    public boolean isStudentRegistered(String idStudent) {
        return (isNaturalNumber(idStudent)
                && learningPlatformRepository.isStudentIdRegistered(Integer.valueOf(idStudent)));
    }

    public List<Enrolment> enrolmentsFinishedToBeAcknowledged() {
        return learningPlatformRepository.enrolmentsFinishedToBeAcknowledged();
    }

    private boolean isNaturalNumber(String strNum) {
        if (strNum == null) {
            return false;
        }
        return strNum.matches(REGEXP_NATURAL_NUMBER);
    }

    public int totalCreditsPerCourse(Integer idStudent, String strCourse) {
        return learningPlatformRepository.getAchievedStudentCourseCredits(idStudent, strCourse);
    }

    private Map<String, Integer> mapStudentCreditsValuesToCoursesHashMap(
            Integer JavaValue, Integer DSAValue, Integer DatabasesValue, Integer SpringValue) {
        Map<String, Integer> map = new HashMap<>();
        if (JavaValue != 0) {
            map.put(learningPlatformRepository.COURSE_JAVA, JavaValue);
        }
        if (DSAValue != 0) {
            map.put(learningPlatformRepository.COURSE_DSA, DSAValue);
        }
        if (DatabasesValue != 0) {
            map.put(learningPlatformRepository.COURSE_DATABASES, DatabasesValue);
        }
        if (SpringValue != 0) {
            map.put(learningPlatformRepository.COURSE_SPRING, SpringValue);
        }

        return map;
    }


}
