package cn.v1.unionc_user.model;

/**
 * Created by qy on 2018/3/8.
 */

public class IsBindJianhurenData extends BaseData {

private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public class DataData{
private String hasGuardian;

        public String getHasGuardian() {
            return hasGuardian;
        }

        public void setHasGuardian(String hasGuardian) {
            this.hasGuardian = hasGuardian;
        }
    }


}
