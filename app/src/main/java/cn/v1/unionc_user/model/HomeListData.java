package cn.v1.unionc_user.model;

import java.io.Serializable;
import java.util.List;

import cn.v1.unionc_user.tecent_qcloud.tim_model.Message;

/**
 * Created by qy on 2018/2/9.
 */

public class HomeListData extends BaseData  {


    /**
     * data : {"attendingDoctors":[{"clinicId":"50708","clinicName":"小郊亭社区卫生服务站","departName":"骨科","distance":"2.0301","doctorName":"45测试","major":"100","professLevel":"知名专家"}],"recommendDoctors":[{"clinicId":"50708","clinicName":"小郊亭社区卫生服务站","departName":"骨科","distance":"2.0301","doctorName":"45测试","major":"100","professLevel":"知名专家"}],"signedDoctros":[{"clinicId":"50708","clinicName":"小郊亭社区卫生服务站","departName":"骨科","distance":"2.0301","doctorName":"45测试","major":"100","professLevel":"知名专家"}]}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
        private List<HomeData> attendingDoctors;
        private List<HomeData> recommendDoctors;
        private List<HomeData> signedDoctros;



        private List<HomeData> activities;

        public List<HomeData> getAttendingDoctors() {
            return attendingDoctors;
        }

        public void setAttendingDoctors(List<HomeData> attendingDoctors) {
            this.attendingDoctors = attendingDoctors;
        }

        public List<HomeData> getRecommendDoctors() {
            return recommendDoctors;
        }

        public void setRecommendDoctors(List<HomeData> recommendDoctors) {
            this.recommendDoctors = recommendDoctors;
        }

        public List<HomeData> getSignedDoctros() {
            return signedDoctros;
        }

        public void setSignedDoctros(List<HomeData> signedDoctros) {
            this.signedDoctros = signedDoctros;
        }

        public List<HomeData> getActivities() {
            return activities;
        }

        public void setActivities(List<HomeData> activities) {
            this.activities = activities;
        }

        public static class HomeData implements Serializable{
            @Override
            public String toString() {
                return "HomeData{" +
                        "type='" + type + '\'' +
                        ", lastMessage=" + lastMessage +
                        ", lasttime='" + lasttime + '\'' +
                        ", unReadMessage='" + unReadMessage + '\'' +
                        ", clinicId='" + clinicId + '\'' +
                        ", clinicName='" + clinicName + '\'' +
                        ", departName='" + departName + '\'' +
                        ", distance='" + distance + '\'' +
                        ", doctId='" + doctId + '\'' +
                        ", doctorName='" + doctorName + '\'' +
                        ", identifier='" + identifier + '\'' +
                        ", imagePath='" + imagePath + '\'' +
                        ", major='" + major + '\'' +
                        ", professLevel='" + professLevel + '\'' +
                        ", LinkUrl='" + LinkUrl + '\'' +
                        ", EndTime='" + EndTime + '\'' +
                        ", activityId='" + ActivityId + '\'' +
                        ", StartTime='" + StartTime + '\'' +
                        ", ImagePath='" + ImagePath + '\'' +
                        ", LinkType='" + LinkType + '\'' +
                        ", MessageId='" + MessageId + '\'' +
                        ", Name='" + Name + '\'' +
                        ", Address='" + Address + '\'' +
                        ", CreatedTime='" + CreatedTime + '\'' +
                        '}';
            }

            private String type;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            private Message lastMessage;

            public Message getLastMessage() {
                return lastMessage;
            }

            public void setLastMessage(Message lastMessage) {
                this.lastMessage = lastMessage;
            }

            private String lasttime;

            public String getLasttime() {
                return lasttime;
            }

            public void setLasttime(String lasttime) {
                this.lasttime = lasttime;
            }

            private String unReadMessage;

            public String getUnReadMessage() {
                return unReadMessage;
            }

            public void setUnReadMessage(String unReadMessage) {
                this.unReadMessage = unReadMessage;
            }

            /**
             * clinicId : 50708
             * clinicName : 小郊亭社区卫生服务站
             * departName : 骨科
             * distance : 2.03
             * doctId : 45
             * doctorName : 45测试
             * identifier : JKmo8DOJ3PnrG9MYH5vjiRl/rpwJI/HuwBuOGzlt/ngAf0CRODjq2AgdozfansJ6kCfxC2fMLo2vYZXIG2QOFFP1TB5QndgA
             * imagePath : http://192.168.11.216:8080/unionWeb/image/webServer/compress/43/2/4/479ba869-eb04-4a1d-b914-96990b9b6108_Chrysanthemum.jpg
             * major : 100
             * professLevel : 知名专家
             */

            private String clinicId;
            private String clinicName;
            private String departName;
            private String distance;
            private String doctId;
            private String doctorName;
            private String identifier;
            private String imagePath;
            private String major;
            private String professLevel;

            public String getClinicId() {
                return clinicId;
            }

            public void setClinicId(String clinicId) {
                this.clinicId = clinicId;
            }

            public String getClinicName() {
                return clinicName;
            }

