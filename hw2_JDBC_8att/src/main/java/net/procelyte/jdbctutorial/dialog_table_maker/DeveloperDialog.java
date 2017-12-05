package net.procelyte.jdbctutorial.dialog_table_maker;

import net.procelyte.jdbctutorial.DAO.CRUD;
import net.procelyte.jdbctutorial.DAO.DeveloperCRUD;
import net.procelyte.jdbctutorial.tables.Developers;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;


/*
* Питання:
* де краще створювати екземпляри, що використовуються в функціях
* (developersCRUD, service)
* */

public class DeveloperDialog implements CaseDialog {

    private DialogService service = new DialogService();
    private CRUD<Developers, Long> developersCRUD = new DeveloperCRUD();
    private Developers developer = new Developers();
    private List<Developers> developersList;

    public String getNameTable() {
        return "Developers";
    }


    public void createDialog(){
        developersCRUD.create(fillDeveloper());
    }

/*
* method developerCRUD.read(long id) throws NoSuchElementException if id not exist in table
* */
    public void readDialog() {
        long id = service.getLongId();
        try {
            developer = developersCRUD.read(id);
            service.printTable(developer.getNameColumns(), developer.getCortaje());
            //service.printTable();
        } catch (NoSuchElementException e) {
            System.out.println("Developer not found in table");
        }
    }

    public void listDialog() {
        developersList = developersCRUD.getAllFromTable();
        for (Developers d:
             developersList) {
            service.printTable(d.getNameColumns(), d.getCortaje());
        }
    }

    public void updateDialog() {
        long id = service.getLongId();
        try {
            System.out.println("Update developer?");
            developer = developersCRUD.read(id);
            service.printTable(developer.getNameColumns(), developer.getCortaje());
            Scanner scanner = new Scanner(System.in);
            if (scanner.nextLine().toLowerCase().charAt(0) != 'y') return;
            developersCRUD.update(fillDeveloper());
        }catch (NoSuchElementException e){
            System.out.println("Developer not found in table.");
        }
    }

    public void deleteDialog() {
        long id = service.getLongId();
//        System.out.println("Delete entry at number " + id + "?\n" +
//                           "[Y]es, [N]o?");
        developer = developersCRUD.read(id);

        developersCRUD.delete(id);
    }

    public void clearDialog() {
        developersCRUD.clear();
    }

    private Developers fillDeveloper(){
        Developers d = new Developers();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name of developer");
        d.setFirstName(scanner.nextLine());
        System.out.println("Enter last name");
        d.setLastName(scanner.nextLine());
        System.out.println("Enter skill");
        d.setSkills(scanner.nextLine());
        System.out.println("Enter salary");

        BigDecimal salary = service.checkBigDecimal();
        d.setSalary(salary);
        return d;
    }
}
