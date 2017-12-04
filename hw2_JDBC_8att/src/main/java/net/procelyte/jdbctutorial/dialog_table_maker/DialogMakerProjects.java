package net.procelyte.jdbctutorial.dialog_table_maker;

public class DialogMakerProjects implements DialogMakerInterface {
    public CaseCRUD dialogMake() {
        return new ProjectDialog();
    }
}
