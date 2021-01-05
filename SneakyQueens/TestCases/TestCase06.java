// Sean Szumlanski
// COP 3503, Summer 2018

// =============================
// SneakyQueens: TestCase06.java
// =============================
// A very large test case to help you determine whether your program is working
// correctly, and also to help determine whether your program is running in
// linear time. If so, this one should execute very quickly.
//
// There are two queens in this one that are able to attack one another.
//
// For detailed compilation and testing instructions, see the assignment PDF.


import java.io.*;
import java.util.*;

public class TestCase06
{
	private static void checkTest(boolean success)
	{
		System.out.println(success ? "Hooray!" : "fail whale :(");
	}

	public static void main(String [] args) throws Exception
	{
		Scanner in = new Scanner(new File("input_files/TestCase06-input.txt"));
		ArrayList<String> list = new ArrayList<String>();

		// Read each line from the input file into the ArrayList.
		while (in.hasNext())
			list.add(in.next());

		checkTest(SneakyQueens.allTheQueensAreSafe(list, 60000) == false);
	}
}
