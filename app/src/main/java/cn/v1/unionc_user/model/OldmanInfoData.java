package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/3/8.
 */

public class OldmanInfoData extends BaseData {
    /**
     *
     * {"message":"成功","data":{"elderLyInfo":{"Address":"地址","UserName":"姓名","Images":[{"ImageId":5,"ImagePath":"/webServer/compress/78/9/14/0765cd6b-03c9-4cdd-b9dc-8efd43ca9f30_file.jpg"}],"Telphone":"电话","CardNo":"身份证号"}},"code":"4000"}
     *
     */
    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }


    public class DataData{

        private DataDataData elderLyInfo;

        public DataDataData getElderLyInfo() {
            return elderLyInfo;
        }

        public void setElderLyInfo(DataDataData elderLyInfo) {
            this.elderLyInfo = elderLyInfo;
        }

        public class DataDataData{
            private String Address;
             private String UserName;
            private List<ImageInfo> Images;
            private String Telphone;
            private String CardNo;

            public String getAddress() {
                return Address;
            }

            public void setAddress(String address) {
                Address = address;
            }

            public String getUserName() {
                return UserName;
            }

            public void setUserName(String userName) {
                UserName = userName;
            }

            public List<ImageInfo> getImages() {
                return Images;
            }

            public void setImages(List<ImageInfo> images) {
                Images = images;
            }

            public String getTelphone() {
                return Telphone;
            }

            public void setTelphone(String telphone) {
                Telphone = telphone;
            }

            public String getCardNo() {
                return CardNo;
            }

            public void setCardNo(String cardNo) {
                CardNo = cardNo;
            }

            public class ImageInfo{
              private String ImageId;
              private String ImagePath;

              public String getImageId() {
                  return ImageId;
              }

              public void setImageId(String imageId) {
                  ImageId = imageId;
              }

              public String getImagePath() {
                  return ImagePath;
              }

              public void setImagePath(String imagePath) {
                  ImagePath = imagePath;
              }
          }
        }
    }



}
