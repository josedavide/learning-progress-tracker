package tracker.studentsmanager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tracker.LearningPlatformRepository;
import tracker.model.Student;

import static org.junit.jupiter.api.Assertions.*;

class LearningPlatfromRepositoryTest {

    @Test
    void getStudentsByEmail() {
    }

    @Test
    void getStudentsById() {
    }

    @Test
    @DisplayName("Build second student then it has Id 2")
    void buildSecondStudentItIncreasesIdBy1() {
        // Given
        LearningPlatformRepository learningPlatformRepository = new LearningPlatformRepository();
        Student firstStudent = learningPlatformRepository
                .addNewStudent("My", "First", "student@uni.com");

        // When
        Student secondStudent = learningPlatformRepository
                .buildNewStudent("My", "Second", "student2@uni.com");

        // Then
        assertTrue(secondStudent.getStudentId() == firstStudent.getStudentId() + 1);
    }

    @Test
    @DisplayName("Add student with free email")
    void addStudentWithFreeEmail() {
        // Given
        LearningPlatformRepository learningPlatformRepository = new LearningPlatformRepository();
        Student expectedStudent = learningPlatformRepository
                .addNewStudent("My", "First", "student@uni.com");

        // When

        // Then

        assertEquals(expectedStudent,
                learningPlatformRepository.getStudentsByEmail().get("student@uni.com"));
    }

    @Test
    @DisplayName("Add student with registered email")
    void addStudentWithRegisteredEmail() {
        // Given
        LearningPlatformRepository learningPlatformRepository = new LearningPlatformRepository();
        Student expectedStudent = learningPlatformRepository
                .addNewStudent("My", "First", "student@uni.com");
        // When
        Student repeatedStudent = learningPlatformRepository
                .addNewStudent("My", "First", "student@uni.com");
        // Then
        assertEquals(null, repeatedStudent);
    }

    @Test
    @DisplayName("Check if email is registered for added student")
    void checkIfEmailIsRegisteredForAddedStudent() {
        // Given
        LearningPlatformRepository learningPlatformRepository = new LearningPlatformRepository();
        Student expectedStudent = learningPlatformRepository
                .addNewStudent("My", "First", "student@uni.com");

        // Then
        assertTrue(learningPlatformRepository.isStudentEmailRegistered("student@uni.com"));

    }

    @Test
    @DisplayName("Check if email is registered for added student")
    void checkIfEmailIsRegisteredForNonAddedStudent() {
        // Given
        LearningPlatformRepository learningPlatformRepository = new LearningPlatformRepository();

        // Then
        assertFalse(learningPlatformRepository.isStudentEmailRegistered("student@uni.com"));

    }
}