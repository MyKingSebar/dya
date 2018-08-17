package cn.v1.unionc_user.model;

/**
 * Created by qy on 2018/3/14.
 */

public class DoctorOrClinicData extends BaseData {


    /**
     * :
     {
     "message": "成功",
     "data": {
     "roleType": "0",
     "clinicName": "北京市第一医院",
     "identifier": "88fcbcd0c56e41a69c46a02dc584098c",
     "clinicId": "779391",
     "clinicImagePath": "http://192.168.21.93:8080/unionWeb/image/webServer/compress/2/6/0/d423d648-a032-4b87-8b41-acf66edd0ec0_19875a84292e3e8b5796e49a8ca520db.jpg"
     },
     "code": "4000"
     }
     :
     {
     "message": "成功",
     "data": {
     "doctImagePath": "http://192.168.21.93:8080/unionWeb/image/webServer/compress/2/6/0/d423d648-a032-4b87-8b41-acf66edd0ec0_19875a84292e3e8b5796e49a8ca520db.jpg",
     "roleType": "1",
     "doctId": "6",
     "doctName": "豆豆003",
     "identifier": "c62a90d71b7f498ca1f4619bf633c83e"
     },
     "code": "4000"
     }
     :
     roleType：0-医院，1-医生，2-护士
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
        private String roleType;
        private String identifier;

        private String clinicName;
        private String clinicId;
        private String clinicImagePath;

        private String doctId;
        private String doctName;

        public String getRoleType() {
            return roleType;
        }

        public void setRoleType(String roleType) {
            this.roleType = roleType;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getClinicName() {
            return clinicName;
        }

        public void setClinicName(String clinicName) {
            this.clinicName = clinicName;
        }

        public String getClinicId() {
            return clinicId;
        }

        public void setClinicId(String clinicId) {
            this.clinicId = clinicId;
        }

        public String getClinicImagePath() {
            return clinicImagePath;
        }

        public void setClinicImagePath(String clinicImagePath) {
            this.clinicImagePath = clinicImagePath;
        }

        public String getDoctId() {
            return doctId;
        }

        public void setDoctId(String doctId) {
            this.doctId = doctId;
        }

        public String getDoctName() {
            return doctName;
        }

        public void setDoctName(String doctName) {
            this.doctName = doctName;
        }
    }
}
