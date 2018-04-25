package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/3/26.
 */

public class HeartHistoryListData extends BaseData {


    /**
     * "data":{
     * "healthDatas":[
     * {"MonitorDate":"2018-04-14 05:00:00","HeartRate":"101","DataId":"4"},
     * {"MonitorDate":"2018-04-14 04:00:00","HeartRate":"100","DataId":"3"},
     * {"MonitorDate":"2018-04-14 04:00:00","HeartRate":"100","DataId":"5"}]

     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {

        private List<Heartdata> healthDatas;

        public List<Heartdata> getHealthDatas() {
            return healthDatas;
        }

        public void setHealthDatas(List<Heartdata> healthDatas) {
            this.healthDatas = healthDatas;
        }

        public static class Heartdata {
            private String MonitorDate;
            private String HeartRate;

            public String getMonitorDate() {
                return MonitorDate;
            }

            public void setMonitorDate(String monitorDate) {
                MonitorDate = monitorDate;
            }

            public String getHeartRate() {
                return HeartRate;
            }

            public void setHeartRate(String heartRate) {
                HeartRate = heartRate;
            }

            public String getDataId() {
                return DataId;
            }

            public void setDataId(String dataId) {
                DataId = dataId;
            }

            private String DataId;


        }




    }
}
