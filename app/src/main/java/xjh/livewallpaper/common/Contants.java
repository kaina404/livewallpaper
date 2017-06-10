package xjh.livewallpaper.common;

/**
 * Created by lovexujh on 2017/5/20
 */

public class Contants {

    public static final int CHANGE_PLAYER_PATH = 123;
    public static final String HOST_URL = "https://ct.hyhuo.huoying666.com/";

    public interface URL {

        String VIDEO_LIST = HOST_URL + "site/videolist";
        String INIT_LIST = HOST_URL + "site/init_start";
    }

    public interface APP {
        String UUID_VALUE = "af9ab6c6-43ee-28f6-19dd-d9e5ad599376";
        String CRYPT_VALUE = "f6a94c8ac6ca7b953229d90219debbfd";
        String VERSION_CODE_VALUE = "2017052400";
    }

    public interface PARAMS_NAME {
        String UUID = "uuid";
        String VERSION_CODE = "version_code";
        String PAGE = "page";
        String CRYPT = "crypt";
        String CATEGORY_ID = "category_id";
        String CHANNEL = "channel";
    }

    public interface SpUtils {
        String SP_WALLPAPER_NAME = "SP_WALLPAPER_NAME";
        String SP_WALLPAPER_URL = "SP_WALLPAPER_URL";

        String SP_SOUND = "SP_SOUND";
        String SP_SOUND_OFF = "SP_SOUND_OFF";
        String SP_SOUND_ON = "SP_SOUND_ON";
    }

    //请求第一页数据
    /**
     *  POST /site/videolist HTTP/1.1
     Content-Length: 205
     Content-Type: application/x-www-form-urlencoded
     Host: ct.hyhuo.huoying666.com
     Connection: Keep-Alive
     Accept-Encoding: gzip

     params=eyJjYXRlZ29yeV9pZCI6IiIsImNyeXB0IjoiZmQ5NWRhN2JlNWU1YTZmNzk3ZGU5OWMyMmU5NTM2MDQiLCJ1dWlkIjoiMDJhMTgwYTctNmVjZC1lZjViLTAwMTQtYmVlNWQ0ZGFmMGZlIiwicGFnZSI6IjEiLCJ2ZXJzaW9uX2NvZGUiOiIyMDE3MDUxNTAwIn0%3D
     备注：
     params是Base64加密数据
     params实际值是:
     {"category_id":"","crypt":"fd95da7be5e5a6f797de99c22e953604","uuid":"02a180a7-6ecd-ef5b-0014-bee5d4daf0fe","page":"1","version_code":"2017051500"}7

     */

    //其他数据

    /**
     * {
     * "error_code": 1,
     * "data": {
     * "uuid": "02a180a7-6ecd-ef5b-0014-bee5d4daf0fe",
     * "category_ids": [
     * {
     * "category_id": "1",
     * "name": "热门推荐",
     * "icon_on": "http://img1.huoying666.com/images/icon/icon_m_01_h@3x.png",
     * "icon_off": "http://img1.huoying666.com/images/icon/icon_m_01@3x.png"
     * },
     * {
     * "category_id": "99",
     * "name": "最新上线",
     * "icon_on": "http://img1.huoying666.com/images/icon/icon_m_99_h@3x.png",
     * "icon_off": "http://img1.huoying666.com/images/icon/icon_m_99@3x.png"
     * },
     * {
     * "category_id": "2",
     * "name": "卡通动漫",
     * "icon_on": "http://img1.huoying666.com/images/icon/icon_m_02_h@3x.png",
     * "icon_off": "http://img1.huoying666.com/images/icon/icon_m_02@3x.png"
     * },
     * {
     * "category_id": "3",
     * "name": "游戏专区",
     * "icon_on": "http://img1.huoying666.com/images/icon/icon_m_03_h@3x.png",
     * "icon_off": "http://img1.huoying666.com/images/icon/icon_m_03@3x.png"
     * },
     * {
     * "category_id": "4",
     * "name": "网络红人",
     * "icon_on": "http://img1.huoying666.com/images/icon/icon_m_004_h@3x.png?x=1",
     * "icon_off": "http://img1.huoying666.com/images/icon/icon_m_004@3x.png?x=1"
     * },
     * {
     * "category_id": "5",
     * "name": "热门影视",
     * "icon_on": "http://img1.huoying666.com/images/icon/icon_m_05_h@3x.png",
     * "icon_off": "http://img1.huoying666.com/images/icon/icon_m_05@3x.png"
     * },
     * {
     * "category_id": "6",
     * "name": "体育明星",
     * "icon_on": "http://img1.huoying666.com/images/icon/icon_m_06_h@3x.png",
     * "icon_off": "http://img1.huoying666.com/images/icon/icon_m_06@3x.png"
     * },
     * {
     * "category_id": "7",
     * "name": "动物萌宠",
     * "icon_on": "http://img1.huoying666.com/images/icon/icon_m_07_h@3x.png",
     * "icon_off": "http://img1.huoying666.com/images/icon/icon_m_07@3x.png"
     * },
     * {
     * "category_id": "8",
     * "name": "风景名胜",
     * "icon_on": "http://img1.huoying666.com/images/icon/icon_m_08_h@3x.png",
     * "icon_off": "http://img1.huoying666.com/images/icon/icon_m_08@3x.png"
     * },
     * {
     * "category_id": "9",
     * "name": "娱乐明星",
     * "icon_on": "http://img1.huoying666.com/images/icon/icon_m_09_h@3x.png",
     * "icon_off": "http://img1.huoying666.com/images/icon/icon_m_09@3x.png"
     * },
     * {
     * "category_id": "10",
     * "name": "歌曲舞蹈",
     * "icon_on": "http://img1.huoying666.com/images/icon/icon_m_10_h@3x.png?x=1",
     * "icon_off": "http://img1.huoying666.com/images/icon/icon_m_10@3x.png?x=1"
     * },
     * {
     * "category_id": "12",
     * "name": "其他资源",
     * "icon_on": "http://img1.huoying666.com/images/icon/icon_m_12_h@3x.png",
     * "icon_off": "http://img1.huoying666.com/images/icon/icon_m_12@3x.png"
     * }
     * ],
     * "version_name": "1.2.2",
     * "version_code": "2017051500",
     * "is_force": "15",
     * "channel": "c1098",
     * "apk_url": "http://img1.huoying666.com/apk/2017051500/huoying-c1098-2017051500.apk",
     * "version_description": "1.解决了个别手机打电话有视频声音的bug。\r\n2.解决了个别手机会出现红色标志的bug。\r\n3.增加设置白名单的方法",
     * "apk_size": "12M"
     * }
     * }
     */


