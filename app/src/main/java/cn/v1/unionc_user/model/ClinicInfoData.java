package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/3/26.
 */

public class ClinicInfoData extends BaseData {


    /**
     * {"message":"成功","data":{"clinicInfo":{"IsHaveContributing":"","AUniress":"","Distance":"0.00","CollectionCount":"0","ClinicId":"50708","RecommendCount":"0","Name":"","Notes":"","evaluates":[],"Latitude":0.0,"Longitude":0.0,"ImagePath":"","Tips":[],"Tel":"","EvaCount":"0"},"doctors":[]},"code":"4000"}
     * <p>
     * "clinicInfo": {
     * "IsHaveContributing": "0",
     * "AUniress": "北京市,市辖区,东城区,锐创国际",
     * "EvaCount": "0",
     * "ImagePath": "http://192.168.21.93:8080/unionWeb/image/webServer/compress/68/14/4/baaaccdf-e809-49ed-8a5c-e7b2e54b6a82_logo.png",
     * "Tel": "0108888888",
     * "Latitude": 40.015698,
     * "CollectionCount": "0",
     * "RecommendCount ": “0”,
     * "Longitude": 116.491371,
     * "Distance": "0.32",
     * "Notes": "范围12",
     * "Name": "苏涛的测试医院",
     * "Tips": [
     * "感冒",
     * "发烧",
     * "口腔"
     * ]
     * }
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
        public ClinicData getClinicInfo() {
            return clinicInfo;
        }

        public void setClinicInfo(ClinicData clinicInfo) {
            this.clinicInfo = clinicInfo;
        }

        /**
         * doctors : {"AUniress":"北京市,市辖区,东城区,锐创国际","ClinicName":"苏涛的测试医院 ","CollectionCount":"0","DepartName":"口腔科","Distance":"0.88","DoctId":"45","DoctorName":"苏涛1111","EvaluateCount":"0","FirstClinicName":"小郊亭社区卫生服务站","ImagePath":"http://192.168.21.93:8080/unionWeb/image/webServer/compress/68/12/0/952f4d8d-7832-4a7c-b7ee-97c19c27a598_u=3247990642,490396215&fm=27&gp=0.jpg","IsAttention":"0","IsRecom":"0","IsShare":"0","Latitude":"40.015698","Longitude":"116.491371","Major":"擅长","ProfessLevel":"知名专家","RecommendCount":"0","Remarks":"备注","Telphone":"0108888888"}
         * familyDoctFlag : 1
         * questions : [{"PageView":"15","Question":"提问","QuestionId":"1"}]
         */

        private ClinicData clinicInfo;
        private List<DoctorsData> doctors;
        private List<ServiceData> service;

        public List<ServiceData> getService() {
            return service;
        }

        public void setService(List<ServiceData> service) {
            this.service = service;
        }

        public List<DoctorsData> getDoctors() {
            return doctors;
        }

        public void setDoctors(List<DoctorsData> doctors) {
            this.doctors = doctors;
        }

        /**
         *
         *
         * "service":[{"ServiceName":"服务名称","ServiceCode":"服务code","Content":"内容"}]
         */
        public class ServiceData {
            private String ServiceName;
            private String ServiceCode;
            private String Content;

            public String getServiceName() {
                return ServiceName;
            }

            public void setServiceName(String serviceName) {
                ServiceName = serviceName;
            }

            public String getServiceCode() {
                return ServiceCode;
            }

            public void setServiceCode(String serviceCode) {
                ServiceCode = serviceCode;
            }

            public String getContent() {
                return Content;
            }

            public void setContent(String content) {
                Content = content;
            }
        }

        public static class ClinicData {
            public String getIsHaveContributing() {
                return IsHaveContributing;
            }

            public void setIsHaveContributing(String isHaveContributing) {
                IsHaveContributing = isHaveContributing;
            }

            public String getAUniress() {
                return AUniress;
            }

            public void setAUniress(String AUniress) {
                this.AUniress = AUniress;
            }

            public String getEvaCount() {
                return EvaCount;
            }

            public void setEvaCount(String evaCount) {
                EvaCount = evaCount;
            }

            public String getImagePath() {
                return ImagePath;
            }

            public void setImagePath(String imagePath) {
                ImagePath = imagePath;
            }

            public String getTel() {
                return Tel;
            }

            public void setTel(String tel) {
                Tel = tel;
            }

            public String getLatitude() {
                return Latitude;
            }

            public void setLatitude(String latitude) {
                Latitude = latitude;
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

            public String getLongitude() {
                return Longitude;
            }

            public void setLongitude(String longitude) {
                Longitude = longitude;
            }

            public String getDistance() {
                return Distance;
            }

            public void setDistance(String distance) {
                Distance = distance;
            }

            public String getNotes() {
                return Notes;
            }

            public void setNotes(String notes) {
                Notes = notes;
            }

            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }

            public List<String> getTips() {
                return Tips;
            }

            public void setTips(List<String> tips) {
                Tips = tips;
            }

            /**
             * "IsHaveContributing": "0",
             * "AUniress": "北京市,市辖区,东城区,锐创国际",
             * "EvaCount": "0",
             * "ImagePath": "http://192.168.21.93:8080/unionWeb/image/webServer/compress/68/14/4/baaaccdf-e809-49ed-8a5c-e7b2e54b6a82_logo.png",
             * "Tel": "0108888888",
             * "Latitude": 40.015698,
             * "CollectionCount": "0",
             * "RecommendCount ": “0”,
             * "Longitude": 116.491371,
             * "Distance": "0.32",
             * "Notes": "范围12",
             * "Name": "苏涛的测试医院",
             * "Tips": [
             * "感冒",
             * "发烧",
             * "口腔"
             * ]
             * <p>
             * 新家俩字段：clinicData.put("DoctName", "");
             * clinicData.put("DoctImagePath", "");
             */

