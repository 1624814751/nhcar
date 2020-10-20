package loopj.android.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class WebImage implements SmartImage {
    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 10000;

    private static WebImageCache webImageCache;

    private String url;

    public WebImage(String url) {
        this.url = url;
    }

    public Bitmap getBitmap(Context context) {
        // Don't leak context
        if(webImageCache == null) {
            webImageCache = new WebImageCache(context);
        }

        // Try getting bitmap from cache first
        Bitmap bitmap = null;
        if(url != null) {
            bitmap = webImageCache.get(url);
            if(bitmap == null) {
                bitmap = getBitmapFromUrl(url);
                if(bitmap != null){
                    webImageCache.put(url, bitmap);
                }
            }
        }

        return bitmap;
    }

    private Bitmap getBitmapFromUrl(String url) {
        Bitmap bitmap = null;

        try {
            URLConnection conn = new URL(url).openConnection();
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            
			Options opts = new Options();
//			opts.inJustDecodeBounds = true;
//			// ��ȥ��Ľ���ͼƬ ��ȡͼƬͷ����Ϣ
//			BitmapFactory.decodeFile(filePath, opts);
//			int height = opts.outHeight;
//			int width = opts.outWidth;
//			Log.i("MapView", "height=" + height + ";width=" + width);
//			int w, h;
//			if (width > height) {// �����
//				w = 500 * width / height;
//				h = 500;
//			} else {
//				w = 500;
//				h = 500 * height / width;
//			}
//			// ���������
//			int scaleX = height / h;
//			int scaleY = width / w;
//			int scale = Math.min(scaleX, scaleY);
//			opts.inJustDecodeBounds = false;
//			opts.inSampleSize = scale;
			opts.inPreferredConfig = Bitmap.Config.RGB_565;
			opts.inPurgeable = true;
			opts.inInputShareable = true;
            
            bitmap = BitmapFactory.decodeStream((InputStream) conn.getContent(),null, opts);
            Log.d("---dddddd-", "");
        } catch(Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static void removeFromCache(String url) {
        if(webImageCache != null) {
            webImageCache.remove(url);
        }
    }
}
