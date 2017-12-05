package net.procelyte.jdbctutorial.DAO;

import net.procelyte.jdbctutorial.DBConnection;
import net.procelyte.jdbctutorial.tables.Developers;
import net.procelyte.jdbctutorial.tables.Tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

class ServiceCRUD {
/*
*   допоміжні методи
*   setCuurenrId - встановлює ід (автоматично від максимального, що існує в таблиці)
*   getAllId - повертає всі Ід для перевірки на коректність введених даних (Викристовується в DBDialog)
*   private boolean checkId(long[] along, long id) - перевіряє чи є ід в масиві
*/
    private Statement statement;
    ServiceCRUD() {
        try {
            this.statement = DBConnection.getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void setCurrentId(Tables tables){
        long a = 0;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT MAX(" +
                    tables.getNameTable() + "_id) FROM " +
                    tables.getNameTable());
            while (resultSet.next()) {
                a = resultSet.getLong(1);
                tables.setId(++a);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        tables.setId(a);
    }
    long[] getAllId(Tables tables){
        String tbl = tables.getNameTable();
        try {
            int l = 1;
            int i = 0;
            System.out.println("SELECT COUNT(" +
                    tbl + "_id) FROM " + tbl);
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(" +
                    tbl + "_id) FROM " + tbl);
            while (resultSet.next()){
                l = resultSet.getInt(1);
            }
            resultSet = statement.executeQuery("SELECT " +
                    tbl + "_id FROM " + tbl);
            long[] along = new long[l];
            while (resultSet.next()){
                along[i] = resultSet.getLong(1);
                i++;
            }
            return along;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new long[0];
    }
    boolean checkId(long[] along, long l){
        boolean b = false;

        for (long anAlong : along) {
            b = (anAlong == l);
            if (b) break;
        }
        if (!b) {
            System.out.println("Wrong id!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return b;
    }

}
