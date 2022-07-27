package Pixiv;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.io.*;
import java.util.List;

public class PixivGet implements PageProcessor {
    private Site site;

    {
        try
    {
        site = Site.me()
                .addCookie("PHPSESSID", "77388058_h8hChr11CumxdFvEo1S1AWSL4dWfZwIf")
                .setRetryTimes(3)
                .addHeader("Cookie", "你的Cookie")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .addHeader("Accept-Language", "zh-CN,zh-Hans;q=0.9")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("Connection", "keep-alive")
                .addHeader("referer", "https://accounts.pixiv.net/")
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.5 Safari/605.1.15")
                .setSleepTime(7000);
    } catch(
    Exception e)

    {
        throw new RuntimeException(e);
    }

}

    private static final String LIST_URL = "https://www.pixiv.net/ajax/search/.*";

    @Override
    public void process(Page page) {
        if (page.getUrl().regex(LIST_URL).match()) {
            List<String> all = page.getHtml().regex("\"id\":\"[0-9]+\"").all();
            for (String str :
                    all) {
                String id = str.substring(6, str.length() - 1);
                page.addTargetRequest("https://www.pixiv.net/ajax/illust/" + id + "?lang=zh");
            }
        } else {
            JsonPathSelector jsonPathSelector = new JsonPathSelector("$.body.urls.original");
            String select = jsonPathSelector.select(page.getRawText());
            JsonPathSelector jsonPathSelector1 = new JsonPathSelector("$.body.illustTitle");
            String select1 = jsonPathSelector1.select(page.getRawText());
            String imageId = select.substring(select.length() - 14);
            System.out.println(select);
            try {
                Download_pixiv.Download(select, "/Users/yangpixi/Documents/GenShin/" + select1 + "_" + imageId);
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
//        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 10; i++) {
            Spider.create(new PixivGet())
                    .addUrl("https://www.pixiv.net/ajax/search/artworks/%E5%8E%9F%E7%A5%9E?word=%E5%8E%9F%E7%A5%9E&order=popular_d&mode=safe&p="+ i +"&s_mode=s_tag_full&type=all&lang=zh")
//                .addUrl("https://www.pixiv.net/ajax/search/illustrations/%E7%BE%8E%E5%B0%91%E5%A5%B3?word=%E7%BE%8E%E5%B0%91%E5%A5%B3&order=date_d&mode=safe&p=" + scanner.next() +"&s_mode=s_tag_full&type=illust_and_ugoira&lang=zh")
                    .thread(3)
                    .run();
        }
    }
}