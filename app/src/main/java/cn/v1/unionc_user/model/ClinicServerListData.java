package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/3/8.
 */

public class ClinicServerListData extends BaseData {
    /**
     *
     * {"message":"成功","data":{"service":[{"ServiceName":"健康监护","ServiceCode":"000","Charge":"免费","IsOpenUp":"0（0：未开通 1：已开通）","Content":"含紧急告警、健康咨询等基础服务。"}]},"code":"4000"}
     */
    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }


    public class DataData{
        public List<DataDataData> getService() {
            return service;
        }

        public void setService(List<DataDataData> service) {
            this.service = service;
        }

        private List<DataDataData> service;
        public class DataDataData{
            private String ServiceName;

            public String getServiceName() {
                return ServiceName;
            }

            public void setServiceName(String serviceName) {
                ServiceName = serviceName;
            }

            public String getServiceCode() {
                return ServiceCode;
            }

            public void setServiceCode(String serviceCode) {
                ServiceCode = serviceCode;
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

            public String getContent() {
                return Content;
            }

            public void setContent(String content) {
                Content = content;
            }

            private String ServiceCode;
            private String Charge;
            private String IsOpenUp;
            private String Content;

        }
    }



}
