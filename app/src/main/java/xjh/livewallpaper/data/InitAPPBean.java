package xjh.livewallpaper.data;

import java.util.List;

/**
 * Created by lovexujh on 2017/5/31
 * 初始化APP信息
 */

public class InitAPPBean {

    /**
     * error_code : 1
     * data : {"uuid":"02a180a7-6ecd-ef5b-0014-bee5d4daf0fe","category_ids":[{"category_id":"1","name":"热门推荐","icon_on":"http://img1.huoying666.com/images/icon/icon_m_01_h@3x.png","icon_off":"http://img1.huoying666.com/images/icon/icon_m_01@3x.png"},{"category_id":"99","name":"最新上线","icon_on":"http://img1.huoying666.com/images/icon/icon_m_99_h@3x.png","icon_off":"http://img1.huoying666.com/images/icon/icon_m_99@3x.png"},{"category_id":"2","name":"卡通动漫","icon_on":"http://img1.huoying666.com/images/icon/icon_m_02_h@3x.png","icon_off":"http://img1.huoying666.com/images/icon/icon_m_02@3x.png"},{"category_id":"3","name":"游戏专区","icon_on":"http://img1.huoying666.com/images/icon/icon_m_03_h@3x.png","icon_off":"http://img1.huoying666.com/images/icon/icon_m_03@3x.png"},{"category_id":"4","name":"网络红人","icon_on":"http://img1.huoying666.com/images/icon/icon_m_004_h@3x.png?x=1","icon_off":"http://img1.huoying666.com/images/icon/icon_m_004@3x.png?x=1"},{"category_id":"5","name":"热门影视","icon_on":"http://img1.huoying666.com/images/icon/icon_m_05_h@3x.png","icon_off":"http://img1.huoying666.com/images/icon/icon_m_05@3x.png"},{"category_id":"6","name":"体育明星","icon_on":"http://img1.huoying666.com/images/icon/icon_m_06_h@3x.png","icon_off":"http://img1.huoying666.com/images/icon/icon_m_06@3x.png"},{"category_id":"7","name":"动物萌宠","icon_on":"http://img1.huoying666.com/images/icon/icon_m_07_h@3x.png","icon_off":"http://img1.huoying666.com/images/icon/icon_m_07@3x.png"},{"category_id":"8","name":"风景名胜","icon_on":"http://img1.huoying666.com/images/icon/icon_m_08_h@3x.png","icon_off":"http://img1.huoying666.com/images/icon/icon_m_08@3x.png"},{"category_id":"9","name":"娱乐明星","icon_on":"http://img1.huoying666.com/images/icon/icon_m_09_h@3x.png","icon_off":"http://img1.huoying666.com/images/icon/icon_m_09@3x.png"},{"category_id":"10","name":"歌曲舞蹈","icon_on":"http://img1.huoying666.com/images/icon/icon_m_10_h@3x.png?x=1","icon_off":"http://img1.huoying666.com/images/icon/icon_m_10@3x.png?x=1"},{"category_id":"12","name":"其他资源","icon_on":"http://img1.huoying666.com/images/icon/icon_m_12_h@3x.png","icon_off":"http://img1.huoying666.com/images/icon/icon_m_12@3x.png"}],"version_name":"1.2.2","version_code":"2017051500","is_force":"15","channel":"c1098","apk_url":"http://img1.huoying666.com/apk/2017051500/huoying-c1098-2017051500.apk","version_description":"1.解决了个别手机打电话有视频声音的bug。\r\n2.解决了个别手机会出现红色标志的bug。\r\n3.增加设置白名单的方法","apk_size":"12M"}
     */

