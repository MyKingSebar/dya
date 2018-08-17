package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/3/26.
 */

public class RecommendDoctorsData extends BaseData {



    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {


        private List<DoctorsData> doctors;

        public List<DoctorsData> getDoctors() {
            return doctors;
        }

        public void setDoctors(List<DoctorsData> doctors) {
            this.doctors = doctors;
        }

        public static class DoctorsData {
            /**
             * "ImagePath": "http://192.168.11.216:8080/unionWeb/image/webServer/compress/68/0/11/8a6711c2-1171-4951-ae33-f4fc15e26a07_Koala.jpg",
             "DoctName": "测试推荐2",
             "ProfessLevel": "知名专家",
             "DepartName": "外科",
             "DepartId": "16"
             DoctId:1
             */



            private String ImagePath;
            private String DoctName;
            private String ProfessLevel;
            private String DepartName;
            private String DepartId;
            private String DoctId;



            public String getDoctId() {
                return DoctId;
            }

            public void setDoctId(String doctId) {
                DoctId = doctId;
            }

            public String getImagePath() {
                return ImagePath;
            }

            public void setImagePath(String imagePath) {
                ImagePath = imagePath;
            }

            public String getDoctName() {
                return DoctName;
            }

            public void setDoctName(String doctName) {
                DoctName = doctName;
            }

            public String getProfessLevel() {
                return ProfessLevel;
            }

            public void setProfessLevel(String professLevel) {
                ProfessLevel = professLevel;
            }

            public String getDepartName() {
                return DepartName;
            }

            public void setDepartName(String departName) {
                DepartName = departName;
            }

            public String getDepartId() {
                return DepartId;
            }

            public void setDepartId(String departId) {
                DepartId = departId;
            }
        }


    }
}
