package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/2/9.
 */

public class HeartIndicationData extends BaseData {


    /**
     * {"message":"成功","data":{"basicDicts":[{"BasicCode":"0301","BasicName":"无症状"},{"BasicCode":"0302","BasicName":"手抖"},{"BasicCode":"0303","BasicName":"出冷汗"},{"BasicCode":"0304","BasicName":"心悸"},{"BasicCode":"0305","BasicName":"饥饿感"},{"BasicCode":"0306","BasicName":"烦躁不安"},{"BasicCode":"0307","BasicName":"心疼"},{"BasicCode":"0308","BasicName":"头晕"},{"BasicCode":"0309","BasicName":"嗜睡"},{"BasicCode":"0310","BasicName":"定向力障碍"},{"BasicCode":"0311","BasicName":"无欲状"},{"BasicCode":"0312","BasicName":"昏睡"},{"BasicCode":"0313","BasicName":"癫痫"},{"BasicCode":"0314","BasicName":"视物模糊"}]},"code":"4000"}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {

public List<HeartIndicationDataData> basicDicts;

        public List<HeartIndicationDataData> getBasicDicts() {
            return basicDicts;
        }

        public void setBasicDicts(List<HeartIndicationDataData> basicDicts) {
            this.basicDicts = basicDicts;
        }
      public  class HeartIndicationDataData{
            private String BasicCode;
            private String BasicName;

            public String getBasicCode() {
                return BasicCode;
            }

            public void setBasicCode(String basicCode) {
                BasicCode = basicCode;
            }

            public String getBasicName() {
                return BasicName;
            }

            public void setBasicName(String basicName) {
                BasicName = basicName;
            }
        }
    }

}
