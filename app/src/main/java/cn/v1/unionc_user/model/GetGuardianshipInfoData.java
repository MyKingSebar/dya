package cn.v1.unionc_user.model;

/**
 * Created by qy on 2018/3/15.
 */

public class GetGuardianshipInfoData extends BaseData {


    /**
     * "{"message":"成功","data":{"HeadImage":"http://192.168.21.93:8080/unionWeb/image","GuardianUserName":"","IMUserId":"debb99a8209f4933b75db9b1e8119a17"},"code":"4000"}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
       private String IMUserId;

        public String getIMUserId() {
            return IMUserId;
        }

        public void setIMUserId(String IMUserId) {
            this.IMUserId = IMUserId;
        }

        private String HeadImage;

        public String getHeadImage() {
            return HeadImage;
        }

        public void setHeadImage(String headImage) {
            HeadImage = headImage;
        }

        public String getGuardianUserName() {
            return GuardianUserName;
        }

        public void setGuardianUserName(String guardianUserName) {
            GuardianUserName = guardianUserName;
        }

        private String GuardianUserName;

    }

}
