package cn.v1.unionc_user.ui.home.BloodPressure.data;

import java.util.List;

/**
 * Created by An4 on 2016/9/27.
 */
public class HealthMonitorData {


    /**
     * code : 0000
     * message : 请求成功
     * data : {"diabetesType":"","monitorData":[{"monitorId":"","monitorDate":"2016-09-26","data1":"","data4":"","data5":"","data2":"","data3":"","data8":"","data6":"","data7":"","id":"","pic1":"","userId":"","pic6":"","pic5":"","pic4":"","pic3":"","week":"9.26 周一","pic2":""},{"monitorId":"","monitorDate":"2016-09-27","data1":"","data4":"","data5":"","data2":"","data3":"","data8":"","data6":"","data7":"","id":"","pic1":"","userId":"","pic6":"","pic5":"","pic4":"","pic3":"","week":"9.27 周二","pic2":""},{"monitorId":"","monitorDate":"2016-09-28","data1":"","data4":"","data5":"","data2":"","data3":"","data8":"","data6":"","data7":"","id":"","pic1":"","userId":"","pic6":"","pic5":"","pic4":"","pic3":"","week":"9.28 周三","pic2":""},{"monitorId":"","monitorDate":"2016-09-29","data1":"","data4":"","data5":"","data2":"","data3":"","data8":"","data6":"","data7":"","id":"","pic1":"","userId":"","pic6":"","pic5":"","pic4":"","pic3":"","week":"9.29 周四","pic2":""},{"monitorId":"","monitorDate":"2016-09-30","data1":"","data4":"","data5":"","data2":"","data3":"","data8":"","data6":"","data7":"","id":"","pic1":"","userId":"","pic6":"","pic5":"","pic4":"","pic3":"","week":"9.30 周五","pic2":""},{"monitorId":"","monitorDate":"2016-10-01","data1":"","data4":"","data5":"","data2":"","data3":"","data8":"","data6":"","data7":"","id":"","pic1":"","userId":"","pic6":"","pic5":"","pic4":"","pic3":"","week":"10.1 周六","pic2":""},{"monitorId":"","monitorDate":"2016-10-02","data1":"","data4":"","data5":"","data2":"","data3":"","data8":"","data6":"","data7":"","id":"","pic1":"","userId":"","pic6":"","pic5":"","pic4":"","pic3":"","week":"10.2 周日","pic2":""}],"messageCount":0,"cureMedicine":""}
     */

    private String code;
    private String message;
    /**
     * diabetesType :
     * monitorData : [{"monitorId":"","monitorDate":"2016-09-26","data1":"","data4":"","data5":"","data2":"","data3":"","data8":"","data6":"","data7":"","id":"","pic1":"","userId":"","pic6":"","pic5":"","pic4":"","pic3":"","week":"9.26 周一","pic2":""},{"monitorId":"","monitorDate":"2016-09-27","data1":"","data4":"","data5":"","data2":"","data3":"","data8":"","data6":"","data7":"","id":"","pic1":"","userId":"","pic6":"","pic5":"","pic4":"","pic3":"","week":"9.27 周二","pic2":""},{"monitorId":"","monitorDate":"2016-09-28","data1":"","data4":"","data5":"","data2":"","data3":"","data8":"","data6":"","data7":"","id":"","pic1":"","userId":"","pic6":"","pic5":"","pic4":"","pic3":"","week":"9.28 周三","pic2":""},{"monitorId":"","monitorDate":"2016-09-29","data1":"","data4":"","data5":"","data2":"","data3":"","data8":"","data6":"","data7":"","id":"","pic1":"","userId":"","pic6":"","pic5":"","pic4":"","pic3":"","week":"9.29 周四","pic2":""},{"monitorId":"","monitorDate":"2016-09-30","data1":"","data4":"","data5":"","data2":"","data3":"","data8":"","data6":"","data7":"","id":"","pic1":"","userId":"","pic6":"","pic5":"","pic4":"","pic3":"","week":"9.30 周五","pic2":""},{"monitorId":"","monitorDate":"2016-10-01","data1":"","data4":"","data5":"","data2":"","data3":"","data8":"","data6":"","data7":"","id":"","pic1":"","userId":"","pic6":"","pic5":"","pic4":"","pic3":"","week":"10.1 周六","pic2":""},{"monitorId":"","monitorDate":"2016-10-02","data1":"","data4":"","data5":"","data2":"","data3":"","data8":"","data6":"","data7":"","id":"","pic1":"","userId":"","pic6":"","pic5":"","pic4":"","pic3":"","week":"10.2 周日","pic2":""}]
     * messageCount : 0
     * cureMedicine :
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
        private String diabetesType;
        private String messageCount;
        private String cureMedicine;
        private String monitorDateStart;
        private String monitorDateEnd;
        private String dateStr;
        private String pic1;
        private String pic2;
        private String pic3;
        private String pic4;
        private String pic5;
        private String pic6;
        private String monitorDate;
        private String monitorDateStr;
        private String id;
        private String monitorId;
        private String data1;
        private String data2;
        private String data3;
        private String data4;
        private String data5;
        private String data6;
        private String data7;
        private String data8;
        /**
         * monitorId :
         * monitorDate : 2016-09-26
         * data1 :
         * data4 :
         * data5 :
         * data2 :
         * data3 :
         * data8 :
         * data6 :
         * data7 :
         * id :
         * pic1 :
         * userId :
         * pic6 :
         * pic5 :
         * pic4 :
         * pic3 :
         * week : 9.26 周一
         * pic2 :
         */

        private List<MonitorDataBean> monitorData;

        public String getDiabetesType() {
            return diabetesType;
        }

