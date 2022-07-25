package Konachan;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;
import java.util.List;

public class KonachanGet implements PageProcessor {

    private static Site site;

    {
        site = Site.me().setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.5 Safari/605.1.15")
                ;
    }

    @Override
    public void process(Page page) {
        List<String> all = page.getHtml().regex("download_file\\('https://konachan.net/image/\\S*'\\)").all();
        for (String str :
                all) {
            int start = str.indexOf(',');
            String image_url = str.substring(15, start - 1);
            String id = str.substring(start + 2, str.length() - 2);
            String suffix = image_url.substring(image_url.length() - 4);
            try {
                System.out.println("-----------正在下载：" + id + "号图片---------");
                Download_konachan.Download(image_url, "/Users/yangpixi/Documents/test/" + id + suffix);
                System.out.println("url:" + image_url);
                System.out.println("Successfully");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        for (int i = 5; i <= 10; i++) {
            Spider.create(new KonachanGet())
                    .addUrl("https://gelbooru.wjcodes.com/index.php?tag=rating:safe&p=" + i)
                    .thread(3)
                    .run();
        }

    }

}
