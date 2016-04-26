package no.ntnu.iie.stud.cateringstorm.util;

import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Public class for storing variables accessible to the whole system
 */
public final class GlobalStorage {
    private static final String PROPERTIES_FILE = "./config/app.properties";
    private static Employee loggedInEmployee;
    private static Properties properties;
    static {
        properties = new Properties();

        // Create file if it's not found
        try {
            File f = new File(PROPERTIES_FILE);
            if (!f.exists()) {
                File folder = new File("./config");
                boolean success = folder.mkdir();
                success = f.createNewFile();
                System.out.println(success);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load properties file
        try {
            properties.load(new FileInputStream(PROPERTIES_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Employee getLoggedInEmployee() {
        return loggedInEmployee;
    }

    public static void setLoggedInEmployee(Employee loggedInEmployee) {
        GlobalStorage.loggedInEmployee = loggedInEmployee;
    }

    public static Properties getProperties() {
        return properties;
    }
}