        public void setDiabetesType(String diabetesType) {
            this.diabetesType = diabetesType;
        }

        public String getMessageCount() {
            return messageCount;
        }

        public void setMessageCount(String messageCount) {
            this.messageCount = messageCount;
        }

        public String getCureMedicine() {
            return cureMedicine;
        }

        public void setCureMedicine(String cureMedicine) {
            this.cureMedicine = cureMedicine;
        }

        public List<MonitorDataBean> getMonitorData() {
            return monitorData;
        }

        public void setMonitorData(List<MonitorDataBean> monitorData) {
            this.monitorData = monitorData;
        }

        public String getMonitorDateStart() {
            return monitorDateStart;
        }

        public void setMonitorDateStart(String monitorDateStart) {
            this.monitorDateStart = monitorDateStart;
        }

        public String getMonitorDateEnd() {
            return monitorDateEnd;
        }

        public void setMonitorDateEnd(String monitorDateEnd) {
            this.monitorDateEnd = monitorDateEnd;
        }

        public String getDateStr() {
            return dateStr;
        }

        public void setDateStr(String dateStr) {
            this.dateStr = dateStr;
        }

        public String getPic1() {
            return pic1;
        }

        public void setPic1(String pic1) {
            this.pic1 = pic1;
        }

        public String getPic2() {
            return pic2;
        }

        public void setPic2(String pic2) {
            this.pic2 = pic2;
        }

        public String getPic3() {
            return pic3;
        }

        public void setPic3(String pic3) {
            this.pic3 = pic3;
        }

        public String getPic4() {
            return pic4;
        }

        public void setPic4(String pic4) {
            this.pic4 = pic4;
        }

        public String getPic5() {
            return pic5;
        }

        public void setPic5(String pic5) {
            this.pic5 = pic5;
        }

        public String getPic6() {
            return pic6;
        }

        public void setPic6(String pic6) {
            this.pic6 = pic6;
        }

        public String getMonitorDate() {
            return monitorDate;
        }

        public void setMonitorDate(String monitorDate) {
            this.monitorDate = monitorDate;
        }

        public String getMonitorDateStr() {
            return monitorDateStr;
        }

        public void setMonitorDateStr(String monitorDateStr) {
            this.monitorDateStr = monitorDateStr;
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

        public String getData1() {
            return data1;
        }

        public void setData1(String data1) {
            this.data1 = data1;
        }

        public String getData2() {
            return data2;
        }

        public void setData2(String data2) {
            this.data2 = data2;
        }

        public String getData3() {
            return data3;
        }

        public void setData3(String data3) {
            this.data3 = data3;
        }

        public String getData4() {
            return data4;
        }

        public void setData4(String data4) {
            this.data4 = data4;
        }

        public String getData5() {
            return data5;
        }

        public void setData5(String data5) {
            this.data5 = data5;
        }

        public String getData6() {
            return data6;
        }

        public void setData6(String data6) {
            this.data6 = data6;
        }

        public String getData7() {
            return data7;
        }

        public void setData7(String data7) {
            this.data7 = data7;
        }

        public String getData8() {
            return data8;
        }

        public void setData8(String data8) {
            this.data8 = data8;
        }

        public static class MonitorDataBean {
            private String monitorId;
            private String monitorDate;
            private String data1;
            private String data4;
            private String data5;
            private String data2;
            private String data3;
            private String data8;
            private String data6;
            private String data7;
            private String id;
            private String pic1;
            private String userId;
            private String pic6;
            private String pic5;
            private String pic4;
            private String pic3;
            private String week;
            private String pic2;
            private String cureMedicine;

            public String getCureMedicine() {
                return cureMedicine;
            }

            public void setCureMedicine(String cureMedicine) {
                this.cureMedicine = cureMedicine;
            }

            public String getMonitorId() {
                return monitorId;
            }

            public void setMonitorId(String monitorId) {
                this.monitorId = monitorId;
            }

            public String getMonitorDate() {
                return monitorDate;
            }

            public void setMonitorDate(String monitorDate) {
                this.monitorDate = monitorDate;
            }

            public String getData1() {
                return data1;
            }

            public void setData1(String data1) {
                this.data1 = data1;
            }

            public String getData4() {
                return data4;
            }

            public void setData4(String data4) {
                this.data4 = data4;
            }

            public String getData5() {
                return data5;
            }

            public void setData5(String data5) {
                this.data5 = data5;
            }

            public String getData2() {
                return data2;
            }

            public void setData2(String data2) {
                this.data2 = data2;
            }

            public String getData3() {
                return data3;
            }

            public void setData3(String data3) {
                this.data3 = data3;
            }

            public String getData8() {
                return data8;
            }

            public void setData8(String data8) {
                this.data8 = data8;
            }

            public String getData6() {
                return data6;
            }

            public void setData6(String data6) {
                this.data6 = data6;
            }

            public String getData7() {
                return data7;
            }

            public void setData7(String data7) {
                this.data7 = data7;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPic1() {
                return pic1;
            }

            public void setPic1(String pic1) {
                this.pic1 = pic1;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getPic6() {
                return pic6;
            }

            public void setPic6(String pic6) {
                this.pic6 = pic6;
            }

            public String getPic5() {
                return pic5;
            }

            public void setPic5(String pic5) {
                this.pic5 = pic5;
            }

            public String getPic4() {
                return pic4;
            }

            public void setPic4(String pic4) {
                this.pic4 = pic4;
            }

            public String getPic3() {
                return pic3;
            }

            public void setPic3(String pic3) {
                this.pic3 = pic3;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getPic2() {
                return pic2;
            }

            public void setPic2(String pic2) {
                this.pic2 = pic2;
            }
        }
    }
}
