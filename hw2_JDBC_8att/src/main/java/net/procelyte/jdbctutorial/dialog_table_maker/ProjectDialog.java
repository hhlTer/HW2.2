package net.procelyte.jdbctutorial.dialog_table_maker;

import net.procelyte.jdbctutorial.CRUD;
import net.procelyte.jdbctutorial.DeveloperCRUD;
import net.procelyte.jdbctutorial.ProjectsCRUD;
import net.procelyte.jdbctutorial.tables.Developers;
import net.procelyte.jdbctutorial.tables.Projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ProjectDialog implements CaseCRUD {

    private DialogService service = new DialogService();
    private CRUD<Projects, Long> projectsCRUD = new ProjectsCRUD();
    private Projects projects = new Projects();
    private List<Projects> projectsList;

    public String getNameTable() {
        return "Projects";
    }
    /*
     *  fill
     */
    public void createDialog(){
        projects = fillProject();
        projectsCRUD.create(projects);
    }

    /*
    * method projectCRUD.read(long id) throws NoSuchElementException if id not exist in table
    * */
    public void readDialog() {
        long id = service.getLongId();
        System.out.println("from readDialog DialogProjectClass");
        try {
            projects = projectsCRUD.read(id);
            service.printTable(projects.getNameColumns(), projects.getCortaje());
            //service.printTable();
        } catch (NoSuchElementException e) {
            System.out.println("Project not found in table");
        }
    }

    public void listDialog() {
        projectsList = projectsCRUD.getAllFromTable();
        for (Projects p:
                projectsList) {
            service.printTable(p.getNameColumns(), p.getCortaje());
        }
    }

    public void updateDialog() {
        long id = service.getLongId();
        try {
            System.out.println("Update project?");
            projects = projectsCRUD.read(id);
            service.printTable(projects.getNameColumns(), projects.getCortaje());
            Scanner scanner = new Scanner(System.in);
            if (scanner.nextLine().toLowerCase().charAt(0) != 'y') return;
            projectsCRUD.update(fillProject());
        }catch (NoSuchElementException e){
            System.out.println("Project not found in table.");
        }
    }

    public void deleteDialog() {
        long id = service.getLongId();
//        System.out.println("Delete entry at number " + id + "?\n" +
//                           "[Y]es, [N]o?");
        projects = projectsCRUD.read(id);
        projectsCRUD.delete(id);
    }

    public void clearDialog() {
        projectsCRUD.clear();
    }


    private Projects fillProject(){
        Projects p = new Projects();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name of project");
        p.setProjectName(scanner.nextLine());
        System.out.println("Enter description");
        p.setDescription(scanner.nextLine());
        return p;
    }
}
