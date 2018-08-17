package cn.v1.unionc_user.ui.home.BloodPressure.data;

/**
 * Created by An4 on 2016/9/28.
 */
public class HealthMoitorItemData {

    /**
     * dataValue : data1
     * dataIndex : 1
     */

    private String dataValue;
    private String dataIndex;
    private String id;
    private String monitorId;
    private String monitorDate;
    private String titleStr;

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public String getDataIndex() {
        return dataIndex;
    }

    public void setDataIndex(String dataIndex) {
        this.dataIndex = dataIndex;
    }
}
