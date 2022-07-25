package Pixiv;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

public class Download_pixiv {
    public static void Download(String url, String localPath) throws IOException {
        CloseableHttpClient client = HttpClients.custom()
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.5 Safari/605.1.15")
                .build();
        HttpGet get = new HttpGet(url);
        get.setHeader("Accept","image/webp,image/png,image/svg+xml,image/*;q=0.8,video/*;q=0.8,*/*;q=0.5");
        get.setHeader("Accept-Encoding", "gzip, deflate, br");
        get.setHeader("Host", "i.pximg.net");
        get.setHeader("Accept-Language", "zh-CN,zh-Hans;q=0.9");
        get.setHeader("Referer", "https://www.pixiv.net/");
        get.setHeader("Connection", "keep-alive");
        CloseableHttpResponse response = null;
        try {
            response = client.execute(get);
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
    
}
