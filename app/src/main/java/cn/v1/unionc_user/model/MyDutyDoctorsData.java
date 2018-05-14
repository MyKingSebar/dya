package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/2/27.
 */

public class MyDutyDoctorsData extends BaseData {

    /**
     * {
     "message": "成功",
     "data": {
     "dutyDoctors": [
     {
     "DutyName": "zhangsan3",
     "DutyTime": "2018-05-11",
     "ClinicName": "北京市第一医院",
     "Distance": "0.0",
     "DoctId": "2",
     "ClinicId": "779391",
     "DifferenceTime": "0.90",
     "EndTime": "15:30:00",
     "StartTime": "09:00:00",
     "ClinicImagePath": "http://192.168.21.93:8080/unionWeb/image/webServer/compress/2/7/3/60fefc74-48d9-4363-9fa8-c155313dac31_医生头像3.jpg",
     "Identifier": "a63aeab30db34dbeab3af17242fcd02c",
     "DoctImagePath": "http://192.168.21.93:8080/unionWeb/image/webServer/compress/2/3/4/6253898a-9664-408c-b150-f86a8650dbbd_医生头像2.jpg",
     "DoctName": "豆豆002"
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


        private List<DoctorsData> dutyDoctors;

        public List<DoctorsData> getDutyDoctors() {
            return dutyDoctors;
        }

        public void setDutyDoctors(List<DoctorsData> dutyDoctors) {
            this.dutyDoctors = dutyDoctors;
        }

        public static class DoctorsData {

            /**
             *
             * "dutyDoctors": [
             {
             "ClinicName": "北京市第一医院",
             "Identifier": "a63aeab30db34dbeab3af17242fcd02c",
             ""DutyName": "zhangsan3",
             "DutyTime": "2018-05-11",
             "Distance": "0.0",
             "DoctId": "2",
             "ClinicId": "779391",
             "DifferenceTime": "0.90",
             "EndTime": "15:30:00",
             "StartTime": "09:00:00",
             "ClinicImagePath": "http://192.168.21.93:8080/unionWeb/image/webServer/compress/2/7/3/60fefc74-48d9-4363-9fa8-c155313dac31_医生头像3.jpg",
             "DoctImagePath": "http://192.168.21.93:8080/unionWeb/image/webServer/compress/2/3/4/6253898a-9664-408c-b150-f86a8650dbbd_医生头像2.jpg",
             "DoctName": "豆豆002"
             }
             ]
             },
             */
            private String ClinicName;
            private String Identifier;
            private String DutyName;
            private String DutyTime;
            private String Distance;
            private String DoctId;
            private String ClinicId;
            private String DifferenceTime;
            private String EndTime;
            private String StartTime;
            private String ClinicImagePath;
            private String DoctImagePath;

            @Override
            public String toString() {
                return "DoctorsData{" +
                        "ClinicName='" + ClinicName + '\'' +
                        ", Identifier='" + Identifier + '\'' +
                        ", DutyName='" + DutyName + '\'' +
                        ", DutyTime='" + DutyTime + '\'' +
                        ", Distance='" + Distance + '\'' +
                        ", DoctId='" + DoctId + '\'' +
                        ", ClinicId='" + ClinicId + '\'' +
                        ", DifferenceTime='" + DifferenceTime + '\'' +
                        ", EndTime='" + EndTime + '\'' +
                        ", StartTime='" + StartTime + '\'' +
                        ", ClinicImagePath='" + ClinicImagePath + '\'' +
                        ", DoctImagePath='" + DoctImagePath + '\'' +
                        ", DoctName='" + DoctName + '\'' +
                        '}';
            }

            private String DoctName;

            public String getClinicName() {
                return ClinicName;
            }

            public void setClinicName(String clinicName) {
                ClinicName = clinicName;
            }

            public String getIdentifier() {
                return Identifier;
            }

            public void setIdentifier(String identifier) {
                Identifier = identifier;
            }

            public String getDutyName() {
                return DutyName;
            }

            public void setDutyName(String dutyName) {
                DutyName = dutyName;
            }

            public String getDutyTime() {
                return DutyTime;
            }

            public void setDutyTime(String dutyTime) {
                DutyTime = dutyTime;
            }

            public String getDistance() {
                return Distance;
            }

            public void setDistance(String distance) {
                Distance = distance;
            }

            public String getDoctId() {
                return DoctId;
            }

            public void setDoctId(String doctId) {
                DoctId = doctId;
            }

            public String getClinicId() {
                return ClinicId;
            }

            public void setClinicId(String clinicId) {
                ClinicId = clinicId;
            }

            public String getDifferenceTime() {
                return DifferenceTime;
            }

            public void setDifferenceTime(String differenceTime) {
                DifferenceTime = differenceTime;
            }

            public String getEndTime() {
                return EndTime;
            }

            public void setEndTime(String endTime) {
                EndTime = endTime;
            }

            public String getStartTime() {
                return StartTime;
            }

            public void setStartTime(String startTime) {
                StartTime = startTime;
            }

            public String getClinicImagePath() {
                return ClinicImagePath;
            }

            public void setClinicImagePath(String clinicImagePath) {
                ClinicImagePath = clinicImagePath;
            }

            public String getDoctImagePath() {
                return DoctImagePath;
            }

            public void setDoctImagePath(String doctImagePath) {
                DoctImagePath = doctImagePath;
            }

            public String getDoctName() {
                return DoctName;
            }

            public void setDoctName(String doctName) {
                DoctName = doctName;
            }
        }

    }
}
