package com.ikemole.expressionevaluator.tests.assertions;

import com.ikemole.expressionevaluator.structure.node.BracketNode;
import com.ikemole.expressionevaluator.structure.node.ExpressionNode;
import com.ikemole.expressionevaluator.structure.node.NumberNode;
import org.junit.jupiter.api.Assertions;

/**
 * A helper class for unit tests to test the equality of ExpressionNode objects
 */
public class ExpressionNodeAssert {
    /**
     * Assert that two ExpressionNode objects are equal.
     * That means the types are the same and they contain the same value.
     * @param expected The expected value
     * @param actual The actual value
     */
    public static void assertEquals(ExpressionNode expected, ExpressionNode actual){
        Assertions.assertEquals(expected.type(), actual.type());

        if(expected instanceof NumberNode){
            Assertions.assertTrue(actual instanceof NumberNode);
            Assertions.assertEquals(((NumberNode)expected).number(), ((NumberNode)actual).number());
        }

        if(expected instanceof BracketNode){
            Assertions.assertTrue(actual instanceof BracketNode);
            Assertions.assertEquals(((BracketNode)expected).expression(), ((BracketNode)actual).expression());
        }
    }
}
