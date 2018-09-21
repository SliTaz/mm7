package com.zbensoft.mmsmp.api.config.spring.securityjwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
		authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		return new JwtAuthenticationTokenFilter();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				// 由于使用的是JWT，我们这里不需要csrf
				.csrf().disable()

				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

				// 基于token，所以不需要session
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				.authorizeRequests()
				// .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // 允许对于网站静态资源的无授权访问
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/**/*.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.woff2",
                        "/**/*.ttf",
                        "/**/*.png",
                        "/swagger-resources",
                        "/v2/api-docs"
                ).permitAll()
                // 对于获取token的rest api要允许匿名访问
                .antMatchers("/login").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/consumerUser/get/picture/path/*").permitAll()
                .antMatchers("/merchantUser/get/picture/path/*").permitAll()
                .antMatchers("/merchantEmployee/get/picture/path/*").permitAll()
                .antMatchers("/notice/getAppNotice").permitAll()
                .antMatchers("/notice/get/noticepicture/path/*").permitAll()
                .antMatchers("/app/reportAppErrors").permitAll()
                .antMatchers("/app/getApp").permitAll()
                .antMatchers("/app/downloadApp").permitAll()
                .antMatchers("/app/downloadSellerAndroidApp").permitAll()
                .antMatchers("/app/downloadBuyerAndroidApp").permitAll()
                .antMatchers("/app/reportAppErrorMessage").permitAll()
                
                
                
                .antMatchers("/webservice/**").permitAll()
                .antMatchers("/merchantUser/get/errorLog").permitAll()
                .antMatchers("/consumerUser/get/errorLog").permitAll()
                .antMatchers("/clapUseStore/get/errorLog").permitAll()
                .antMatchers("/license").permitAll()
                .antMatchers("/heapdump").permitAll()
                
                //找回密码不需要登录
                .antMatchers("/consumerUser/vid").permitAll()
                .antMatchers("/consumerUser/retrievePassword").permitAll()
                .antMatchers("/merchantUser/vid").permitAll()
                .antMatchers("/merchantUser/selectProvideOrEmployeeByEmail").permitAll()
                .antMatchers("/merchantUser/sendProviderOrEmployeeEmailCode").permitAll()
                .antMatchers("/merchantUser/validateProviderOrEmployeeEmailCode").permitAll()
                .antMatchers("/merchantUser/retrieveMerchantOrEmployeePassword").permitAll()
                
                .antMatchers("/merchantUser/registerProviderWithRif").permitAll()
                .antMatchers("/merchantUser/registerEmployee").permitAll()
                .antMatchers("/merchantUser/registerEmployeeClap").permitAll()
                .antMatchers("/merchantUser/registerOffice").permitAll()
                .antMatchers("/merchantUser/registerPersonalWithoutRif").permitAll()
                
                .antMatchers("/merchantUser/retrievePassword").permitAll()
                .antMatchers("/merchantEmployee/vid").permitAll()
                .antMatchers("/merchantEmployee/retrievePassword").permitAll()
                .antMatchers("/merchantUser/registerGetRif").permitAll()
                .antMatchers("/merchantUser/registerGetPerson").permitAll()
                .antMatchers("/merchantUser/registerGetRifAndPerson").permitAll()
                .antMatchers("/merchantUser/registerMail").permitAll()
                .antMatchers("/merchantUser/registerValidateEmailCode").permitAll()
                .antMatchers("/merchantUser/checkProviderType").permitAll()
                .antMatchers("/merchantUser/validateCodes").permitAll()
                .antMatchers("/merchantUser/officeRegisterInfoShow").permitAll()
                .antMatchers("/merchantUser/employRegisterInfoShow").permitAll()
                .antMatchers("/merchantUser/employClapRegisterInfoShow").permitAll()
                
                .antMatchers("/statistics/getOnlineUserCount").permitAll()
                .antMatchers("/consumerUser/registerGetConsumerInfo").permitAll()
                .antMatchers("/consumerUser/registerMail").permitAll()
                .antMatchers("/consumerUser/registerValidateEmailCode").permitAll()
                .antMatchers("/consumerUser/registerImgvalidateCode").permitAll()
                
                .antMatchers("/TestBuyerRegisterApi/*").permitAll()
                
                .antMatchers("/TestSellerRegisterApi/*").permitAll()
                
                .antMatchers("/helpDocType/**").permitAll()
                .antMatchers("/helpDocGroup/**").permitAll()
                .antMatchers("/helpDocument/**").permitAll()
                .antMatchers("/helpDocument/title").permitAll()
                .antMatchers("/helpDocument/getHtml").permitAll()
                .antMatchers("/epayment/get_user_count").permitAll()
                .antMatchers("/corbiz/whiteList").permitAll()
                .antMatchers("/corbiz/cpInfo").permitAll()
                .antMatchers("/corbiz/userOrder").permitAll()
                .antMatchers("/corbiz/getSpurlByVaspid").permitAll()
                .antMatchers("/corbiz/requestacc").permitAll()
                .antMatchers("/corbiz/getSysConfig").permitAll()
                .antMatchers("/corbiz/getServiceIDbyProductid").permitAll()
                .antMatchers("/corbiz/getSpReportUrlByServiceCode").permitAll()
                .antMatchers("/corbiz/blackList").permitAll()
                .antMatchers("/corbiz/spInfo").permitAll()
                .antMatchers("/corbiz/productInfo").permitAll()
                .antMatchers("/corbiz/productService").permitAll()
                .antMatchers("/corbiz/getConfirmmsgByProductid").permitAll()
                .antMatchers("/corbiz/getSpProductId").permitAll()
                .antMatchers("/corbiz/delUserOrder/**").permitAll()
                .antMatchers("/corbiz/selectUserOrder/**").permitAll()
                .antMatchers("/ownbiz/userOrder/**").permitAll()
                .antMatchers("/ownbiz/userOrderHis").permitAll()
                .antMatchers("/ownbiz/userOrder").permitAll()
                .antMatchers("/ownbiz/userOrderDelete/**").permitAll()
                .antMatchers("/ownbiz/contentInfo").permitAll()
                .antMatchers("/ownbiz/contentInfoDelete/**").permitAll()
                .antMatchers("/corbiz/userRecv").permitAll()
                .antMatchers("/corbiz/getLatestMoOrderMsgText").permitAll()
                .antMatchers("/corbiz/getServiceNameByAcc").permitAll()
                .antMatchers("/corbiz/getVasSpCpInfo").permitAll()
                .antMatchers("/ownbiz/getSystemParamBykey").permitAll()
                .antMatchers("/ownbiz/getAllVaspEnitiy").permitAll()
                .antMatchers("/ownbiz/getAllVasServiceRelation").permitAll()
                .antMatchers("/ownbiz/getVasServiceRelation").permitAll()
                .antMatchers("/ownbiz/queryContentCount").permitAll()
                .antMatchers("/ownbiz/updateNotifySpStatus").permitAll()
                .antMatchers("/ownbiz/cooperkey").permitAll()
                .antMatchers("/ownbiz/selectCooperkey").permitAll()
                .antMatchers("/ownbiz/proxypaymessage").permitAll()
                .antMatchers("/ownbiz/userOrderPay").permitAll()
                .antMatchers("/ownbiz/userOrderPay/**").permitAll()
                .antMatchers("/ownbiz/updateProxyPayMessage").permitAll()
                .antMatchers("/ownbiz/getCooperKeyMessage").permitAll()
                .antMatchers("/ownbiz/getProxyPayMessage").permitAll()
                .antMatchers("/corbiz/delAllOrderRelation/**").permitAll()
                .antMatchers("/corbiz/getAreaCodeByUserPhone").permitAll()
                .antMatchers("/corbiz/saveDemandMessage").permitAll()
                .antMatchers("/corbiz/getSmsSenderUrl").permitAll()
                .antMatchers("/corbiz/updateMmsGrsCode").permitAll()
                .antMatchers("/corbiz/saveUserZSMTRecord").permitAll()
                .antMatchers("/corbiz/updateSpMMSSendRecord").permitAll()
                .antMatchers("/corbiz/batchInsertMTRecords").permitAll()
                .antMatchers("/corbiz/updateGatewaySRecord").permitAll()
                .antMatchers("/corbiz/updateOrderMessage").permitAll()
                .antMatchers("/corbiz/saveOrderMessage").permitAll()
                .antMatchers("/corbiz/getSpProductIds").permitAll()
				.antMatchers("/ownbiz/userOrderCheck").permitAll()
                .antMatchers("/ownbiz/getProxyPayMessageById").permitAll()

				//ftp sysc
				.antMatchers("/spInfo/getSpInfoCountById").permitAll()
				.antMatchers("/spInfo/sycUpdateSpInfo").permitAll()
				.antMatchers("/spInfo/sycDeleteSpInfo").permitAll()
				.antMatchers("/spInfo/getAllSpIds").permitAll()

				.antMatchers("/productInfo/sysDeleteProduct").permitAll()
				.antMatchers("/productInfo/sysSaveOrUpdateProduct").permitAll()

				.antMatchers("/spAccess/sysDeleteAccess").permitAll()
				.antMatchers("/spAccess/sysSaveOrUpdateAccess").permitAll()

				// .antMatchers("/**/**").permitAll()
				// 除上面外的所有请求全部需要鉴权认证
				.anyRequest().authenticated();

		// 添加JWT filter
		httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

		// 禁用缓存
		httpSecurity.headers().cacheControl();
		httpSecurity.headers().frameOptions().sameOrigin();
		httpSecurity.headers().contentTypeOptions();
		httpSecurity.headers().httpStrictTransportSecurity();
		httpSecurity.headers().xssProtection();
	}
}
