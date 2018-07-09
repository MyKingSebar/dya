package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/2/27.
 */

public class MeguardianshipData extends BaseData {


    /**
     * {
     * "message": "成功",
     * "data": {
     * "guardianr": {
     * "GuardianRoleName": "",
     * "GuardianId": "1288",
     * "DataId": "1",
     * "GuardianHeadImage": "http://192.168.21.93:8080/unionWeb/image/webServer/compress/78/9/14/0765cd6b-03c9-4cdd-b9dc-8efd43ca9f30_file.jpg",
     * "GuardianName": "苏涛测试2",
     * "GuardianIdentifier": "a1aae524a27d464f81cc55c8b94b05a4"
     * }
     * },
     * "code": "4000"
     * }
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
        private String hasGuardian;//hasGuardian字段，0-无，1-有

        public String getHasGuardian() {
            return hasGuardian;
        }

        public void setHasGuardian(String hasGuardian) {
            this.hasGuardian = hasGuardian;
        }

        private List<GuardianshipData> guardian;

        public List<GuardianshipData> getGuardian() {
            return guardian;
        }

        public void setGuardian(List<GuardianshipData> guardian) {
            this.guardian = guardian;
        }

        public static class GuardianshipData {
/**
 *  "GuardianRoleName": "",
 "GuardianId": "1288",
 "DataId": "1",
 "GuardianHeadImage": "http://192.168.21.93:8080/unionWeb/image/webServer/compress/78/9/14/0765cd6b-03c9-4cdd-b9dc-8efd43ca9f30_file.jpg",
 "GuardianName": "苏涛测试2",
 "GuardianIdentifier": "a1aae524a27d464f81cc55c8b94b05a4"
 */
            /**
             * "GuardianRoleName":"绑定的角色","GuardianId":"监护人Id","DataId":"1","GuardianHeadImage":"头像","GuardianName":"姓名","GuardianIdentifier":"监护人IM Id"
             */
            /**
             * 返回值：{"message":"成功","data":{"guardian":[{"IsAssignDoctor":"0","CreatedTime":"2018-07-05 15:07:35","ClinicName":"","ElderlyId":"23","DataId":"26","GuardianHeadImage":"http://192.168.21.93:8080/unionWeb/image/webServer/compress/78/9/14/0765cd6b-03c9-4cdd-b9dc-8efd43ca9f30_file.jpg","ElderlyUserId":"1330","GuardianIdentifier":"45722f85a2ec497a9ff44ba19c184d93","DoctName":"","GuardianId":"1330","GuardianRoleName":"","GuardianName":"苏涛测试","IsBound":" 0:初始化 1：绑定","IsOpenUp":"0：未开通 1：审核中 2：已开通"}],"hasGuardian":1},"code":"4000"}
             */
            private String IsAssignDoctor;
            private String CreatedTime;
            private String ClinicName;
            private String ElderlyId;
            private String IsBound;
            private String IsOpenUp;

            private String GuardianRoleName;
            private String GuardianId;
            private String DataId;
            private String GuardianHeadImage;
            private String GuardianName;
            private String DoctName;
            private String ElderlyUserId;
            private String GuardianIdentifier;


            private boolean haschecked ;

            public boolean getHaschecked() {
                return haschecked;
            }

            public void setHaschecked(boolean haschecked) {
                this.haschecked = haschecked;
            }

            public String getElderlyUserId() {
                return ElderlyUserId;
            }

            public void setElderlyUserId(String elderlyUserId) {
                ElderlyUserId = elderlyUserId;
            }

            public String getDoctName() {
                return DoctName;
            }

            public void setDoctName(String doctName) {
                DoctName = doctName;
            }

            public String getGuardianRoleName() {
                return GuardianRoleName;
            }

            public void setGuardianRoleName(String guardianRoleName) {
                GuardianRoleName = guardianRoleName;
            }

            public String getGuardianId() {
                return GuardianId;
            }

            public void setGuardianId(String guardianId) {
                GuardianId = guardianId;
            }

            public String getDataId() {
                return DataId;
            }

            public void setDataId(String dataId) {
                DataId = dataId;
            }

            public String getGuardianHeadImage() {
                return GuardianHeadImage;
            }

            public void setGuardianHeadImage(String guardianHeadImage) {
                GuardianHeadImage = guardianHeadImage;
            }

            public String getGuardianName() {
                return GuardianName;
            }

            public void setGuardianName(String guardianName) {
                GuardianName = guardianName;
            }

            public String getGuardianIdentifier() {
                return GuardianIdentifier;
            }

            public void setGuardianIdentifier(String guardianIdentifier) {
                GuardianIdentifier = guardianIdentifier;
            }

            public String getIsAssignDoctor() {
                return IsAssignDoctor;
            }

            public void setIsAssignDoctor(String isAssignDoctor) {
                IsAssignDoctor = isAssignDoctor;
            }

            public String getCreatedTime() {
                return CreatedTime;
            }

            public void setCreatedTime(String createdTime) {
                CreatedTime = createdTime;
            }

            public String getClinicName() {
                return ClinicName;
            }

            public void setClinicName(String clinicName) {
                ClinicName = clinicName;
            }

            public String getElderlyId() {
                return ElderlyId;
            }

            public void setElderlyId(String elderlyId) {
                ElderlyId = elderlyId;
            }

            public String getIsBound() {
                return IsBound;
            }

            public void setIsBound(String isBound) {
                IsBound = isBound;
            }

            public String getIsOpenUp() {
                return IsOpenUp;
            }

            public void setIsOpenUp(String isOpenUp) {
                IsOpenUp = isOpenUp;
            }
        }
    }
}
