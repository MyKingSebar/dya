package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/2/9.
 */

public class MeWatchingHospitalListData extends BaseData {


    /**
     *
     *"data": {
     "clinics": [
     {
     "ClinicEvaCount": "0",
     "IsShare": "1",
     "ClinicName": "小郊亭社区卫生服务站",
     "ClinicId": "50708",
     "ImagePath": "http://192.168.11.216:8080/unionWeb/image/webServer/compress/43/9/12/c4b84a39-6911-40e5-b9e0-782e716b82b6_22.jpg",
     "CollectionCount": "0",
     "RecommendCount ": “0”,
     "Tel": "0303333333",
     "Distance": "0.00",
     "Tips": [
     "33333",
     "4444",
     "5555"
     ]
     }
     ]
     },
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {


        public List<HospitalData> getClinics() {
            return clinics;
        }

        public void setClinics(List<HospitalData> clinics) {
            this.clinics = clinics;
        }

        private List<HospitalData> clinics;



        public static class HospitalData {

            public String getClinicEvaCount() {
                return ClinicEvaCount;
            }

            public void setClinicEvaCount(String clinicEvaCount) {
                ClinicEvaCount = clinicEvaCount;
            }

            public String getIsShare() {
                return IsShare;
            }

            public void setIsShare(String isShare) {
                IsShare = isShare;
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

            public String getImagePath() {
                return ImagePath;
            }

            public void setImagePath(String imagePath) {
                ImagePath = imagePath;
            }

            public String getCollectionCount() {
                return CollectionCount;
            }

            public void setCollectionCount(String collectionCount) {
                CollectionCount = collectionCount;
            }

            public String getRecommendCount() {
                return RecommendCount;
            }

            public void setRecommendCount(String recommendCount) {
                RecommendCount = recommendCount;
            }

            public String getTel() {
                return Tel;
            }

            public void setTel(String tel) {
                Tel = tel;
            }

            public String getDistance() {
                return Distance;
            }

            public void setDistance(String distance) {
                Distance = distance;
            }

            public List<String> getTips() {
                return Tips;
            }

            public void setTips(List<String> tips) {
                Tips = tips;
            }
            public String getAddress() {
                return Address;
            }

            public void setAddress(String address) {
                Address = address;
            }

            /**
             医院评价数
             */
            private String ClinicEvaCount;
            /**
             是否特约医生
             */
            private String IsShare;
            /**
             医院名称
             */
            private String ClinicName;
            /**
             医院Id
             */
            private String ClinicId;
            /**
             医院图片
             */
            private String ImagePath;
            /**
             医院收藏数
             */
            private String CollectionCount;
            /**
             推荐数
             */
            private String RecommendCount ;
            /**
             电话
             */
            private String Tel;
            /**
             医院距离
             */
            private String Distance;



            /**
             医院地址
             */
            private String Address;
            /**
             标签
             */
            private List<String> Tips;





        }

    }
}
