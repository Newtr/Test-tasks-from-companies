import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("INPUT.TXT"));
        PrintWriter pw = new PrintWriter(new FileWriter("OUTPUT.TXT"));
        
        String[] input = br.readLine().split("/");
        int A = Integer.parseInt(input[0]);
        int B = Integer.parseInt(input[1]);
        
        String result = divide(A, B);
        pw.println(result);
        
        br.close();
        pw.close();
    }

    private static String divide(int A, int B) {
        StringBuilder result = new StringBuilder();
        int integerPart = A / B;
        int remainder = A % B;
        result.append(integerPart);
        
        if (remainder == 0) {
            return result.toString(); // Если делится нацело
        }
        
        result.append(".");
        Map<Integer, Integer> remainderMap = new HashMap<>();
        StringBuilder fractionalPart = new StringBuilder();
        
        while (remainder != 0) {
            if (remainderMap.containsKey(remainder)) {
                int start = remainderMap.get(remainder);
                fractionalPart.insert(start, "(");
                fractionalPart.append(")");
                break;
            }
            remainderMap.put(remainder, fractionalPart.length());
            remainder *= 10;
            fractionalPart.append(remainder / B);
            remainder %= B;
        }
        
        result.append(fractionalPart);
        return result.toString();
    }
}
