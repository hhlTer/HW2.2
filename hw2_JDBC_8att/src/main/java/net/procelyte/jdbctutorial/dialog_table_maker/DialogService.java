package net.procelyte.jdbctutorial.dialog_table_maker;

import java.math.BigDecimal;
import java.util.Scanner;

class DialogService {
    BigDecimal checkBigDecimal(){
        Scanner scanner = new Scanner(System.in);
        BigDecimal bd = new BigDecimal(0);
        try {
            bd = scanner.nextBigDecimal();
        } catch (Exception e) {
            System.out.println("Wrong format\n");
            checkBigDecimal();
        }
        return bd;
    }

    void printTable(String[] nameColumn, String[] volumes){

        final int COUNT_COLUMN = nameColumn.length; // 1. id, 2. name, 3. last_name, 4. skill, 5. salary

        //length - length each of entering field
        //+
        //першовизначна ширина
        //FE:
        //lengthCol[0] = (" id:").length = 4
        //lengthCol[1] = (" First name:).length = 12
        //lengthNNN[0] = ("9").length = 1
        //lengthNNN[1] = ("10").length = 2

        int[] lengthNNN = new int[COUNT_COLUMN];
        int[] lengthCol = new int[COUNT_COLUMN];
        for (int i = 0; i < COUNT_COLUMN; i++) {
            lengthNNN[i] = (1 + volumes[i].length());
            lengthCol[i] = (1 + nameColumn[i].length());
        }
       // lengthCol[3] = LENGTH_DESCRIPTION_COL;

/*
 *      conclutionLength -
 *      Determine cells size in conclusion (визначення довжини ячейок у висновку)
 *      "id:" + пробіли по боках = 5. Якщо ід наприклад 9493 (4 символи) - тоді довжина = " " + 4 + " " = 6
 *      but no smallest then name of column length (довжина не може бути меншою довжини назви колонки)
 *
 *      id0 - "Olga"
 *      conclotionLength[0] = 12 (" Firs name ")
 *      id1 = "Kahiashvilius"
 *      conclutionLength[1] = 15 (" Kahiashvilius ")
 *
 */
        int[] conclutionLength = new int[COUNT_COLUMN];
        for (int i = 0; i < COUNT_COLUMN; i++) {
//                                                                   +1         +2    :
//                                "Olga"(4)    <= "First name"(10) ? " First name "   :
//                      "Kahiashvilius"(13)    <= "First name"(10) ?                  : " Kahiashvilius "
            conclutionLength[i] = lengthNNN[i] <= lengthCol[i]     ? lengthCol[i] + 2 : lengthNNN[i] + 2;
        }


/*
 *       determine count of gap, whose can be add if length of entering data
 *       biggest then NAME COLUMN length
 *       (minimum - one gap)
 *        FE: first name=
 *           "first_n"  then- p[i] =
 *          "1first n234"      = 4 *
 *
 */
        String[] p = new String[COUNT_COLUMN];
        for (int i = 0; i < COUNT_COLUMN; i++) {
            p[i] = " ";
            for (int j = 1; j < (conclutionLength[i] - 1 - lengthCol[i]); j++) {
                p[i] += " ";
            }
        }


        //верхня планка
        //" ____ _________ _____ __________"

        String[] s = new String[COUNT_COLUMN];
        for (int i = 0; i < COUNT_COLUMN; i++) {
            s[i] = " ";
            for (int j = 1; j < conclutionLength[i]; j++) {
                s[i] += "_";
            }
            System.out.print(s[i]);
        }
        System.out.println();
//                           _____ ____________ ___________ _________
//                          | id: | developer  |....       |         |
        for (int i = 0; i < COUNT_COLUMN; i++) {
            System.out.print("| " + nameColumn[i] + p[i]);
        }   System.out.println("|");//         |           |         |
/*       determine count of gap, whose can be add if length of entering data
 *       biggest then ENTERING DATA length
 *      (minimum - one gap)
 *       FE: first name:
 *          "first_name_name": p[i] =
 *         "1first name23456"       = 6
 */
        for (int i = 0; i < COUNT_COLUMN; i++) {
            p[i] = " ";
            for (int j = 0; j < (conclutionLength[i] - 2) - lengthNNN[i]; j++) {
                p[i] += " ";
            }
        }
//                          | 1   | NyName     |....       |         |
        for (int i = 0; i < COUNT_COLUMN; i++) {
            System.out.print("| " + volumes[i] + p[i]);
        }   System.out.println("|");//         |           |         |
        //нижня планка       ````` ```````````` ``````````` `````````
        for (int i = 0; i < COUNT_COLUMN; i++) {
            s[i] = " ";
            for (int j = 1; j < conclutionLength[i]; j++) {
                s[i] += "`";
            }
            System.out.print(s[i]);
        }
        System.out.println();
    }


    long getLongId() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Enter ID \n :>");
        Scanner sc = new Scanner(System.in);
        long id = 0;
        try {
            id = sc.nextLong();
            return id;
        } catch (Exception e) {
            System.out.println("Wrong input format");
            getLongId();
        }
        return id;
    }
}
