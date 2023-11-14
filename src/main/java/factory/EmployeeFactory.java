package factory;

import employee.CustomerService;
import employee.Employee;
import employee.Manager;
import employee.Programmer;

public class EmployeeFactory {
    public Employee getEmployee(String name, String function) {
        switch (function) {
            case "Programmer":
                return new Programmer(name);
            case "Manager":
                return new Manager(name);
            case "CustomerService":
                return new CustomerService(name);
            default:
                return new Employee(name, function);
        }

    }
}
