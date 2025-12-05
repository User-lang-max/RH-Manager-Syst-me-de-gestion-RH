package Main;

import dao.CongeDAO;
import dao.EmployeDAO;
import hrmanager.service.EmployeService;
import ui.MenuConsole;

public class Main {
    public static void main(String[] args) {

    
        EmployeDAO employeDAO = new EmployeDAO();
        CongeDAO congeDAO = new CongeDAO();

        EmployeService employeService = new EmployeService(employeDAO, congeDAO);

 
        MenuConsole menu = new MenuConsole(employeService);

   
        menu.demarrer();
    }
}
