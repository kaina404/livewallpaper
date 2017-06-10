package xjh.livewallpaper.net;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import xjh.livewallpaper.LogUtil;
import xjh.livewallpaper.common.Tool;
import xjh.livewallpaper.listener.ProgressResponseListener;

/**
 * Created by lovexujh on 2017/5/20
 */

public class NetRequest {

    private static OkHttpClient okHttpClient;
    private static MyHandler myHandler = new MyHandler();
    public static final MediaType JSON_TYPE = MediaType.parse("application/json");
    public static final MediaType FORM_TYPE = MediaType.parse("application/x-www-form-urlencoded");
    private static final String CONTENT_TYPE = "Content-Type";
    private static INetCall netCall;
    private static final int HTTP_ERROR = 404;//http未响应
    private static final int HTTP_JSON_SUCCEED = 200;//仅仅HTTP响应


    static {
        okHttpClient = new OkHttpClient.Builder().
                connectTimeout(10, TimeUnit.SECONDS).
                readTimeout(10, TimeUnit.SECONDS).
                writeTimeout(10, TimeUnit.SECONDS).build();
    }

    private static ProgressResponseListener progressListener;


    public interface Method {
        String POST = "POST";
        String GET = "GET";
    }

    public static RequestBody create(@Nullable MediaType contentType, String content) {
        Charset charset = Util.UTF_8;
        if (contentType != null) {
            charset = contentType.charset();
            if (charset == null) {
                charset = Util.UTF_8;
                contentType = MediaType.parse(contentType + "");
            }
        }
        byte[] bytes = content.getBytes(charset);
        return RequestBody.create(contentType, bytes);
    }

    public void http(int reqeustId, String params, String url, String method, MediaType mediaType, INetCall netCall) {

        RequestBody body = null;
        Map<String, List<String>> headers = new HashMap<>();
        //设置Content-Type
        ArrayList<String> list = new ArrayList<>();
        if (Method.POST.equals(method)) {
            if (mediaType == null) {
                body = create(JSON_TYPE, params);
                list.add(JSON_TYPE.type() + "/" + JSON_TYPE.subtype());
            } else {
                body = create(mediaType, params);
                list.add(FORM_TYPE.type() + "/" + FORM_TYPE.subtype());
            }
        }
        headers.put(CONTENT_TYPE, list);
        //构建OKHttpRequest
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.tag(params);
        requestBuilder.url(url);
        requestBuilder.method(method, body);
        if (headers != null && !headers.isEmpty()) {
            Set<String> keySet = headers.keySet();
            for (String key : keySet) {
                List<String> values = headers.get(key);
                requestBuilder.removeHeader(key);
                for (String value : values) {
                    requestBuilder.addHeader(key, value);
                }
            }
        }


        //请求
        Call call = okHttpClient.newCall(requestBuilder.build());
        if (netCall != null) {
            this.netCall = netCall;
            call.enqueue(new OkCallBack(true, url, reqeustId));
        } else {
            // TODO: 2017/3/28  测试null的请求是否有问题
            call.enqueue(null);
        }
    }

    private static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg == null || netCall == null) {
                return;
            }
            switch (msg.arg1) {
                case HTTP_JSON_SUCCEED:
                    netCall.onResponse(msg.what, (String) msg.obj);
                    break;
                case HTTP_ERROR:
                    String err = msg.obj == null ? "未知错误" : (String) msg.obj;
                    netCall.onFailure(msg.what, err);
                    break;
            }
        }
    }

    private static class OkCallBack implements Callback {


        private boolean isGetJson;
        private String url;
        private int requestId = -1;

        public OkCallBack(boolean isGetJson, String url, int requestId) {
            this.isGetJson = isGetJson;
            this.url = url;
            this.requestId = requestId;
        }

        public OkCallBack(boolean isGetJson, String url) {
            this.isGetJson = isGetJson;
            this.url = url;
        }


        @Override
        public void onFailure(Call call, IOException e) {
            Message msg = Message.obtain();
            msg.obj = e.toString();
            msg.arg1 = HTTP_ERROR;
            msg.what = requestId;
            myHandler.sendMessage(msg);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Message msg = Message.obtain();
            //将httpResponse发送到UI线程
            msg.arg1 = HTTP_JSON_SUCCEED;
            msg.what = requestId;
            if (isGetJson) {
                msg.obj = response.body().string();
            } else {
                String tmp = parseDownloadFile(response, url);
                if (!Tool.isEmpty(tmp)) {
                    msg.obj = tmp;
                } else {
                    onFailure(call, new IOException("下载文件解析出错"));
                    return;
                }
            }
            myHandler.sendMessage(msg);
        }

        private String parseDownloadFile(Response response, String url) throws IOException {
            LogUtil.d("=======开始解析下载文件" + url + "======");
            ResponseBody responseBody = response.body();
            InputStream inputStream = responseBody.byteStream();
            if (inputStream == null) {
                return "";
            }

            String absolutePath = Tool.createDownloadFilePath(url);
            if (!Tool.isEmpty(absolutePath)) {
                File file = new File(absolutePath);
                boolean readSuccess = Tool.inputStream2File(inputStream, file, progressListener, responseBody.contentLength());
                inputStream.close();
                if(readSuccess){
                    absolutePath = file.getAbsolutePath();
                    LogUtil.d("=======下载文件==" + url + "==解析失败===");
                }else {
                    absolutePath = "";
                }
                LogUtil.d("=======下载文件====解析成功===路径是===" + absolutePath);

            } else {
                LogUtil.d("=======下载文件==" + url + "==解析失败===");
            }
            return absolutePath;
        }


    }


    public void post(int requestId, String params, String url, MediaType mediaType, INetCall netCall) {
        http(requestId, params, url, Method.POST, mediaType, netCall);
    }


    /**
     * url需要时一个标准的文件链接，如：xiaomi.mp4、apple_demo.avi等等，否则存储文件命名时会出错
     *
     * @param url
     * @param netCall 回调会返回下载的文件路径
     */
    public void downloadFile(String url, INetCall netCall, final ProgressResponseListener progressListener) {
        if (Tool.isEmpty(url)) {
            return;
        }
        Request request = new Request.Builder().get().url(url).build();
        this.netCall = netCall;
        this.progressListener = progressListener;

        okHttpClient.newCall(request).enqueue(new OkCallBack(false, url));
    }

    // TODO: 2017/6/1 批量下载文件需要优化
    public void downloadFile(INetCall netCall, final List<String> urls) {
        if (Tool.isEmpty(urls)) {
            return;
        }
        this.netCall = netCall;
        new Thread() {
            @Override
            public void run() {
                super.run();
                Request.Builder builder = new Request.Builder().get();
                for (int i = 0; i < urls.size(); i++) {
                    okHttpClient.newCall(builder.url(urls.get(i)).build()).enqueue(new OkCallBack(false, urls.get(i)));
                }
            }
        }.start();
    }
}
