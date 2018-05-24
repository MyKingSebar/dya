package cn.v1.unionc_user.ui.home.BloodPressure.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by An4 on 2016/9/21.
 */
public class DossierListData {

    /**
     * message : 查询成功
     * code : 0000
     * List : [{"AGE":"年龄","REAL_NAME":"患者姓名","SEX":"性别","ID":"健康档案id","RELATIONSHIP":"与患者关系"}]
     */

    private String message;
    private String code;
    /**
     * AGE : 年龄
     * REAL_NAME : 患者姓名
     * SEX : 性别
     * ID : 健康档案id
     * RELATIONSHIP : 与患者关系
     */

    private java.util.ArrayList<ListBean> List;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<ListBean> getList() {
        return List;
    }

    public void setList(ArrayList<ListBean> List) {
        this.List = List;
    }

    public static class ListBean implements Serializable {
        private String AGE;
        private String REAL_NAME;
        private String SEX;
        private String ID;
        private String RELATIONSHIP;
        private String DEAD_LINE;
        private String ORDER_ID = "";

        public String getDEAD_LINE() {
            return DEAD_LINE;
        }

        public void setDEAD_LINE(String DEAD_LINE) {
            this.DEAD_LINE = DEAD_LINE;
        }

        public String getORDER_ID() {
            return ORDER_ID;
        }

        public void setORDER_ID(String ORDER_ID) {
            this.ORDER_ID = ORDER_ID;
        }

        public String getAGE() {
            return AGE;
        }

        public void setAGE(String AGE) {
            this.AGE = AGE;
        }

        public String getREAL_NAME() {
            return REAL_NAME;
        }

        public void setREAL_NAME(String REAL_NAME) {
            this.REAL_NAME = REAL_NAME;
        }

        public String getSEX() {
            return SEX;
        }

        public void setSEX(String SEX) {
            this.SEX = SEX;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getRELATIONSHIP() {
            return RELATIONSHIP;
        }

        public void setRELATIONSHIP(String RELATIONSHIP) {
            this.RELATIONSHIP = RELATIONSHIP;
        }
    }
}
