package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/3/8.
 */

public class SaveOldBaseData extends BaseData {

private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }


    public class DataData{
        private String elderlyUserId;

        public String getElderlyUserId() {
            return elderlyUserId;
        }

        public void setElderlyUserId(String elderlyUserId) {
            this.elderlyUserId = elderlyUserId;
        }
    }



}
