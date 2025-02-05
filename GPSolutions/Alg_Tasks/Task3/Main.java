import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("INPUT.TXT"));
        PrintWriter pw = new PrintWriter(new FileWriter("OUTPUT.TXT"));

        // Считываем входные данные
        String[] line1 = br.readLine().split(" ");
        int x0 = Integer.parseInt(line1[0]);
        int y0 = Integer.parseInt(line1[1]);

        String[] line2 = br.readLine().split(" ");
        int Vx = Integer.parseInt(line2[0]);
        int Vy = Integer.parseInt(line2[1]);

        String[] line3 = br.readLine().split(" ");
        int V = Integer.parseInt(line3[0]);
        int t = Integer.parseInt(line3[1]);
        int d = Integer.parseInt(line3[2]);

        // Координаты цели через t секунд
        long xt = x0 + (long) Vx * t;
        long yt = y0 + (long) Vy * t;

        // Расстояние до цели через t секунд
        double dt = Math.sqrt(xt * xt + yt * yt);

        // Проверяем достижимость
        if (Math.abs(dt - d) <= V * t) {
            pw.println("YES");
        } else {
            pw.println("NO");
        }

        pw.close();
    }
}
