// Sean Szumlanski
// COP 3503, Summer 2018

// =========================
// TopoPath: TestCase03.java
// =========================
// This corresponds to graph G1 from the assignment PDF.


import java.io.*;

public class TestCase03
{
	public static void main(String [] args) throws IOException
	{
		System.out.print("Test Case #3: ");

		boolean success = (TopoPath.hasTopoPath("input_files/g1.txt") == false);
		System.out.println(success ? "PASS" : "fail whale :(");
	}
}
