package com.epam.factory;

import com.epam.entity.Person;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ResourceBundle;

/**
 * Created by Sergey_Stefoglo on 7/21/2016.
 */
public class PersonFileAction implements PersonAbstractAction {
    private String filePath;
    private ResourceBundle labels = ResourceBundle.getBundle("config");
    public final static String UTF="UTF-8";

    PersonFileAction(String filePath) {
        this.filePath = labels.getString("pathForFolder") + filePath;
        createFile(filePath);

    }
    private void createFile(String filePath) {
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void writePerson(Person person) {
        try {
            try (
                   PrintStream out = new PrintStream(
                            new BufferedOutputStream(
                                    new FileOutputStream(filePath, true)))) {
                if (readPerson(person.getName()) == null) {
                    out.append(person.getName().toUpperCase() + DELIMETER + person.getAge() + "\n");
                    System.out.println(USER_NAME + person.getName().toUpperCase());
                } else {
                    int i = 1;
                    while (readPerson(person.getName() + i) != null) {
                        i++;
                    }
                    out.append(person.getName().toUpperCase() + i + DELIMETER + person.getAge() + "\n");
                    System.out.println(USER_NAME + person.getName().toUpperCase() + i);

                }
                System.out.println(SAVE_USER);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Person readPerson() {
        Person person = null;
        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                        new FileInputStream(filePath), Charset.forName(UTF)))) {
            String line = reader.readLine();
            person = getPerson(line);
        } catch (IOException e) {
            e.getStackTrace();
        }
        return person;
    }

    public Person readPerson(String name) {
        Person person = null;
        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                        new FileInputStream(filePath), Charset.forName(UTF))))
        {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.substring(0, line.indexOf(DELIMETER)).equals(name.toUpperCase())) {
                    person = getPerson(line);
                    break;
                }
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return person;

    }

    private Person getPerson(String line) {
        return new Person(line.substring(0, line.indexOf(DELIMETER)),
                Integer.valueOf(line.substring(line.indexOf(DELIMETER) + 1)));
    }

}
