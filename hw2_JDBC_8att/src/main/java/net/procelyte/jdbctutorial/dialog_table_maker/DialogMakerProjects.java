package net.procelyte.jdbctutorial.dialog_table_maker;

public class DialogMakerProjects implements DialogMakerInterface {
    public CaseDialog dialogMake() {
        return new ProjectDialog();
    }
}
