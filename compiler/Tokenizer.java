package compiler;
//I am using the style given to us in the Compiler.zip folder in Sample Assignments

import static compiler.Tokens.*;
import java.util.*;

public class Tokenizer {
    private final String input; // Taken in first from the raw file 
    private final List<Token> tokens = new ArrayList<>(); // List to put Tokens in

    private void put() {
        char thisChar = input.charAt(move++);
        if(thisChar == '+') {
        	put(ADD);
        	return;
        } else if (thisChar == '-') {
        	put(SUB);
        	return;
        } else if (thisChar == '*') {
        	put(MUL);
        	return;
        } else if (thisChar == '/') {
        	put(DIV);
        	return;
        } else if (thisChar == '%') {
        	put(MOD);
        	return;
        } else if (thisChar == '{') {
        	put(LCURL);
        	return;
        } else if (thisChar == '}') {
        	put(RCURL);
        	return;
        } else if (thisChar == '(') {
        	put(LPAR);
        	return;
        } else if (thisChar == ')') {
        	put(RPAR);
        	return;
        } else if (thisChar == ';') {
        	put(SEMICOLON);
        	return;
        } else if (thisChar == ',') {
        	put(COMMA);
        	return;
        } else if (thisChar == '!') {
        	if(match('=')) {
        		put(NOTEQUAL);
        	} else {
        		put(NOT);
        	}
        	return;
        } else if (thisChar == '>') {
        	if(match('=')) {
        		put(GTE);
        	} else {
        		put(GT);
        	}
        	return;
        } else if (thisChar == '<') {
        	if(match('=')) {
        		put(LTE);
        	} else {
        		put(LT);
        	}
        	return;
        } else if (thisChar == '&') {
        	if(match('&')) { // otherwise return invalid token
        		put(AND);
        	}
        	return;
        } else if (thisChar == '|') {
        	if(match('|')) { // otherwise return invalid token
        		put(OR);
        	}
        	return;
        } else if (thisChar == ' ') {
        
        } else if (thisChar == '=') {
        	if(match('=')) {
        		put(EQUALS);
        	} else {
        		put(ASSIGN);
        	}
        	return;
        } else {
        	if (Character.isDigit(thisChar)) {
                no();
            } else if (Character.isAlphabetic(thisChar) || thisChar == '$' || thisChar == '_') {
                buildString();
            } 
        }
    }
    
    public Tokenizer(String input){
        this.input = input;
    }
    
    public void tokenize() { // Driver
        while (true) {
            if(move < input.length()) {
            	i = move;
            	put();
            } else {
                tokens.add(new Token(EOF));
            	break;
            }
        }

    }

    private void put(Tokens type) {
        tokens.add(new Token(type));
    }

    private boolean match(char expToken) { // if it's a valid token with the addition, then add it, otherwise move on in both cases
        if (move >= input.length()) {
        	return false;
        }
        if (input.charAt(move) != expToken) { 
        	return false;
        }
        move++;
        return true;
    }

    private char mover() {
        if (move >= input.length()) {
        	return '\0';
        }
        return input.charAt(move);
    }

    private char j() {
        if (move + 1 >= input.length()) {
        	return '\0';
        }
        return input.charAt(move + 1);
    }

    private void no() {

    	boolean varFloat = false;

        while (Character.isDigit(mover())) 
        	input.charAt(move++);
        if (mover() == '.' && Character.isDigit(j())) {
        	input.charAt(move++);
        	varFloat = true;
            while (Character.isDigit(mover())) 
            	input.charAt(move++);
        }
        if (!varFloat) 
        	put(INT_LIT);
        else
        	put(FLOAT_LIT);
    }

    private void buildString(){
        while (true) {
        	if(Character.isAlphabetic(mover()) || mover() == '$' || mover() == '_' || Character.isDigit(mover())) {
        		input.charAt(move++);
        	} else {
        		break;
        	}
        }
        String keyword = "";
        for(int x = i; x < move; x++) {
        	keyword += input.charAt(x);
        }
        if(keyword.equals("while")) {
        	put(WHILE);
        	return;
        } else if (keyword.equals("if")) {
        	put(IF);
        	return;
        } else if (keyword.equals("else")) { 
        	put(ELSE);
        	return;
        } else if (keyword.equals("int")) {
        	put(DATATYPE);
        	return;
        } else if (keyword.equals("float")) {
        	put(DATATYPE);
        	return;
        } else {
        	put(VARID);
        	return;
        }
    }
    
    public List<Token> getTokens() { // for other
        return tokens;
    }
    // like in i and j to keep track, move does same thing as j
    int i = 0;
    int move = 0;
    
}

enum Tokens {
    IF, WHILE, ELSE, SEMICOLON, // STMT
    EOF, LPAR, RPAR, LCURL, RCURL, // BLOCK
    GTE, LTE, LT, GT, EQUALS, NOT, AND, OR, NOTEQUAL, // BOOL, DATATYPE
    ADD, SUB, MUL, DIV, MOD, // EXPR
    ASSIGN, VARID, INT_LIT, FLOAT_LIT, DATATYPE, COMMA, // ASSIGN
}

class Token { // for Syntax.java, returns classified validated tokens
    
    Tokens type() { 
    	return type; 
    }
    
    Token(Tokens type){
        this.type = type;
    }
    
    Tokens type;
}
