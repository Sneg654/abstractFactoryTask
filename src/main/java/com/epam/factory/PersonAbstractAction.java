package com.epam.factory;

import com.epam.entity.Person;

/**
 * Created by Sergey_Stefoglo on 7/21/2016.
 */
public interface PersonAbstractAction {
    String DELIMETER=",";
    String USER_NAME="Name for user ";
    String SAVE_USER="User was save";

    void writePerson (Person person);
    Person readPerson();
    Person readPerson (String name);
}
