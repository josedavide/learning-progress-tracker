package tracker.model;

import lombok.Data;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class Course {
    String code;
    String name;
    int completionPoints;
    Map<Integer, Enrolment> studentsEnrolled;

    public Course(String code, int points) {
        this.name = code;
        this.code = code;
        this.completionPoints = points;
        this.studentsEnrolled = new HashMap<>();
    }

    public Collection<Enrolment> getCollectionOfStudentsEnrolled() {
        return  studentsEnrolled.values();
    }

    public Integer countStudentsEnrolled() {
        return (Integer)studentsEnrolled.size();
    }

    public double averageAssignmentsGrades() {
        return getAllAssignedQualifications()
                .stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(Double.NaN);
    }

    public List<Integer> getAllAssignedQualifications() {
        return studentsEnrolled.values()
                .stream()
                .flatMap(enrolment -> enrolment.getQualifications().stream())
                .collect(Collectors.toList());
    }

    public Integer countTotalCompletionActivitiesByAllStudents() {
        return (Integer) studentsEnrolled.values()
                .stream()
                .reduce(0, (total, enrolment) ->
                        total + enrolment.countCompletionActivities(), Integer::sum);
    }

}
