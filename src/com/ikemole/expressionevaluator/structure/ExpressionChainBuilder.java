package com.ikemole.expressionevaluator.structure;

import com.ikemole.expressionevaluator.exception.BadExpressionException;
import com.ikemole.expressionevaluator.structure.node.BracketNode;
import com.ikemole.expressionevaluator.structure.node.ExpressionNode;
import com.ikemole.expressionevaluator.structure.node.NumberNode;
import com.ikemole.expressionevaluator.structure.node.OperatorNode;
import com.ikemole.expressionevaluator.structure.node.ExpressionNodeType;

import java.util.HashMap;

/**
 * This class is used to build the expression chain for a given math expression.
 */
public class ExpressionChainBuilder {

    /**
     * Build the expression linked list.
     * For example, "2*3" becomes [2]<-->[*]<-->[3]
     * @param expression A string containing a math expression
     * @return The expression list
     */
    public ExpressionChain build(String expression) throws BadExpressionException {
        ExpressionChain expressionChain = new ExpressionChain();
        boolean firstNodeRead = false;

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if(Character.isWhitespace(c))
                continue;

            if(Character.isDigit(c)){
                String digits = getConsecutiveDigits(expression, i);
                int num = Integer.parseInt(digits);
                ExpressionNode node = new NumberNode(num);
                expressionChain.add(node);

                // advance pointer to the end of the digits
                i = i + digits.length() - 1;
            } else if (c == '('){
                String innerExpression = getBracketExpression(expression, i);
                ExpressionNode node = new BracketNode(innerExpression);
                expressionChain.add(node);

                // advance pointer to the closing bracket
                // note: innerExpression excludes opening and closing brackets
                i = i + innerExpression.length() + 1;
            } else if (isOperator(c)){

                // If the expression starts with an operator,
                // take different decisions.
                if(!firstNodeRead){
                    switch (c){
                        case '-':
                        case '+':
                            expressionChain.add(new NumberNode(0));
                            break;
                        default:
                            throw new IllegalArgumentException("Operator cannot begin the expression: " + c);
                    }
                }

                ExpressionNode node = new OperatorNode(OPERATORS.get(c));
                expressionChain.add(node);
            } else {
                throw new IllegalArgumentException(String.format(
                        "The character %s at position %s in the expression \"%s\" is not valid", c, i, expression));
            }

            firstNodeRead = true;
        }

        return expressionChain;
    }

    private static HashMap<Character, ExpressionNodeType> OPERATORS = new HashMap<>();
    static {
        OPERATORS.put('*', ExpressionNodeType.Multiplication);
        OPERATORS.put('/', ExpressionNodeType.Division);
        OPERATORS.put('+', ExpressionNodeType.Addition);
        OPERATORS.put('-', ExpressionNodeType.Subtraction);
        OPERATORS.put('^', ExpressionNodeType.Exponent);
    }

    private boolean isOperator(char c) {
        return OPERATORS.containsKey(c);
    }

    /**
     * Get the expression inside a bracket, assuming the bracket starts at
     * "startingIndex". The returned expression will exclude the opening and closing brackets.
     * The expression will include any nested brackets.
     * @param expression The expression
     * @param startingIndex The index of the opening brackets
     * @return The content of the brackets
     */
    private String getBracketExpression(String expression, int startingIndex) throws BadExpressionException {
        StringBuilder sb = new StringBuilder();
        int openBracketCount = 1;
        for (int i = startingIndex + 1; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if(c == '('){
                openBracketCount++;
            }

            if(c == ')'){
                openBracketCount--;
            }

            if(openBracketCount == 0){
                break;
            }

            sb.append(c);
        }

        if(openBracketCount > 0)
            throw new BadExpressionException("The open bracket at index " + startingIndex + " was not closed.");

        return sb.toString();
    }

    /**
     * Get the consecutive digits in the string starting from "startingIndex".
     * @param expression The expression
     * @param startingIndex The startingIndex of the first digit
     * @return The string of consecutive digits
     */
    private String getConsecutiveDigits(String expression, int startingIndex) {
        StringBuilder sb = new StringBuilder();
        for (int i = startingIndex; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if(Character.isDigit(c))
                sb.append(c);
            else
                break;
        }
        return sb.toString();
    }
}