    //initList
     /*{
        "channel":"c1098", "crypt":"fd95da7be5e5a6f797de99c22e953604", "version_code":
        "2017051500", "uuid":"02a180a7-6ecd-ef5b-0014-bee5d4daf0fe"
    }

    {
    "error_code": 1,
    "data": {
        "uuid": "02a180a7-6ecd-ef5b-0014-bee5d4daf0fe",
        "category_ids": [
            {
                "category_id": "1",
                "name": "热门推荐",
                "icon_on": "http://img1.huoying666.com/images/icon/icon_m_01_h@3x.png",
                "icon_off": "http://img1.huoying666.com/images/icon/icon_m_01@3x.png"
            },
            {
                "category_id": "99",
                "name": "最新上线",
                "icon_on": "http://img1.huoying666.com/images/icon/icon_m_99_h@3x.png",
                "icon_off": "http://img1.huoying666.com/images/icon/icon_m_99@3x.png"
            },
            {
                "category_id": "2",
                "name": "卡通动漫",
                "icon_on": "http://img1.huoying666.com/images/icon/icon_m_02_h@3x.png",
                "icon_off": "http://img1.huoying666.com/images/icon/icon_m_02@3x.png"
            },
            {
                "category_id": "3",
                "name": "游戏专区",
                "icon_on": "http://img1.huoying666.com/images/icon/icon_m_03_h@3x.png",
                "icon_off": "http://img1.huoying666.com/images/icon/icon_m_03@3x.png"
            },
            {
                "category_id": "4",
                "name": "网络红人",
                "icon_on": "http://img1.huoying666.com/images/icon/icon_m_004_h@3x.png?x=1",
                "icon_off": "http://img1.huoying666.com/images/icon/icon_m_004@3x.png?x=1"
            },
            {
                "category_id": "5",
                "name": "热门影视",
                "icon_on": "http://img1.huoying666.com/images/icon/icon_m_05_h@3x.png",
                "icon_off": "http://img1.huoying666.com/images/icon/icon_m_05@3x.png"
            },
            {
                "category_id": "6",
                "name": "体育明星",
                "icon_on": "http://img1.huoying666.com/images/icon/icon_m_06_h@3x.png",
                "icon_off": "http://img1.huoying666.com/images/icon/icon_m_06@3x.png"
            },
            {
                "category_id": "7",
                "name": "动物萌宠",
                "icon_on": "http://img1.huoying666.com/images/icon/icon_m_07_h@3x.png",
                "icon_off": "http://img1.huoying666.com/images/icon/icon_m_07@3x.png"
            },
            {
                "category_id": "8",
                "name": "风景名胜",
                "icon_on": "http://img1.huoying666.com/images/icon/icon_m_08_h@3x.png",
                "icon_off": "http://img1.huoying666.com/images/icon/icon_m_08@3x.png"
            },
            {
                "category_id": "9",
                "name": "娱乐明星",
                "icon_on": "http://img1.huoying666.com/images/icon/icon_m_09_h@3x.png",
                "icon_off": "http://img1.huoying666.com/images/icon/icon_m_09@3x.png"
            },
            {
                "category_id": "10",
                "name": "歌曲舞蹈",
                "icon_on": "http://img1.huoying666.com/images/icon/icon_m_10_h@3x.png?x=1",
                "icon_off": "http://img1.huoying666.com/images/icon/icon_m_10@3x.png?x=1"
            },
            {
                "category_id": "12",
                "name": "其他资源",
                "icon_on": "http://img1.huoying666.com/images/icon/icon_m_12_h@3x.png",
                "icon_off": "http://img1.huoying666.com/images/icon/icon_m_12@3x.png"
            }
        ],
        "version_name": "1.2.2",
        "version_code": "2017051500",
        "is_force": "15",
        "channel": "c1098",
        "apk_url": "http://img1.huoying666.com/apk/2017051500/huoying-c1098-2017051500.apk",
        "version_description": "1.解决了个别手机打电话有视频声音的bug。\r\n2.解决了个别手机会出现红色标志的bug。\r\n3.增加设置白名单的方法",
        "apk_size": "12M"
    }
}
    */

    public interface ServiceAction {
        String SOUND_ON = "SOUND_ON";
        String SOUND_OFF = "SOUND_OFF";
    }

}
