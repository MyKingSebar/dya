package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/3/26.
 */

public class HeartHistoryData extends BaseData {


    /**
     * "healthData":
     * {"MonitorDate":"2018-04-14 04:00:00",
     * "CureMedicine":"各种药物",
     * "HeartRate":"100",
     * "DiabetesType":"高血压心脏病",
     * "HeartRateImage":"http://192.168.21.93:8080/unionWeb/image/unionWeb/original/916/6/8/b15e79f4-0c20-438b-8fd2-59979bc6bbb9_temphead.jpg",
     * "Disorder":"心疼,头晕",
     * "Reason":"影响因素111"}}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {

        private List<Heartdata> healthData;

        public List<Heartdata> getHealthData() {
            return healthData;
        }

        public void setHealthData(List<Heartdata> healthData) {
            this.healthData = healthData;
        }

        public static class Heartdata {
            private String MonitorDate;
            private String CureMedicine;
            private String HeartRate;
            private String DiabetesType;
            private String HeartRateImage;
            private String Disorder;
            private String Reason;

            public String getMonitorDate() {
                return MonitorDate;
            }

            public void setMonitorDate(String monitorDate) {
                MonitorDate = monitorDate;
            }

            public String getCureMedicine() {
                return CureMedicine;
            }

            public void setCureMedicine(String cureMedicine) {
                CureMedicine = cureMedicine;
            }

            public String getHeartRate() {
                return HeartRate;
            }

            public void setHeartRate(String heartRate) {
                HeartRate = heartRate;
            }

            public String getDiabetesType() {
                return DiabetesType;
            }

            public void setDiabetesType(String diabetesType) {
                DiabetesType = diabetesType;
            }

            public String getHeartRateImage() {
                return HeartRateImage;
            }

            public void setHeartRateImage(String heartRateImage) {
                HeartRateImage = heartRateImage;
            }

            public String getDisorder() {
                return Disorder;
            }

            public void setDisorder(String disorder) {
                Disorder = disorder;
            }

            public String getReason() {
                return Reason;
            }

            public void setReason(String reason) {
                Reason = reason;
            }
        }




    }
}
