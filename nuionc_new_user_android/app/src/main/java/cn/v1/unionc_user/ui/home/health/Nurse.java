/**
 * 
 */
package cn.v1.unionc_user.ui.home.health;

import java.io.Serializable;

/**
*
 *
 * 护士
 */
public class Nurse implements Serializable {





	private String orderId;
	private String msgStatus;
	private String orderType = "";


	/*
	* 共同
	* */

		private String roleName;
		private String skilledSymptom = "";
		private String experienced;
		private String serviceDistance;
		private String commentsSize;
		private String praiseSize;
		private String answerSize;
		private String attentionSize;
		private String userId;
		private String userName;
		private String userRealName;
		private String userHeadPicUrl;
		private String userPartId;
		private String feedbackate;

	public String getFeedbackate() {
		return feedbackate;
	}

	public void setFeedbackate(String feedbackate) {
		this.feedbackate = feedbackate;
	}
		/*
	*
	* 护士
	* */

		private String hostitalName;
		private String name;
		private String dutyName;
		private String province;
		private String city;
		private String certificatePhoto1;
		private String certificatePhoto2;
		private String certificatePhoto3;
		private String certificatePhoto4;
		private String serviceName;
		private String professionName;
	private String angelValue;

	public String getAngelValue() {
		return angelValue;
	}

	public void setAngelValue(String angelValue) {
		this.angelValue = angelValue;
	}

	public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCertificatePhoto1() {
		return certificatePhoto1;
	}

	public void setCertificatePhoto1(String certificatePhoto1) {
		this.certificatePhoto1 = certificatePhoto1;
	}

	public String getCertificatePhoto2() {
		return certificatePhoto2;
	}

	public void setCertificatePhoto2(String certificatePhoto2) {
		this.certificatePhoto2 = certificatePhoto2;
	}

	public String getCertificatePhoto3() {
		return certificatePhoto3;
	}

	public void setCertificatePhoto3(String certificatePhoto3) {
		this.certificatePhoto3 = certificatePhoto3;
	}

	public String getCertificatePhoto4() {
		return certificatePhoto4;
	}

	public void setCertificatePhoto4(String certificatePhoto4) {
		this.certificatePhoto4 = certificatePhoto4;
	}

	//	{"idCardNo":"130206198708280955","experienced":"","hospitalName":"中国医学科学院北京协和医院","hospitalId":3834,
//			"firstDepartId":"","firstDepartName":"","departmentsName":"","departmentsId":764,"skilledSymptom":"89睡觉\n","userName":"姚福玉",
//			"answerSize":0,"attentionSize":1,"academicResearch":"睡觉睡觉","educationalBackground":"细菌学家","subscribeSize":0,"praiseRate":"0%",
//			"collectionStatus":1,"headPicUrl":"http://file.zgzcw.com/1/M00/0D/EA/wKjbZFYbW2WAQZr1AAO7BWtfeq0064.png","title":"主任医师"}
	/*"title":"","titleCode":""
	* 医生
	* */
		private String subscribeSize;
		private String praiseRate;
		private String idCardNo;
		private String hospital;
		private String departmentsId;
		private String symptom;
		private String academicResearch;
		private String educationalBackground;
		private String title;
		private String hospitalName;
		private String departmentsName;
		private String headPicUrl;
		private String firstDepartId;
		private String firstDepartName;
		private String proxyStatus;
		private String titleCode;
		private String collectionStatus;

	/*
	* 护工
	* */

		private String longitude;//经度
		private String latitude;//纬度
		private String isCollected;
		private String nativePlace;//籍贯
		private String sex;//性别
		private String age;//年龄

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTitleCode() {
		return titleCode;
	}

	public void setTitleCode(String titleCode) {
		this.titleCode = titleCode;
	}

	public String getProxyStatus() {
		return proxyStatus;
	}

	public void setProxyStatus(String proxyStatus) {
		this.proxyStatus = proxyStatus;
	}

	public String getFirstDepartId() {
		return firstDepartId;
	}

	public void setFirstDepartId(String firstDepartId) {
		this.firstDepartId = firstDepartId;
	}

