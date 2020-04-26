package com.ikemole.expressionevaluator.tests.assertions;

import com.ikemole.expressionevaluator.structure.ExpressionChain;
import com.ikemole.expressionevaluator.structure.node.ExpressionNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A helper class for unit tests to run common equality checks on ExpressionChain objects
 */
public class ExpressionChainAssert {

    /**
     * Assert that an expression chain contains the same nodes as that in the provided list.
     * @param expressionChain The expressions list to test on
     * @param expectedNodes The list of expected nodes in the list
     */
    public static void assertNodesAreCorrect(ExpressionChain expressionChain, ExpressionNode[] expectedNodes){
        assertEquals(expectedNodes.length, expressionChain.length());

        ExpressionNode node = expressionChain.first();
        int i = 0;
        do{
            ExpressionNodeAssert.assertEquals(expectedNodes[i], node);
            i++;
            node = node.right();
        } while (node != null);
    }
}
