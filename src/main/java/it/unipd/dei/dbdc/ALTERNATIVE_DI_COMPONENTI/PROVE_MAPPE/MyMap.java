package it.unipd.dei.dbdc.ALTERNATIVE_DI_COMPONENTI.PROVE_MAPPE;

import java.util.*;

class MyEntry extends AbstractMap.SimpleEntry<Integer, String> implements Comparable
{
    public MyEntry(int a, String b)
    {
        super(a, b);
    }

    public int compareTo(Object a)
    {
        MyEntry a2 = (MyEntry) a;
        if (a2.getKey() != this.getKey())
        {
            return a2.getKey() - this.getKey();
        }
        return (this.getValue()).compareToIgnoreCase(a2.getValue());
    }
}

public class MyMap {
    public static ArrayList<Map.Entry<Integer, String>> getTop(String[] text)
    {
        TreeMap<String, Map.Entry<Integer, String>> mappona = new TreeMap<>();
        for(int i = 0; i<text.length; i++)
        {
            TreeMap<String, Integer> map = new TreeMap<>();
            Scanner sc = new Scanner(text[i]);

            // Bisogna mettere ++ perché così rileva i casi come spazio seguito da " o altre cose non alfanumeriche
            sc.useDelimiter("[^’'\\-a-zA-Z]+");
            while (sc.hasNext()) {
                String s = sc.next();
                map.put(s.toLowerCase(), 1);
            }
            for (java.util.Map.Entry<String, Integer> el : map.entrySet()) {
                Map.Entry<Integer, String> val = mappona.get(el.getKey());
                if (val == null) {
                    val = new MyEntry(0, el.getKey());
                }
                Integer v = val.getKey()+1;
                mappona.put(el.getKey(), new MyEntry(v, el.getKey()));
            }
            sc.close();
        }
        PriorityQueue<Map.Entry<Integer, String>> pq = new PriorityQueue<>(mappona.values());
        ArrayList<Map.Entry<Integer, String>> max = new ArrayList<Map.Entry<Integer, String>>(50);
        for (int i = 0; i < 50; i++)
        {
            max.add(i, pq.poll());
        }        
        return max;
    }
}