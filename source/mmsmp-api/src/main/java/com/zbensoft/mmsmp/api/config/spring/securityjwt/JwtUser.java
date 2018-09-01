package com.zbensoft.mmsmp.api.config.spring.securityjwt;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.zbensoft.mmsmp.api.common.CommonFun;

public class JwtUser implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String username;
	private String password;
	private String email;
	private String loginUserName;
	private Collection<? extends GrantedAuthority> authorities;
	private Date lastPasswordResetDate;
	private boolean enabled;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired = true;
	private boolean accountNonExpired = true;
	// 身份证ID
	private String idNumber;
	// clap卡编号
	private String clapCardNo;

	private Integer isDefaultPassword;
	private Integer isDefaultPayPassword;
	private boolean isEmplyoee = false;
	private String emplyoeeSellerUserId;
	private int emailBindStatus = -1;
	private Integer isFirstLogin;//是否首页登录‘
	private Integer userLoginType;//seller用户登录类型
	private Integer isClap;
	private Integer hasMainOffice;
	private Integer storeType;
	private Integer priovideType;
	private Integer employeeType;
	private String rifName;
	private String rifNameOffice;
	private String rifNameEmployee;
	private String rifNameProvide;
	private int isGasStation;

	public JwtUser() {

	}

	/**
	 * String id,String loginUserName, String username, String password, String email,String idNumber, Collection<? extends GrantedAuthority> authorities, Date
	 * lastPasswordResetDate, boolean enabled, boolean accountNonLocked
	 * 
	 * @param id
	 * @param loginUserName
	 * @param username
	 * @param password
	 * @param email
	 * @param idNumber
	 * @param authorities
	 * @param lastPasswordResetDate
	 * @param enabled
	 * @param accountNonLocked
	 */
	public JwtUser(String id, String loginUserName, String username, String password, String email, String idNumber, String clapCardNo, Collection<? extends GrantedAuthority> authorities, Date lastPasswordResetDate,
			boolean enabled, boolean accountNonLocked, Integer isDefaultPassword, Integer isDefaultPayPassword, int emailBindStatus,int isFirstLogin,int userLoginType,int isClap,int hasMainOffice,int priovideType,int storeType,int employeeType,String rifName,String rifNameOffice,String rifNameEmployee,String rifNameProvide,int isGasStation) {
		this(id, loginUserName, username, password, email, idNumber, clapCardNo, authorities, lastPasswordResetDate, enabled, accountNonLocked, isDefaultPassword, isDefaultPayPassword, emailBindStatus,isFirstLogin,userLoginType,isClap,hasMainOffice,false, null,priovideType,storeType,employeeType,rifName,rifNameOffice,rifNameEmployee,rifNameProvide,isGasStation);
	}

	public JwtUser(String id, String loginUserName, String username, String password, String email, String idNumber, String clapCardNo, Collection<? extends GrantedAuthority> authorities, Date lastPasswordResetDate,
			boolean enabled, boolean accountNonLocked, Integer isDefaultPassword, Integer isDefaultPayPassword, int emailBindStatus,int isFirstLogin, int userLoginType,int isClap,int hasMainOffice,boolean isEmplyoee, String emplyoeeSellerUserId,int priovideType,int storeType,int employeeType,String rifName,String rifNameOffice,String rifNameEmployee,String rifNameProvide,int isGasStation) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = CommonFun.getEmailWhithStar(email);
		this.authorities = authorities;
		this.lastPasswordResetDate = lastPasswordResetDate;
		this.enabled = enabled;
		this.accountNonLocked = accountNonLocked;
		this.loginUserName = loginUserName;
		this.idNumber = idNumber;
		this.clapCardNo = clapCardNo;
		this.isDefaultPassword = isDefaultPassword;
		this.isDefaultPayPassword = isDefaultPayPassword;
		this.isEmplyoee = isEmplyoee;
		this.emplyoeeSellerUserId = emplyoeeSellerUserId;
		this.emailBindStatus = emailBindStatus;
		this.isFirstLogin = isFirstLogin;
		this.userLoginType = userLoginType;
		this.isClap = isClap;
		this.hasMainOffice = hasMainOffice;
		this.priovideType = priovideType;
		this.storeType = storeType;
		this.employeeType = employeeType;
		this.rifName = rifName;
		this.rifNameOffice = rifNameOffice;
		this.rifNameEmployee = rifNameEmployee;
		this.rifNameProvide = rifNameProvide;
		this.isGasStation=isGasStation;
	}

	public int getEmailBindStatus() {
		return emailBindStatus;
	}

	public void setEmailBindStatus(int emailBindStatus) {
		this.emailBindStatus = emailBindStatus;
	}

	public String getEmplyoeeSellerUserId() {
		return emplyoeeSellerUserId;
	}

	public void setEmplyoeeSellerUserId(String emplyoeeSellerUserId) {
		this.emplyoeeSellerUserId = emplyoeeSellerUserId;
	}

	public boolean isEmplyoee() {
		return isEmplyoee;
	}

	public void setEmplyoee(boolean isEmplyoee) {
		this.isEmplyoee = isEmplyoee;
	}

	public String getClapCardNo() {
		return clapCardNo;
	}

	public void setClapCardNo(String clapCardNo) {
		this.clapCardNo = clapCardNo;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getId() {
		return id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public String getEmail() {
		return email;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
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

	public Integer getIsFirstLogin() {
		return isFirstLogin;
	}

	public void setIsFirstLogin(Integer isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

	public Integer getUserLoginType() {
		return userLoginType;
	}

	public void setUserLoginType(Integer userLoginType) {
		this.userLoginType = userLoginType;
	}

	public Integer getIsClap() {
		return isClap;
	}

	public void setIsClap(Integer isClap) {
		this.isClap = isClap;
	}

	public Integer getHasMainOffice() {
		return hasMainOffice;
	}

	public void setHasMainOffice(Integer hasMainOffice) {
		this.hasMainOffice = hasMainOffice;
	}

	public Integer getStoreType() {
		return storeType;
	}

	public void setStoreType(Integer storeType) {
		this.storeType = storeType;
	}

	public Integer getPriovideType() {
		return priovideType;
	}

	public void setPriovideType(Integer priovideType) {
		this.priovideType = priovideType;
	}

	public Integer getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(Integer employeeType) {
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
