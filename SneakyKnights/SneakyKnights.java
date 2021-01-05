// Ryan Bell
// COP 3503, Summer 2018

import java.io.*;
import java.util.*;
import java.lang.*;

public class SneakyKnights
{
  // Returns the row that this knight is in as an int between 0 and boardSize-1.
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

    return row-1;
  }

  //Returns the column that this knight is in as an int between 0 and boardSize-1.
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

    return col-1;
  }

  // -----------------------------------------------------------------------------------------------------------------------------------------

  public static boolean allTheKnightsAreSafe(ArrayList<String> coordinateStrings, int boardSize)
  {
    // This knights row, column, and the row and column of the squares that it's attacking
    // 'positions' will hold the positions of the squares currently being attacked
    int row, col, attRow, attCol, i, j, k;
    HashSet<String> positions = new HashSet<String>();

    for(i = 0; i < coordinateStrings.size(); i++)
    {
      row = parseRow(coordinateStrings.get(i));
      col = parseCol(coordinateStrings.get(i));

      // If this knight is placed on a currently attacked square,then the knights are not safe.
      if(positions.contains(Integer.toString(row) + " " + Integer.toString(col)))
        return false;

      for(j = -2; j <= 2; j++)
        for(k = -2; k <= 2; k++)
        {
          // Only allowing for the (up to) 8 squares this knight attacks.
          if(Math.abs(j * k) != 2)
            continue;

          attRow = row + j;
          attCol = col + k;

          // Add the (up to) 8 squares this knight attacks to the HashSet
          if(attRow >= 0 && attCol >= 0)
            positions.add(Integer.toString(attRow) + " " + Integer.toString(attCol));
        }

    }

    return true;
  }



  public static double difficultyRating()
  {
    return 3.5;
  }



  public static double hoursSpent()
  {
    return 5.0;
  }
}
