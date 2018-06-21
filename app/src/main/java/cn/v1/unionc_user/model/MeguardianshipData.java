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
            private String GuardianRoleName;
            private String GuardianId;
            private String DataId;
            private String GuardianHeadImage;
            private String GuardianName;
            private String DoctName;
            private String ElderlyUserId;

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

            private String GuardianIdentifier;
        }
    }
}
