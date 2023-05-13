import java.util.*;
import java.util.regex.Pattern;

class MyOtherEntry extends AbstractMap.SimpleEntry<String, Integer>
{
    MyOtherEntry(String s, int a)
    {
        super(s, a);
    }

    public boolean isMajorThan(MyOtherEntry a)
    {
        if (this.getValue() > a.getValue())
        {
            return true;
        }
        else if (this.getValue() == a.getValue() && (this.getKey().compareToIgnoreCase(a.getKey()) < 0))
        {
            return true;
        }
        return false;
    }
}

public class MyPriorityQueue {
    public static ArrayList<MyOtherEntry> getTop(String[] text)
    {
        TreeMap<String, Integer> mappona = new TreeMap<>();
        for (int i = 0; i < text.length; i++) {
            TreeMap<String, Integer> map = new TreeMap<>();
            Scanner sc = new Scanner(text[i]);
            sc.useDelimiter("[\\W\\s]");
            while (sc.hasNext()) {
                String s = sc.next();
                map.put(s.toLowerCase(), 1);
            }
            for (Map.Entry<String, Integer> el : map.entrySet()) {
                Integer val = mappona.get(el.getKey());
                if (val == null) {
                    val = 0;
                }
                mappona.put(el.getKey(), val + 1);
            }
            sc.close();
        }
        ArrayList<MyOtherEntry> max = new ArrayList<MyOtherEntry>(50);
        for (Map.Entry<String, Integer> el : mappona.entrySet()) {
            addOrdered(max, el);
        }
        return max;
    }
    
    private static void addOrdered(ArrayList<MyOtherEntry> vec, Map.Entry<String, Integer> entry)
    {
        MyOtherEntry el = new MyOtherEntry(entry.getKey(), entry.getValue());
        int mapsize = vec.size();
        if (mapsize < 50)
        {
            // Devo aggiungerlo
            int i = 1;
            while(i < mapsize && vec.get(i-1).isMajorThan(el))
            {
                i++;
            }
            if (i >= mapsize)
            {
                vec.add(el);
                return;
            }
            MyOtherEntry old = vec.get(i - 1);
            vec.set(i - 1, el);
            i++;
            while (i < mapsize)
            {
                MyOtherEntry new_old = vec.get(i);
                vec.set(i - 1, old);
                old = new_old;
                i++;
            }
            vec.add(old);
        }
        else
        {
            int i = 49;
            while (i >= 0 && el.isMajorThan(vec.get(i)))
            {
                if (i == 49) {
                    i--;
                    continue;
                }
                vec.set(i + 1, vec.get(i));
                i--;
            }
            if (i != 49)
            {
                vec.set(i + 1, el);
            }
        }
    }
}