package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/3/8.
 */

public class NetCouldPullData extends BaseData {

private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    /**
     * {
     "message": "成功",
     "data": {
     "lives": [
     {
     "HttpPullUrl": "http://flv0251f594.live.126.net/live/bb67363ad6f948b7be36e7f1aca6b165.flv?netease=flv0251f594.live.126.net",
     "LiveStatus": "2",
     "LiveIntro": "直播简介",
     "LiveId": "11",
     "Cid": "bb67363ad6f948b7be36e7f1aca6b165",
     "LiveTitle": "直播标题7",
     "StartTime": "2018-07-04 11:01:12.0",
     "Speaker": "主讲人",
     "Banner": "直播banner",
     "SpeakerIntro": "主讲人介绍"
     },
     {
     "HttpPullUrl": "http://flv0251f594.live.126.net/live/5732b0de73194a38b40f0b6f07c6f3ff.flv?netease=flv0251f594.live.126.net",
     "LiveStatus": "0",
     "LiveIntro": "直播简介",
     "LiveId": "8",
     "Cid": "5732b0de73194a38b40f0b6f07c6f3ff",
     "LiveTitle": "直播标题5",
     "StartTime": "2018-07-04 11:01:11.0",
     "Speaker": "主讲人",
     "Banner": "直播banner",
     "SpeakerIntro": "主讲人介绍"
     }
     ]
     },
     "code": "4000"
     }
     */

    /**
     *
     HttpPullUrl	拉流地址
     LiveStatus	直播状态 1-未开始，2-直播中，3-已结束,
     LiveIntro	直播简介
     LiveId		直播id（本地id）
     Cid			频道id
     LiveTitle	直播标题
     StartTime	直播开始时间
     Speaker 	主讲人
     Banner		图
     SpeakerIntro主讲人简介

     */
    public class DataData{
private List<datadatadata> lives;

        public List<datadatadata> getLives() {
            return lives;
        }

        public void setLives(List<datadatadata> lives) {
            this.lives = lives;
        }

        public class datadatadata{
            private String HttpPullUrl;
            private String LiveStatus;
            private String LiveIntro;
            private String LiveId;
            private String Cid;
            private String LiveTitle;
            private String StartTime;
            private String Speaker;
            private String Banner;
            private String SpeakerIntro;
            private String ClinicId;
            private String ClinicName;
            private String ClinicImagePath;

            public String getClinicId() {
                return ClinicId;
            }

            public void setClinicId(String clinicId) {
                ClinicId = clinicId;
            }

            public String getClinicName() {
                return ClinicName;
            }

            public void setClinicName(String clinicName) {
                ClinicName = clinicName;
            }

            public String getClinicImagePath() {
                return ClinicImagePath;
            }

            public void setClinicImagePath(String clinicImagePath) {
                ClinicImagePath = clinicImagePath;
            }

            public String getHttpPullUrl() {
                return HttpPullUrl;
            }

            public void setHttpPullUrl(String httpPullUrl) {
                HttpPullUrl = httpPullUrl;
            }

            public String getLiveStatus() {
                return LiveStatus;
            }

            public void setLiveStatus(String liveStatus) {
                LiveStatus = liveStatus;
            }

            public String getLiveIntro() {
                return LiveIntro;
            }

            public void setLiveIntro(String liveIntro) {
                LiveIntro = liveIntro;
            }

            public String getLiveId() {
                return LiveId;
            }

            public void setLiveId(String liveId) {
                LiveId = liveId;
            }

            public String getCid() {
                return Cid;
            }

            public void setCid(String cid) {
                Cid = cid;
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

            public String getSpeaker() {
                return Speaker;
            }

            public void setSpeaker(String speaker) {
                Speaker = speaker;
            }

            public String getBanner() {
                return Banner;
            }

            public void setBanner(String banner) {
                Banner = banner;
            }

            public String getSpeakerIntro() {
                return SpeakerIntro;
            }

            public void setSpeakerIntro(String speakerIntro) {
                SpeakerIntro = speakerIntro;
            }
        }
    }



}
