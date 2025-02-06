import java.io.*;
import java.util.*;

class Point 
{
    int x, y;
    Point(int x, int y) 
    {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    public static void main(String[] args) throws IOException 
    {
        BufferedReader br = new BufferedReader(new FileReader("INPUT.TXT"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int[][] grid = new int[N][M];
        for (int i = 0; i < N; i++) 
        {
            st = new StringTokenizer(br.readLine());
            int y = N - 1 - i; //преобразуемс в нормальный вид снизу вверх слева направо
            for (int x = 0; x < M; x++) 
            {
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }
        br.close();

        boolean[][] covered = new boolean[M][N];
        String[] result = new String[K + 1];

        for (int rectNum = K; rectNum >= 1; rectNum--) 
        {
            List<Point> cells = new ArrayList<>();
            for (int x = 0; x < M; x++) 
            {
                for (int y = 0; y < N; y++) 
                {
                    if (!covered[x][y] && grid[y][x] == rectNum) 
                    {
                        cells.add(new Point(x, y));
                    }
                }
            }

            if (cells.isEmpty()) 
            {
                throw new RuntimeException("Invalid input for rectangle " + rectNum);
            }

            int minX = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxY = Integer.MIN_VALUE;

            for (Point p : cells) 
            {
                minX = Math.min(minX, p.x);
                maxX = Math.max(maxX, p.x);
                minY = Math.min(minY, p.y);
                maxY = Math.max(maxY, p.y);
            }

            result[rectNum] = minX + " " + minY + " " + (maxX + 1) + " " + (maxY + 1);

            for (int x = minX; x <= maxX; x++) 
            {
                for (int y = minY; y <= maxY; y++) 
                {
                    if (x >= 0 && x < M && y >= 0 && y < N) 
                    {
                        covered[x][y] = true;
                    }
                }
            }
        }

        PrintWriter pw = new PrintWriter(new FileWriter("OUTPUT.TXT"));
        for (int i = 1; i <= K; i++) 
        {
            pw.println(result[i]);
        }
        pw.close();
    }
}