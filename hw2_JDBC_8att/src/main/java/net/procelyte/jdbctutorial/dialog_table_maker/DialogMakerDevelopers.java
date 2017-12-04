package net.procelyte.jdbctutorial.dialog_table_maker;

public class DialogMakerDevelopers implements DialogMakerInterface {
    public CaseCRUD dialogMake() {
        return new DeveloperDialog();
    }
}
