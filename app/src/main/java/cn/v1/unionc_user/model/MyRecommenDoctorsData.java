package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/2/27.
 */

public class MyRecommenDoctorsData extends BaseData {




    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {


        private List<DoctorsData> recommendDoctors;

        public List<DoctorsData> getRecommendDoctors() {
            return recommendDoctors;
        }

        public void setRecommendDoctors(List<DoctorsData> recommendDoctors) {
            this.recommendDoctors = recommendDoctors;
        }

        public static class DoctorsData {

            /**
             *
             * "recommendDoctors": [
             {
             "clinicName": "小米医院",
             "identifier": "c9c24d9035984e54be7bef40920e39ca",
             "clinicId": "779395",
             "doctorName": "华医生",
             "professLevel": "主任医师",
             "distance": "0.9227",
             "major": "擅长老年病，心脑血管疾病康复",
             "isSigned": "0",
             "imagePath": "http://www.yibashi.cn/image/webServer/compress/22/15/5/6accfccc-7aeb-4413-a644-4b06a45421bf_医生头像3.jpg",
             "doctId": "9",
             "departName": "脑科"
             }
             ]
             },
             */
            private String clinicName;
            private String identifier;
            private String clinicId;
            private String doctorName;
            private String professLevel;
            private String distance;
            private String major;
            /**
             * 是否签约（-1:待审核  0：未签约 1：已签约）',
             */
            private String isSigned;
            private String imagePath;
            private String doctId;
            private String departName;

            public String getClinicName() {
                return clinicName;
            }

            public void setClinicName(String clinicName) {
                this.clinicName = clinicName;
            }

            public String getIdentifier() {
                return identifier;
            }

            public void setIdentifier(String identifier) {
                this.identifier = identifier;
            }

            public String getClinicId() {
                return clinicId;
            }

            public void setClinicId(String clinicId) {
                this.clinicId = clinicId;
            }

            public String getDoctorName() {
                return doctorName;
            }

            public void setDoctorName(String doctorName) {
                this.doctorName = doctorName;
            }

            public String getProfessLevel() {
                return professLevel;
            }

            public void setProfessLevel(String professLevel) {
                this.professLevel = professLevel;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getMajor() {
                return major;
            }

            public void setMajor(String major) {
                this.major = major;
            }

            public String getIsSigned() {
                return isSigned;
            }

            public void setIsSigned(String isSigned) {
                this.isSigned = isSigned;
            }

            public String getImagePath() {
                return imagePath;
            }

            public void setImagePath(String imagePath) {
                this.imagePath = imagePath;
            }

            public String getDoctId() {
                return doctId;
            }

            public void setDoctId(String doctId) {
                this.doctId = doctId;
            }

            public String getDepartName() {
                return departName;
            }

            public void setDepartName(String departName) {
                this.departName = departName;
            }
        }

    }
}
