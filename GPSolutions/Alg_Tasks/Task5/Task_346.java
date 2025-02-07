import java.util.*;

public class Task_346 
{
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        int c = sc.nextInt();
        
        int[] aDigits = Integer.toString(a).chars().map(ch -> ch - '0').toArray();
        Arrays.sort(aDigits);
        
        boolean found = false;
        do 
        {
            int x = arrayToNumber(aDigits);
            int y = c - x;
            if (y < 0) continue;
            
            if (isPermutation(y, b)) 
            {
                System.out.println("YES");
                System.out.println(x + " " + y);
                found = true;
                break;
            }
        } 
        while (nextPermutation(aDigits));
        
        if (!found) 
        {
            System.out.println("NO");
        }
    }
    
    private static int arrayToNumber(int[] digits) 
    {
        StringBuilder sb = new StringBuilder();
        for (int d : digits) 
        {
            sb.append(d);
        }
        String s = sb.toString().replaceFirst("^0+(?!$)", "");
        if (s.isEmpty()) 
        {
            return 0;
        }
        return Integer.parseInt(s);
    }
    
    private static boolean isPermutation(int y, int originalB) 
    {
        String bStr = Integer.toString(originalB);
        String yStr = Integer.toString(y);
        if (yStr.length() < bStr.length()) 
        {
            yStr = String.format("%0" + bStr.length() + "d", y);
        } 
        else if (yStr.length() > bStr.length()) 
        {
            return false;
        }
        
        int[] count = new int[10];
        for (char ch : bStr.toCharArray()) 
        {
            count[ch - '0']++;
        }
        for (char ch : yStr.toCharArray()) 
        {
            count[ch - '0']--;
        }
        for (int cnt : count) 
        {
            if (cnt != 0) 
            {
                return false;
            }
        }
        return true;
    }
    
    private static boolean nextPermutation(int[] array) 
    {
        int i = array.length - 2;
        while (i >= 0 && array[i] >= array[i + 1]) 
        {
            i--;
        }
        if (i < 0) 
        {
            return false;
        }
        
        int j = array.length - 1;
        while (array[j] <= array[i]) 
        {
            j--;
        }
        swap(array, i, j);
        reverse(array, i + 1);
        return true;
    }
    
    private static void swap(int[] array, int i, int j) 
    {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    private static void reverse(int[] array, int start) 
    {
        int left = start, right = array.length - 1;
        while (left < right) 
        {
            swap(array, left, right);
            left++;
            right--;
        }
    }
}
