package com.epam.factory;

import com.epam.entity.Person;
import com.epam.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Sergey_Stefoglo on 7/21/2016.
 */
public class PersonDBFactory implements PersonAbstractFactory {

    private static final String INSERT_USER = "INSERT INTO factory_user VALUES (?,?)";
    private static final String RANDOM_USER = "select user_name, age from factory_user where rownum=1";
    private static final String USER_BY_NAME = "select user_name, age from factory_user where user_name=?";
    public static final String USER_NAME_DB = "USER_NAME";
    public static final String USER_AGE_DB = "AGE";
    public final ConnectionPool pool=ConnectionPool.getInstance();



    public void writePerson(Person person) {

        try (Connection conn = pool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_USER)
        ) {
             if(readPerson(person.getName())==null) {
                 stmt.setString(1, person.getName().toUpperCase());
                System.out.println(USER_NAME +person.getName());
             }else{
                 int i=1;
                 while (readPerson(person.getName()+i)!=null){
                     i++;
                 }
                 stmt.setString(1,person.getName().toUpperCase()+i);
                 System.out.println(USER_NAME +person.getName()+i);
            }
                 stmt.setInt(2, person.getAge());
                 stmt.executeQuery();
                 System.out.println(SAVE_USER);

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    public Person readPerson() {
        Person person = null;
        try (Connection conn = pool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(RANDOM_USER);
             ResultSet resultSet = stmt.executeQuery()
        ) {
            person = getPerson(resultSet);

        } catch (Exception e) {
            System.out.println(e.getStackTrace());

        }
        return person;
    }

    public Person readPerson(String name) {
        Person person = null;
        try (Connection conn = pool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(USER_BY_NAME)

        ) {
            stmt.setString(1, name.toUpperCase());
            try (ResultSet resultSet = stmt.executeQuery()) {

                person = getPerson(resultSet);

            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }

        } catch (Exception e) {
            System.out.println(e.getStackTrace());

        }
        return person;
    }

    private Person getPerson(ResultSet resultSet) throws Exception {
        while (resultSet.next()) {
            String user = resultSet.getString(USER_NAME_DB);
            if (user != null) {
                return new Person(user, resultSet.getInt(USER_AGE_DB));
            }
        }
        return null;
    }
 }
