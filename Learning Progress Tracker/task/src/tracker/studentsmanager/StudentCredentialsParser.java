package tracker.studentsmanager;

import java.util.Arrays;

public class StudentCredentialsParser {

    private static final int MIN_USER_CREDENTIALS_VALUES = 3;
    private static final String REGEXP_NAME = "[a-zA-Z]{2,}|([a-zA-Z]+(('[a-zA-Z]+)|(-[a-zA-Z]+))+)";
    private static final String REGEXP_LASTNAME = "(([a-zA-Z]{2,}|([a-zA-Z]+(('[a-zA-Z]+)|(-[a-zA-Z]+))+))\\s*)*";
    private static final String REGEXP_EMAIL = "^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)+";
    //private static final String REGEXP_EMAIL = "^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean hasMinimumCredentialsValues(String[] inputArray) {
        return inputArray.length >= MIN_USER_CREDENTIALS_VALUES;
    }

    public static String getFirstNameFromCredentialsArray(String[] inputArray) {
        return inputArray.length > 0 ? inputArray[0] : "";
    }

    public static String getLastNameFromCredentialsArray(String[] inputArray) {

        String lastName = inputArray.length >= MIN_USER_CREDENTIALS_VALUES - 1 ?
                String.join(" ", Arrays.copyOfRange(inputArray, 1, inputArray.length - 1))
                : "";
        return lastName;
    }

    public static  String getEmailFromCredentialsArray(String[] inputArray) {
        return inputArray.length >= MIN_USER_CREDENTIALS_VALUES ? inputArray[inputArray.length - 1] : "";
    }

    public static  boolean firstNameIsValid (String firstName) {
        return firstName.matches(REGEXP_NAME);
    }

    public static  boolean lastNameIsValid (String lastName) {
        return lastName.matches(REGEXP_LASTNAME);
    }

    public static  boolean emailIsValid (String email) {
        return email.matches(REGEXP_EMAIL);
    }

}
