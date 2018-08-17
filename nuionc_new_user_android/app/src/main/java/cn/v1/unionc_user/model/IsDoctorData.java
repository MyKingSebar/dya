package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/3/26.
 */

public class IsDoctorData extends BaseData {


    /**
     * {
     "message": "成功",
     "data": {
     "isMedical": 1
     },
     "code": "4000"
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

        private String isMedical;
        private String clinicId;

        public String getClinicId() {
            return clinicId;
        }

        public void setClinicId(String clinicId) {
            this.clinicId = clinicId;
        }

        public String getIsMedical() {
            return isMedical;
        }

        public void setIsMedical(String isMedical) {
            this.isMedical = isMedical;
        }
    }
}
