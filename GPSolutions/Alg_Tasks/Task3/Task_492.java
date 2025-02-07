import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Scanner;

public class Task_492 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);

        long x0 = scanner.nextLong();
        long y0 = scanner.nextLong();
        long vx = scanner.nextLong();
        long vy = scanner.nextLong();
        long v = scanner.nextLong();
        long t = scanner.nextLong();
        long d = scanner.nextLong();

        long xt = x0 + vx * t;
        long yt = y0 + vy * t;

        BigDecimal xSquared = BigDecimal.valueOf(xt).pow(2);
        BigDecimal ySquared = BigDecimal.valueOf(yt).pow(2);
        BigDecimal sum = xSquared.add(ySquared);

        BigDecimal distance = sum.sqrt(new MathContext(30));

        BigDecimal vt = BigDecimal.valueOf(v).multiply(BigDecimal.valueOf(t));
        BigDecimal dBig = BigDecimal.valueOf(d);

        if (distance.compareTo(vt.add(dBig)) > 0 || distance.compareTo(dBig.subtract(vt)) < 0) 
        {
            System.out.println("NO");
        } 
        else 
        {
            System.out.println("YES");
        }

        scanner.close();
    }
}
