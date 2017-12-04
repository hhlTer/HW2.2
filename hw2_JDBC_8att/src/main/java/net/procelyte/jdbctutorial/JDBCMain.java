package net.procelyte.jdbctutorial;

import net.procelyte.jdbctutorial.dialog_table_maker.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCMain {
    public static void main(String[] args) {
//  CREATE TABLES
//  ***************************************************************************
        ImplementDialog implementDialog = new ImplementDialog();
        implementDialog.createTableDev();
        implementDialog.createTablePro();
        implementDialog.createMTMtable();
//  ***************************************************************************

        Scanner scanner = new Scanner(System.in);
        System.out.println("Hi!");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("sleepMainCatch");
        }
/*
 *  implementation Fabric pattern (DialogMakerInterface, dialogMaker...)
 *
 *  ImpplementDialog - different functions for realization dialog (built-in class)
 *  char getAnswer(String string) - return char, which is entered by the user until he
 *          inputs the correct (from the string) value
 *  BigDecimal getBigDecimal - check and return BigDecimal
 *  Long getLongId - check and return id
 */

        DialogMakerInterface dialog;
        System.out.println("Choice the table\n" +
                "[D]evelopers, [P]roject\n" +
                "[E]xit");
        char c = implementDialog.getAnswer("dpe");
        switch (c) {
            case 'd':
                dialog = dialogMaker(EnumTables.DEVELOPERS);
                break;
            case 'p':
                dialog = dialogMaker(EnumTables.PROJECTS);
                break;
            case 'e':
                System.out.println("Bye!");
                System.exit(0);
            default:
                dialog = null;
        }
        if (dialog == null) {
            System.out.println("Error");
            System.exit(1);
        }

        System.out.println("You did choice " + dialog.dialogMake().getNameTable());
        try {
            Thread.sleep(999);
        } catch (InterruptedException e) {
            System.out.println("sleepMainCatch#2");
        }
/*
 *   choice CRUD "menu"
 *
 */

        do {
            long idl;
            System.out.println("Choose command:");
            System.out.println("Crea[t]e, [U]pdate, [R]ead, [L]ist, [D]elete, [C]lear");
            System.out.println("[E]xit");
            char c2 = implementDialog.getAnswer("turldce");
            switch (c2) {
//Create
                case 't':
                    dialog.dialogMake().createDialog();
                    break;
//Read
                case 'r':
                    dialog.dialogMake().readDialog();
                    break;
//SELECT * FROM
                case 'l':
                    dialog.dialogMake().listDialog();
                    break;
//Delete where id =
                case 'd':
                    dialog.dialogMake().deleteDialog();
                    break;
//Clear table
                case 'c':
                    dialog.dialogMake().clearDialog();
                    break;
//Exit
                case 'e':
                    System.out.println("Bye!");
                    System.exit(0);
            }
            }
            while (true) ;
        }


    private static DialogMakerInterface dialogMaker(EnumTables tables) {
        if (tables == EnumTables.DEVELOPERS) {
            return new DialogMakerDevelopers();
        } else if (tables == EnumTables.PROJECTS) {
            return new DialogMakerProjects();
        }
        throw new RuntimeException("Wrong table name");
    }

    private static class ImplementDialog {
        Statement statement;
        ImplementDialog(){
            try {
                statement = DBConnection.getConnection().createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //        *перевірка вводу команди (Чи належить до "Crea[t]e, [R]ead, [L]ist, [D]elete, [C]lear, [U]pdate")
        private char getAnswer(String crud) {
            System.out.println(":>");
            Scanner sc = new Scanner(System.in);
            String answer = sc.nextLine();
            char c = ' ';
            if (answer.length() > 0) {
                c = answer.toLowerCase().charAt(0);
            }
            if (!crud.contains("" + c))
                getAnswer(crud);
            return c;
        }
        private void createTableDev(){
            try {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS developers(" +
                        "  developers_id   BIGINT  NOT NULL PRIMARY KEY,\n" +
                        "  firstName  VARCHAR(50)  NOT NULL,\n" +
                        "  secondName VARCHAR(50)  NOT NULL,\n" +
                        "  skills     VARCHAR(255) NOT NULL,\n" +
                        "  salary     DECIMAL      NOT NULL)");
            } catch (SQLException e) {
                System.out.println("createTableDevSQL");
                e.printStackTrace();
            } catch (NullPointerException e){
                System.out.println("table developers already exist");
                e.printStackTrace();
            }
        }
        private void createMTMtable(){
            try {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS mtm_dev_prjct(" +
                        "mtm_id_dev BIGINT NOT NULL ," +
                        "mtm_id_project BIGINT NOT NULL, " +
                        "FOREIGN KEY (mtm_id_dev) REFERENCES developers(developers_id) ," +
                        "FOREIGN KEY (mtm_id_project) REFERENCES projects(projects_id));");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        private void createTablePro(){
            try {
                statement.executeUpdate("CREATE TABLE if not EXISTS projects(" +
                        "  id              bigint  NOT NULL PRIMARY KEY,\n" +
                        "  projects_id   bigint  NOT NULL ,\n" +
                        "  p_name        VARCHAR(100)  NOT NULL,\n" +
                        "  description VARCHAR(255)  NOT NULL)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}


