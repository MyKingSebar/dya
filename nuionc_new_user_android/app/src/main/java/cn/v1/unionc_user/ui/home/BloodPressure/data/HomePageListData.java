package cn.v1.unionc_user.ui.home.BloodPressure.data;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2015/9/29.
 */
public class HomePageListData {
    private String code;
    private String message;
    private String payOrdr;
    private String pageH5;
    private String picH5;
    private String titleH5;
    private String contentH5;
    private String shareH5Url;
    private String sharePic;
    private String jumpType;

    public String getJumpType() {
        return jumpType;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }

    public String getPayOrdr() {
        return payOrdr;
    }

    public void setPayOrdr(String payOrdr) {
        this.payOrdr = payOrdr;
    }

    public String getPageH5() {
        return pageH5;
    }

    public void setPageH5(String pageH5) {
        this.pageH5 = pageH5;
    }

    public String getPicH5() {
        return picH5;
    }

    public void setPicH5(String picH5) {
        this.picH5 = picH5;
    }

    public String getTitleH5() {
        return titleH5;
    }

    public void setTitleH5(String titleH5) {
        this.titleH5 = titleH5;
    }

    public String getContentH5() {
        return contentH5;
    }

    public void setContentH5(String contentH5) {
        this.contentH5 = contentH5;
    }

    public String getShareH5Url() {
        return shareH5Url;
    }

    public void setShareH5Url(String shareH5Url) {
        this.shareH5Url = shareH5Url;
    }

    public String getSharePic() {
        return sharePic;
    }

    public void setSharePic(String sharePic) {
        this.sharePic = sharePic;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    private String updateTime;
    private List<PageItem> data;
    private String voucher_item;
    /**
     * activity : {"activityId":"3","activityName":"注册完善信息领取红包","activityPrice":"10元"}
     */
    @SerializedName("activity")
    @Nullable
    private ActivityEntity activity;


    public String getVoucher_item() {
        return voucher_item;
    }

    public void setVoucher_item(String voucher_item) {
        this.voucher_item = voucher_item;
    }

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

    public List<PageItem> getData() {
        return data;
    }

    public void setData(List<PageItem> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HomePageListData{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public void setActivity(ActivityEntity activity) {
        this.activity = activity;
    }

    public ActivityEntity getActivity() {
        return activity;
    }

    public static class PageItem implements Serializable {
        private String code;
        private String name;
        private String descrip;
        private String jump;
        private String role_code;
        private String service_code = "";
        private String subServiceCode; //健康计划新加,二级服务
        private String flag; //健康计划新加
        private String role_name;
        private String pic;
        private String h5_introduction;
        private String open_city;
        private String openStatus; //首页 暂停服务 字段 1 不停  0 暂停
        private String isRecommend; // 新版首页主推三项标识
        private String image; //图片
        private String headImg;
        private String order; //新版首页服务项展示顺序
        private String isClick; //是否能跳转 //居家养老
        private String jumpUrl; //跳转的链接  //居家养老
        private String physicalExaminationType; //健康计划新加字段，只用于本地
        private String healthPlanOrderId;//健康计划订单id用于本地。

        public String getHealthPlanOrderId() {
            return healthPlanOrderId;
        }

        public void setHealthPlanOrderId(String healthPlanOrderId) {
            this.healthPlanOrderId = healthPlanOrderId;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getSubServiceCode() {
            return subServiceCode;
        }

        public void setSubServiceCode(String subServiceCode) {
            this.subServiceCode = subServiceCode;
        }

        public String getPhysicalExaminationType() {
            return physicalExaminationType;
        }

        public void setPhysicalExaminationType(String physicalExaminationType) {
            this.physicalExaminationType = physicalExaminationType;
        }
        private String clientJumpType = "";//客户端使用，与服务器无关，1代表是转诊陪诊进入到医院列表

        public String getClientJumpType() {
            return clientJumpType;
        }

        public void setClientJumpType(String clientJumpType) {
            this.clientJumpType = clientJumpType;
        }

        public String getIsClick() {
            return isClick;
        }

        public void setIsClick(String isClick) {
            this.isClick = isClick;
        }

        public String getJumpUrl() {
            return jumpUrl;
        }

        public void setJumpUrl(String jumpUrl) {
            this.jumpUrl = jumpUrl;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getHeadImg() {
            return headImg;
        }
        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getIsRecommend() {
            return isRecommend == null ? "" : isRecommend;
        }

        public void setIsRecommend(String isRecommend) {
            this.isRecommend = isRecommend;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getOpenStatus() {
            return openStatus;
        }

        public void setOpenStatus(String openStatus) {
            this.openStatus = openStatus;
        }

        public String getOpen_city() {
            if (open_city == null) {
                return "";
            }
            return open_city;
        }
        public void setOpen_city(String openCity) {
            open_city = openCity;
        }

        public String getH5_introduction() {
            return h5_introduction;
        }

        public void setH5_introduction(String h5_introduction) {
            this.h5_introduction = h5_introduction;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescrip() {
            return descrip;
        }

        public void setDescrip(String descrip) {
            this.descrip = descrip;
        }

        public String getJump() {
            return jump;
        }

        public void setJump(String jump) {
            this.jump = jump;
        }

        public String getRole_code() {
            return role_code;
        }

        public void setRole_code(String role_code) {
            this.role_code = role_code;
        }

        public String getService_code() {
            return service_code;
        }

        public void setService_code(String service_code) {
            this.service_code = service_code;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getRole_name() {
            return role_name;
        }

        public void setRole_name(String role_name) {
            this.role_name = role_name;
        }

        @Override
        public String toString() {
            return "PageItem{" +
                    "code='" + code + '\'' +
                    ", name='" + name + '\'' +
                    ", descrip='" + descrip + '\'' +
                    ", jump='" + jump + '\'' +
                    ", role_code='" + role_code + '\'' +
                    ", service_code='" + service_code + '\'' +
                    ", role_name='" + role_name + '\'' +
                    ", pic='" + pic + '\'' +
                    ", h5_introduction='" + h5_introduction + '\'' +
                    '}';
        }
    }

    public class ActivityEntity {
        /**
         * activityId : 3
         * activityName : 注册完善信息领取红包
         * activityPrice : 10元
         */
        private String activityId;
        private String activityName;
        private String activityPrice;

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public void setActivityPrice(String activityPrice) {
            this.activityPrice = activityPrice;
        }

        public String getActivityId() {
            return activityId;
        }

        public String getActivityName() {
            return activityName;
        }

        public String getActivityPrice() {
            return activityPrice;
        }
    }
}
