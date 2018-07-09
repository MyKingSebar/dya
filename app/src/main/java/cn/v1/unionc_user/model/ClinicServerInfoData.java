package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/3/8.
 */

public class ClinicServerInfoData extends BaseData {
    /**
     *返回值：{"message":"成功","data":{"serviceInfo":{"ServiceImage":"","ServiceName":"健康监护服务","Other":"含紧急告警、健康咨询等基础服务。","Charge":"免费","IsOpenUp":"0","Tips":"1.紧急呼叫（5小时/月）\r2.健康咨询（不限次）\r3.健康讲座通知 \r4.活动通知"}},"code":"4000"}
     */
    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }


    public class DataData{

        private DataDataData serviceInfo;

        public DataDataData getServiceInfo() {
            return serviceInfo;
        }

        public void setServiceInfo(DataDataData serviceInfo) {
            this.serviceInfo = serviceInfo;
        }

        public class DataDataData{
            private String ServiceImage;
             private String ServiceName;
            private String Other;
            private String Charge;
            private String IsOpenUp;
            private String Tips;

            public String getServiceImage() {
                return ServiceImage;
            }

            public void setServiceImage(String serviceImage) {
                ServiceImage = serviceImage;
            }

            public String getServiceName() {
                return ServiceName;
            }

            public void setServiceName(String serviceName) {
                ServiceName = serviceName;
            }

            public String getOther() {
                return Other;
            }

            public void setOther(String other) {
                Other = other;
            }

            public String getCharge() {
                return Charge;
            }

            public void setCharge(String charge) {
                Charge = charge;
            }

            public String getIsOpenUp() {
                return IsOpenUp;
            }

            public void setIsOpenUp(String isOpenUp) {
                IsOpenUp = isOpenUp;
            }

            public String getTips() {
                return Tips;
            }

            public void setTips(String tips) {
                Tips = tips;
            }
        }
    }



}
