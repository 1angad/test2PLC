package compiler;
import static compiler.Tokens.*;

import java.util.List;
//I am using the style given to us in the Compiler.zip folder in Sample Assignments

public class Syntax {

	public void getFirstToken() {
		grammar();
		if(t.type() == LCURL) {
			BLOCK();
		}
		// get first token if not token then return false otherwise proceed
	}

	private void STMT(){ 
		System.out.println("Enter <STMT>");
		if(t.type() == IF) {
			IF_STMT();
		} else if (t.type() == LCURL) {
			BLOCK();
		} else if (t.type() == VARID) {
			ASSIGN();
		} else if (t.type() == DATATYPE) {
			DECLARE();
		} else if (t.type() == WHILE) {
			WHILE_LOOP();
		} else {
			// throwError() doesn't work
		}
	} 

	private void STMT_LIST(){ 
		System.out.println("Enter <STMT_LIST>");
		STMT();
		if(t.type() == SEMICOLON) { 
			t = stored.get(++i); 
		} else {
			throwError();
		}
	}

	private void WHILE_LOOP(){ 
		System.out.println("Enter <WHILE_STMT>");
		if(t.type() == WHILE) {
			t = stored.get(++i); 
			if(t.type() == LPAR) {
				t = stored.get(++i); 
				BOOL_EXPR();
				if(t.type() == RPAR) {
					t = stored.get(++i); 
					BLOCK();
				}
			}
		} else {
			throwError();
		}
		System.out.println("-- EXIT <WHILE_STMT>");
	}

	private void IF_STMT(){
		System.out.println("Enter <IF_STMT>");
		if(t.type() == IF) {
			t = stored.get(++i); 
			if(t.type() == LPAR) {
				t = stored.get(++i); 
				BOOL_EXPR();
				if(t.type() == RPAR) {
					t = stored.get(++i); 
					BLOCK();
					if(t.type() == ELSE) {
						t = stored.get(++i); 
						BLOCK();
					}
				}
			}
		} else {
			throwError();
		}
		System.out.println("-- EXIT <IF_STMT>");
	}

	// whole bool term chaos
	private void BOOL_EXPR(){
		System.out.println("Enter <BOOL_EXP>");
		BTERM();
		if(t.type() == GT || t.type() == LT || t.type() == GTE  || t.type() == LTE ) {
			t = stored.get(++i); 
			BTERM();
		} else {
			// throwError() doesn't work
		}
		System.out.println("-- EXIT <BOOL_EXP>");
	}

	private void BTERM(){
		System.out.println("Enter <BTERM>");
		BAND();
		if(t.type() == EQUALS || t.type() == NOTEQUAL) {
			t = stored.get(++i); 
			BAND();
		} else {
			// throwError() doesn't work
		}
		System.out.println("-- EXIT <BTERM>");
	}

	private void BAND(){
		System.out.println("Enter <BOOL_AND>");
		BOR();
		if(t.type() == AND) {
			t = stored.get(++i); 
			BOR();
		} else {
			// throwError() doesn't work
		}
		System.out.println("-- EXIT <BOOL_AND>");
	}

	private void BOR(){
		System.out.println("Enter <BOOL_OR>");
		EXPR();
		if(t.type() == OR) {
			t = stored.get(++i); 
			EXPR();
		} else {
			// throwError() doesn't work
		}
		System.out.println("-- EXIT <BOOL_OR>");
	}

	private void BLOCK(){
		System.out.println("Enter <BLOCK>");
		if(t.type() == LCURL) {
			t = stored.get(++i); 
			STMT_LIST();
			if(t.type() == RCURL) {
				t = stored.get(++i); 
			}
		} else {
			throwError();
		}

		System.out.println("-- EXIT <BLOCK>");

	}

	private void DECLARE(){
		System.out.println("Enter <DECLARE>");
		if(t.type() == DATATYPE) {
			t = stored.get(++i); 
			if(t.type() == VARID) {
				t = stored.get(++i); 
				if(t.type() == COMMA){
					t = stored.get(++i); 
					// for multiple declarations
					if(t.type() == VARID) {
						t = stored.get(++i);
					}
				}
			}
		} else {
			throwError();
		}
		System.out.println("-- EXIT <DECLARE>");
	}

	private void ASSIGN(){
		System.out.println("Enter <ASSIGN>");
		if(t.type() == VARID) {
			t = stored.get(++i); 
			if(t.type() == ASSIGN) {
				t = stored.get(++i); 
				EXPR();
			}
		} else {
			throwError();
		}
		System.out.println("-- EXIT <ASSIGN>");
	}
	// cycle back up for bool term
	private void EXPR(){
		System.out.println("Enter <EXPR>");
		TERM();
		if(t.type() == ADD || t.type() == SUB) {
			t = stored.get(++i); 
			TERM();
		} else {
			// throwError() doesn't work
		}
		System.out.println("-- EXIT <EXPR>");
	}

	private void TERM(){
		System.out.println("Enter <TERM>");
		FACT();
		if(t.type() == MUL || t.type() == DIV || t.type() == MOD) {
			t = stored.get(++i); 
			FACT();
		} else {
			// throwError() doesn't work
		}
		System.out.println("-- EXIT <TERM>");
	}

	private void FACT(){ 
		System.out.println("Enter <FACT>");
		if(t.type() == VARID || t.type() == FLOAT_LIT || t.type() == INT_LIT) {
			t = stored.get(++i);
			return;
		} else if (t.type() == LPAR) {
			t = stored.get(++i); 
			EXPR();
			if(t.type() != LPAR) {
				throwError();
			}
		} else {
			throwError();
		}
		System.out.println("-- EXIT <FACT>");
	}

	public boolean grammar(){
		STMT();
		if(invalid == false) { 
			return true; 
		} else {
			return false;
		}
	}

	private void throwError(){ // Immediately exits 
		System.out.print("INVALID");
		System.exit(0);
	}

	public Syntax(List<Token> tokens){
		this.stored = tokens;
		this.t = tokens.get(i);
	}


	// Initialize GLobal Variables
	int i = 0; // for place i.e. for loop
	boolean invalid = false;
	final List<Token> stored; // Accessing all the stored tokens
	Token t; // t = token.get(i);

}