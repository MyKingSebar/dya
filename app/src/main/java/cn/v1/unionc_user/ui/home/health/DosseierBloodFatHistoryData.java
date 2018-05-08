package cn.v1.unionc_user.ui.home.health;

import java.util.List;

/**
 * Created by qy on 2017/4/8.
 *
 *  血脂、心率数据结构
 */
public class DosseierBloodFatHistoryData {

    /**
     * code : 0000
     * message : 请求成功
     * data : {"avgData":"74.5","minData":"65","record3":"0","maxData":"84","record4":"0","resultList":[{"pic1":"http://file.yihu365.com/onlinetHospital/001/00115234175291214298.png","id":"985","monitorId":"178","disorder":"","diabetesType":"心脏肿瘤","monitorDate":"2018-04-11 12:12:12","userId":"11621","isUpload":"1","monitorDateStr":"2018-04-11","cureMedicine":"越来越检验检疫局越来越","data7":"84"},{"pic1":"","id":"984","monitorId":"178","disorder":"","diabetesType":"无病史","monitorDate":"2018-04-11 12:12:12","userId":"11621","monitorDateStr":"2018-04-11","cureMedicine":"他咯莫","data7":"65"}],"record1":"0","record2":"2","totalRecord":"2"}
     */

    private String code;
    private String message;
    /**
     * avgData : 74.5
     * minData : 65
     * record3 : 0
     * maxData : 84
     * record4 : 0
     * resultList : [{"pic1":"http://file.yihu365.com/onlinetHospital/001/00115234175291214298.png","id":"985","monitorId":"178","disorder":"","diabetesType":"心脏肿瘤","monitorDate":"2018-04-11 12:12:12","userId":"11621","isUpload":"1","monitorDateStr":"2018-04-11","cureMedicine":"越来越检验检疫局越来越","data7":"84"},{"pic1":"","id":"984","monitorId":"178","disorder":"","diabetesType":"无病史","monitorDate":"2018-04-11 12:12:12","userId":"11621","monitorDateStr":"2018-04-11","cureMedicine":"他咯莫","data7":"65"}]
     * record1 : 0
     * record2 : 2
     * totalRecord : 2
     */

    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String avgData;
        private String minData;
        private String record3;
        private String maxData;
        private String record4;
        private String record1;
        private String record2;
        private String totalRecord;
        /**
         * pic1 : http://file.yihu365.com/onlinetHospital/001/00115234175291214298.png
         * id : 985
         * monitorId : 178
         * disorder :
         * diabetesType : 心脏肿瘤
         * monitorDate : 2018-04-11 12:12:12
         * userId : 11621
         * isUpload : 1
         * monitorDateStr : 2018-04-11
         * cureMedicine : 越来越检验检疫局越来越
         * data7 : 84
         */

        private List<ResultListBean> resultList;

        public String getAvgData() {
            return avgData;
        }

        public void setAvgData(String avgData) {
            this.avgData = avgData;
        }

        public String getMinData() {
            return minData;
        }

        public void setMinData(String minData) {
            this.minData = minData;
        }

        public String getRecord3() {
            return record3;
        }

        public void setRecord3(String record3) {
            this.record3 = record3;
        }

        public String getMaxData() {
            return maxData;
        }

        public void setMaxData(String maxData) {
            this.maxData = maxData;
        }

        public String getRecord4() {
            return record4;
        }

        public void setRecord4(String record4) {
            this.record4 = record4;
        }

        public String getRecord1() {
            return record1;
        }

        public void setRecord1(String record1) {
            this.record1 = record1;
        }

        public String getRecord2() {
            return record2;
        }

        public void setRecord2(String record2) {
            this.record2 = record2;
        }

        public String getTotalRecord() {
            return totalRecord;
        }

        public void setTotalRecord(String totalRecord) {
            this.totalRecord = totalRecord;
        }

        public List<ResultListBean> getResultList() {
            return resultList;
        }

        public void setResultList(List<ResultListBean> resultList) {
            this.resultList = resultList;
        }

        public static class ResultListBean {
            private String pic1;
            private String id;
            private String monitorId;
            private String disorder;
            private String diabetesType;
            private String monitorDate;
            private String userId;
            private String isUpload;
            private String monitorDateStr;
            private String cureMedicine;
            private String data7;

            public String getPic1() {
                return pic1;
            }

            public void setPic1(String pic1) {
                this.pic1 = pic1;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMonitorId() {
                return monitorId;
            }

            public void setMonitorId(String monitorId) {
                this.monitorId = monitorId;
            }

            public String getDisorder() {
                return disorder;
            }

            public void setDisorder(String disorder) {
                this.disorder = disorder;
            }

            public String getDiabetesType() {
                return diabetesType;
            }

            public void setDiabetesType(String diabetesType) {
                this.diabetesType = diabetesType;
            }

            public String getMonitorDate() {
                return monitorDate;
            }

            public void setMonitorDate(String monitorDate) {
                this.monitorDate = monitorDate;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getIsUpload() {
                return isUpload;
            }

            public void setIsUpload(String isUpload) {
                this.isUpload = isUpload;
            }

            public String getMonitorDateStr() {
                return monitorDateStr;
            }

            public void setMonitorDateStr(String monitorDateStr) {
                this.monitorDateStr = monitorDateStr;
            }

            public String getCureMedicine() {
                return cureMedicine;
            }

            public void setCureMedicine(String cureMedicine) {
                this.cureMedicine = cureMedicine;
            }

            public String getData7() {
                return data7;
            }

            public void setData7(String data7) {
                this.data7 = data7;
            }
        }
    }
}
