package no.ntnu.iie.stud.cateringstorm.util;

import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;

/**
 * Public class for storing variables accesible to the whole system
 * Created by EliasBrattli on 14/04/2016.
 */
public final class GlobalStorage {
    private static Employee loggedInEmployee;

    public static void setLoggedInEmployee(Employee loggedInEmployee) {
        GlobalStorage.loggedInEmployee = loggedInEmployee;
    }

    public static Employee getLoggedInEmployee() {
        return loggedInEmployee;
    }
}
