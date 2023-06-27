package it.unipd.dei.dbdc.analysis.src_strategies;

import it.unipd.dei.dbdc.analysis.OrderedEntryStringInt;

import java.util.Map;

//This is only a utility class that is used for the tests. It extends OrderedEntryStringInt, as it is the only way to implement an Analyzer
public class OrderedEntryComparable extends OrderedEntryStringInt implements Comparable<OrderedEntryComparable>
{
    public OrderedEntryComparable(Map.Entry<String, Integer> e)
    {
        super(e.getKey(), e.getValue());
    }

    @Override
    public int compareTo(OrderedEntryComparable o) {
        if (this.isMajorThan(o))
        {
            return 1;
        } else if (this.equals(o)) {
            return 0;
        }
        return -1;
    }

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