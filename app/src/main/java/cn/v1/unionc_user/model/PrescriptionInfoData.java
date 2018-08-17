package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/3/26.
 */

public class PrescriptionInfoData extends BaseData {


    /**
     * {"message":"成功","data":{"prescription":{"ClinicalDiagnosis":"临床诊断","CreatedTime":"2018-07-20 17:31:22","ClinicName":"北京市第一医院","Supplement":"补充说明","Charge":"价格","medicines":[{"Num":数量,"SalePrice":售价,"Usages":"用法","Title":"药品名称","Packaging":"包装"}],"DoctName":"医生姓名"}},"code":"4000"}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {


        private PrescriptionData prescription;

        public PrescriptionData getPrescription() {
            return prescription;
        }

        public void setPrescription(PrescriptionData prescription) {
            this.prescription = prescription;
        }

        /**
         *
         *"medicines":[{"Num":数量,"SalePrice":售价,"Usages":"用法","Title":"药品名称","Packaging":"包装"}],"DoctName":"医生姓名"}}
         */
        public class PrescriptionData {
            private String ClinicalDiagnosis;
            private String CreatedTime;
            private String ClinicName;
            private String Supplement;
            private String Charge;
            private String DoctName;

            private String UserName;
            private String Age;
            private String  DepartName;
            private String  Gender;
            private List<MedicinesData> medicines;

            public String getUserName() {
                return UserName;
            }

            public void setUserName(String userName) {
                UserName = userName;
            }

            public String getAge() {
                return Age;
            }

            public void setAge(String age) {
                Age = age;
            }

            public String getDepartName() {
                return DepartName;
            }

            public void setDepartName(String departName) {
                DepartName = departName;
            }

            public String getGender() {
                return Gender;
            }

            public void setGender(String gender) {
                Gender = gender;
            }

            public String getClinicalDiagnosis() {
                return ClinicalDiagnosis;
            }

            public void setClinicalDiagnosis(String clinicalDiagnosis) {
                ClinicalDiagnosis = clinicalDiagnosis;
            }

            public String getCreatedTime() {
                return CreatedTime;
            }

            public void setCreatedTime(String createdTime) {
                CreatedTime = createdTime;
            }

            public String getClinicName() {
                return ClinicName;
            }

            public void setClinicName(String clinicName) {
                ClinicName = clinicName;
            }

            public String getSupplement() {
                return Supplement;
            }

            public void setSupplement(String supplement) {
                Supplement = supplement;
            }

            public String getCharge() {
                return Charge;
            }

            public void setCharge(String charge) {
                Charge = charge;
            }

            public String getDoctName() {
                return DoctName;
            }

            public void setDoctName(String doctName) {
                DoctName = doctName;
            }

            public List<MedicinesData> getMedicines() {
                return medicines;
            }

            public void setMedicines(List<MedicinesData> medicines) {
                this.medicines = medicines;
            }

            public class MedicinesData{
                private String Num;
                private String SalePrice;
                private String Usages;
                private String Title;
                private String Packaging;

                public String getNum() {
                    return Num;
                }

                public void setNum(String num) {
                    Num = num;
                }

                public String getSalePrice() {
                    return SalePrice;
                }

                public void setSalePrice(String salePrice) {
                    SalePrice = salePrice;
                }

                public String getUsages() {
                    return Usages;
                }

                public void setUsages(String usages) {
                    Usages = usages;
                }

                public String getTitle() {
                    return Title;
                }

                public void setTitle(String title) {
                    Title = title;
                }

                public String getPackaging() {
                    return Packaging;
                }

                public void setPackaging(String packaging) {
                    Packaging = packaging;
                }
            }
        }




        }




}