	public String getFirstDepartName() {
		return firstDepartName;
	}

	public void setFirstDepartName(String firstDepartName) {
		this.firstDepartName = firstDepartName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getDepartmentsName() {
		return departmentsName;
	}

	public void setDepartmentsName(String departmentsName) {
		this.departmentsName = departmentsName;
	}

	public String getHeadPicUrl() {
		return headPicUrl;
	}

	public void setHeadPicUrl(String headPicUrl) {
		this.headPicUrl = headPicUrl;
	}

	public String getCollectionStatus() {
		return collectionStatus;
	}

	public void setCollectionStatus(String collectionStatus) {
		this.collectionStatus = collectionStatus;
	}


	public String getSubscribeSize() {
		return subscribeSize;
	}

	public void setSubscribeSize(String subscribeSize) {
		this.subscribeSize = subscribeSize;
	}

	public String getIsCollected() {
			return isCollected;
		}

		public void setIsCollected(String isCollected) {
			this.isCollected = isCollected;
		}

		public String getRoleName() {
			return roleName;
		}

		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}

		public String getSkilledSymptom() {
			return skilledSymptom;
		}

		public void setSkilledSymptom(String skilledSymptom) {
			this.skilledSymptom = skilledSymptom;
		}

		public String getExperienced() {
			return experienced;
		}

		public void setExperienced(String experienced) {
			this.experienced = experienced;
		}

		public String getServiceDistance() {
			return serviceDistance;
		}

		public void setServiceDistance(String serviceDistance) {
			this.serviceDistance = serviceDistance;
		}

		public String getCommentsSize() {
			return commentsSize;
		}

		public void setCommentsSize(String commentsSize) {
			this.commentsSize = commentsSize;
		}

		public String getPraiseSize() {
			return praiseSize;
		}

		public void setPraiseSize(String praiseSize) {
			this.praiseSize = praiseSize;
		}

		public String getAnswerSize() {
			return answerSize;
		}

		public void setAnswerSize(String answerSize) {
			this.answerSize = answerSize;
		}

		public String getAttentionSize() {
			return attentionSize;
		}

		public void setAttentionSize(String attentionSize) {
			this.attentionSize = attentionSize;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getUserRealName() {
			return userRealName;
		}

		public void setUserRealName(String userRealName) {
			this.userRealName = userRealName;
		}

		public String getUserHeadPicUrl() {
			return userHeadPicUrl;
		}

		public void setUserHeadPicUrl(String userHeadPicUrl) {
			this.userHeadPicUrl = userHeadPicUrl;
		}

		public String getUserPartId() {
			return userPartId;
		}

		public void setUserPartId(String userPartId) {
			this.userPartId = userPartId;
		}

		public String getHostitalName() {
			return hostitalName;
		}

		public void setHostitalName(String hostitalName) {
			this.hostitalName = hostitalName;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPraiseRate() {
			return praiseRate;
		}

		public void setPraiseRate(String praiseRate) {
			this.praiseRate = praiseRate;
		}

		public String getIdCardNo() {
			return idCardNo;
		}

		public void setIdCardNo(String idCardNo) {
			this.idCardNo = idCardNo;
		}

		public String getHospital() {
			return hospital;
		}

		public void setHospital(String hospital) {
			this.hospital = hospital;
		}

		public String getDepartmentsId() {
			return departmentsId;
		}

		public void setDepartmentsId(String departmentsId) {
			this.departmentsId = departmentsId;
		}

		public String getSymptom() {
			return symptom;
		}

		public void setSymptom(String symptom) {
			this.symptom = symptom;
		}

		public String getAcademicResearch() {
			return academicResearch;
		}

		public void setAcademicResearch(String academicResearch) {
			this.academicResearch = academicResearch;
		}

		public String getEducationalBackground() {
			return educationalBackground;
		}

		public void setEducationalBackground(String educationalBackground) {
			this.educationalBackground = educationalBackground;
		}

		public String getLongitude() {
			return longitude;
		}

		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}

		public String getLatitude() {
			return latitude;
		}

		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}
	}
