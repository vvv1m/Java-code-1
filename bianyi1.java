import java.util.Scanner;
import java.util.Stack;
import java.util.HashMap;
import java.util.Map;

public class bianyi1{
    private enum TokenType{
        ID, NUM, ADD, SUB, MUL, DIV, LEF, RIT, EOF
    }
    private enum noEndWord{
        E, EP, T, TP, F
    }
    private static class Token{
        TokenType type;
        String lexeme;
        public Token(TokenType type, String lexeme){
            this.type = type;
            this.lexeme = lexeme;
        }
        @Override
        public String toString(){
            return lexeme;
        }
    }
    private static int position;
    private static String input;
    private static Token currentToken;

    private static Map<noEndWord, Map<TokenType, String>> parseTable;
    public static  void main(String[] args){
        init_parse_table();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            input = scanner.nextLine();
            if(input.isEmpty()) break;
            position = 0;
            try{
                getNextToken();
                boolean result = parse();
                System.out.println(result ? "True" : "False");
            } catch(Exception e){
                System.out.println("False");
            }
        }
        scanner.close();
    }
    private static void init_parse_table(){
        parseTable = new HashMap<>();
        for(noEndWord nt : noEndWord.values()){
            parseTable.put(nt, new HashMap<>());
        }
        //E->TE'
        Map<TokenType, String> eMap = parseTable.get(noEndWord.E);
        eMap.put(TokenType.ID, "TE'");
        eMap.put(TokenType.NUM, "TE'");
        eMap.put(TokenType.LEF, "TE'");
        //E'->+TE' | -TE' | 空
        Map<TokenType, String> epMap = parseTable.get(noEndWord.EP);
        epMap.put(TokenType.ADD, "+TE'");
        epMap.put(TokenType.SUB, "-TE'");
        epMap.put(TokenType.RIT, "ε");
        epMap.put(TokenType.EOF, "ε");
        //T->FT'
        Map<TokenType, String> tMap = parseTable.get(noEndWord.T);
        tMap.put(TokenType.ID, "FT'");
        tMap.put(TokenType.NUM, "FT'");
        tMap.put(TokenType.LEF, "FT'");
        //T'->*FT' | /FT' | 空
        Map<TokenType, String> tpMap = parseTable.get(noEndWord.TP);
        tpMap.put(TokenType.MUL, "*FT'");
        tpMap.put(TokenType.DIV, "/FT'");
        tpMap.put(TokenType.ADD, "ε");
        tpMap.put(TokenType.SUB, "ε");
        tpMap.put(TokenType.LEF, "ε");
        tpMap.put(TokenType.RIT, "ε");
        tpMap.put(TokenType.EOF, "ε");
        //F->id | num | (E)
        Map<TokenType, String> fMap = parseTable.get(noEndWord.F);
        fMap.put(TokenType.ID, "id");
        fMap.put(TokenType.NUM, "num");
        fMap.put(TokenType.LEF, "(E)");
    }
    private static boolean parse(){
        Stack<String> stack = new Stack<>();
        stack.push("$");
        stack.push("E");
        while(!stack.isEmpty()){
            String top = stack.peek();
            if(top.equals("$")){
                return currentToken.type == TokenType.EOF;
            } else if(isEndWord(top)){
                if(matchesEndWord(top)){
                    stack.pop();
                    getNextToken();
                } else{
                    return false;
                }
            } else{
                noEndWord noEndWord = getnoEndWord(top);
                stack.pop();
                Map<TokenType, String> row = parseTable.get(noEndWord);
                String production = row.get(currentToken.type);
                if(production == null){
                    return false;
                }
                if(!production.equals("ε")){
                    pushToStack(stack, production);
                }
            }
        }
        return true;
    }
    private static void pushToStack(Stack<String> stack, String production){
        if (production.equals("TE'")) {
            stack.push("E'");
            stack.push("T");
            return;
        }
        if (production.equals("FT'")) {
            stack.push("T'");
            stack.push("F");
            return;
        }
        if (production.equals("+TE'")) {
            stack.push("E'");
            stack.push("T");
            stack.push("+");
            return;
        }
        if (production.equals("-TE'")) {
            stack.push("E'");
            stack.push("T");
            stack.push("-");
            return;
        }
        if (production.equals("*FT'")) {
            stack.push("T'");
            stack.push("F");
            stack.push("*");
            return;
        }
        if (production.equals("/FT'")) {
            stack.push("T'");
            stack.push("F");
            stack.push("/");
            return;
        }
        if (production.equals("(E)")) {
            stack.push(")");
            stack.push("E");
            stack.push("(");
            return;
        }
        // 单字符处理
        stack.push(production);
    }
    private static boolean isEndWord(String symbol){
        return symbol.equals("id") || symbol.equals("num") ||
            symbol.equals("+") || symbol.equals("-") ||
            symbol.equals("*") || symbol.equals("/") ||
            symbol.equals("(") || symbol.equals(")");
    }
    private static boolean matchesEndWord(String EndWord){
        switch(EndWord){
            case "id":
                return currentToken.type == TokenType.ID;
            case "num":
                return currentToken.type == TokenType.NUM;
            case "+":
                return currentToken.type == TokenType.ADD;
            case "-":
                return currentToken.type == TokenType.SUB;
            case "*":
                return currentToken.type == TokenType.MUL;
            case "/":
                return currentToken.type == TokenType.DIV;
            case "(":
                return currentToken.type == TokenType.LEF;
            case ")":
                return currentToken.type == TokenType.RIT;
            default:
                return false;
        }
    }
    private static noEndWord getnoEndWord(String symbol){
        switch (symbol){
            case "E":
                return noEndWord.E;
            case "E'":
                return noEndWord.EP;
            case "T":
                return noEndWord.T;
            case "T'":
                return noEndWord.TP;
            case "F":
                return noEndWord.F;
            default:
                throw new RuntimeException("error");
        }
    }
    private static void getNextToken(){
        while (position < input.length() && Character.isWhitespace(input.charAt(position))){
            position++;
        }
        if(position >= input.length()){
            currentToken = new Token(TokenType.EOF, "$");
            return;
        }
        char c = input.charAt(position);
        if(Character.isLetter(c)){
            StringBuilder lexeme = new StringBuilder();
            while (position < input.length() && (Character.isLetterOrDigit(input.charAt(position)))){
                lexeme.append(input.charAt(position));
                position++;
            }
            currentToken = new Token(TokenType.ID, lexeme.toString());
        } else if(Character.isDigit(c)){
            StringBuilder lexeme = new StringBuilder();
            while(position < input.length() && Character.isDigit(input.charAt(position))){
                lexeme.append(input.charAt(position));
                position++;
            }
            if(position < input.length() && input.charAt(position) == '.'){
                lexeme.append('.');
                position++;
                if(position >= input.length() || !Character.isDigit(input.charAt(position))){
                    throw new RuntimeException("error");
                }
                while(position < input.length() && Character.isDigit(input.charAt(position))){
                    lexeme.append(input.charAt(position));
                    position++;
                }
            }
            if(position < input.length() && (input.charAt(position) == 'e' || input.charAt(position) == 'E')){
                lexeme.append(input.charAt(position));
                position++;
                if(position < input.length() && (input.charAt(position) == '+' || input.charAt(position) == '-')){
                    lexeme.append(input.charAt(position));
                    position++;
                }
                if(position >= input.length()||!Character.isDigit(input.charAt(position))){
                    throw new RuntimeException("error");
                }
                while(position < input.length() && Character.isDigit(input.charAt(position))){
                    lexeme.append(input.charAt(position));
                    position++;
                }
            }
            currentToken = new Token(TokenType.NUM, lexeme.toString());
        } else {
            position++;
            switch(c){
                case '+':
                    currentToken = new Token(TokenType.ADD, "+");
                    break;
                case '-':
                    currentToken = new Token(TokenType.SUB, "-");
                    break;
                case '*':
                    currentToken = new Token(TokenType.MUL, "*");
                    break;
                case '/':
                    currentToken = new Token(TokenType.DIV, "/");
                    break;
                case '(':
                    currentToken = new Token(TokenType.LEF, "(");
                    break;
                case ')':
                    currentToken = new Token(TokenType.RIT, ")");
                    break;
                default:
                    throw new RuntimeException("error");

            }
        }
    }
}