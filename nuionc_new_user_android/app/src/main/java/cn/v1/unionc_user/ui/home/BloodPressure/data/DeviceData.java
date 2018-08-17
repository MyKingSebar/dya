package cn.v1.unionc_user.ui.home.BloodPressure.data;

/**
 * Created by qy on 2018/5/11.
 */

public class DeviceData {



    private String device;
    private int deviceImg;
    private String deviceName;

    public DeviceData(String device, int deviceImg, String deviceName) {
        this.device = device;
        this.deviceImg = deviceImg;
        this.deviceName = deviceName;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public int getDeviceImg() {
        return deviceImg;
    }

    public void setDeviceImg(int deviceImg) {
        this.deviceImg = deviceImg;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
