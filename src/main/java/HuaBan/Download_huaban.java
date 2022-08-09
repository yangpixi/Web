package HuaBan;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

public class Download_huaban {
    public static void Download(String url, String localPath) throws IOException {
        CloseableHttpClient client = HttpClients.custom()
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.6 Safari/605.1.15")
                .build();
        HttpGet get = new HttpGet(url);
        get.setHeader("Accept", "image/webp,image/png,image/svg+xml,image/*;q=0.8,video/*;q=0.8,*/*;q=0.5");
        get.setHeader("Accept-Encoding", "gzip, deflate, br");
        get.setHeader("Host", "gd-hbimg.huaban.com");
        get.setHeader("Accept-Language", "zh-CN,zh-Hans;q=0.9");
        get.setHeader("Referer", "https://huaban.com/pins/4905315979");
        get.setHeader("Connection", "keep-alive");
        CloseableHttpResponse response = null;
        try {
            response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                InputStream in = entity.getContent();
                byte[] buffer = new byte[1024];
                BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
                FileOutputStream fileOutputStream = new FileOutputStream(new File(localPath));
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                int len = 0;
                while ((len = bufferedInputStream.read(buffer, 0, 1024)) != -1) {
                    bufferedOutputStream.write(buffer, 0, len);
                }
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                in.close();
            }
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            client.close();
            response.close();
        }
    }
}