    private int error_code;
    private DataBean data;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * uuid : 02a180a7-6ecd-ef5b-0014-bee5d4daf0fe
         * category_ids : [{"category_id":"1","name":"热门推荐","icon_on":"http://img1.huoying666.com/images/icon/icon_m_01_h@3x.png","icon_off":"http://img1.huoying666.com/images/icon/icon_m_01@3x.png"},{"category_id":"99","name":"最新上线","icon_on":"http://img1.huoying666.com/images/icon/icon_m_99_h@3x.png","icon_off":"http://img1.huoying666.com/images/icon/icon_m_99@3x.png"},{"category_id":"2","name":"卡通动漫","icon_on":"http://img1.huoying666.com/images/icon/icon_m_02_h@3x.png","icon_off":"http://img1.huoying666.com/images/icon/icon_m_02@3x.png"},{"category_id":"3","name":"游戏专区","icon_on":"http://img1.huoying666.com/images/icon/icon_m_03_h@3x.png","icon_off":"http://img1.huoying666.com/images/icon/icon_m_03@3x.png"},{"category_id":"4","name":"网络红人","icon_on":"http://img1.huoying666.com/images/icon/icon_m_004_h@3x.png?x=1","icon_off":"http://img1.huoying666.com/images/icon/icon_m_004@3x.png?x=1"},{"category_id":"5","name":"热门影视","icon_on":"http://img1.huoying666.com/images/icon/icon_m_05_h@3x.png","icon_off":"http://img1.huoying666.com/images/icon/icon_m_05@3x.png"},{"category_id":"6","name":"体育明星","icon_on":"http://img1.huoying666.com/images/icon/icon_m_06_h@3x.png","icon_off":"http://img1.huoying666.com/images/icon/icon_m_06@3x.png"},{"category_id":"7","name":"动物萌宠","icon_on":"http://img1.huoying666.com/images/icon/icon_m_07_h@3x.png","icon_off":"http://img1.huoying666.com/images/icon/icon_m_07@3x.png"},{"category_id":"8","name":"风景名胜","icon_on":"http://img1.huoying666.com/images/icon/icon_m_08_h@3x.png","icon_off":"http://img1.huoying666.com/images/icon/icon_m_08@3x.png"},{"category_id":"9","name":"娱乐明星","icon_on":"http://img1.huoying666.com/images/icon/icon_m_09_h@3x.png","icon_off":"http://img1.huoying666.com/images/icon/icon_m_09@3x.png"},{"category_id":"10","name":"歌曲舞蹈","icon_on":"http://img1.huoying666.com/images/icon/icon_m_10_h@3x.png?x=1","icon_off":"http://img1.huoying666.com/images/icon/icon_m_10@3x.png?x=1"},{"category_id":"12","name":"其他资源","icon_on":"http://img1.huoying666.com/images/icon/icon_m_12_h@3x.png","icon_off":"http://img1.huoying666.com/images/icon/icon_m_12@3x.png"}]
         * version_name : 1.2.2
         * version_code : 2017051500
         * is_force : 15
         * channel : c1098
         * apk_url : http://img1.huoying666.com/apk/2017051500/huoying-c1098-2017051500.apk
         * version_description :
         * 1.解决了个别手机打电话有视频声音的bug。
         * 2.解决了个别手机会出现红色标志的bug。
         * 3.增加设置白名单的方法
         * apk_size : 12M
         */

        private String uuid;
        private String version_name;
        private String version_code;
        private String is_force;
        private String channel;
        private String apk_url;
        private String version_description;
        private String apk_size;
        private List<CategoryIdsBean> category_ids;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getVersion_name() {
            return version_name;
        }

        public void setVersion_name(String version_name) {
            this.version_name = version_name;
        }

        public String getVersion_code() {
            return version_code;
        }

        public void setVersion_code(String version_code) {
            this.version_code = version_code;
        }

        public String getIs_force() {
            return is_force;
        }

        public void setIs_force(String is_force) {
            this.is_force = is_force;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getApk_url() {
            return apk_url;
        }

        public void setApk_url(String apk_url) {
            this.apk_url = apk_url;
        }

        public String getVersion_description() {
            return version_description;
        }

        public void setVersion_description(String version_description) {
            this.version_description = version_description;
        }

        public String getApk_size() {
            return apk_size;
        }

        public void setApk_size(String apk_size) {
            this.apk_size = apk_size;
        }

        public List<CategoryIdsBean> getCategory_ids() {
            return category_ids;
        }

        public void setCategory_ids(List<CategoryIdsBean> category_ids) {
            this.category_ids = category_ids;
        }

        public static class CategoryIdsBean {
            /**
             * category_id : 1
             * name : 热门推荐
             * icon_on : http://img1.huoying666.com/images/icon/icon_m_01_h@3x.png
             * icon_off : http://img1.huoying666.com/images/icon/icon_m_01@3x.png
             */

            private String category_id;
            private String name;
            private String icon_on;
            private String icon_off;

            public String getCategory_id() {
                return category_id;
            }

            public void setCategory_id(String category_id) {
                this.category_id = category_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIcon_on() {
                return icon_on;
            }

            public void setIcon_on(String icon_on) {
                this.icon_on = icon_on;
            }

            public String getIcon_off() {
                return icon_off;
            }

            public void setIcon_off(String icon_off) {
                this.icon_off = icon_off;
            }
        }
    }
}
