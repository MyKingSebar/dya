package cn.v1.unionc_user.model;

/**
 * Created by qy on 2018/3/15.
 */

public class GetRongTokenData extends BaseData {


    /**
     * "data":{"IMUserId":"d91d2da5dcf63adfcc1c24404ed615eb"}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
       private String IMToken;

        public String getIMToken() {
            return IMToken;
        }

        public void setIMToken(String IMToken) {
            this.IMToken = IMToken;
        }
    }

}
