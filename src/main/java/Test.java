import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import org.jsoup.select.Elements;

/**
 * @author Loser
 * 爬取指定图组
 */
public class Test {

    // 指定文件保存路径为：E:/struct
    private static String path = "E:\\struct/";
    private static String filenameTemp;

    public static void main (String[] args) throws IOException {
        System.out.println("开始爬取网页内容...");
        // 如果指定的路径不存在，则创建
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        Connection connection = Jsoup.connect("http://tp.wcctv.top/index.php/archives/208/");
        // 获取图片代码
        try {
            Element document = connection.get();
            Elements imgs = document.getElementsByTag("img");
            System.out.println("共检测到下列图片URL：");
            System.out.println("开始下载");
            // 遍历img标签并获得src的属性
            for (Element element : imgs) {
                //获取每个img标签URL "abs:"表示绝对路径
                String imgSrc = element.attr("abs:src");
                // 打印URL
                System.out.println(imgSrc);
                //下载图片到本地
                downImages("E:/struct/"+element.attr("title"), imgSrc);
            }
            System.out.println("图片下载完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void downImages (String filePath, String imgUrl) {
        // 若指定文件夹没有，则先创建
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 截取图片文件名
        String fileName = imgUrl.substring(imgUrl.lastIndexOf('/') + 1, imgUrl.length());

        try {
            // 文件名里面可能有中文或者空格，所以这里要进行处理。但空格又会被URLEncoder转义为加号
            String urlTail = URLEncoder.encode(fileName, "UTF-8");
            // 因此要将加号转化为UTF-8格式的%20
            imgUrl = imgUrl.substring(0, imgUrl.lastIndexOf('/') + 1) + urlTail.replaceAll("\\+", "\\%20");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 写出的路径
        File file = new File(filePath + File.separator + fileName);

        try {
            // 获取图片URL
            URL url = new URL(imgUrl);
            // 获得连接
            URLConnection connection = url.openConnection();
            // 设置10秒的相应时间
            connection.setConnectTimeout(10 * 1000);
            // 获得输入流
            InputStream in = connection.getInputStream();
            // 获得输出流
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            // 构建缓冲区
            byte[] buf = new byte[1024];
            int size;
            // 写入到文件
            while (-1 != (size = in.read(buf))) {
                out.write(buf, 0, size);
            }
            out.close();
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}