package tracker.studentsmanager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StudentCredentialsParserTest {

    @ParameterizedTest
    @MethodSource("provideValuesForCredentialsValidation")
    @DisplayName("StudentCredentialsParser test: hasMinimumCredentialsValues")
    void hasMinimumCredentialsValues(String[] input, boolean expected) {
        assertEquals(expected, StudentCredentialsParser.hasMinimumCredentialsValues(input));
    }

    @ParameterizedTest
    @MethodSource("provideValuesForFirstNameParse")
    @DisplayName("StudentCredentialsParser test: Get First Name from Credentials Array")
    void getFirstNameFromCredentialsArray(String[] input, String expected) {
        assertEquals(expected, StudentCredentialsParser.getFirstNameFromCredentialsArray(input));
    }

    @ParameterizedTest
    @MethodSource("provideValuesForLastNameParse")
    @DisplayName("StudentCredentialsParser test: Get Last Name from Credentials Array")
    void getLastNameFromCredentialsArray(String[] input, String expected) {
        assertEquals(expected, StudentCredentialsParser.getLastNameFromCredentialsArray(input));
    }

    @ParameterizedTest
    @MethodSource("provideValuesForEmailParse")
    @DisplayName("StudentCredentialsParser test: Get Email from Credentials Array")
    void getEmailFromCredentialsArray(String[] input, String expected) {
        assertEquals(expected, StudentCredentialsParser.getEmailFromCredentialsArray(input));
    }

    @ParameterizedTest
    @MethodSource("provideValuesForFirstNameValidation")
    @DisplayName("StudentCredentialsParser test: Verify if First Name is valid")
    void firstNameIsValid(String input, boolean expected) {
        assertEquals(expected, StudentCredentialsParser.firstNameIsValid(input));
    }

    @ParameterizedTest
    @MethodSource("provideValuesForLastNameValidation")
    @DisplayName("Verify if Last Name is valid")
    void lastNameIsValid(String input, boolean expected) {
        assertEquals(expected, StudentCredentialsParser.lastNameIsValid(input));
    }

    @ParameterizedTest
    @MethodSource("provideValuesForEmailValidation")
    @DisplayName("Verify if Email is valid")
    void emailIsValid(String input, boolean expected) {
        assertEquals(expected, StudentCredentialsParser.emailIsValid(input));
    }



    private static Stream<Arguments> provideValuesForCredentialsValidation() {
        return Stream.of(
                Arguments.of(new String[]{"John", "Smith", "jsmith@hotmail.com"}, true),
                Arguments.of(new String[]{"Anny", "Doolittle", "anny.md@mail.edu"}, true),
                Arguments.of(new String[]{"Jean-Claude", "O'Connor", "jcda123@google.net"}, true),
                Arguments.of(new String[]{"Mary", "Emelianenko", "125367at@zzz90.z9"}, true),
                Arguments.of(new String[]{"Al", "Owen", "u15da125@a1s2f4f7.a1c2c5s4"}, true),
                Arguments.of(new String[]{"A-", "Doolittle", "Owen", "anny.md@mail.edu"}, true),
                Arguments.of(new String[]{".y", "Owen", "Doolittle", "Owen", "erty.md@mail.edu"}, true),
                Arguments.of(new String[]{"John", "Smith", "--", "Owen", "jsmith2@hotmail.com"}, true),
                Arguments.of(new String[]{"Anny", "anny.md2@mail.edu"}, false),
                Arguments.of(new String[]{"Jean-Claude"}, false),
                Arguments.of(new String[]{"Mary Emelianenko 125367at2@zzz90.z9"}, false),
                Arguments.of(new String[]{"Al", "Owen u15da1252@a1s2f4f7.a1c2c5s4"}, false),
                Arguments.of(new String[]{""}, false),
                Arguments.of(new String[]{"A-", "Doolittle"}, false),
                Arguments.of(new String[]{"erty.md2@mail.edu"}, false)
        );
    }

    private static Stream<Arguments> provideValuesForFirstNameParse() {
        return Stream.of(
                Arguments.of(new String[]{"John", "Smith", "jsmith@hotmail.com"}, "John"),
                Arguments.of(new String[]{"Anny", "Doolittle", "anny.md@mail.edu"}, "Anny"),
                Arguments.of(new String[]{"John's", "Smith", "jsmith2@hotmail.com"}, "John's"),
                Arguments.of(new String[]{"Jean-Claude", "O'Connor", "jcda123@google.net"}, "Jean-Claude"),
                Arguments.of(new String[]{"J.", "Smith", "mith@hotmail.com"}, "J."),
                Arguments.of(new String[]{"A-", "Doolittle", "anny.md@mail.edu"}, "A-"),
                Arguments.of(new String[]{".y", "Owen", "erty.md@mail.edu"}, ".y")
        );
    }

    private static Stream<Arguments> provideValuesForFirstNameValidation() {
        return Stream.of(
                Arguments.of("John", true),
                Arguments.of("Anny", true),
                Arguments.of("John's", true),
                Arguments.of("Jean-Claude", true),
                Arguments.of("Mary", true),
                Arguments.of("Al", true),
                Arguments.of("Anny'", false),
                Arguments.of("J.", false),
                Arguments.of("A-", false),
                Arguments.of(".y", false),
                Arguments.of("y", false),
                Arguments.of(".", false),
                Arguments.of("José", false),
                Arguments.of("Caça", false)
        );
    }

    private static Stream<Arguments> provideValuesForLastNameParse() {
        return Stream.of(
                Arguments.of(new String[]{"John", "Smith", "jsmith@hotmail.com"}, "Smith"),
                Arguments.of(new String[]{"Anny", "Doolittle", "anny.md@mail.edu"}, "Doolittle"),
                Arguments.of(new String[]{"Anny", "Doolittle", "Ann", "eras.md@mail.edu"}, "Doolittle Ann"),
                Arguments.of(new String[]{"Juan", "Sebastian", "el", "Cano", "cano.md@mail.edu"}, "Sebastian el Cano"),
                Arguments.of(new String[]{"Jean-Claude", "O'Connor", "jcda123@google.net"}, "O'Connor"),
                Arguments.of(new String[]{"Mary", "Emelianenko", "125367at@zzz90.z9"}, "Emelianenko"),
                Arguments.of(new String[]{"Anny", "Doolittle-2", "anny.md6@mail.edu"}, "Doolittle-2"),
                Arguments.of(new String[]{"Al", "Owen", "O'Connor", "u15da125@a1s2f4f7.a1c2c5s4"}, "Owen O'Connor"),
                Arguments.of(new String[]{"Al", "Owen", "O'Connor", "Campos", "u15da1255@a1s2f4f7.a1c2c5s4"}, "Owen O'Connor Campos"),
                Arguments.of(new String[]{"Juan", "Seb'ast'ia-n", "e-l", "Ca'n-o", "cano2.md@mail.edu"}, "Seb'ast'ia-n e-l Ca'n-o"),
                Arguments.of(new String[]{"Al", "Owen", "O'Con--nor", "u15da1255@a1s2f4f7.a1c2c5s4"}, "Owen O'Con--nor"),
                Arguments.of(new String[]{"Anny", "Doolittle-", "anny.md2@mail.edu"}, "Doolittle-"),
                Arguments.of(new String[]{"Anny", "Doolittle-'", "anny.md3@mail.edu"}, "Doolittle-'"),
                Arguments.of(new String[]{"Anny", "-", "4", "anny.md4@mail.edu"}, "- 4")
        );
    }

    private static Stream<Arguments> provideValuesForLastNameValidation() {
        return Stream.of(
                Arguments.of("Smith", true),
                Arguments.of("Doolittle", true),
                Arguments.of("Doolittle Ann", true),
                Arguments.of("Sebastian el Cano", true),
                Arguments.of("O'Connor", true),
                Arguments.of("Emelianenko", true),
                Arguments.of("Owen O'Connor", true),
                Arguments.of("Owen O'Connor Campos", true),
                Arguments.of("Seb'ast'ia-n e-l Ca'n-o", true),
                Arguments.of("Doolittle-2", false),
                Arguments.of("Owen O'Con--nor", false),
                Arguments.of("Doolittle-", false),
                Arguments.of("Doolittle-'", false),
                Arguments.of("- 4", false)
        );
    }

    private static Stream<Arguments> provideValuesForEmailParse() {
        return Stream.of(
                Arguments.of(new String[]{"John", "Smith", "jsmith@hotmail.com"}, "jsmith@hotmail.com"),
                Arguments.of(new String[]{"Anny", "Doolittle", "anny.md@mail.edu"}, "anny.md@mail.edu"),
                Arguments.of(new String[]{"Jean-Claude", "O'Connor", "jcda123@google.net"}, "jcda123@google.net"),
                Arguments.of(new String[]{"Mary", "Emelianenko", "125367at@zzz90.z9"}, "125367at@zzz90.z9"),
                Arguments.of(new String[]{"Al", "Owen", "O'Connor", "u15da125@a1s2f4f7.a1c2c5s4"}, "u15da125@a1s2f4f7.a1c2c5s4"),
                Arguments.of(new String[]{"Al", "Owen", "O'Connor", "Campos", "u15da1255@a1s2f4f7."}, "u15da1255@a1s2f4f7."),
                Arguments.of(new String[]{"Al", "Owen", "O'Connor", "Campos", "name@company"}, "name@company"),
                Arguments.of(new String[]{"Al", "Owen", "O'Connor", "Campos", "nameATcompany.coom"}, "nameATcompany.coom")
        );
    }

    private static Stream<Arguments> provideValuesForEmailValidation() {
        return Stream.of(
                Arguments.of("jsmith@hotmail.com", true),
                Arguments.of("anny.md@mail.edu", true),
                Arguments.of("jcda123@google.net", true),
                Arguments.of("125367at@zzz90.z9", true),
                Arguments.of("u15da125@a1s2f4f7.a1c2c5s4", true),
                Arguments.of("u15da1255@a1s2f4f7.", false),
                Arguments.of("name@company", false),
                Arguments.of("nameATcompany.coom", false),
                Arguments.of("@company.com", false),
                Arguments.of("@company", false),
                Arguments.of("@", false),
                Arguments.of("name@.com", false)
        );
    }

}