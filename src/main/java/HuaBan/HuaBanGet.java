package HuaBan;

import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;


public class HuaBanGet implements PageProcessor {

    private static final String ORGINAL_URL = "https://api.huaban.com/discovery/anime.*";
    private static final String IMAGE_PAGE = "https://api.huaban.com/pins/.*";


    private static Site site;

    {
        site = Site.me().setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.6 Safari/605.1.15")
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Origin", "https://huaban.com")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("Host", "api.huaban.com")
                .addHeader("Accept-Language", "zh-CN,zh-Hans;q=0.9")
                .addHeader("Referer", "https://huaban.com/discovery/anime")
                .addHeader("Connection", "keep-alive")
                .setSleepTime(5000)
                .setRetryTimes(10)
                ;
    }

    @Override
    public void process(Page page) {
        if (page.getUrl().regex(ORGINAL_URL).match()) {
            JsonPathSelector pin_id = new JsonPathSelector("$.pins[*].pin_id");
            List<String> ids = pin_id.selectList(page.getRawText());
            String NextLink = "https://api.huaban.com/discovery/anime?limit=20" + "max=" + ids.get(ids.size() - 1);
            page.addTargetRequest(NextLink);
        }
        if (page.getUrl().regex(ORGINAL_URL).match()) {
            JsonPathSelector pin_id = new JsonPathSelector("$.pins[*].pin_id");
            List<String> ids = pin_id.selectList(page.getRawText());
            for (String i :
                    ids) {
                page.addTargetRequest("https://api.huaban.com/pins/" + i + "?pins=20");
            }
        }
        if (page.getUrl().regex(IMAGE_PAGE).match()) {
            JsonPathSelector key_get = new JsonPathSelector("$.pin.file.key");
            JsonPathSelector id_get = new JsonPathSelector("$.pin.pin_id");
            String id = id_get.select(page.getRawText());
            String key = key_get.select(page.getRawText());
            String image = "https://gd-hbimg.huaban.com/" + key;
            try {
                Download_huaban.Download(image, "/Users/yangpixi/Documents/test/" + id + ".jpeg");
                System.out.println("Downloading Success");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {

            }
        }

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new HuaBanGet()).addUrl("https://api.huaban.com/discovery/anime?limit=20")
                .thread(2)
                .run();
    }
}
