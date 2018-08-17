package cn.v1.unionc_user.ui.home.health;

import java.util.List;

/**
 * Created by An4 on 2016/9/29.
 */
public class PsyDictionaryData {


    /**
     * code : 0000
     * message : 请求成功
     * data : [{"pfsnId":89,"pfsnlName":"1型糖尿病"},{"pfsnId":90,"pfsnlName":"2型糖尿病"},{"pfsnId":91,"pfsnlName":"其他类型糖尿病"},{"pfsnId":92,"pfsnlName":"非糖尿病"}]
     */

    private String code;
    private String message;
    /**
     * pfsnId : 89
     * pfsnlName : 1型糖尿病
     */

    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String pfsnId;
        private String pfsnlName;

        public String getPfsnId() {
            return pfsnId;
        }

        public void setPfsnId(String pfsnId) {
            this.pfsnId = pfsnId;
        }

        public String getPfsnlName() {
            return pfsnlName;
        }

        public void setPfsnlName(String pfsnlName) {
            this.pfsnlName = pfsnlName;
        }
    }
}
