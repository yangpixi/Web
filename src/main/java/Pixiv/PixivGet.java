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
                .addHeader("Cookie", "cto_bundle=uWaccV9OcTRqUWhPVW95RDBMRGFDTEJxNUVrYlNKa3BOSVdPa1QzWGRSNkdGYlRlUVZmamwlMkZIZmc4Q0xiMWVYbmRtRjByQTRoN3FhWjhEZk1MMkk5RXZtRWxVd0ZXN1RwU2RpM1lic21nRGVMalBWZUdLbU1aWXRWS3JDMnFzbG90cGdx; adr_id=BCeFLrScHRduRavsP3dkQyVPaShmj3uPMQhUCmH8kyFG5P4I; _im_vid=01G82K54Y55E7D6ZXWKZ4K5RD3; _fbp=fb.1.1657944490766.263670161; __utma=235335808.357361955.1657944490.1658061712.1658066881.10; __utmb=235335808.4.10.1658066881; __utmc=235335808; __utmv=235335808.|2=login%20ever=yes=1^3=plan=premium=1^5=gender=male=1^6=user_id=77388058=1^9=p_ab_id=1=1^10=p_ab_id_2=1=1^11=lang=zh=1; __utmz=235335808.1657944490.1.1.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); _ga=GA1.1.357361955.1657944490; _ga_75BBYNYN9J=GS1.1.1658066879.13.1.1658066929.0; PHPSESSID=77388058_h8hChr11CumxdFvEo1S1AWSL4dWfZwIf; a_type=0; b_type=1; c_type=22; device_token=c9d4f894137cd4b4ec2e4e6f1b5ff1fb; privacy_policy_agreement=0; privacy_policy_notification=0; _gid=GA1.2.12146589.1658061773; __cf_bm=DFI8w954_Wngb0hEo1jRyiq2N1DNrwCrQKtMcEQAzXM-1658066881-0-AUnt+ANIdmtrPhaB+z0Iws9CNXxtS1f2pS7DZgV7UUurbp20q0kWgBwqHaMgY9TjIiafppvevh3LnXHU1ZrFFXOpq4+5nPmvIX/2et6TlSJhvxcq8arl8JF8B/24lXK3yBmkXmhbeBzV72PPiUuyxA9EKJLVyujSQqoodwKIHfr711AFB9q2Hy8PzwPDr+Q3IA==; __utmt=1; pt_47mvrj9g=uid=lLEx2BV/BDYLLNGEFSgpug&nid=0&vid=xjqCuqq1rWkdfqNYNmtxvw&vn=1&pvn=2&sact=1658062831040&to_flag=0&pl=X5HI-3shhhNWgXmBfR1aMQ*pt*1658062765891; pt_s_47mvrj9g=vt=1658062765891&cad=; user_language=zh; tag_view_ranking=_EOd7bsGyl~Lt-oEicbBr~TqiZfKmSCg~ziiAzr_h04~yxXUNz8bXw~bvp7fCUKNH~NW99fuIGG8~7-cdu1A0eA~wZxRo26lqT~RTJMXD26Ak~QIc0RHSvtN~99-dVV-h9A~JrZT530U46~VLDgZNuX1x~0APtr_pGV6~OUF2gvwPef~BSlt10mdnm~dg_40nEhSE~0xsDLqCEW6~ZBoVMjk2oM~bxwl7RUjli~04JSdi09T9~BC84tpS1K_~zr_apdNkN_~_hSAdpN9rx~PFZx9zv6rS~4ItmsOrL7Q~-_-VE3I3zO~LoZYc4_a90~HVS7XBoRND~qtVr8SCFs5~Ib0YGS-7FI~pTIOeNjI5B~vQ8AwyNxCM~w6DOLSTOSN~Oa9b6mEc1T~xa-Uc9GFil~0G1fdsiW-i~uusOs0ipBx~nQRrj5c6w_~XKfFMGUPlw~BcgdSrD7gc~rJlD4WHFPF~lhJLvPIIlV~fg8EOt4owo~Gtd5A8fdBE; _gcl_au=1.1.1376755794.1658061722; login_ever=yes; categorized_tags=pvU1D1orJa; tags_sended=1; QSI_S_ZN_5hF4My7Ad6VNNAi=v:0:0; p_ab_d_id=537171723; p_ab_id=1; p_ab_id_2=1; first_visit_datetime_pc=2022-07-16+13%3A08%3A08; yuid_b=IRiYdEc")
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