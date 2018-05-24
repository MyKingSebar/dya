package cn.v1.unionc_user.ui.home.BloodPressure.data;

import java.io.Serializable;
import java.util.List;

import cn.v1.unionc_user.model.BaseData;

/**
 * Created by qy on 2018/5/12.
 */

public class BloodPresureHistoryData extends BaseData {


    /**
     * data : {"bloodPressure":[{"avgMeasure":"","bdaCode":"","cureMedicine":"","deviceModel":"","deviceType":"","highPressure":"177","lowPressure":"148","measureDate":"2018-05-12","measureTime":"18:11","measureWay":"1","patientInfoId":"57","pluseRate":"75","rate":6,"rateName":"重度高血压","recordId":"396","reportUrl":""},{"avgMeasure":"","bdaCode":"","cureMedicine":"","deviceModel":"","deviceType":"","highPressure":"130","lowPressure":"80","measureDate":"2018-05-12","measureTime":"15:54","measureWay":"1","patientInfoId":"57","pluseRate":"75","rate":3,"rateName":"正常高值血压","recordId":"67","reportUrl":""},{"avgMeasure":"","bdaCode":"","cureMedicine":"","deviceModel":"","deviceType":"","highPressure":"130","lowPressure":"80","measureDate":"2018-05-12","measureTime":"15:05","measureWay":"1","patientInfoId":"57","pluseRate":"75","rate":3,"rateName":"正常高值血压","recordId":"63","reportUrl":""}],"device":""}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
        /**
         * bloodPressure : [{"avgMeasure":"","bdaCode":"","cureMedicine":"","deviceModel":"","deviceType":"","highPressure":"177","lowPressure":"148","measureDate":"2018-05-12","measureTime":"18:11","measureWay":"1","patientInfoId":"57","pluseRate":"75","rate":6,"rateName":"重度高血压","recordId":"396","reportUrl":""},{"avgMeasure":"","bdaCode":"","cureMedicine":"","deviceModel":"","deviceType":"","highPressure":"130","lowPressure":"80","measureDate":"2018-05-12","measureTime":"15:54","measureWay":"1","patientInfoId":"57","pluseRate":"75","rate":3,"rateName":"正常高值血压","recordId":"67","reportUrl":""},{"avgMeasure":"","bdaCode":"","cureMedicine":"","deviceModel":"","deviceType":"","highPressure":"130","lowPressure":"80","measureDate":"2018-05-12","measureTime":"15:05","measureWay":"1","patientInfoId":"57","pluseRate":"75","rate":3,"rateName":"正常高值血压","recordId":"63","reportUrl":""}]
         * device :
         */

        private Device device;
        private List<BloodPressureData> bloodPressure;

        public Device getDevice() {
            return device;
        }

        public void setDevice(Device device) {
            this.device = device;
        }

        public List<BloodPressureData> getBloodPressure() {
            return bloodPressure;
        }

        public void setBloodPressure(List<BloodPressureData> bloodPressure) {
            this.bloodPressure = bloodPressure;
        }


        public static class Device{
            private String icon;
            private String deviceName;
            private String bdaCode;
            private String deviceId;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getDeviceName() {
                return deviceName;
            }

            public void setDeviceName(String deviceName) {
                this.deviceName = deviceName;
            }

            public String getBdaCode() {
                return bdaCode;
            }

            public void setBdaCode(String bdaCode) {
                this.bdaCode = bdaCode;
            }

            public String getDeviceId() {
                return deviceId;
            }

            public void setDeviceId(String deviceId) {
                this.deviceId = deviceId;
            }
        }

        public static class BloodPressureData implements Serializable {

            /**
             * age : 35
             * avgMeasure :
             * bdaCode :
             * cureMedicine : 啊路哟
             * deviceModel :
             * deviceType :
             * highPressure : 83
             * lowPressure : 80
             * measureDate : 2018-05-14
             * measureTime : 09:42
             * measureWay : 1
             * patientInfoId : 60
             * pluseRate : 75
             * rate : 1
             * rateName : 低血压
             * realName : 爸爸真名
             * recordId : 452
             * relationShip : 爸爸
             * reportUrl : http://file.yihu365.com/nurse/003/00315262621994952274.jpg
             * sex :
             */

            private String age;
            private String avgMeasure;
            private String bdaCode;
            private String cureMedicine;
            private String deviceModel;
            private String deviceType;
            private String highPressure;
            private String lowPressure;
            private String measureDate;
            private String measureTime;
            private String measureWay;
            private String patientInfoId;
            private String pluseRate;
            private int rate;
            private String rateName;
            private String realName;
            private String recordId;
            private String relationShip;
            private String reportUrl;
            private String sex;
            private boolean isHead;
            private String headText;


            public String getHeadText() {
                return headText;
            }

            public void setHeadText(String headText) {
                this.headText = headText;
            }

            public boolean isHead() {
                return isHead;
            }

            public void setHead(boolean head) {
                isHead = head;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getAvgMeasure() {
                return avgMeasure;
            }

            public void setAvgMeasure(String avgMeasure) {
                this.avgMeasure = avgMeasure;
            }

            public String getBdaCode() {
                return bdaCode;
            }

            public void setBdaCode(String bdaCode) {
                this.bdaCode = bdaCode;
            }

            public String getCureMedicine() {
                return cureMedicine;
            }

            public void setCureMedicine(String cureMedicine) {
                this.cureMedicine = cureMedicine;
            }

            public String getDeviceModel() {
                return deviceModel;
            }

            public void setDeviceModel(String deviceModel) {
                this.deviceModel = deviceModel;
            }

            public String getDeviceType() {
                return deviceType;
            }

            public void setDeviceType(String deviceType) {
                this.deviceType = deviceType;
            }

            public String getHighPressure() {
                return highPressure;
            }

            public void setHighPressure(String highPressure) {
                this.highPressure = highPressure;
            }

            public String getLowPressure() {
                return lowPressure;
            }

            public void setLowPressure(String lowPressure) {
                this.lowPressure = lowPressure;
            }

            public String getMeasureDate() {
                return measureDate;
            }

            public void setMeasureDate(String measureDate) {
                this.measureDate = measureDate;
            }

            public String getMeasureTime() {
                return measureTime;
            }

            public void setMeasureTime(String measureTime) {
                this.measureTime = measureTime;
            }

            public String getMeasureWay() {
                return measureWay;
            }

            public void setMeasureWay(String measureWay) {
                this.measureWay = measureWay;
            }

            public String getPatientInfoId() {
                return patientInfoId;
            }

            public void setPatientInfoId(String patientInfoId) {
                this.patientInfoId = patientInfoId;
            }

            public String getPluseRate() {
                return pluseRate;
            }

            public void setPluseRate(String pluseRate) {
                this.pluseRate = pluseRate;
            }

            public int getRate() {
                return rate;
            }

            public void setRate(int rate) {
                this.rate = rate;
            }

            public String getRateName() {
                return rateName;
            }

            public void setRateName(String rateName) {
                this.rateName = rateName;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getRecordId() {
                return recordId;
            }

            public void setRecordId(String recordId) {
                this.recordId = recordId;
            }

            public String getRelationShip() {
                return relationShip;
            }

            public void setRelationShip(String relationShip) {
                this.relationShip = relationShip;
            }

            public String getReportUrl() {
                return reportUrl;
            }

            public void setReportUrl(String reportUrl) {
                this.reportUrl = reportUrl;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }
        }
    }
}
