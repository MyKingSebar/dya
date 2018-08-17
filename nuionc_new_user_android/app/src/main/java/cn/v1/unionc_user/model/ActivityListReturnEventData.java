package cn.v1.unionc_user.model;

/**
 * Created by qy on 2018/3/6.
 */

public class ActivityListReturnEventData {

    private boolean noData;

    public boolean isNoData() {
        return noData;
    }

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }

    private String clinicId;

    public ActivityListReturnEventData(String clinicId) {
        this.clinicId = clinicId;
    }

    public boolean hasNoData() {
        return noData;
    }

    public void setNoData(boolean noData) {
        this.noData = noData;
    }
}
