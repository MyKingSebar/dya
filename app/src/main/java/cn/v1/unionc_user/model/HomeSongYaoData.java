package cn.v1.unionc_user.model;

/**
 * Created by qy on 2018/2/9.
 */

public class HomeSongYaoData extends BaseData {


    /**
     * {"code":"4000","data":{"redirectUrl":"http://192.168.21.160:8082/yihudaojia/intoYihuPage?partner=yibashi&phone=15033111957&thirdPartId=ybs_2&sign=0B2002A56C31DE35083002C652F17847"},"message":"成功"}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {


        private String redirectUrl;

        public String getRedirectUrl() {
            return redirectUrl;
        }

        public void setRedirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
        }
    }
}
