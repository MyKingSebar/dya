package cn.v1.unionc_user.tecent_qcloud.tim_model;

import java.io.Serializable;

/**
 * Created by qy on 2018/3/29
 */

public class HospitalInfo implements Serializable{

    private String identifier;
    private String imagePath;
    private String hospitalName;

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
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


}
