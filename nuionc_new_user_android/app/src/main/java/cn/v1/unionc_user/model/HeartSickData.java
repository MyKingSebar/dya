package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/2/9.
 */

public class HeartSickData extends BaseData {


    /**
     * {"code":"4000","data":{"basicDicts":[{"BasicName":"无病史","BasicCode":"0401"},{"BasicName":"风湿性心脏病","BasicCode":"0402"},{"BasicName":"先天性心脏病","BasicCode":"0403"},{"BasicName":"冠心病","BasicCode":"0404"},{"BasicName":"高血压心脏病","BasicCode":"0405"},{"BasicName":"肺源性心脏病","BasicCode":"0406"},{"BasicName":"心肌病","BasicCode":"0407"},{"BasicName":"心脏肿瘤","BasicCode":"0408"},{"BasicName":"血管病变","BasicCode":"0409"}]},"message":"成功"}
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
