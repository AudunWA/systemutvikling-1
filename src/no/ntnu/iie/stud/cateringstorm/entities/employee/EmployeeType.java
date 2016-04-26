package no.ntnu.iie.stud.cateringstorm.entities.employee;

/**
 * The base class for all employees.
 */
public enum EmployeeType {
    EMPLOYEE(0),
    CHEF(1),
    CHAUFFEUR(2),
    NUTRITION_EXPERT(3),
    ADMINISTRATOR(4),
    SALESPERSON(5);

    private int type;

    EmployeeType(int type) {
        this.type = type;
    }

    public static EmployeeType getEmployeeType(int type) {
        switch (type) {
            case 0:
                return EMPLOYEE;
            case 1:
                return CHEF;
            case 2:
                return CHAUFFEUR;
            case 3:
                return NUTRITION_EXPERT;
            case 4:
                return ADMINISTRATOR;
            case 5:
                return SALESPERSON;
            default:
                return EMPLOYEE;
        }
    }

    public int getType() {
        return type;
    }
}
