package cn.v1.unionc_user.ui.home.BloodPressure.data;

import java.util.ArrayList;

/**
 * Created by An4 on 2015/9/24.
 */
public class SearchMatchDoctorData {

    /**
     * message : 请求成功
     * retList : [{"distance":111,"price":0,"professionId":"1","department":"心血管内科","userId":"2","nurserName":"崔宗强","experience":"20","prizeSize":0,"skilledSymptom":"心脏病,脑血管病","userhead_img":"http://ww.baid.com/pic/b.jpg","hospital":"北京协和医院","roleId":"002"}]
     * code : 0000
     * size : 1
     * price: 0,0
     */
    private String message;
    private ArrayList<RetListEntity> retList;
    private String code;
    private int size;
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRetList(ArrayList<RetListEntity> retList) {
        this.retList = retList;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<RetListEntity> getRetList() {
        return retList;
    }

    public String getCode() {
        return code;
    }

    public int getSize() {
        return size;
    }

    public static class RetListEntity {
        /**
         * distance : 111
         * price : 0
         * professionId : 1
         * department : 心血管内科
         * userId : 2
         * nurserName : 崔宗强
         * experience : 20
         * prizeSize : 0
         * skilledSymptom : 心脏病,脑血管病
         * userhead_img : http://ww.baid.com/pic/b.jpg
         * hospital : 北京协和医院
         * roleId : 002
         * professionName: 医生
         * heartNum: 爱心数
         * gzFlag:关注 1-关注；
         * yyFlag：预约 1-预约过
         * angelValue: 天使值
         */
        private int distance;
        private int price;
        private String professionId;
        private String department;
        private String userId;
        private String nurserName;
        private String experience;
        private int prizeSize;
        private String skilledSymptom;
        private String userhead_img;
        private String hospital;
        private String roleId;
        private String professionName;
        public boolean isCheck = false;
        private String answerSize;
        private String subscribeSize;
        private String sex;
        private String age;
        private String nativeplace;
        private String heartNum;
        private String gzFlag;
        private String yyFlag;
        private String angelValue;

        public String getYyFlag() {
            return yyFlag;
        }

        public void setYyFlag(String yyFlag) {
            this.yyFlag = yyFlag;
        }

        public String getGzFlag() {
            return gzFlag;
        }

        public void setGzFlag(String gzFlag) {
            this.gzFlag = gzFlag;
        }

        public String getAngelValue() {
            return angelValue;
        }

        public void setAngelValue(String angelValue) {
            this.angelValue = angelValue;
        }

        public String getHeartNum() {
            return heartNum;
        }

        public void setHeartNum(String heartNum) {
            this.heartNum = heartNum;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getNativeplace() {
            return nativeplace;
        }

        public void setNativeplace(String nativeplace) {
            this.nativeplace = nativeplace;
        }

        public String getSubscribeSize() {
            return subscribeSize;
        }

        public void setSubscribeSize(String subscribeSize) {
            this.subscribeSize = subscribeSize;
        }

        public String getAnswerSize() {
            return answerSize;
        }

        public void setAnswerSize(String answerSize) {
            this.answerSize = answerSize;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public void setProfessionId(String professionId) {
            this.professionId = professionId;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setNurserName(String nurserName) {
            this.nurserName = nurserName;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public void setPrizeSize(int prizeSize) {
            this.prizeSize = prizeSize;
        }

        public void setSkilledSymptom(String skilledSymptom) {
            this.skilledSymptom = skilledSymptom;
        }

        public void setUserhead_img(String userhead_img) {
            this.userhead_img = userhead_img;
        }

        public void setHospital(String hospital) {
            this.hospital = hospital;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public int getDistance() {
            return distance;
        }

        public int getPrice() {
            return price;
        }

        public String getProfessionId() {
            return professionId;
        }

        public String getDepartment() {
            return department;
        }

        public String getUserId() {
            return userId;
        }

        public String getNurserName() {
            return nurserName;
        }

        public String getExperience() {
            return experience;
        }

        public int getPrizeSize() {
            return prizeSize;
        }

        public String getSkilledSymptom() {
            return skilledSymptom;
        }

        public String getUserhead_img() {
            return userhead_img;
        }

        public String getHospital() {
            return hospital;
        }

        public String getRoleId() {
            return roleId;
        }

        public String getProfessionName() {
            return professionName;
        }

        public void setProfessionName(String professionName) {
            this.professionName = professionName;
        }
    }
}
