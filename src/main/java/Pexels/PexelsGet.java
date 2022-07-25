package Pexels;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.io.IOException;
import java.util.List;

public class PexelsGet implements PageProcessor {


    private static Site site;

    {
        site = Site.me().setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.5 Safari/605.1.15")
                .addHeader("Accept", "*/*")
                .addHeader("Content-Type", "application/json")
                .addHeader("Host", "www.pexels.com")
                .addHeader("Accept-Language" ,"zh-CN,zh-Hans;q=0.9")
                .addHeader("Referer", "https://www.pexels.com/zh-cn/search/%E6%99%AF%E8%A7%80/")
                .addHeader("secret-key", "H2jk9uKnhRmL6WPwh89zBezWvr")
                .addHeader("Connection", "keep-alive")
                .addHeader("sentry-trace", "82cc6ff0be714dba9d91c4f1260c23fb-93d74ea8b7af64f2-0")
                .setRetryTimes(3)
                .setTimeOut(4000)
                .setSleepTime(5000)
        ;
    }

    @Override
    public void process(Page page) {
        JsonPathSelector S1 = new JsonPathSelector("$.data[*].id");
        List<String> ids = S1.selectList(page.getRawText());
        JsonPathSelector S2 = new JsonPathSelector("$.data[*].attributes.title");
        List<String> names = S2.selectList(page.getRawText());
        for (int i = 0; i < ids.size(); i++) {
            String url = "https://images.pexels.com/photos/"+ ids.get(i) +"/pexels-photo-"+ ids.get(i) +".jpeg";
            try {
                Download_pexels.Download(url, "/Users/yangpixi/Documents/Cloud/" + names.get(i) + ".jpeg");
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
        for (int i = 3; i < 10; i++) {
            Spider.create(new PexelsGet())
                    .addUrl("https://www.pexels.com/zh-cn/api/v3/search/photos?page="+ i +"&per_page=24&query=%E5%A4%A9%E7%A9%BA&orientation=all&size=all&color=all")
                    .thread(3)
                    .run();
        }

    }
}
