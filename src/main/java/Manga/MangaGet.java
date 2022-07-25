package Manga;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MangaGet implements PageProcessor {
    private static String MangaName;
    private static boolean isMake = false;
    private static final String START_URL = "https://www.biliplus.com/manga/\\?act=detail\\&mangaid=.*";

    private static final String TITLE = "title=\"\\d*\"";
    private static Site site;

    {
        site = Site.me()
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.5 Safari/605.1.15")
                .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .addHeader("Host","www.biliplus.com")
                .addHeader("Accept-Language", "zh-CN,zh-Hans;q=0.9")
                .addHeader("Cookie", "你的Cookie")
                .addHeader("Connection", "keep-alive")
                .setSleepTime(50000)
                .setRetryTimes(3)
        ;
    }

    @Override
    public void process(Page page) {
        if (isMake == false) {
            MangaUtils.MangaDirMake(page);
            isMake = true;
            MangaName = MangaUtils.GetName(page);
        }
        if (page.getUrl().regex(START_URL).match()) {
            String id = page.getUrl().regex("mangaid=.*").get();
            String mangaid = id.substring(8);
            List<String> epids = page.getHtml().regex("ID: \\S*").all();
            for (String str:
                    epids) {
                String epid = str.substring(4);
                page.addTargetRequest("https://www.biliplus.com/manga/?act=read&mangaid="+ mangaid +"&epid=" + epid);
            }
        }else {
            System.out.println("进入程序");
            List<String> images = page.getHtml().regex("_src=\\S*\"").replace("amp;", "").all();
            List<String> titles = page.getHtml().regex(TITLE).all();
            String DownloadPath = MangaUtils.PageDirMake(page, MangaName);
            for (int i = 0; i < images.size(); i++) {
                String url = images.get(i).substring(6, images.get(i).length() - 1);
                String title = titles.get(i).substring(7, titles.get(i).length() - 1);
                try {
                    System.out.println("开始下载");
                    Download_manga.Download(url, DownloadPath + title + ".jpg");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        System.out.println("请输入漫画链接:");
        Scanner scanner = new Scanner(System.in);
        Spider.create(new MangaGet())
                .addUrl(scanner.next())
                .thread(3)
                .run();
    }
}
