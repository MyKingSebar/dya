package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/3/26.
 */

public class HeartHistoryListData extends BaseData {
/**
 *
 * if (null != heartRateCount && heartRateCount.size() > 0) {
 dataMap.put("MaxData",MapUtils.getString(heartRateCount, "MaxData",""));		//最大值
 dataMap.put("MinData",MapUtils.getString(heartRateCount, "MinData",""));		//最小值
 dataMap.put("AvgData",MapUtils.getString(heartRateCount, "AvgData",""));		//平均值
 dataMap.put("TotalCount",MapUtils.getString(heartRateCount, "TotalCount",""));	//总次数
 dataMap.put("Record1",MapUtils.getString(heartRateCount, "Record1",""));		//偏低次数
 dataMap.put("Record2",MapUtils.getString(heartRateCount, "Record2",""));		//正常次数
 dataMap.put("Record3",MapUtils.getString(heartRateCount, "Record3",""));		//偏高次数
 dataMap.put("Record4",MapUtils.getString(heartRateCount, "Record4",""));		//严重次数
 }
 //历史记录列表
 List<Map<String, Object>> healthDataList = userInfoDao.queryHeartRateDataList(queryDataParam);
 if(healthDataList != null && healthDataList.size() > 0){
 for (Map<String, Object> map : healthDataList) {
 Map<String, Object> healthData=new HashMap<String, Object>();
 healthData.put("DataId", MapUtils.getString(map, "DataId",""));				//记录id
 healthData.put("Disorder", MapUtils.getString(map, "Disorder",""));			//不适应症
 healthData.put("HeartRate", MapUtils.getString(map, "HeartRate",""));			//心率值
 healthData.put("HeartRateImagePath", MapUtils.getString(map, "HeartRateImagePath",""));	//心率监测图
 healthData.put("CureMedicine", MapUtils.getString(map, "CureMedicine",""));		//治疗药物
 healthData.put("DiabetesType", MapUtils.getString(map, "DiabetesType",""));		//心胀病类型
 healthData.put("MonitorDate", MapUtils.getString(map, "MonitorDate",""));		//监控日期
 healthInfoDatas.add(healthData);
 }
 }
 */

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
        private String MaxData;
        private String MinData;
        private String AvgData;
        private String TotalCount;
        private String Record1;
        private String Record2;
        private String Record3;
        private String Record4;
        private List<Heartdata> healthDatas;


        public String getMaxData() {
            return MaxData;
        }

        public void setMaxData(String maxData) {
            MaxData = maxData;
        }

        public String getMinData() {
            return MinData;
        }

        public void setMinData(String minData) {
            MinData = minData;
        }

        public String getAvgData() {
            return AvgData;
        }

        public void setAvgData(String avgData) {
            AvgData = avgData;
        }

        public String getTotalCount() {
            return TotalCount;
        }

        public void setTotalCount(String totalCount) {
            TotalCount = totalCount;
        }

        public String getRecord1() {
            return Record1;
        }

        public void setRecord1(String record1) {
            Record1 = record1;
        }

        public String getRecord2() {
            return Record2;
        }

        public void setRecord2(String record2) {
            Record2 = record2;
        }

        public String getRecord3() {
            return Record3;
        }

        public void setRecord3(String record3) {
            Record3 = record3;
        }

        public String getRecord4() {
            return Record4;
        }

        public void setRecord4(String record4) {
            Record4 = record4;
        }


        public List<Heartdata> getHealthDatas() {
            return healthDatas;
        }

        public void setHealthDatas(List<Heartdata> healthDatas) {
            this.healthDatas = healthDatas;
        }

        public static class Heartdata {
            private String MonitorDate;
            private String HeartRate;
            private String DataId;


            private String Disorder;
            private String HeartRateImagePath;
            private String CureMedicine;
            private String DiabetesType;
            private String MonitorDateStr;

            public String getDisorder() {
                return Disorder;
            }

            public void setDisorder(String disorder) {
                Disorder = disorder;
            }

            public String getHeartRateImagePath() {
                return HeartRateImagePath;
            }

            public void setHeartRateImagePath(String heartRateImagePath) {
                HeartRateImagePath = heartRateImagePath;
            }

            public String getCureMedicine() {
                return CureMedicine;
            }

            public void setCureMedicine(String cureMedicine) {
                CureMedicine = cureMedicine;
            }

            public String getDiabetesType() {
                return DiabetesType;
            }

            public void setDiabetesType(String diabetesType) {
                DiabetesType = diabetesType;
            }

            public String getMonitorDateStr() {
                return MonitorDateStr;
            }

            public void setMonitorDateStr(String monitorDateStr) {
                MonitorDateStr = monitorDateStr;
            }

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


        }


    }
}
