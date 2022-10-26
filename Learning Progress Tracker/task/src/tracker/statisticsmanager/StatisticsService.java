package tracker.statisticsmanager;

import tracker.LearningPlatformRepository;
import tracker.model.Course;
import tracker.model.Enrolment;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticsService {

    LearningPlatformRepository learningPlatformRepository;


    public Comparator<Course> coursePopularityComparator =
            Comparator.comparing(Course::countStudentsEnrolled);
    public Comparator<Course> courseActivityComparator =
            Comparator.comparing(Course::countTotalCompletionActivitiesByAllStudents);

    public Comparator<Course> courseDifficultyComparator =
            Comparator.comparing(Course::averageAssignmentsGrades);

    StatisticsService(LearningPlatformRepository learningRepository) {
        learningPlatformRepository = learningRepository;
    }

    public Course findCourse(String codeCourse) {

        Course course = learningPlatformRepository.getCourse(codeCourse);

        return course;
    }

    public Collection<Enrolment> getEnrolmentsByCourse(String courseCode) {
        return learningPlatformRepository
                .getCourse(courseCode).getCollectionOfStudentsEnrolled();
    }
    public Collection<Enrolment> getEnrolmentsByCourseSorted(String courseCode) {
        return getEnrolmentsByCourse(courseCode)
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Course> mostPopularCourse() {
        List<Course> ret = null;

        if (learningPlatformRepository.isAnyStudentEnrolled()) {
            ret = selectMaxCoursesByComparator(
                    learningPlatformRepository.getCourses(),
                    coursePopularityComparator);
        }
        return ret;
    }
    public List<Course> lessPopularCourse() {
        List<Course> ret = null;

        if (learningPlatformRepository.isAnyStudentEnrolled()) {
            ret = selectMaxCoursesByComparator(
                    learningPlatformRepository.getCourses(),
                    coursePopularityComparator.reversed());
            ret.removeAll(mostPopularCourse());
        }

        return ret;
    }

    public List<Course> mostActiveCourse() {
        List<Course> ret = null;

        if (learningPlatformRepository.isAnyStudentEnrolled()) {
            ret = selectMaxCoursesByComparator(
                    learningPlatformRepository.getCourses(),
                    courseActivityComparator);
        }

        return ret;
    }

    public List<Course> lessActiveCourse() {
        List<Course> ret = null;

        if (learningPlatformRepository.isAnyStudentEnrolled()) {
            ret = selectMaxCoursesByComparator(
                    learningPlatformRepository.getCourses(),
                    courseActivityComparator.reversed());
            ret.removeAll(mostActiveCourse());
        }

        return ret;
    }

    public List<Course> lessDifficultCourse() {
        List<Course> ret = null;

        if (learningPlatformRepository.isAnyStudentEnrolled()) {
            ret = selectMaxCoursesByComparator(
                    learningPlatformRepository.getCourses(),
                    courseDifficultyComparator.reversed());
        }

        return ret;
    }
    public List<Course> mostDifficultCourse() {

        List<Course> ret = null;

        if (learningPlatformRepository.isAnyStudentEnrolled()) {
            ret = selectMaxCoursesByComparator(
                    learningPlatformRepository.getCourses(),
                    courseDifficultyComparator.reversed());
            //ret.removeAll(lessDifficultCourse());
        }
        return ret;
    }


    private List<Course> selectMaxCoursesByComparator (Collection<Course> courses, Comparator<Course> comparator) {
        Course maxCourse = courses
                .stream()
                .max(comparator)
                .orElse(null);

        List<Course> ret = maxCourse == null ? null
                : courses
                .stream()
                .filter(course -> course.countStudentsEnrolled() == maxCourse.countStudentsEnrolled())
                .collect(Collectors.toList());
        return ret;
    }
}
