package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/3/26.
 */

public class GetLiveDoctorListData extends BaseData {

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
     "videoDoctors": [
     {
     "DoctId": "58",
     "DoctImagePath": "http://192.168.21.93:8081/image/webServer/compress/101/10/10/107497db-6193-466a-aa2d-3c21ee08701c_641947948088577221.jpg",
     "EvaluateCount": "1",		//评价数
     "ProfessName": "主任医师",	//级别
     "Telphone": "15615611111",
     "DepartName": "儿科",		//科室
     "AtteCount": "1",			//关注数
     "Major": "儿科",			//擅长
     "OnlineState": "0",			是否在线
     "ClinicName": "孟的测试医院",
     "ClinicId": "779427",
     "DoctName": "孟医生"
     },
     {
     "DoctId": "42",
     "DoctImagePath": "http://192.168.21.93:8081/image/webServer/compress/78/9/14/0765cd6b-03c9-4cdd-b9dc-8efd43ca9f30_file.jpg",
     "EvaluateCount": "0",
     "ProfessName": "副主任医师",
     "Telphone": "15615672129",
     "DepartName": "儿科",
     "AtteCount": "0",
     "Major": "啊啊啊啊",
     "OnlineState": "0",
     "ClinicName": "孟的测试医院",
     "ClinicId": "779416",
     "DoctName": "小孟"
     }
     ],
     "videoDoctorsCount": 2
     },
     "message": "成功"
     }
     */
    public class DataData {
        private List<DataDataData> videoDoctors;

        public List<DataDataData> getVideoDoctors() {
            return videoDoctors;
        }

        public void setVideoDoctors(List<DataDataData> videoDoctors) {
            this.videoDoctors = videoDoctors;
        }

        public class DataDataData {
            private String DoctId;
            private String DoctImagePath;
            private String EvaluateCount;
            private String ProfessName;
            private String Telphone;
            private String DepartName;
            private String AtteCount;
            private String Major;
            private String OnlineState;
            private String ClinicName;
            private String ClinicId;
            private String DoctName;
            private String Identifier;

            public String getIdentifier() {
                return Identifier;
            }

            public void setIdentifier(String identifier) {
                Identifier = identifier;
            }

            public String getDoctImagePath() {
                return DoctImagePath;
            }

            public void setDoctImagePath(String doctImagePath) {
                DoctImagePath = doctImagePath;
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

            public String getAtteCount() {
                return AtteCount;
            }

            public void setAtteCount(String atteCount) {
                AtteCount = atteCount;
            }

            public String getMajor() {
                return Major;
            }

            public void setMajor(String major) {
                Major = major;
            }

            public String getDoctId() {
                return DoctId;
            }

            public void setDoctId(String doctId) {
                DoctId = doctId;
            }

            public String getOnlineState() {
                return OnlineState;
            }

            public void setOnlineState(String onlineState) {
                OnlineState = onlineState;
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

        }
    }
}
