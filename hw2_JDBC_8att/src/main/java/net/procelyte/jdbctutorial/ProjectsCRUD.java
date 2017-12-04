package net.procelyte.jdbctutorial;

import net.procelyte.jdbctutorial.tables.Projects;
import net.procelyte.jdbctutorial.tables.Projects;
import net.procelyte.jdbctutorial.tables.Projects;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProjectsCRUD extends ServiceCRUD implements CRUD <Projects, Long> {
    private List<Projects> projectsList;
    private List<Projects> allProjectsList;
    private Connection connection;
    private Statement statement;

    private PreparedStatement prCreateCortaje;
    private PreparedStatement prSelectProject;
    private PreparedStatement prUpdateProject;
    private PreparedStatement prDeleteProject;

    public ProjectsCRUD() {

        try {
            this.connection = DBConnection.getConnection();
            this.statement = connection.createStatement();
//            createTableDev();
//            createMTMtable();
            prCreateCortaje = connection.prepareCall("INSERT INTO projects VALUES (?,?,?);");
            prSelectProject = connection.prepareCall("SELECT * FROM projects WHERE projects_id= ?");
            prUpdateProject = connection.prepareCall("UPDATE projects SET " +
                    "firstName = ?," +
                    "secondName = ?," +
                    "skills = ?," +
                    "salary = ? WHERE projects_id = ?");
            prDeleteProject = connection.prepareCall("DELETE FROM projects WHERE projects_id = ?");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void create(Projects projects) {
        do {
            try {
                prCreateCortaje.setLong(1, projects.getId());
                prCreateCortaje.setString(2, projects.getProjectName());
                prCreateCortaje.setString(3, projects.getDescription());
                prCreateCortaje.executeUpdate();
                System.out.println("Done!");
                Thread.sleep(1000);
                break;
            } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
                setCurrentId(projects);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                System.out.println("id = " + projects.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (true);
    }

    public Projects read(Long id) {
        try {
            Projects projects = new Projects();
/*
*          check id - if listID not contains id - throws NullPointerException
*/
            long[] listID = getAllId(projects);
            if (!checkId(listID, id)) throw new NoSuchElementException();

            prSelectProject.setLong(1, id);
            ResultSet resultSet = prSelectProject.executeQuery();
            while (resultSet.next()) {
                projects.setId(resultSet.getLong(1));
                projects.setProjectName(resultSet.getString(2));
                projects.setDescription(resultSet.getString(3));
            }
            return projects;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void update(Projects projects) {
        try {
            prUpdateProject.setString(1, projects.getProjectName());
            prUpdateProject.setString(2, projects.getDescription());
            prUpdateProject.setLong(3, projects.getId());
            prUpdateProject.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                prUpdateProject.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void delete(Long id) {
        try {
            prDeleteProject.setLong(1, id);
            prDeleteProject.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        try {
            statement.executeQuery("TRUNCATE projects");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Projects> getList() {
        return projectsList;
    }

    public List<Projects> getAllFromTable() {
        setIntoListAllFromTable();
        return allProjectsList;    }
    private void setIntoListAllFromTable() {
        try {
            this.allProjectsList = new ArrayList<Projects>();
            ResultSet resultSet= statement.executeQuery("SELECT * FROM projects");
            while (resultSet.next()){
                Projects projects = new Projects();
                projects.setId(resultSet.getLong(1));
                projects.setProjectName(resultSet.getString(2));
                projects.setDescription(resultSet.getString(3));
                allProjectsList.add(projects);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
