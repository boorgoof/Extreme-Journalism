package it.unipd.dei.dbdc.analysis;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Class that tests {@link OrderedEntryStringIntTest}.
 * It doesn't test the constructors, only the {@link OrderedEntryStringInt#isMajorThan(OrderedEntryStringInt)} function.
 */
@Order(7)
public class OrderedEntryStringIntTest
{
    /**
     * Tests of {@link OrderedEntryStringInt#isMajorThan(OrderedEntryStringInt)}.
     */
    @Test
    public void isMajorThan()
    {
        //Same value
        OrderedEntryStringInt e1 = new OrderedEntryStringInt("a", 12);
        OrderedEntryStringInt e2 = new OrderedEntryStringInt("b", 12);
        assertTrue(e1.isMajorThan(e2));
        assertFalse(e2.isMajorThan(e1));

        e1 = new OrderedEntryStringInt("helpme", 12);
        e2 = new OrderedEntryStringInt("helpmem", 12);
        assertTrue(e1.isMajorThan(e2));
        assertFalse(e2.isMajorThan(e1));

        e1 = new OrderedEntryStringInt("helpMe", 12);
        e2 = new OrderedEntryStringInt("helpMe", 12);
        assertFalse(e1.isMajorThan(e2));
        assertFalse(e2.isMajorThan(e1));

        //They are identical
        e1 = new OrderedEntryStringInt("help", 12);
        e2 = new OrderedEntryStringInt("help", 12);
        assertFalse(e1.isMajorThan(e2));
        assertFalse(e2.isMajorThan(e1));

        //Different value
        e1 = new OrderedEntryStringInt("a", 13);
        e2 = new OrderedEntryStringInt("b", 12);
        assertTrue(e1.isMajorThan(e2));
        assertFalse(e2.isMajorThan(e1));

        e1 = new OrderedEntryStringInt("z", 13);
        e2 = new OrderedEntryStringInt("b", 12);
        assertTrue(e1.isMajorThan(e2));
        assertFalse(e2.isMajorThan(e1));

        e1 = new OrderedEntryStringInt("b", 13);
        e2 = new OrderedEntryStringInt("b", 12);
        assertTrue(e1.isMajorThan(e2));
        assertFalse(e2.isMajorThan(e1));

        e1 = new OrderedEntryStringInt("ba", 13);
        e2 = new OrderedEntryStringInt("b", 12);
        assertTrue(e1.isMajorThan(e2));
        assertFalse(e2.isMajorThan(e1));

        e1 = new OrderedEntryStringInt("bb", 0);
        e2 = new OrderedEntryStringInt("bz", -3);
        assertTrue(e1.isMajorThan(e2));
        assertFalse(e2.isMajorThan(e1));

        e1 = new OrderedEntryStringInt("a", 46);
        e2 = new OrderedEntryStringInt("b", 33);
        assertTrue(e1.isMajorThan(e2));
        assertFalse(e2.isMajorThan(e1));

        e1 = new OrderedEntryStringInt("b", 789);
        e2 = new OrderedEntryStringInt("b", 788);
        assertTrue(e1.isMajorThan(e2));
        assertFalse(e2.isMajorThan(e1));

        //Null values
        assertFalse(e1.isMajorThan(null));
    }

    /**
     * The only constructor of the class. It is declared as private to
     * prevent the default constructor to be created.
     */
    private OrderedEntryStringIntTest() {}
}