            public void setClinicName(String clinicName) {
                this.clinicName = clinicName;
            }

            public String getDepartName() {
                return departName;
            }

            public void setDepartName(String departName) {
                this.departName = departName;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getDoctId() {
                return doctId;
            }

            public void setDoctId(String doctId) {
                this.doctId = doctId;
            }

            public String getDoctorName() {
                return doctorName;
            }

            public void setDoctorName(String doctorName) {
                this.doctorName = doctorName;
            }

            public String getIdentifier() {
                return identifier;
            }

            public void setIdentifier(String identifier) {
                this.identifier = identifier;
            }

            public String getImagePath() {
                return imagePath;
            }

            public void setImagePath(String imagePath) {
                this.imagePath = imagePath;
            }

            public String getMajor() {
                return major;
            }

            public void setMajor(String major) {
                this.major = major;
            }

            public String getProfessLevel() {
                return professLevel;
            }

            public void setProfessLevel(String professLevel) {
                this.professLevel = professLevel;
            }




            /**
             * 活动弹窗
             *
             *
             "LinkUrl": "www.baidu.com",
             "EndTime": "2017-12-29 22:00:00",
             "ActivityId": "2",
             "StartTime": "2017-12-28 10:00:00",
             "ImagePath": "/webServer/compress/68/15/1/fa586bba-b4b8-4d1c-be2c-fe2c2dc72c10_登录.png",
             "LinkType": "1",
             "MessageId": "14",
             "Name": "弹窗2"
             {"message":"成功","data":{"activities":
             [{"activityId":"16","Name":"成功吧","CreatedTime":"2018-03-20 15:23:57","LinkType":"1","MessageId":"31","Address":"北京北京","LinkUrl":"","EndTime":"2018-03-21 15:23:49","StartTime":"2018-03-19 15:23:47","ImagePath":"http://192.168.21.93:8081/webServer/image/webServer/compress/1/7/7/a2b9afcf-0240-4711-b0b6-55b91814036e_logo.jpg"},
             {"activityId":"2","Name":"测试320","CreatedTime":"2018-03-20 15:21:40","LinkType":"1","MessageId":"30","Address":"北京","LinkUrl":"","EndTime":"2018-03-21 15:21:32","StartTime":"2018-03-19 15:21:29","ImagePath":"http://192.168.21.93:8081/webServer/image/webServer/compress/1/10/6/510f3f0b-c434-4d92-9ea6-950d6d1cd417_logo.jpg"}]},"code":"4000"}
             */
            private String LinkUrl;
            private String EndTime;
            private String ActivityId;
            private String StartTime;
            private String ImagePath;
            private String LinkType;
            private String MessageId;
            private String Name;
            private String Address;
            private String CreatedTime;


            public String getActivityId() {
                return ActivityId;
            }

            public void setActivityId(String activityId) {
                ActivityId = activityId;
            }

            public String getImagePath2() {
                return ImagePath;
            }

            public void setImagePath2(String ImagePath) {
                this.ImagePath = ImagePath;
            }
            public String getLinkUrl() {
                return LinkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                LinkUrl = linkUrl;
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

            public String getLinkType() {
                return LinkType;
            }

            public void setLinkType(String linkType) {
                LinkType = linkType;
            }

            public String getMessageId() {
                return MessageId;
            }

            public void setMessageId(String messageId) {
                MessageId = messageId;
            }

            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }

            public String getAddress() {
                return Address;
            }

            public void setAddress(String address) {
                Address = address;
            }

            public String getCreatedTime() {
                return CreatedTime;
            }

            public void setCreatedTime(String createdTime) {
                CreatedTime = createdTime;
            }


            private String JumpId;
//            private String CreatedTime;
//            private String MessageId;
            private String OrderBy;
            private String IsDelete;
            private String IsJump;
            private String IsRead;
            private String PushCategory;//"推送类型（1-活动，2-直播，3-医生，4-护士5-绑定监护人）",
            private MainMessagePushData.DataData.DataDataData.MessageData Content;

            public String getJumpId() {
                return JumpId;
            }

            public void setJumpId(String jumpId) {
                JumpId = jumpId;
            }

            public String getOrderBy() {
                return OrderBy;
            }

            public void setOrderBy(String orderBy) {
                OrderBy = orderBy;
            }

            public String getIsDelete() {
                return IsDelete;
            }

            public void setIsDelete(String isDelete) {
                IsDelete = isDelete;
            }

            public String getIsJump() {
                return IsJump;
            }

            public void setIsJump(String isJump) {
                IsJump = isJump;
            }

            public String getIsRead() {
                return IsRead;
            }

            public void setIsRead(String isRead) {
                IsRead = isRead;
            }

            public String getPushCategory() {
                return PushCategory;
            }

            public void setPushCategory(String pushCategory) {
                PushCategory = pushCategory;
            }

            public MainMessagePushData.DataData.DataDataData.MessageData getContent() {
                return Content;
            }

            public void setContent(MainMessagePushData.DataData.DataDataData.MessageData content) {
                Content = content;
            }
        }

    }
}
