package no.ntnu.iie.stud.cateringstorm.entities.hours;

import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.util.GlobalStorage;

import java.util.ArrayList;

/**
 * Created by EliasBrattli on 14/04/2016.
 */
public final class HoursFactory {

    // TODO: Implement factory class here
    public static ArrayList<Hours> getAllHours(){
        // TODO: Implement method gathering all hours from table, only where employee ID equals this ID
        Employee thisEmployee = GlobalStorage.getLoggedInEmployee(); //Access to the respective employee

        return null;
    }

}
