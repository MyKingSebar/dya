package cn.v1.unionc_user.model;

import java.util.List;

import cn.v1.unionc_user.tecent_qcloud.tim_model.Message;

/**
 * Created by qy on 2018/2/9.
 */

public class MeWatchingDoctorListData extends BaseData {


    /**
     * "data": {
     "doctors": [
     {
     "DoctorName": "苏涛1",
     "FirstClinicName": "",
     "weekNow": "",
     "DoctId": "2",
     "DoctEvaCount": "9",
     "RefundAmount": "10.0",
     "CollectionCount": "2",
     "RecommendCount ": “0”,
     "DepartName": "儿科",
     "ProfessLevel": "主任医师",
     "Major": "吃饭，睡觉，打豆豆",
     "IsShare": "0",
     "Remarks": "开天辟地，无所不能",
     "ClinicName": "小郊亭社区卫生服务站",
     "weekDay": "",
     "ImagePath": "",
     "weekEnd": "",
     "ServicePrice": "100.0",
     "Distance": "0.00"
     }
     ]

     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {


        private List<DoctorData> doctors;

        public List<DoctorData> getDoctors() {
            return doctors;
        }

        public void setDoctors(List<DoctorData> doctors) {
            this.doctors = doctors;
        }

        public static class DoctorData {

            /**
             医生姓名
             */
            private String DoctorName;
            /**
             第一执业医院
             */
            private String FirstClinicName;
            /**
             当日门诊
             */
            private String weekNow;
            /**
             医生Id
             */
            private String DoctId;
            /**
             医生评价数
             */
            private String DoctEvaCount;
            /**
             优惠金额
             */
            private String RefundAmount;
            /**
             医生收藏数
             */
            private String CollectionCount;
            /**
             推荐数
             */
            private String RecommendCount;
            /**
             医生所属科室
             */
            private String DepartName;
            /**
             级别
             */
            private String ProfessLevel;
            /**
             擅长
             */
            private String Major;
            /**
            是否特约医生
             */
            private String IsShare;
            /**
             简介
             */
            private String Remarks;
            /**
             坐诊医院名称
             */
            private String ClinicName;
            /**
             日常约诊
             */
            private String weekDay;
            /**
             头像
             */
            private String ImagePath;
            /**
             周末约诊
             */
            private String weekEnd;
            /**
             医事服务费
             */
            private String ServicePrice;
            /**
             距离
             */
            private String Distance;

            public String getDoctorName() {
                return DoctorName;
            }

            public void setDoctorName(String doctorName) {
                DoctorName = doctorName;
            }

            public String getFirstClinicName() {
                return FirstClinicName;
            }

            public void setFirstClinicName(String firstClinicName) {
                FirstClinicName = firstClinicName;
            }

            public String getWeekNow() {
                return weekNow;
            }

            public void setWeekNow(String weekNow) {
                this.weekNow = weekNow;
            }

            public String getDoctId() {
                return DoctId;
            }

            public void setDoctId(String doctId) {
                DoctId = doctId;
            }

            public String getDoctEvaCount() {
                return DoctEvaCount;
            }

            public void setDoctEvaCount(String doctEvaCount) {
                DoctEvaCount = doctEvaCount;
            }

            public String getRefundAmount() {
                return RefundAmount;
            }

            public void setRefundAmount(String refundAmount) {
                RefundAmount = refundAmount;
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

            public String getDepartName() {
                return DepartName;
            }

            public void setDepartName(String departName) {
                DepartName = departName;
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

            public String getIsShare() {
                return IsShare;
            }

            public void setIsShare(String isShare) {
                IsShare = isShare;
            }

            public String getRemarks() {
                return Remarks;
            }

            public void setRemarks(String remarks) {
                Remarks = remarks;
            }

            public String getClinicName() {
                return ClinicName;
            }

            public void setClinicName(String clinicName) {
                ClinicName = clinicName;
            }

            public String getWeekDay() {
                return weekDay;
            }

            public void setWeekDay(String weekDay) {
                this.weekDay = weekDay;
            }

            public String getImagePath() {
                return ImagePath;
            }

            public void setImagePath(String imagePath) {
                ImagePath = imagePath;
            }

            public String getWeekEnd() {
                return weekEnd;
            }

            public void setWeekEnd(String weekEnd) {
                this.weekEnd = weekEnd;
            }

            public String getServicePrice() {
                return ServicePrice;
            }

            public void setServicePrice(String servicePrice) {
                ServicePrice = servicePrice;
            }

            public String getDistance() {
                return Distance;
            }

            public void setDistance(String distance) {
                Distance = distance;
            }


        }

    }
}
