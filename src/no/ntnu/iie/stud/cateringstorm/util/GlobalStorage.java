package no.ntnu.iie.stud.cateringstorm.util;

import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;

/**
 * Public class for storing variables accesible to the whole system
 */
public final class GlobalStorage {
    private static Employee loggedInEmployee;

    public static Employee getLoggedInEmployee() {
        return loggedInEmployee;
    }

    public static void setLoggedInEmployee(Employee loggedInEmployee) {
        GlobalStorage.loggedInEmployee = loggedInEmployee;
    }
}
