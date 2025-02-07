import java.io.*;
import java.util.*;

public class Task_193 
{
    public static void main(String[] args) 
    {
        try (Scanner in = new Scanner(new File("input.txt"));
             PrintWriter out = new PrintWriter(new File("output.txt"))) 
             {

            int n = in.nextInt();
            int m = in.nextInt();
            int k = in.nextInt();

            int[] left = new int[k + 1];
            int[] right = new int[k + 1];
            int[] up = new int[k + 1];
            int[] down = new int[k + 1];

            Arrays.fill(left, m + 1);
            Arrays.fill(right, 0);
            Arrays.fill(up, n + 1);
            Arrays.fill(down, 0);

            for (int z = 0; z < n; z++) 
            {
                for (int x = 0; x < m; x++) 
                {
                    int a = in.nextInt();
                    if (a == 0) continue;

                    left[a] = Math.min(left[a], x);
                    right[a] = Math.max(right[a], x);
                    up[a] = Math.min(up[a], n - z - 1);
                    down[a] = Math.max(down[a], n - z - 1);

                    left[0] = Math.min(left[0], x);
                    right[0] = Math.max(right[0], x);
                    up[0] = Math.min(up[0], n - z - 1);
                    down[0] = Math.max(down[0], n - z - 1);
                }
            }

            for (int z = 1; z <= k; z++) 
            {
                if (left[z] < m + 1) 
                {
                    out.println(left[z] + " " + up[z] + " " + (right[z] + 1) + " " + (down[z] + 1));
                } 
                else 
                {
                    out.println(left[0] + " " + up[0] + " " + (right[0] + 1) + " " + (down[0] + 1));
                }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
