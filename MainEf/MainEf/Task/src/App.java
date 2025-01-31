import java.util.ArrayList;
import java.util.Arrays;

public class App {

    public static void merge(ArrayList<Integer> a, ArrayList<Integer> b) 
    {

    int ArraySize = a.size() + b.size();
    int i = 0;
    int j = 0;

    ArrayList<Integer> MergedArray = new ArrayList<>(ArraySize);
    
    while (i < a.size() && j < b.size()) 
    {
        if (a.get(i) <= b.get(j)) 
        {
            MergedArray.add(a.get(i));
            i++;
        } 
        else 
        {
            MergedArray.add(b.get(j));
            j++;
        }
    }
    
    while (i < a.size()) 
    {
        MergedArray.add(a.get(i));
        i++;
    }
    
    while (j < b.size()) 
    {
        MergedArray.add(b.get(j));
        j++;
    }
    
    a.clear();
    a.addAll(MergedArray);

    }

    public static void main(String[] args) throws Exception {
        ArrayList<Integer> a = new ArrayList<>(Arrays.asList(1, 7, 9));
        ArrayList<Integer> b = new ArrayList<>(Arrays.asList(2, 6, 8));

        merge(a, b);

        System.out.println(a);
        System.out.println(b);
    }
}
