package tracker.model;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

@Data
public class Enrolment implements Comparable<Enrolment> {
    private Student student;
    private Course course;
    private List<Integer> qualifications;
    private boolean acknowledgmentLetterSent;

    public Enrolment(Student student, Course course, Integer qualification) {
        this.student = student;
        this.course = course;
        this.qualifications = new ArrayList<Integer>();
        this.qualifications.add(qualification);
        this.acknowledgmentLetterSent = false;
    }

    public int countCompletionActivities() {
        return qualifications.size();
    }

    public int getTotalAchievedCredits() {
        int ret = qualifications.stream().reduce(0, Integer::sum);
        return ret;
    }

    public Double completedPercentage() {
        return ((double)getTotalAchievedCredits() / (double)course.getCompletionPoints()) * 100;
    }

    public boolean isCompletedAndNotAcknowledged() {
        return completedPercentage() >= 100 && !isAcknowledgmentLetterSent();
    }

    @Override
    public int compareTo(Enrolment o) {
        if (completedPercentage() != o.completedPercentage()) {
            // completion in descending order
            return o.completedPercentage().compareTo(completedPercentage());
        } else {
            //studentId in ascending order
            return student.getStudentId() < o.getStudent().getStudentId() ? -1 : 1;
        }

    }
}
