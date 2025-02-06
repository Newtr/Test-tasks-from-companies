import java.io.*;
import java.util.*;

public class Main 
{
    public static void main(String[] args) throws IOException 
    {
        BufferedReader br = new BufferedReader(new FileReader("INPUT.TXT"));
        PrintWriter pw = new PrintWriter(new FileWriter("OUTPUT.TXT"));
        
        String[] input = br.readLine().split(" ");
        int a = Integer.parseInt(input[0]);
        int b = Integer.parseInt(input[1]);
        int c = Integer.parseInt(input[2]);
        br.close();
        
        List<Integer> aPerms = getValidPermutations(a);
        List<Integer> bPerms = getValidPermutations(b);
        
        for (int x : aPerms) 
        {
            for (int y : bPerms) 
            {
                if (x + y == c) 
                {
                    pw.println("YES");
                    pw.println(x + " " + y);
                    pw.close();
                    return;
                }
            }
        }
        
        pw.println("NO");
        pw.close();
    }
    
    private static List<Integer> getValidPermutations(int num) 
    {
        char[] digits = String.valueOf(num).toCharArray();
        Set<Integer> permSet = new HashSet<>();
        permute(digits, 0, permSet);
        List<Integer> permList = new ArrayList<>(permSet);
        Collections.sort(permList);
        return permList;
    }
    
    private static void permute(char[] arr, int index, Set<Integer> permSet) 
    {
        if (index == arr.length) 
        {
            String permutedStr = new String(arr).replaceFirst("^0+", "");
            if (!permutedStr.isEmpty()) 
            {
                permSet.add(Integer.parseInt(permutedStr));
            }
            return;
        }
        for (int i = index; i < arr.length; i++) 
        {
            swap(arr, i, index);
            permute(arr, index + 1, permSet);
            swap(arr, i, index);
        }
    }
    
    private static void swap(char[] arr, int i, int j) 
    {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
