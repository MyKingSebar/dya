package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/3/26.
 */

public class OMLHistoryData extends BaseData {


    /**
     * {
     "message": "成功",
     "data": {
     "healthDatas": [
     {
     "PatientInfoId": "",
     "LowPressure": "65",          //低压
     "rate": 2,                    //血压等级
     "monitorDate": "2018-05-29",  //监测日期
     "BdaCode": "resgresgresres",  //bda码
     "HeartRate": "65",            //心率
     "DataId": "1",                //记录id
     "AvgMeasure": "0",            //
     "monitorTime": "10:38:00",    //监测日期
     "DeviceType": "0",            //
     "rateName": "正常血压",       //血压等级
     "MeasureWay": "0",            //
     "HighPressure": "100",        //高压
     "DeviceModel": "欧姆龙血压仪" //仪器名称和型号
     },
     {
     "PatientInfoId": "",
     "LowPressure": "65",
     "rate": 2,
     "monitorDate": "2018-05-29",
     "BdaCode": "resgresgresres",
     "HeartRate": "65",
     "DataId": "2",
     "AvgMeasure": "0",
     "monitorTime": "10:38:00",
     "DeviceType": "0",
     "rateName": "正常血压",
     "MeasureWay": "0",
     "HighPressure": "100",
     "DeviceModel": "欧姆龙血压仪"
     }
     ]
     },
     "code": "4000"
     }
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {

        private List<OMLdatta> healthDatas;

        public List<OMLdatta> getHealthDatas() {
            return healthDatas;
        }

        public void setHealthDatas(List<OMLdatta> healthDatas) {
            this.healthDatas = healthDatas;
        }

        public static class OMLdatta {
            /*"PatientInfoId": "",
                    "LowPressure": "65",          //低压
                    "rate": 2,                    //血压等级
                    "monitorDate": "2018-05-29",  //监测日期
                    "BdaCode": "resgresgresres",  //bda码
                    "HeartRate": "65",            //心率
                    "DataId": "1",                //记录id
                    "AvgMeasure": "0",            //
                    "monitorTime": "10:38:00",    //监测日期
                    "DeviceType": "0",            //
                    "rateName": "正常血压",       //血压等级
                    "MeasureWay": "0",            //
                    "HighPressure": "100",        //高压
                    "DeviceModel": "欧姆龙血压仪" //仪器名称和型号*/
            private String PatientInfoId;
            private String LowPressure;
            private String rate;
            private String monitorDate;
            private String BdaCode;
            private String HeartRate;
            private String DataId;
            private String AvgMeasure;
            private String monitorTime;
            private String DeviceType;
            private String rateName;
            private String MeasureWay;
            private String HighPressure;
            private String DeviceModel;

            public String getPatientInfoId() {
                return PatientInfoId;
            }

            public void setPatientInfoId(String patientInfoId) {
                PatientInfoId = patientInfoId;
            }

            public String getLowPressure() {
                return LowPressure;
            }

            public void setLowPressure(String lowPressure) {
                LowPressure = lowPressure;
            }

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }

            public String getMonitorDate() {
                return monitorDate;
            }

            public void setMonitorDate(String monitorDate) {
                this.monitorDate = monitorDate;
            }

            public String getBdaCode() {
                return BdaCode;
            }

            public void setBdaCode(String bdaCode) {
                BdaCode = bdaCode;
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

            public String getAvgMeasure() {
                return AvgMeasure;
            }

            public void setAvgMeasure(String avgMeasure) {
                AvgMeasure = avgMeasure;
            }

            public String getMonitorTime() {
                return monitorTime;
            }

            public void setMonitorTime(String monitorTime) {
                this.monitorTime = monitorTime;
            }

            public String getDeviceType() {
                return DeviceType;
            }

            public void setDeviceType(String deviceType) {
                DeviceType = deviceType;
            }

            public String getRateName() {
                return rateName;
            }

            public void setRateName(String rateName) {
                this.rateName = rateName;
            }

            public String getMeasureWay() {
                return MeasureWay;
            }

            public void setMeasureWay(String measureWay) {
                MeasureWay = measureWay;
            }

            public String getHighPressure() {
                return HighPressure;
            }

            public void setHighPressure(String highPressure) {
                HighPressure = highPressure;
            }

            public String getDeviceModel() {
                return DeviceModel;
            }

            public void setDeviceModel(String deviceModel) {
                DeviceModel = deviceModel;
            }
        }




    }
}
