package it.unipd.dei.dbdc.Search_terms;

import java.util.AbstractMap;

public class MapEntrySI extends AbstractMap.SimpleEntry<String, Integer>
{
    MapEntrySI(String s, int a)
    {
        super(s, a);
    }

    public boolean isMajorThan(MapEntrySI a)
    {
        if (this.getValue() > a.getValue())
        {
            return true;
        }
        else return this.getValue().equals(a.getValue()) && (this.getKey().compareToIgnoreCase(a.getKey()) < 0);
    }
}