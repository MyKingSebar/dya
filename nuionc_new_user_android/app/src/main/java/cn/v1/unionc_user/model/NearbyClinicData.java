package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/3/8.
 */

public class NearbyClinicData extends BaseData {

private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    /**
     * 返回值：{
     "message": "成功",
     "data": {
     "clinicDatas": [{
     "Status": "状态：0:初始化 1：审核中 2：审核通过",
     "ClinicName": "医院名称",
     "AUniress": "地址",
     "IsNew": "是否新增（0：否 1：是）",
     "Distance": "0.0708（距离）",
     "ClinicId": "医院Id",
     "ImagePath": "图片Url",
     "Tips": "标签"
     }]
     },
     "code": "4000"
     }
     */
    public class DataData{
        private List<DataDataData> clinicDatas;

        public List<DataDataData> getClinicDatas() {
            return clinicDatas;
        }

        public void setClinicDatas(List<DataDataData> clinicDatas) {
            this.clinicDatas = clinicDatas;
        }

        public class DataDataData{
            private String Status;

            public String getStatus() {
                return Status;
            }

            public void setStatus(String status) {
                Status = status;
            }

            public String getClinicName() {
                return ClinicName;
            }

            public void setClinicName(String clinicName) {
                ClinicName = clinicName;
            }

            public String getAUniress() {
                return AUniress;
            }

            public void setAUniress(String AUniress) {
                this.AUniress = AUniress;
            }

            public String getIsNew() {
                return IsNew;
            }

            public void setIsNew(String isNew) {
                IsNew = isNew;
            }

            public String getDistance() {
                return Distance;
            }

            public void setDistance(String distance) {
                Distance = distance;
            }

            public String getClinicId() {
                return ClinicId;
            }

            public void setClinicId(String clinicId) {
                ClinicId = clinicId;
            }

            public String getImagePath() {
                return ImagePath;
            }

            public void setImagePath(String imagePath) {
                ImagePath = imagePath;
            }

            public String getTips() {
                return Tips;
            }

            public void setTips(String tips) {
                Tips = tips;
            }

            private String ClinicName;
            private String AUniress;
            private String IsNew;
            private String Distance;
            private String ClinicId;
            private String ImagePath;
            private String Tips;
        }
    }



}
