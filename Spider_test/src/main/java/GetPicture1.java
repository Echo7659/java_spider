import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/*
                       _oo0oo_
                      o8888888o
                      88" . "88
                      (| -_- |)
                      0\  =  /0
                    ___/`---'\___
                  .' \\|     |// '.
                 / \\|||  :  |||// \
                / _||||| -:- |||||- \
               |   | \\\  - /// |   |
               | \_|  ''\---/''  |_/ |
               \  .-\__  '-'  ___/-. /
             ___'. .'  /--.--\  `. .'___
          ."" '<  `.___\_<|>_/___.' >' "".
         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
         \  \ `_.   \_ __\ /__ _/   .-` /  /
     =====`-.____`.___ \_____/___.-`___.-'=====
                       `=---='


     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

               佛祖保佑       永不宕机     永无BUG

               佛曰:
               写字楼里写字间，写字间里程序员；
               程序人员写程序，又拿程序换酒钱。
               酒醒只在网上坐，酒醉还来网下眠；
               酒醉酒醒日复日，网上网下年复年。
               但愿老死电脑间，不愿鞠躬老板前；
               奔驰宝马贵者趣，公交自行程序员。
               别人笑我忒疯癫，我笑自己命太贱；
               不见满街漂亮妹，哪个归得程序员？
*/

public class GetPicture1 {

    @Test
    public void test2()  {
        List<String> imgs = new ArrayList<>();
        //爬取的页数 5-208
        for (int j = 8; j <= 8; j++) {
            try {
                String url2 = "http://tp.wcctv.top/index.php/archives/"+j;
                URL u2 = new URL(url2);
                Document doc2 = null;
                doc2 = Jsoup.parse(u2, 5000);
                //图片标签className
                Elements img2 = doc2.getElementsByClass("post-item col-xs-6 col-sm-4 col-md-3").select("img.post-item-img");
                for (Element element : img2) {
                    String imgSrc = element.attr("abs:src");
                    downImages("E:/struct/"+(String)img2.attr("title"), imgSrc);
                    imgs.add(element.attr("src"));
                }
            } catch (IOException e) {

            }
        }

    }

    public static void downImages (String filePath, String imgUrl)  {
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

        }
        // 写出的路径
        File file = new File(filePath + File.separator + fileName);

        try {
            java.net.URLDecoder.decode(imgUrl, "UTF-8");
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

        } catch (IOException e) {

        }
    }

}