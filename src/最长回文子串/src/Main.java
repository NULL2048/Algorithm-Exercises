public class Main
{
    public static void main(String[] args) throws IOException
    {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        //PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        int a, b;
        while(in.nextToken() != StreamTokenizer.TT_EOF)  // 表示读到了文件末尾
        {
            a = (int)in.nval;
            in.nextToken();
            b = (int)in.nval;
            //out.println(a + b);
            System.out.println("a + b = "+(a+b));
        }
        //out.flush();
    }
