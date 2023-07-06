package it.unipd.dei.dbdc.analysis.src_strategies;

import it.unipd.dei.dbdc.analysis.OrderedEntryStringInt;

import java.util.Map;

/**
 * This is only a utility class that is used for the tests.
 * It extends {@link OrderedEntryStringInt}, as it is the only way to use it inside an {@link it.unipd.dei.dbdc.analysis.interfaces.Analyzer},
 * and defines an order that is used inside the {@link PriorityQueueSplitAnalyzer} class.
 * It also implements {@link Comparable} to be used inside a {@link java.util.PriorityQueue}.
 * There is no test for this class, as it is really easy.
 */
public class OrderedEntryComparable extends OrderedEntryStringInt implements Comparable<OrderedEntryComparable>
{
    /**
     * The constructor of the class.
     *
     * @param e A {@link Map.Entry}. It uses the constructor of {@link OrderedEntryStringInt}
     */
    public OrderedEntryComparable(Map.Entry<String, Integer> e)
    {
        super(e.getKey(), e.getValue());
    }

    /**
     * The function that defines the order. As we want to use a {@link java.util.PriorityQueue} that is a max priority queue,
     * but the one used by java is only a min priority queue, the order is inverted: it returns -1 if this object is
     * major to the one passed as a parameter, 1 if it is minor, 0 if it is equal. It uses {@link OrderedEntryStringInt#isMajorThan(OrderedEntryStringInt)}
     *
     * @param o A {@link OrderedEntryComparable} to compare to.
     * @return -1 if this object is major to the one passed as a parameter, 1 if it is minor, 0 if it is equal
     */
    @Override
    public int compareTo(OrderedEntryComparable o) {
        //Return the opposite of what it should be because we use it in a min priority queue to have a max priority queue
        if (this.isMajorThan(o))
        {
            return -1;
        } else if (this.equals(o)) {
            return 0;
        }
        return 1;
    }

    /**
     * Overrides the function of {@link Object}.
     *
     * @param o A {@link OrderedEntryComparable} to compare to.
     * @return true only if the parameter passed has the same key and value of this one.
     */
    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof OrderedEntryComparable))
        {
            return false;
        }
        OrderedEntryComparable other = (OrderedEntryComparable) o;
        return this.getValue().equals(other.getValue()) && this.getKey().equals(other.getKey());
    }
}