package tracker;

import tracker.model.Course;
import tracker.model.Enrolment;
import tracker.model.Student;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class LearningPlatformRepository {

    private static final int BASE_STUDENT_ID_COUNTER = 1000;

    public static final String COURSE_JAVA = "Java";
    public static final String COURSE_DSA = "DSA";
    public static final String COURSE_DATABASES = "Databases";
    public static final String COURSE_SPRING = "Spring";

    private final LinkedHashMap<String, Student> studentsByEmail;
    private final LinkedHashMap<Integer, Student> studentsById;
    private final Map<String, Course> courses;

    public LearningPlatformRepository() {
        studentsByEmail = new LinkedHashMap<>();
        studentsById = new LinkedHashMap<>();

        courses = new LinkedHashMap<>();
        courses.put(COURSE_JAVA, new Course(COURSE_JAVA, 600));
        courses.put(COURSE_DSA, new Course(COURSE_DSA, 400));
        courses.put(COURSE_DATABASES, new Course(COURSE_DATABASES, 480));
        courses.put(COURSE_SPRING, new Course(COURSE_SPRING, 550));
    }

    public LinkedHashMap<String, Student> getStudentsByEmail() {
        return studentsByEmail;
    }

    public LinkedHashMap<Integer, Student> getStudentsById() {
        return studentsById;
    }

    public Student getStudentById(Integer studentId) {
        return studentsById.get(studentId);
    }

    public Collection<Course> getCourses() {
        return courses.values();
    }

    public Course getCourse(String course) {
        return courses.get(course);
    }

    public Collection<Enrolment> listCourseStudents(String course) {

        return getCourse(course).getCollectionOfStudentsEnrolled();
    }


    public Student buildNewStudent(String firstName, String lastName, String email) {
        int studentId = BASE_STUDENT_ID_COUNTER + studentsByEmail.size() + 1;
        Student newStudent = new Student(studentId, firstName, lastName, email);

        return newStudent;
    }

    public Student addNewStudent(String firstName, String lastName, String email) {
        Student newStudent = null;

        if (!isStudentEmailRegistered(email)) {
            newStudent = buildNewStudent(firstName, lastName, email);
            studentsByEmail.put(email, newStudent);
            studentsById.put(newStudent.getStudentId(), newStudent);
        }
        return newStudent;
    }

    public Student registerNewActivityCredits(int studentId, Map<String, Integer> courseCredits) {
        Student student = studentsById.get(studentId);
        if (student != null) {
            for (String course: courseCredits.keySet()) {
                addStudentCourseQualification(student, courses.get(course), courseCredits.get(course));
            }
        }
        return student;
    }

    public boolean isStudentEmailRegistered(String email) {
        return (studentsByEmail.get(email) != null);
    }

    public boolean isStudentIdRegistered(Integer id) {
        return (studentsById.get(id) != null);
    }

    public int getAchievedStudentCourseCredits(Integer studentId, String courseCode) {
        Enrolment enrolment = getCourseEnrolmentByStudent(studentId, courseCode);
        if (enrolment == null) {
            return 0;
        } else {
            return enrolment.getTotalAchievedCredits();
        }
    }


    public Enrolment getCourseEnrolmentByStudent(Integer studentId, String courseCode) {
        return getStudentById(studentId).getEnrolledCourses().get(courseCode);
    }

    public boolean isAnyStudentEnrolled() {
        return studentsById.values().stream().anyMatch(student -> student.getEnrolledCourses().size() > 0);
    }

    public List<Enrolment> enrolmentsFinishedToBeAcknowledged() {
        return studentsById.values()
                .stream()
                .flatMap(student -> student.getEnrolledCourses().values().stream())
                .filter(enrollment -> enrollment.isCompletedAndNotAcknowledged())
                .toList();
    }

    private void addStudentCourseQualification(Student student, Course course, Integer qualification) {
        Enrolment enrolment = course.getStudentsEnrolled().get(student.getStudentId());
        if (enrolment == null){
            enrolStudentToCourseWithQualification(student, course, qualification);
        } else {
            enrolment.getQualifications().add(qualification);
        }
    }

    private void enrolStudentToCourseWithQualification(Student student, Course course, Integer qualification) {
        Enrolment enrollment = new Enrolment(student, course, qualification);
        course.getStudentsEnrolled().put(student.getStudentId(), enrollment);
        student.getEnrolledCourses().put(course.getCode(), enrollment);
    }



}
