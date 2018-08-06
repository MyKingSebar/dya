package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/3/26.
 */

public class GetNurseListData extends BaseData {

    //0-无，1-有
    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    /**
     * {
     "code": "4000",
     "data": {
     "nurseListCount": 3,
     "nursers": [
     {
     "DoctImagePath": "",
     "DoctId": "72",
     "DoctUserId": "130",
     "EvaluateCount": "0",
     "ProfessName": "",
     "Telphone": "15510253516",
     "DepartName": "",
     "AtteCount": "0",
     "Identifier": "326cc9d8a2c54b4985d53d01d53897aa",
     "Major": "asdf",
     "ClinicName": "孟的测试社区",
     "ClinicId": "779433",
     "DoctName": "孟护工"
     },
     {
     "DoctImagePath": "",
     "DoctId": "73",
     "DoctUserId": "131",
     "EvaluateCount": "0",
     "ProfessName": "",
     "Telphone": "15510653516",
     "DepartName": "",
     "AtteCount": "0",
     "Identifier": "db9cb9e834a0443d9a030ffa05cdc6ed",
     "Major": "asdf",
     "ClinicName": "孟的测试社区",
     "ClinicId": "779433",
     "DoctName": "孟护工3"
     },
     {
     "DoctImagePath": "",
     "DoctId": "75",
     "DoctUserId": "133",
     "EvaluateCount": "0",
     "ProfessName": "",
     "Telphone": "13784393001",
     "DepartName": "",
     "AtteCount": "0",
     "Identifier": "cf8ff9153a7c4d5c86c3b138e7f3c8f2",
     "Major": "111",
     "ClinicName": "孟的测试社区",
     "ClinicId": "779433",
     "DoctName": "孟护工001"
     }
     ]
     },
     "message": "成功"
     }
     */
    public class DataData {
        private List<DataDataData> nursers;

        public List<DataDataData> getNursers() {
            return nursers;
        }

        public void setNursers(List<DataDataData> nursers) {
            this.nursers = nursers;
        }



        public class DataDataData {
            private String DoctImagePath;
            private String DoctId;
            private String DoctUserId;
            private String EvaluateCount;
            private String ProfessName;
            private String Telphone;
            private String DepartName;
            private String AtteCount;
            private String Identifier;
            private String Major;
            private String ClinicName;
            private String ClinicId;
            private String DoctName;

            public String getDoctImagePath() {
                return DoctImagePath;
            }

            public void setDoctImagePath(String doctImagePath) {
                DoctImagePath = doctImagePath;
            }

            public String getDoctId() {
                return DoctId;
            }

            public void setDoctId(String doctId) {
                DoctId = doctId;
            }

            public String getDoctUserId() {
                return DoctUserId;
            }

            public void setDoctUserId(String doctUserId) {
                DoctUserId = doctUserId;
            }

            public String getEvaluateCount() {
                return EvaluateCount;
            }

            public void setEvaluateCount(String evaluateCount) {
                EvaluateCount = evaluateCount;
            }

            public String getProfessName() {
                return ProfessName;
            }

            public void setProfessName(String professName) {
                ProfessName = professName;
            }

            public String getTelphone() {
                return Telphone;
            }

            public void setTelphone(String telphone) {
                Telphone = telphone;
            }

            public String getDepartName() {
                return DepartName;
            }

            public void setDepartName(String departName) {
                DepartName = departName;
            }

            public String getAtteCount() {
                return AtteCount;
            }

            public void setAtteCount(String atteCount) {
                AtteCount = atteCount;
            }

            public String getIdentifier() {
                return Identifier;
            }

            public void setIdentifier(String identifier) {
                Identifier = identifier;
            }

            public String getMajor() {
                return Major;
            }

            public void setMajor(String major) {
                Major = major;
            }

            public String getClinicName() {
                return ClinicName;
            }

            public void setClinicName(String clinicName) {
                ClinicName = clinicName;
            }

            public String getClinicId() {
                return ClinicId;
            }

            public void setClinicId(String clinicId) {
                ClinicId = clinicId;
            }

            public String getDoctName() {
                return DoctName;
            }

            public void setDoctName(String doctName) {
                DoctName = doctName;
            }
        }
    }
}
