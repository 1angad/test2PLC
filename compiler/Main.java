package compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
// Notes for the Grader (also at bottom of code)
// I am using the style given to us in the Compiler.zip folder in Sample Assignments
// I just converted my homework4 to Java and proceeded with this style
// Also sorry if the comments are irritating, it just helps me keep track of my code
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
    	// Copy address of file and paste here with addition of file name at the end
        File file = new File("C:\\Users\\Angad\\eclipse-workspace\\NewParser\\src\\sample");
        Scanner scan = new Scanner(file);
        String input = "";
        while(scan.hasNext()) {
        	input = scan.nextLine();
        }
        System.out.println("Scanning: " + input);

        Tokenizer tokens = new Tokenizer(input);
        tokens.tokenize();
        Syntax valid = new Syntax(tokens.getTokens());
        if(valid.grammar()) {
        	System.out.println("VALID with " + (tokens.getTokens().size()-1) + " tokens");
        }
        scan.close();
    }
}

/*
 * Sorry that it doesn't read multiple lines, if you would like more test cases, I have provided some here and you can replace them in the test file
 * All these are valid and if ran in the same line, they will be printed
 * READS THE BOTTOM MOST LINE
 * -------------------------------------------------------------------------------------------------------------
 * if (a || b) { i = i + 3 ; } // IF STMT, OR OPERATOR, ASSIGN OPERATOR
 * while (x != 3) { x = x - 3 ; }  // WHILE LOOP, NOT EQUAL OPERATOR, ASSIGN OPERATOR
 * if( x >= 3 ) { x = x + 3 ; } else { x = x % 3 ; }   // IF ELSE STMT AND ASSIGN OPERATORS
 * int x = 3; // DECLARE
 * while(a && b) { i = i % 5; } // AND OPERATOR, EXPRESSION W MOD  
 * -------------------------------------------------------------------------------------------------------------
 */

// This is what output should look like :
// Scanning: if( x == 10 ) { x = x + 3 ; } else { x = x%3; } 
// - Running through non terminals -
// VALID with 23 tokens
