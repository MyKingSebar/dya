package cn.v1.unionc_user.ui.home.BloodPressure;

/**
 * Created by An4 on 2018/5/12.
 */
public class OmronResultData {

    /**
     * bdaCode : sdasd
     * deviceType : 0
     * deviceModel : BleSmart_0000016EBFJS0SDF
     * highPressure : 115
     * lowPressure : 75
     * pluseRate : 60
     * measureWay : 0
     * avgMeasure :
     * measureDate : 2018-05-11 10:10:10
     * patientInfoId :
     */

    private String bdaCode;
    private String deviceType;
    private String deviceModel;
    private String highPressure;
    private String lowPressure;
    private String pluseRate;
    private String measureWay;
    private String avgMeasure;
    private String measureDate;
    private String patientInfoId;

    public String getBdaCode() {
        return bdaCode;
    }

    public void setBdaCode(String bdaCode) {
        this.bdaCode = bdaCode;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getHighPressure() {
        return highPressure;
    }

    public void setHighPressure(String highPressure) {
        this.highPressure = highPressure;
    }

    public String getLowPressure() {
        return lowPressure;
    }

    public void setLowPressure(String lowPressure) {
        this.lowPressure = lowPressure;
    }

    public String getPluseRate() {
        return pluseRate;
    }

    public void setPluseRate(String pluseRate) {
        this.pluseRate = pluseRate;
    }

    public String getMeasureWay() {
        return measureWay;
    }

    public void setMeasureWay(String measureWay) {
        this.measureWay = measureWay;
    }

    public String getAvgMeasure() {
        return avgMeasure;
    }

    public void setAvgMeasure(String avgMeasure) {
        this.avgMeasure = avgMeasure;
    }

    public String getMeasureDate() {
        return measureDate;
    }

    public void setMeasureDate(String measureDate) {
        this.measureDate = measureDate;
    }

    public String getPatientInfoId() {
        return patientInfoId;
    }

    public void setPatientInfoId(String patientInfoId) {
        this.patientInfoId = patientInfoId;
    }
}
