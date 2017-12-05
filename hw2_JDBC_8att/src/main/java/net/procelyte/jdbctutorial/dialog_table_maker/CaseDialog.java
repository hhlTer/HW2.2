package net.procelyte.jdbctutorial.dialog_table_maker;

public interface CaseDialog {
    String getNameTable();

    void createDialog();
    void readDialog();
    void listDialog();
    void updateDialog();
    void deleteDialog();
    void clearDialog();
}
