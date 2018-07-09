package cn.v1.unionc_user.model;

/**
 * Created by qy on 2018/3/30.
 */

public class JiGuangData {
    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }



    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public String getPushCategory() {
        return pushCategory;
    }

    public void setPushCategory(String pushCategory) {
        this.pushCategory = pushCategory;
    }

    /**
     pushCategory：类型  推送类型：1-活动，2-直播，3-医生，4-护士
     activityId：活动id
     title:活动主题
     digest：活动描述
     actionAddr：活动地点
     publishTime：活动发布时间
     startTime：活动开始时间
     endTime：活动结束时间
     */
private String pushCategory;
private String activityId;
    private String title;
    private String digest;
private String actionAddr;

    public String getActionAddr() {
        return actionAddr;
    }

    public void setActionAddr(String actionAddr) {
        this.actionAddr = actionAddr;
    }

    private String publishTime;
private String startTime;
private String endTime;
private String jump;
private String show;

    public String getJump() {
        return jump;
    }

    public void setJump(String jump) {
        this.jump = jump;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }
}
