package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/3/26.
 */

public class MainMessagePushData extends BaseData {


    /**
     * 返回值：{
     "message": "成功",
     "data": {
     "messages": [{
     "JumpId": "跳转ID",
     "CreatedTime": "推送时间 2018-7-18 15:27:11",
     "MessageId": "消息Id",
     "OrderBy": "排序号",
     "IsDelete": "是否删除（0：未删除 1：已删除）",
     "IsJump": "是否可以跳转（0：不可以 1：可以）",
     "IsRead": "是否已读（0：未读 1：已读）",
     "PushCategory": "推送类型（1-活动，2-直播，3-医生，4-护士5-绑定监护人）",
     "Content": {
     "HttpPullUrl1": "推流URL",
     "LiveTitle": "直播标题",
     "StartTime": "开始时间 2018-7-18 15:28:58",
     "Banner": "http://192.168.21.93:8081/image/webServer/compress/94/4/7/f8fdba5b-7223-4bc6-ba2b-f4c87ab2302c_timg2.jpg"
     }
     },
     {
     "JumpId": "0",
     "CreatedTime": "2018-7-18 14:58:57",
     "MessageId": "316",
     "OrderBy": "0",
     "IsDelete": "0",
     "IsJump": "0",
     "IsRead": "0",
     "PushCategory": "3",
     "Content": {
     "ClinicName": "医院名称",
     "Type": "类型（1：医生 2：护士）",
     "DoctId": "医生Id",
     "ElderlyRealName": "老人姓名",
     "DoctName": "医生姓名"
     }
     }, {
     "JumpId": "0",
     "CreatedTime": "2018-7-18 14:41:57",
     "MessageId": "315",
     "OrderBy": "0",
     "IsDelete": "0",
     "IsJump": "0",
     "IsRead": "0",
     "PushCategory": "4",
     "Content": {
     "ClinicName": "医院名称",
     "Type": "类型（1：医生 2：护士）",
     "DoctId": "医生Id",
     "ElderlyRealName": "老人姓名",
     "DoctName": "医生姓名"
     }
     }, {
     "JumpId": "51",
     "CreatedTime": "2018-7-18 14:28:23",
     "MessageId": "308",
     "OrderBy": "0",
     "IsDelete": "0",
     "IsJump": "1",
     "IsRead": "0",
     "PushCategory": "1",
     "Content": {
     "ActivityId": "活动Id",
     "PublishTime": "发布时间 ：07.18",
     "Digest": "摘要",
     "EndTime": "结束时间07.18",
     "StartTime": "开始时间07.18",
     "Title": "活动标题",
     "ActionAddr": "地址"
     }
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
        private List<DataDataData> messages;

        public List<DataDataData> getMessages() {
            return messages;
        }

        public void setMessages(List<DataDataData> messages) {
            this.messages = messages;
        }

        public class DataDataData{
private String JumpId;
private String CreatedTime;
private String MessageId;
private String OrderBy;
private String IsDelete;
private String IsJump;
private String IsRead;
private String PushCategory;//"推送类型（1-活动，2-直播，3-医生，4-护士5-绑定监护人）",
private MessageData Content;

            public String getJumpId() {
                return JumpId;
            }

            public void setJumpId(String jumpId) {
                JumpId = jumpId;
            }

            public String getCreatedTime() {
                return CreatedTime;
            }

            public void setCreatedTime(String createdTime) {
                CreatedTime = createdTime;
            }

            public String getMessageId() {
                return MessageId;
            }

            public void setMessageId(String messageId) {
                MessageId = messageId;
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

            public MessageData getContent() {
                return Content;
            }

            public void setContent(MessageData content) {
                Content = content;
            }

            public class MessageData{
    private String HttpPullUrl1;
    private String LiveTitle;
    private String StartTime;
    private String Banner;




    private String ClinicName;
    private String Type;
    private String DoctId;
    private String ElderlyRealName;
    private String DoctName;





    private String ActivityId;
    private String PublishTime;
    private String Digest;
    private String EndTime;
//    private String StartTime;
    private String Title;
    private String ActionAddr;

                public String getHttpPullUrl1() {
                    return HttpPullUrl1;
                }

                public void setHttpPullUrl1(String httpPullUrl1) {
                    HttpPullUrl1 = httpPullUrl1;
                }

                public String getLiveTitle() {
                    return LiveTitle;
                }

                public void setLiveTitle(String liveTitle) {
                    LiveTitle = liveTitle;
                }

                public String getStartTime() {
                    return StartTime;
                }

                public void setStartTime(String startTime) {
                    StartTime = startTime;
                }

                public String getBanner() {
                    return Banner;
                }

                public void setBanner(String banner) {
                    Banner = banner;
                }

                public String getClinicName() {
                    return ClinicName;
                }

                public void setClinicName(String clinicName) {
                    ClinicName = clinicName;
                }

                public String getType() {
                    return Type;
                }

                public void setType(String type) {
                    Type = type;
                }

                public String getDoctId() {
                    return DoctId;
                }

                public void setDoctId(String doctId) {
                    DoctId = doctId;
                }

                public String getElderlyRealName() {
                    return ElderlyRealName;
                }

                public void setElderlyRealName(String elderlyRealName) {
                    ElderlyRealName = elderlyRealName;
                }

                public String getDoctName() {
                    return DoctName;
                }

                public void setDoctName(String doctName) {
                    DoctName = doctName;
                }

                public String getActivityId() {
                    return ActivityId;
                }

                public void setActivityId(String activityId) {
                    ActivityId = activityId;
                }

                public String getPublishTime() {
                    return PublishTime;
                }

                public void setPublishTime(String publishTime) {
                    PublishTime = publishTime;
                }

                public String getDigest() {
                    return Digest;
                }

                public void setDigest(String digest) {
                    Digest = digest;
                }

                public String getEndTime() {
                    return EndTime;
                }

                public void setEndTime(String endTime) {
                    EndTime = endTime;
                }

                public String getTitle() {
                    return Title;
                }

                public void setTitle(String title) {
                    Title = title;
                }

                public String getActionAddr() {
                    return ActionAddr;
                }

                public void setActionAddr(String actionAddr) {
                    ActionAddr = actionAddr;
                }
            }
        }
    }
}
