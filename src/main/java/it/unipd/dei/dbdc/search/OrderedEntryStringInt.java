package it.unipd.dei.dbdc.search;

import java.util.*;


public class OrderedEntryStringInt extends AbstractMap.SimpleEntry<String, Integer>
{
    public OrderedEntryStringInt(String s, int a)
    {
        super(s, a);
    }

    public OrderedEntryStringInt(Map.Entry<String, Integer> e)
    {
        super(e.getKey(), e.getValue());
    }

    public boolean isMajorThan(OrderedEntryStringInt a)
    {
        if (this.getValue() > a.getValue())
        {
            return true;
        }
        else return this.getValue().equals(a.getValue()) && (this.getKey().compareToIgnoreCase(a.getKey()) < 0);
    }
}