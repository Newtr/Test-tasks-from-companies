import java.io.*;

public class Main 
{
    public static void main(String[] args) throws IOException 
    {
        BufferedReader br = new BufferedReader(new FileReader("INPUT.TXT"));
        String num1 = br.readLine();
        String num2 = br.readLine();
        br.close();

        String result = mergeNumbers(num1, num2);

        PrintWriter pw = new PrintWriter(new FileWriter("OUTPUT.TXT"));
        pw.print(result);
        pw.close();
    }

    private static String mergeNumbers(String num1, String num2) 
    {
        StringBuilder merged = new StringBuilder();
        int i = 0, j = 0;

        while (i < num1.length() && j < num2.length()) 
        {
            char digit1 = num1.charAt(i);
            char digit2 = num2.charAt(j);

            if (digit1 < digit2) 
            {
                merged.append(digit1);
                i++;
            } 
            else if (digit1 > digit2) 
            {
                merged.append(digit2);
                j++;
            } 
            else 
            {
                String remaining1 = num1.substring(i);
                String remaining2 = num2.substring(j);
                if ((remaining1 + remaining2).compareTo(remaining2 + remaining1) < 0) 
                {
                    merged.append(digit1);
                    i++;
                } 
                else 
                {
                    merged.append(digit2);
                    j++;
                }
            }
        }

        merged.append(num1.substring(i));
        merged.append(num2.substring(j));

        return merged.toString();
    }
}