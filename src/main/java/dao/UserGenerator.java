package dao;

import models.User;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;

/**
 * A temporary class that generates and returns {@link User} objects. This class may be removed when
 * the server is created and the ServerFacade no longer needs to return dummy data.
 */
public class UserGenerator {

    private static final String MALE_NAMES_URL = "mnames.txt";
    private static final String FEMALE_NAMES_URL = "fnames.txt";
    private static final String SURNAMES_URL = "sname.txt";

//    private static final String [] maleNames;
//    private static final String [] femaleNames;
//    private static final String [] surnames;

    static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private static UserGenerator instance;

    /**
     * A private constructor that ensures no instances of this class can be created.
     */
    private UserGenerator() {}

    /**
     * Returns the singleton instance of the class
     *
     * @return the instance.
     */
    public static UserGenerator getInstance() {
        if(instance == null) {
            instance = new UserGenerator();
        }

        return instance;
    }

    /*
     * Loads a lists of female first names, male first names, and surnames from the json files when
     * this class is loaded into memory.
     */
//    static {
//        try {
//            maleNames = loadNamesFromJSon(MALE_NAMES_URL);
//            femaleNames = loadNamesFromJSon(FEMALE_NAMES_URL);
//            surnames = loadNamesFromJSon(SURNAMES_URL);
//        } catch (IOException e) {
//            throw new ExceptionInInitializerError(e);
//        }
//    }

    /**
     * Loads and returns the names from the specified json file.
     *
     * @param path the url to the file containing the names.
     * @return the names.
     * @throws IOException if an IO error occurs.
     */
//    private static String [] loadNamesFromJSon(String path) throws IOException {
//
//        Names names;
//
//        File file = new File(path);
//
//        InputStream is = new FileInputStream(file);
//        InputStreamReader isr = new InputStreamReader(is);
//        BufferedReader br = new BufferedReader(isr);
//
//        names = (new Gson()).fromJson(br, Names.class);
//
//
//        return names == null ? null : names.getNames();
//    }

    /**
     * Generates the specified number of users with names randomly selected from the json files.
     *
     * @param count the number of users to generate.
     * @return the generated users.
     */
    public List<User> generateUsers(int count) {

        List<User> users = new ArrayList<>(count);

        int i = 0;
        while(users.size() < count) {
            if(i%2 == 0) {
                users.add(new User("fname" + i,
                        "lname" + i,
                        "@tempAlias" + i,
                        MALE_IMAGE_URL));
                i++;
            }
            else {
                users.add(new User("fname" + i,
                        "lname" + i,
                        "@tempAlias" + i,
                        FEMALE_IMAGE_URL));
                i++;
            }
        }

        return users;
    }

    /**
     * A class used by Gson to map the json data to an instance of this class.
     */
    class Names {

        @SuppressWarnings("unused")
        private String [] data;

        private String [] getNames() {
            return data;
        }
    }
}
