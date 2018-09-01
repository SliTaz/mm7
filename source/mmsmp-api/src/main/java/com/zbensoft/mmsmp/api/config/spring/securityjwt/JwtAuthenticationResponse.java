package com.zbensoft.mmsmp.api.config.spring.securityjwt;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationResponse implements Serializable {
	public boolean isEmplyoeeFlag() {
		return emplyoeeFlag;
	}

	public void setEmplyoeeFlag(boolean emplyoeeFlag) {
		this.emplyoeeFlag = emplyoeeFlag;
	}

	private static final long serialVersionUID = 1250166508152483573L;

	private String token;
	private String userId;
	private String userName;


	private String idNumber;
	private String clapStoreNumber;


	private String loginUserName;
	private Collection<ZBGrantedAuthority> authorities;
	private JwtUser user;
	
	private Integer isDefaultPassword;
	private Integer isDefaultPayPassword;
	private boolean emplyoeeFlag = false;
	private String emplyoeeSellerUserId;
	private int remainingTimes = -1;
	private int emailBindStatus;
	private int isFirstLogin;
	
	private int userLoginType;
	
	private int isClap;
	private int hasMainOffice;
	
	private int priovideType;
	
	private int storeType;
	
	private int employeeType;
	private String rifName;
	private String rifNameOffice;
	private String rifNameEmployee;
	private String rifNameProvide;
	private int isGasStation;

	public JwtAuthenticationResponse() {

	}

	public JwtAuthenticationResponse(String token, String userId, String userName,String loginUserName,Integer isDefaultPassword,Integer isDefaultPayPassword,Integer emailBindStatus,Integer isFirstLogin,Integer userLoginType,Integer isClap,Integer hasMainOffice,Collection<? extends GrantedAuthority> authorities, JwtUser user) {
		this.token = token;
		this.userId = userId;
		this.userName = userName;
		this.loginUserName = loginUserName;
		this.authorities = (Collection<ZBGrantedAuthority>) authorities;
		this.isDefaultPassword=isDefaultPassword;
		this.isDefaultPayPassword=isDefaultPayPassword;
		this.user = user;
		this.emplyoeeFlag = user.isEmplyoee();
		this.emplyoeeSellerUserId = user.getEmplyoeeSellerUserId();
		this.emailBindStatus=emailBindStatus;
		this.isFirstLogin = isFirstLogin;
		//seller用户登录类型
		this.userLoginType = userLoginType;
		this.isClap = isClap;
		this.hasMainOffice=hasMainOffice;
		this.isGasStation=user.getIsGasStation();
	}


	public int getRemainingTimes() {
		return remainingTimes;
	}

	public void setRemainingTimes(int remainingTimes) {
		this.remainingTimes = remainingTimes;
	}

	public String getEmplyoeeSellerUserId() {
		return emplyoeeSellerUserId;
	}

	public void setEmplyoeeSellerUserId(String emplyoeeSellerUserId) {
		this.emplyoeeSellerUserId = emplyoeeSellerUserId;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public void setUser(JwtUser user) {
		this.user = user;
	}

	public JwtUser getUser() {
		return user;
	}

	public String getUserId() {
		return userId;
	}

	public String getToken() {
		return this.token;
	}

	public String getUserName() {
		return userName;
	}

	public Collection<ZBGrantedAuthority> getAuthorities() {
		return authorities;
	}
	public String getClapStoreNumber() {
		return clapStoreNumber;
	}

	public void setClapStoreNumber(String clapStoreNumber) {
		this.clapStoreNumber = clapStoreNumber;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Integer getIsDefaultPassword() {
		return isDefaultPassword;
	}

	public void setIsDefaultPassword(Integer isDefaultPassword) {
		this.isDefaultPassword = isDefaultPassword;
	}

	public Integer getIsDefaultPayPassword() {
		return isDefaultPayPassword;
	}

	public void setIsDefaultPayPassword(Integer isDefaultPayPassword) {
		this.isDefaultPayPassword = isDefaultPayPassword;
	}

	public int getEmailBindStatus() {
		return emailBindStatus;
	}

	public void setEmailBindStatus(int emailBindStatus) {
		this.emailBindStatus = emailBindStatus;
	}

	public int getIsFirstLogin() {
		return isFirstLogin;
	}

	public void setIsFirstLogin(int isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

	public int getUserLoginType() {
		return userLoginType;
	}

	public void setUserLoginType(int userLoginType) {
		this.userLoginType = userLoginType;
	}

	public int getIsClap() {
		return isClap;
	}

	public void setIsClap(int isClap) {
		this.isClap = isClap;
	}

	public int getHasMainOffice() {
		return hasMainOffice;
	}

	public void setHasMainOffice(int hasMainOffice) {
		this.hasMainOffice = hasMainOffice;
	}

	public int getPriovideType() {
		return priovideType;
	}

	public void setPriovideType(int priovideType) {
		this.priovideType = priovideType;
	}

	public int getStoreType() {
		return storeType;
	}

	public void setStoreType(int storeType) {
		this.storeType = storeType;
	}

	public int getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(int employeeType) {
		this.employeeType = employeeType;
	}

	public String getRifName() {
		return rifName;
	}

	public void setRifName(String rifName) {
		this.rifName = rifName;
	}

	public String getRifNameOffice() {
		return rifNameOffice;
	}

	public void setRifNameOffice(String rifNameOffice) {
		this.rifNameOffice = rifNameOffice;
	}

	public String getRifNameEmployee() {
		return rifNameEmployee;
	}

	public void setRifNameEmployee(String rifNameEmployee) {
		this.rifNameEmployee = rifNameEmployee;
	}

	public String getRifNameProvide() {
		return rifNameProvide;
	}

	public void setRifNameProvide(String rifNameProvide) {
		this.rifNameProvide = rifNameProvide;
	}

	public int getIsGasStation() {
		return isGasStation;
	}

	public void setIsGasStation(int isGasStation) {
		this.isGasStation = isGasStation;
	}

}
