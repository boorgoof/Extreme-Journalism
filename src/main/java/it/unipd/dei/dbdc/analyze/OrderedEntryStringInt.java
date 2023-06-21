package it.unipd.dei.dbdc.analyze;

import java.util.*;

/**
 * This class represents an entry for a map, which is ordered on the base of his value and key.
 * The key is a {@link String} and the value is a {@link Integer}.
 *
 * @see Map.Entry
 * @see AbstractMap.SimpleEntry
 */
public class OrderedEntryStringInt extends AbstractMap.SimpleEntry<String, Integer> implements Comparable<OrderedEntryStringInt>
{
    /**
     * Constructor with a String and an Integer
     *
     * @param s The key of the entry
     * @param a The value of the entry
     */
    public OrderedEntryStringInt(String s, int a)
    {
        super(s, a);
    }

    /**
     * Constructor with a Map.Entry
     *
     * @param e The entry from which this entry has to take the key and value.
     */
    public OrderedEntryStringInt(Map.Entry<String, Integer> e)
    {
        super(e.getKey(), e.getValue());
    }

    /**
     * Comparator between two {@link OrderedEntryStringInt}: a {@link OrderedEntryStringInt} is major than
     * the other only if his value is major or his value is the same and his key is alphabetically previous
     * than the other's key.
     *
     * @param a The other entry
     * @return true if this entry is major than the one passed as a parameter
     */
    public boolean isMajorThan(OrderedEntryStringInt a)
    {
        if (this.getValue() > a.getValue())
        {
            return true;
        }
        else return this.getValue().equals(a.getValue()) && (this.getKey().compareToIgnoreCase(a.getKey()) < 0);
    }

    @Override
    public int compareTo(OrderedEntryStringInt o) {
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
        if (!(o instanceof OrderedEntryStringInt))
        {
            return false;
        }
        OrderedEntryStringInt other = (OrderedEntryStringInt) o;
        return this.getValue().equals(other.getValue()) && this.getKey().equals(other.getKey());
    }
}