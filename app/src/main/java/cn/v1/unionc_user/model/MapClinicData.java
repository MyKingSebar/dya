package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/3/26.
 */

public class MapClinicData extends BaseData {


    /**
     * Content-Type: application/json
     {
     "data": {
     "code": "4000",
     "data": {
     "checkDeparts": [
     {
     "ImagePath": "http://192.168.21.93:8080/unionWeb/image/app/depart/icon-骨科-搜索.png",
     "ID": 12,
     "Name": "骨科"
     }
     ],
     "clinicDatas": [
     {
     "ParCategory": "3",
     "SubCategory": "20",
     "IsShareRoom": "0",
     "Latitude": 40.009092,
     "Id": "104011",
     "Longitude": 116.486676,
     "DepartFlag": "0",
     "Name": "望京结晶社区医院"
     }
     ]
     },
     "message": "成功"
     }
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

        public List<CheckDepartData> getCheckDepart() {
            return checkDepart;
        }

        public void setCheckDepart(List<CheckDepartData> checkDepart) {
            this.checkDepart = checkDepart;
        }

        public List<MapClinic> getClinicDatas() {
            return clinicDatas;
        }

        public void setClinicDatas(List<MapClinic> clinicDatas) {
            this.clinicDatas = clinicDatas;
        }



        private List<CheckDepartData> checkDepart;
        private List<MapClinic> clinicDatas;



        public static class CheckDepartData {
            public String getImagePath() {
                return ImagePath;
            }

            public void setImagePath(String imagePath) {
                ImagePath = imagePath;
            }

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }

            /**
             *"checkDeparts": [
             {
             "ImagePath": "http://192.168.21.93:8080/unionWeb/image/app/depart/icon-骨科-搜索.png",
             "ID": 12,
             "Name": "骨科"
             }
             ],

             */

            private String ImagePath;
            private String ID;
            private String Name;


        }

        public static class MapClinic {
            /**
             * clinicDatas": [
             {
             "ParCategory": "3",
             "SubCategory": "20",
             "IsShareRoom": "0",
             "Latitude": 40.009092,
             "Id": "104011",
             "Longitude": 116.486676,
             "DepartFlag": "0",
             "Name": "望京结晶社区医院"
             }
             ]

             */



            private String ParCategory;
            private String SubCategory;
            private String IsShareRoom;
            private String Latitude;
            private String Id;
            private String Longitude;
            private String DepartFlag;
            private String Name;

            public String getParCategory() {
                return ParCategory;
            }

            public void setParCategory(String parCategory) {
                ParCategory = parCategory;
            }

            public String getSubCategory() {
                return SubCategory;
            }

            public void setSubCategory(String subCategory) {
                SubCategory = subCategory;
            }

            public String getIsShareRoom() {
                return IsShareRoom;
            }

            public void setIsShareRoom(String isShareRoom) {
                IsShareRoom = isShareRoom;
            }

            public String getLatitude() {
                return Latitude;
            }

            public void setLatitude(String latitude) {
                Latitude = latitude;
            }

            public String getId() {
                return Id;
            }

            public void setId(String id) {
                Id = id;
            }

            public String getLongitude() {
                return Longitude;
            }

            public void setLongitude(String longitude) {
                Longitude = longitude;
            }

            public String getDepartFlag() {
                return DepartFlag;
            }

            public void setDepartFlag(String departFlag) {
                DepartFlag = departFlag;
            }

            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }
        }


    }
}
