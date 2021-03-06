package com.epam;

import com.epam.entity.Person;
import com.epam.factory.PersonAbstractAction;
import com.epam.factory.PersonAbstractFactory;
import com.epam.factory.PersonDBAction;
import com.epam.factory.PersonFileAction;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sergey_Stefoglo on 7/21/2016.
 */
public class Run {
    public static final String START_MESSAGE = "Please, select the mode(Enter 1-for DB mode, 2,name.txt -for file, where name.txt - name for file):";
    public static final String REGEX_FORMAT_NAME = "[A-Z,a-z,-]{1,}_([A-Z,a-z,-]{1,}|\\.)";
    public static final String REGEX_FORMAT_AGE = "[0-9]{1,3}";


    public static final String INCORECT_NAME = "Incorrect name user, please repeat";
    public static final String INCORECT_AGE = "Incorrect age user, please repeat";

    public static final String RUN_COMMAND_MESSAGE = "Please, write command (Exampe: 1) write,FirstName_LastName,21 -for add new Person;\n" +
            "2) read -for found some random person;\n" +
            "3) readname,FirstName_LastName -for search person with name FirstName_LastName;" +
            "4) exit - for exit):";
    public static final String  ARRAY_INDEX_OUT ="incorrect number of parameters, please try again";
    public static final String INCORRECT_COMMAND="Incorrect command, please repeat";
    public static final String NOT_FOUND="Person not found";
    public static final String PERSON_NAME="Person name is ";
    public static final String PERSON_AGE=", person age is ";
    public static final String WRITE_COMMAND="write";
    public static final String READ_COMMAND="read";
    public static final String READ_NAME_COMMAND="readname";
    public static final String EXIT_COMMAND="exit";
    public static final String INCORRECT_DELIMETER=" ";
    public static final String REPLACE_INCORRECT_DELIMETER="";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PersonAbstractAction action=null;
        System.out.println(START_MESSAGE);
     try{
        while (sc.hasNextLine()) {
            PersonAbstractFactory personAbstractFactory = new PersonAbstractFactory();

            Pattern namePattern = Pattern.compile(REGEX_FORMAT_NAME);
            Pattern agePattern = Pattern.compile(REGEX_FORMAT_AGE);
            Object[] values = sc.nextLine().replaceAll(INCORRECT_DELIMETER,REPLACE_INCORRECT_DELIMETER).split(PersonAbstractAction.DELIMETER);
            String typeOfAction=String.valueOf(values[0]).trim();
            String nameFile=null;
            if(values.length>1){
                nameFile=String.valueOf(values[1]).trim();
            }
            action = personAbstractFactory.getPersonAction(typeOfAction,
                        nameFile);

            if (action != null) {
                System.out.println(RUN_COMMAND_MESSAGE);

                while (sc.hasNextLine()) {
                    values =sc.nextLine().replaceAll(INCORRECT_DELIMETER,REPLACE_INCORRECT_DELIMETER).split(PersonAbstractAction.DELIMETER);
                    Person person = null;
                    if (values.length == 0) {
                        System.out.println(ARRAY_INDEX_OUT);
                    } else if (values.length == 1) {
                        if (values[0].equals(READ_COMMAND)) {
                            person = action.readPerson();
                            personInfo(person);
                        } else if (values[0].equals(EXIT_COMMAND)) {
                            System.out.println(START_MESSAGE);
                            break;
                        } else {
                            System.out.println(INCORRECT_COMMAND);
                            continue;
                        }
                    } else if (values.length == 2) {
                        if (values[0].equals(READ_NAME_COMMAND)) {
                            person = action.readPerson(values[1].toString());
                            personInfo(person);
                        }

                    } else if (values.length == 3) {
                        if (values[0].equals(WRITE_COMMAND)) {
                            Matcher nameMatcher = namePattern.matcher(values[1].toString());
                            Matcher ageMatcher = agePattern.matcher(values[2].toString());
                            if (!nameMatcher.matches()) {
                                System.out.println(INCORECT_NAME);
                            } else if (!ageMatcher.matches()) {
                                System.out.println(INCORECT_AGE);
                            } else {
                                action.writePerson(new Person(values[1].toString(),
                                        Integer.valueOf(values[2].toString())));
                            }
                        } else {
                            System.out.println(INCORRECT_COMMAND);
                        }
                    } else {
                        System.out.println(ARRAY_INDEX_OUT);
                    }
                    System.out.println(RUN_COMMAND_MESSAGE);
                }

            }
        }
    }catch (ArrayIndexOutOfBoundsException e){
         System.out.println(ARRAY_INDEX_OUT);
     }
    }

    private static void personInfo(Person person){
        if(person!=null){
            System.out.println(PERSON_NAME + person.getName() + PERSON_AGE + person.getAge());}
        else{
            System.out.println(NOT_FOUND);
        }
    }

}


