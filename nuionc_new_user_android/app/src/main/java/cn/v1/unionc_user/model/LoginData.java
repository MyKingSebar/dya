package cn.v1.unionc_user.model;

/**
 * Created by qy on 2018/2/9.
 */

public class LoginData extends BaseData {


    /**
     * data : {"token":"dR4w/RlFdcoTcsz2fSUBzgSalMHVJiyEnJT1f1HgsHA="}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
        /**
         * token : dR4w/RlFdcoTcsz2fSUBzgSalMHVJiyEnJT1f1HgsHA=
         */

        private String token;
        private String identifier;
        private String addrLongitude;
        private String addrLatitude;
        private String addr;

        public String getAddrLongitude() {
            return addrLongitude;
        }

        public void setAddrLongitude(String addrLongitude) {
            this.addrLongitude = addrLongitude;
        }

        public String getAddrLatitude() {
            return addrLatitude;
        }

        public void setAddrLatitude(String addrLatitude) {
            this.addrLatitude = addrLatitude;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
