package Manga;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

public class Download_manga {
    public static void Download(String url, String localPath) throws IOException {
        System.out.println(url);
        CloseableHttpClient client = HttpClients.custom()
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.5 Safari/605.1.15")
                .build();
        HttpGet get = new HttpGet(url);
        get.setHeader("Accept","image/webp,image/png,image/svg+xml,image/*;q=0.8,video/*;q=0.8,*/*;q=0.5");
        get.setHeader("Cookie", "manga-order=last_read; level=5; secure_header=access_key%2Cmid%2Clevel%2Csecurev2_time; securev2=7419e470; securev2_time=1658548762; vipDueDate=0; access_key=92a08ee3be66ca88fdff662cdc942971; expires=1674030899; face=http%3A%2F%2Fi2.hdslb.com%2Fbfs%2Fface%2Fbaef98cdc688a1e8ae8452fb1748642ce0e1b55f.jpg; login=2; mid=59262299; passlogin=1; refresh_token=a3723b5f1c843a6e5cea1deb2dbced71; uname=%E7%BE%8E%E5%88%A9%E5%9D%9A%E7%9A%84%E9%A6%99%E8%95%89%E7%8E%8B; adfly_video_vaild=1659083702%2C8bcdfb52; _ga=GA1.2.1069381401.1658478878; _gid=GA1.2.1273414434.1658478878; UsingLang=zh; visiturl=%2Fmanga%2F; bp_user_id=psd4AXEKgJ7TPLNt_oa2sA; freetoview=3");
        get.setHeader("Accept-Encoding", "gzip, deflate, br");
        get.setHeader("Accept-Language", "zh-CN,zh-Hans;q=0.9");
        get.setHeader("Connection", "keep-alive");
        get.setHeader("Host", "manga.hdslb.com");
        CloseableHttpResponse response = null;
        try {
            response = client.execute(get);
            System.out.println(response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream in = entity.getContent();
                    try {
                        byte[] buffer = new byte[1024];
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
                        int len = 0;
                        FileOutputStream fileOutputStream = new FileOutputStream(new File(localPath));
                        BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream);
                        while ((len = bufferedInputStream.read(buffer, 0 ,1024)) != -1) {
                            outputStream.write(buffer, 0 , len);
                        }
                        outputStream.flush();
                        outputStream.close();
                     } catch (IOException e) {
                        throw e;
                    } finally {
                        in.close();
                    }
                }
            }
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            response.close();
            client.close();
        }
    }

    public static void main(String[] args) {
        try {
            Download("https://manga.hdslb.com/bfs/manga/a91ed99d5f6cb3361e99f6bfb4cc4cb57630bf18.jpg@1400w.jpg?token=54d21c654564ddc4bdbb40ec6b05fb3d&ts=62dabc03&no_cache=1",
                    "/Users/yangpixi/Documents/test.jpg");
            System.out.println(111);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
