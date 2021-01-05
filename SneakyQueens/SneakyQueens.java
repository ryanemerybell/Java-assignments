// Ryan Bell
// COP 3503, Summer 2018

import java.io.*;
import java.util.*;

public class SneakyQueens
{

  // Returns the row that this queen is in as an int between 0 and boardSize-1.
  public static int parseRow(String s)
  {
    char c;
    int row = 0, place = 1, n;

    for(int i = s.length()-1; i >= 0; i--)
    {
      c = s.charAt(i);
      n = (int) c;

      // If we encounter a char that is a digit, we add some
      // power of 10 times that digit to the row number
      if(n>=48 && n<=57)
      {
        n -= 48;
        row += (place * n);
        place *= 10;
      }
    }

    return row - 1;
  }

  //Returns the column that this queen is in as an int between 0 and boardSize-1.
  public static int parseCol(String s)
  {
    char c;
    int col = 0,  place = 1, n;

    for(int i = s.length()-1; i >= 0; i--)
    {
      c = s.charAt(i);
      n = (int) c;

      // Skip it if it ain't a letter
      if(n<97 || n>122)
      {
        continue;
      }

      n -= 96;
      col += (place * n);
      place *=26;

    }

    return col - 1;
  }

  // -----------------------------------------------------------------------------------------------------------------------------------------

  public static boolean allTheQueensAreSafe(ArrayList<String> coordinateStrings, int boardSize)
  {
    int row, col, ND, PD;

    // These arrays will hold the information as to whether a queen is
    // on a specific row, column, or diagonal.
    boolean [] rows = new boolean[boardSize];
    boolean [] cols = new boolean[boardSize];
    boolean [] negD = new boolean[2*(boardSize)-1];
    boolean [] posD = new boolean[2*(boardSize)-1];


    for(int i = 0; i < coordinateStrings.size(); i++)
    {
      // The numbering for the positive diagonals is a little convoluted, but it works.
      row = parseRow(coordinateStrings.get(i));
      col = parseCol(coordinateStrings.get(i));
      ND = col + row;
      PD = col - row + boardSize - 1;

      // This sequence of 4 if/else blocks determine whether a row,
      // column, or diagonal is currently occupied by another queen.
      if(!rows[row])
        rows[row] = true;
      else
        return false;

      if(!cols[col])
        cols[col] = true;
      else
        return false;

      if(!negD[ND])
        negD[ND] = true;
      else
        return false;

      if(!posD[PD])
        posD[PD] = true;
      else
        return false;

    }

    return true;
  }



  public static double difficultyRating()
  {
    return 2.0;
  }



  public static double hoursSpent()
  {
    return 10.0;
  }
}
