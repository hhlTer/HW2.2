package net.procelyte.jdbctutorial.tables;

import java.math.BigDecimal;
import java.util.List;

public class Developers implements Tables{
    private long id;
    private String firstName;
    private String lastName;
    private String skills;
    private BigDecimal salary;

    private List<Projects> projectsList;

    public long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getSkills() {
        return skills;
    }
    public BigDecimal getSalary() {
        return salary;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setSkills(String skills) {
        this.skills = skills;
    }
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String[] getNameColumns(){
        return new String[]{
                "id:",
                "First Name",
                "Last name",
                "Skill",
                "Salary"};
        }
    public String[] getCortaje(){
        return new String[]{
                ("" + getId()),
                   getFirstName(),
                   getLastName(),
                   getSkills(),
                ("" + getSalary())};
    }

    public String getNameTable() {
        return "developers";
    }

    public Developers() {

    }

    @Override
    public String toString(){
        return "developers";
    }
}
