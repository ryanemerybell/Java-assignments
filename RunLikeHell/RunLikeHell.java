// Ryan Bell
// COP 3503, Summer 2018

import java.io.*;
import java.util.*;
import java.lang.*;

public class RunLikeHell
{
  public static int maxGain(int [] blocks)
  {
    // This if statement is unnecessary, but is here for defensive coding
    if(blocks == null || blocks.length == 0)
      return 0;

    // DP array: an array of only length 3 is necessary and sufficient
    int [] array = new int[3];

    // Base Case initialization for either 0 or 1 block(s)
    array[0] = 0;
    array[1] = blocks[0];

    // Here, i represents "number of treasures", and the arguments inside of
    // Math.max represent a binary choice: Include block i or exclude block i?
    for(int i = 2; i <= blocks.length; i++)
      array[i%3] = Math.max(array[(i-2)%3] + blocks[i-1], array[(i-1)%3]);

    // The final overwritten entry in array contains the optimal solution
    return array[(blocks.length)%3];
  }

  public static double difficultyRating()
  {
    return 3.0;
  }

  public static double hoursSpent()
  {
    return 10.0;
  }
}
