package tracker.model;


import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
public class Student implements Comparable<Student> {

    public Student (int studentId, String firstName, String lastName, String email) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.enrolledCourses = new HashMap<>();
    }

    private Integer studentId;
    private String firstName;
    private String lastName;
    private String email;
    private Map<String, Enrolment> enrolledCourses;

    public String fullName() {
        return firstName + " " + lastName;
    }

    @Override
    public int compareTo(Student anotherStudent) {
        return studentId.compareTo(anotherStudent.getStudentId());
    }


    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Student other = (Student) obj;
        return Objects.equals(studentId, other.studentId);
    }

}
