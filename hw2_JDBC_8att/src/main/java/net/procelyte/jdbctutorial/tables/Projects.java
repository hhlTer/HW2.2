package net.procelyte.jdbctutorial.tables;

import java.util.List;

public class Projects implements Tables {
    private long id;
    private String projectName;
    private String description;

    private List<Developers> developersList;

    public long getId() {
        return id;
    }
    public String getProjectName() {
        return projectName;
    }
    public String getDescription() {
        return description;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        return "projects";
    }

    public String[] getNameColumns() {
        return new String[]{
                "id:",
                "Project",
                "Description",
                };
    }
    public String[] getCortaje(){
        return new String[]{
                ("" + getId()),
                getProjectName(),
                getDescription()
                };
    }

    public String getNameTable() {
        return "projects";
    }
}
