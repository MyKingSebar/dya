package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/2/9.
 */

public class WeiXinQRcodeData extends BaseData {


    /**
     * {
     "code": "4000",
     "data": {
     "Type": "0",
     "Id":"123456"
     }
     },
     "message": "成功"
     }

     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {

private String type;
private String id;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            id = id;
        }
    }

}
