package net.procelyte.jdbctutorial.DAO;

import net.procelyte.jdbctutorial.DBConnection;
import net.procelyte.jdbctutorial.tables.Developers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class DeveloperCRUD extends ServiceCRUD implements CRUD<Developers, Long> {
    private List<Developers> developersList;
    private List<Developers> allDevelopersList;
    private Connection connection;
    private Statement statement;

    private PreparedStatement prCreateCortaje;
    private PreparedStatement prSelectDeveloper;
    private PreparedStatement prUpdateDeveloper;
    private PreparedStatement prDeleteDeveloper;

    public DeveloperCRUD() {

        try {
            this.connection = DBConnection.getConnection();
            this.statement = connection.createStatement();
//            createTableDev();
//            createMTMtable();
            prCreateCortaje = connection.prepareCall("INSERT INTO developers VALUES (?,?,?,?,?);");
            prSelectDeveloper = connection.prepareCall("SELECT * FROM developers WHERE developers_id = ?");
            prUpdateDeveloper = connection.prepareCall("UPDATE developers SET " +
                    "firstName = ?," +
                    "secondName = ?," +
                    "skills = ?," +
                    "salary = ? WHERE developers_id = ?");
            prDeleteDeveloper = connection.prepareCall("DELETE FROM developers WHERE developers_id = ?");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    CREATE Developer
    public void create(Developers developers){
        do {
            try {
                prCreateCortaje.setLong(1, developers.getId());
                prCreateCortaje.setString(2, developers.getFirstName());
                prCreateCortaje.setString(3, developers.getLastName());
                prCreateCortaje.setString(4, developers.getSkills());
                prCreateCortaje.setBigDecimal(5, developers.getSalary());
                prCreateCortaje.executeUpdate();
                System.out.println("Done!");
                Thread.sleep(1000);
                break;
            } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
                setCurrentId(developers);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                System.out.println("id = " + developers.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (true);

    }
//    SELECT Developer
    public Developers read(Long id) throws NoSuchElementException {
        try {
            Developers developers = new Developers();
/*
*          check id - if listID not contains id - throws NullPointerException
*/
            long[] listID = getAllId(developers);
            if (!checkId(listID, id)) throw new NoSuchElementException();

            prSelectDeveloper.setLong(1, id);
            ResultSet resultSet = prSelectDeveloper.executeQuery();
            while (resultSet.next()) {
                developers.setId(resultSet.getLong(1));
                developers.setFirstName(resultSet.getString(2));
                developers.setLastName(resultSet.getString(3));
                developers.setSkills(resultSet.getString(4));
                developers.setSalary(resultSet.getBigDecimal(5));
            }
            return developers;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
//    UPDATE Developer
    public void update(Developers developers){
        try {
            prUpdateDeveloper.setString(1, developers.getFirstName());
            prUpdateDeveloper.setString(2, developers.getLastName());
            prUpdateDeveloper.setString(3, developers.getSkills());
            prUpdateDeveloper.setBigDecimal(4, developers.getSalary());
            prUpdateDeveloper.setLong(5, developers.getId());
            prUpdateDeveloper.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                prUpdateDeveloper.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
//    DELETE
    public void delete(Long id){
        try {
            prDeleteDeveloper.setLong(1, id);
            prDeleteDeveloper.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    CLEAR DATABASE
    public void clear(){
        try {
            statement.executeQuery("TRUNCATE developers");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//  *************************  LIST OPERATION  **************************************
    public void setFromList(List<Developers> list){

        try {
            for (Developers developers:
                    list) {
                prCreateCortaje.setLong(1, developers.getId());
                prCreateCortaje.setString(2, developers.getFirstName());
                prCreateCortaje.setString(3, developers.getLastName());
                prCreateCortaje.setString(4, developers.getSkills());
                prCreateCortaje.setBigDecimal(5, developers.getSalary());                prCreateCortaje.addBatch();
            }
            statement.executeQuery("START TRANSACTION ");
            prCreateCortaje.executeBatch();
            statement.executeQuery("COMMIT ");
//            System.out.println(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                prCreateCortaje.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public List<Developers> getList() {
//        setDevelopersList();
        return developersList;
    }
    public void addIntoList(Developers developer){
        developersList.add(developer);
    }
    //    set into list and get ALL developers
    public List<Developers> getAllFromTable() {
        setIntoListAllFromTable();
        return allDevelopersList;
    }

/*
*   setIntoListAllFromTable - додає в allDevelopersList всю таблицю
*   createTable* - створює таблицю
*/

    private void setIntoListAllFromTable() {
        try {
            this.allDevelopersList = new ArrayList<Developers>();
            ResultSet resultSet= statement.executeQuery("SELECT * FROM developers");
            while (resultSet.next()){
                Developers developers = new Developers();
                developers.setId(resultSet.getLong(1));
                developers.setFirstName(resultSet.getString(2));
                developers.setLastName(resultSet.getString(3));
                developers.setSkills(resultSet.getString(4));
                developers.setSalary(resultSet.getBigDecimal(5));
                allDevelopersList.add(developers);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
