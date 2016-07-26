package com.epam.factory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sergey_Stefoglo on 7/26/2016.
 */
public class PersonAbstractFactory {
    public static final String INCORECT_MODE = "Incorrect mode, please repeat";
    public static final String INCORECT_FILE_NAME = "Incorrect name for file, please repeat";
    public static final String MODE_DB = "1";
    public static final String MODE_FILE = "2";
    public static final String REGEX_FORMAT_FILE = "\\w{0,}.txt";
    private Pattern pattern = Pattern.compile(REGEX_FORMAT_FILE);

    public PersonAbstractAction getPersonAction(String typeOfAction, String fileName) {
        PersonAbstractAction personAction = null;
        if (typeOfAction.equals(MODE_DB)) {
            personAction = new PersonDBAction();
        } else if (typeOfAction.equals(MODE_FILE)) {
            if (fileName != null) {
                Matcher matcher = pattern.matcher(fileName);
                if (matcher.matches()) {
                    personAction = new PersonFileAction(fileName);
                } else {
                    System.out.println(INCORECT_FILE_NAME);
                }
            } else {
                System.out.println(INCORECT_FILE_NAME);
            }

        } else {
            System.out.println(INCORECT_MODE);
        }
        return personAction;
    }

}
