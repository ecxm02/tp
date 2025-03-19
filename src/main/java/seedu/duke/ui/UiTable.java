package seedu.duke.ui;

import java.util.ArrayList;

import seedu.duke.ui.exceptions.EmptyTableException;

/**
 * Handles the printing of table cells for listing of the applications
 * E.g.
 * +----+---------+-----------+------------------+---------------------+
 * | ID | Company | Job Title | Status           | Date of Application |
 * +----+---------+-----------+------------------+---------------------+
 * | 1  | Google  | SWE       | Resume Screening | NULL                |
 * +----+---------+-----------+------------------+---------------------+
 */
public class UiTable {
    /**
     * Takes a 2-dimensional array of strings and print the data as a table on the CLI, row by row.
     *
     * @param data 2-d ArrayList of String.
     */
    public void printTable(ArrayList<ArrayList<String>> data) throws EmptyTableException {
        if (data.isEmpty()) {
            throw new EmptyTableException("Empty table");
        }

        int[] columnWidths = getColumnWidths(data);
        printHorizontalBorder(columnWidths);

        for (ArrayList<String> datum : data) {
            printRow(datum, columnWidths);
            printHorizontalBorder(columnWidths);
        }
    }

    /**
     * Determines the largest column width needed for the table by checking each column,
     * finding the string with maximum length
     *
     * @param data 2-d ArrayList of String.
     * @return An array of widths representing the maximum width needed for corresponding columns.
     */
    private int[] getColumnWidths(ArrayList<ArrayList<String>> data) {
        int columns = data.get(0).size();
        int[] widths = new int[columns];

        for (ArrayList<String> row : data) {
            for (int i = 0; i < columns; i++) {
                widths[i] = Math.max(widths[i], row.get(i).length());
            }
        }
        return widths;
    }

    /**
     * Prints the horizontal border of the cells for the table.
     *
     * @param columnWidths An array of widths representing the maximum width needed for corresponding columns.
     */
    private void printHorizontalBorder(int[] columnWidths) {
        System.out.print("+");
        for (int width : columnWidths) {
            System.out.print("-".repeat(width + 2) + "+");
        }
        System.out.println();
    }

    /**
     * Prints a single row for the table.
     *
     * @param row          An arraylist of string to be displayed as a row in the table.
     * @param columnWidths An array of widths representing the maximum width needed for corresponding columns.
     */
    private void printRow(ArrayList<String> row, int[] columnWidths) {
        System.out.print("|");
        for (int i = 0; i < row.size(); i++) {
            printCell(row.get(i), columnWidths[i]);
        }
        System.out.println();
    }

    /**
     * Prints a single cell for the table.
     *
     * @param content The content to be inserted into the current cell of the table.
     * @param width   Maximum width needed for the current cell.
     */
    private void printCell(String content, int width) {
        System.out.print(" " + content + " ".repeat(width - content.length()) + " |");
    }

}
