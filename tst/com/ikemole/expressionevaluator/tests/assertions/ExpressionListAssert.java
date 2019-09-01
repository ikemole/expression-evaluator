package com.ikemole.expressionevaluator.tests.assertions;

import com.ikemole.expressionevaluator.structure.ExpressionList;
import com.ikemole.expressionevaluator.structure.node.ExpressionNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A helper class for unit tests to run common equality checks on ExpressionList objects
 */
public class ExpressionListAssert {

    /**
     * Assert that an expression list contains the same nodes as that in the provided list.
     * @param expressionList The expressions list to test on
     * @param expectedNodes The list of expected nodes in the list
     */
    public static void assertListContents(ExpressionList expressionList, ExpressionNode[] expectedNodes){
        assertEquals(expectedNodes.length, expressionList.length());

        ExpressionNode node = expressionList.first();
        int i = 0;
        do{
            ExpressionNodeAssert.assertEquals(expectedNodes[i], node);
            i++;
            node = node.right();
        } while (node != null);
    }
}
