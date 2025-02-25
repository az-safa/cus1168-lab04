package academy.javapro;

class ExpressionParser {
    private final String input;
    private int position;

    public ExpressionParser(String input) {
        this.input = input;
        this.position = 0;
    }

    // expr → term (+ term)*
    public double parseExpression() {
        double result = parseTerm(); // get leftmost term value
        while (position < input.length() && input.charAt(position) == '+') { // while statement
            position++; // skip '+'
            result += parseTerm(); // term on the right + running total
        }
        return result; // return final value
    } 

    // term → factor (* factor)*
    private double parseTerm() { 
        double result = parseFactor(); // get leftmost factor value
        while (position < input.length() && input.charAt(position) == '*') { // while statement
            position++; // skip '*'
            result *= parseFactor(); // factor on the right * running total
        }
        return result; // return final value
    }

    // factor → (expr) | number
    private double parseFactor() {
        if (position < input.length() && input.charAt(position) == '(') { // check for '('
            position++; // skip '('
            double result = parseExpression(); // parse expression inside '()'
            if (position < input.length() && input.charAt(position) == ')') { 
                position++; // skip ')'
            } else {
                throw new RuntimeException("Mismatched parentheses at position " + position);
            }
            return result;
        }
        return parseNumber();
    }

    // Parse a numeric value
    private double parseNumber() {
        StringBuilder number = new StringBuilder(); // create StringBuilder
        while (position < input.length() && (Character.isDigit(input.charAt(position)) || input.charAt(position) == '.')) {
            number.append(input.charAt(position)); // append current digit to number string
            position++; // move to next char
        }
        if (number.length() == 0) { // convert string of digits to a double
            throw new RuntimeException("Expected a number at position " + position);
        }
        return Double.parseDouble(number.toString()); // return
    }

    public static void main(String[] args) {
        // Test cases
    	String[] testCases = {
                "2 + 3 * (4 + 5)",		// Complex expression with parentheses
                "2 + 3 * 4",			// Basic arithmetic with precedence
                "(2 + 3) * 4",			// Parentheses changing precedence
                "2 * (3 + 4) * (5 + 6)", // Multiple parentheses
                "1.5 + 2.5 * 3"			// Decimal numbers
        };

        // Process each test case
        for (String expression : testCases) {
            System.out.println("\nTest Case: " + expression);
            try {
                ExpressionParser parser = new ExpressionParser(expression.replaceAll("\\s+", ""));
                double result = parser.parseExpression();
                System.out.println("Result: " + result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