            private String ParCategory;
            private String DoctName;

            public String getDoctName() {
                return DoctName;
            }

            public void setDoctName(String doctName) {
                DoctName = doctName;
            }

            public String getDoctImagePath() {
                return DoctImagePath;
            }

            public void setDoctImagePath(String doctImagePath) {
                DoctImagePath = doctImagePath;
            }

            private String DoctImagePath;

            public String getParCategory() {
                return ParCategory;
            }

            public void setParCategory(String parCategory) {
                ParCategory = parCategory;
            }

            private String IsHaveContributing;
            private String AUniress;
            private String EvaCount;
            private String ImagePath;
            private List<String> ImagePaths;

            public List<String> getImagePaths() {
                return ImagePaths;
            }

            public void setImagePaths(List<String> imagePaths) {
                ImagePaths = imagePaths;
            }

            private String Tel;
            private String Latitude;
            private String CollectionCount;
            private String RecommendCount;
            private String Longitude;
            private String Distance;
            private String Notes;
            private String Name;
            //是否有值班医生
            private String IsDuty;
            private String Identifier;

            public String getIsDuty() {
                return IsDuty;
            }

            public void setIsDuty(String isDuty) {
                this.IsDuty = isDuty;
            }

            public String getIdentifier() {
                return Identifier;
            }

            public void setIdentifier(String identifier) {
                Identifier = identifier;
            }


            public String getIsRecom() {
                return IsRecom;
            }

            public void setIsRecom(String isRecom) {
                IsRecom = isRecom;
            }

            public String getIsAttention() {
                return IsAttention;
            }

            public void setIsAttention(String isAttention) {
                IsAttention = isAttention;
            }

            private String IsRecom; //是否推荐（0-未推荐，1-已推荐）
            private String IsAttention;//是否关注 0未关注 1已关注
            private List<String> Tips;


        }

        public static class DoctorsData {
            /**
             * AUniress : 北京市,市辖区,东城区,锐创国际
             * ClinicName : 苏涛的测试医院
             * CollectionCount : 0
             * DepartName : 口腔科
             * Distance : 0.88
             * DoctId : 45
             * DoctorName : 苏涛1111
             * EvaluateCount : 0
             * FirstClinicName : 小郊亭社区卫生服务站
             * ImagePath : http://192.168.21.93:8080/unionWeb/image/webServer/compress/68/12/0/952f4d8d-7832-4a7c-b7ee-97c19c27a598_u=3247990642,490396215&fm=27&gp=0.jpg
             * IsAttention : 0
             * IsRecom : 0
             * IsShare : 0
             * Latitude : 40.015698
             * Longitude : 116.491371
             * Major : 擅长
             * ProfessLevel : 知名专家
             * RecommendCount : 0
             * Remarks : 备注
             * Telphone : 0108888888
             */


            private String DoctId;
            private String DoctorName;
            private String ProfessLevel;
            private String Major;
            private String ClinicName;
            private String FirstClinicName;
            private String AUniress;
            private String ImagePath;
            private String DoctEvaCount;
            private String CollectionCount;
            private String RecommendCount;
            private String weekNow;
            private String weekEnd;
            private String weekDay;
            private String ServicePrice;
            private String DepartName;
            private String IsShare;
            private String Distance;
            private String IsRecom;
            private String IsAttention;

            public String getDoctId() {
                return DoctId;
            }

            public void setDoctId(String doctId) {
                DoctId = doctId;
            }

            public String getDoctorName() {
                return DoctorName;
            }

            public void setDoctorName(String doctorName) {
                DoctorName = doctorName;
            }

            public String getProfessLevel() {
                return ProfessLevel;
            }

            public void setProfessLevel(String professLevel) {
                ProfessLevel = professLevel;
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

            public String getFirstClinicName() {
                return FirstClinicName;
            }

            public void setFirstClinicName(String firstClinicName) {
                FirstClinicName = firstClinicName;
            }

            public String getAUniress() {
                return AUniress;
            }

            public void setAUniress(String AUniress) {
                this.AUniress = AUniress;
            }

            public String getImagePath() {
                return ImagePath;
            }

            public void setImagePath(String imagePath) {
                ImagePath = imagePath;
            }

            public String getDoctEvaCount() {
                return DoctEvaCount;
            }

            public void setDoctEvaCount(String doctEvaCount) {
                DoctEvaCount = doctEvaCount;
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

            public String getWeekNow() {
                return weekNow;
            }

            public void setWeekNow(String weekNow) {
                this.weekNow = weekNow;
            }

            public String getWeekEnd() {
                return weekEnd;
            }

            public void setWeekEnd(String weekEnd) {
                this.weekEnd = weekEnd;
            }

            public String getWeekDay() {
                return weekDay;
            }

            public void setWeekDay(String weekDay) {
                this.weekDay = weekDay;
            }

            public String getServicePrice() {
                return ServicePrice;
            }

            public void setServicePrice(String servicePrice) {
                ServicePrice = servicePrice;
            }

            public String getDepartName() {
                return DepartName;
            }

            public void setDepartName(String departName) {
                DepartName = departName;
            }

            public String getIsShare() {
                return IsShare;
            }

            public void setIsShare(String isShare) {
                IsShare = isShare;
            }

            public String getDistance() {
                return Distance;
            }

            public void setDistance(String distance) {
                Distance = distance;
            }

            public String getIsRecom() {
                return IsRecom;
            }

            public void setIsRecom(String isRecom) {
                IsRecom = isRecom;
            }

            public String getIsAttention() {
                return IsAttention;
            }

            public void setIsAttention(String isAttention) {
                IsAttention = isAttention;
            }
        }


    }
}
