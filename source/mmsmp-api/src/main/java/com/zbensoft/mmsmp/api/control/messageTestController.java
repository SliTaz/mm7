package com.zbensoft.mmsmp.api.control;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zbensoft.mmsmp.api.common.HttpRestStatus;
import com.zbensoft.mmsmp.api.common.LocaleMessageSourceService;
import com.zbensoft.mmsmp.api.common.ResponseRestEntity;
import com.zbensoft.mmsmp.db.domain.SMSTest;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/messageTest")
@RestController
public class messageTestController {
	@Value("${mms.sendContent.upload.url}")
	private String MMS_SENDCONTENT_UPLOAD_URL;
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;
//	@PreAuthorize("hasRole('R_UR_E')")
	@ApiOperation(value = "sms Test", notes = "")
	@RequestMapping(value = "/smsTest/manualWrite", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<Void> smsTest(@Valid @RequestBody SMSTest sms, BindingResult result, UriComponentsBuilder ucBuilder) {
		String[] phone = sms.getPhoneNumber().split("\n");
		List<String> phoneList = new ArrayList<String>();
		for (String string : phone) {
			phoneList.add(string);
		}
		//新增日志
//		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_INSERT, userRecv,CommonLogImpl.CUSTOMER_MANAGE);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setLocation(ucBuilder.path("/userRecv/{id}").buildAndExpand(userRecv.getUserRecvId()).toUri());
		return new ResponseRestEntity<Void>(HttpRestStatus.CREATED,localeMessageSourceService.getMessage("userRecv.create.created.message"));
	}
	
	@ApiOperation(value = "Tree Import", notes = "")
	@RequestMapping(value = "/smsTest/Import", method = RequestMethod.POST)
	public ResponseRestEntity<Void> smsFileUpload(HttpServletRequest request, @RequestParam("name") String name,@RequestParam("sendContent") String sendContent) throws Exception {
		InputStream is = request.getInputStream();
		 ByteArrayOutputStream   baos   =   new   ByteArrayOutputStream(); 
	        int   i=-1; 
	        while((i=is.read())!=-1){ 
	        baos.write(i); 
	        } 
	   String[] phone = baos.toString().split("\r\n");
	   List<String> phoneList = new ArrayList<String>();
		for (String string : phone) {
			phoneList.add(string);
		}

		return new ResponseRestEntity<Void>(HttpRestStatus.CREATED,localeMessageSourceService.getMessage("userRecv.create.created.message"));
	}	
	
//@ApiOperation(value = "sms Test", notes = "")
	@RequestMapping(value = "/mmsTest/manualWrite", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<Void> mmsTest(@Valid @RequestBody String phoneNumber,BindingResult result, UriComponentsBuilder ucBuilder) {
		String[] phone = phoneNumber.split("\n");
		List<String> phoneList = new ArrayList<String>();
		for (String string : phone) {
			phoneList.add(string);
		}
		//新增日志
//		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_INSERT, userRecv,CommonLogImpl.CUSTOMER_MANAGE);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setLocation(ucBuilder.path("/userRecv/{id}").buildAndExpand(userRecv.getUserRecvId()).toUri());
		return new ResponseRestEntity<Void>(HttpRestStatus.CREATED,localeMessageSourceService.getMessage("userRecv.create.created.message"));
	}
	
	@ApiOperation(value = "Tree Import", notes = "")
	@RequestMapping(value = "/mmsTest/Import", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<Void> mmsFileUpload(HttpServletRequest request, @RequestParam("name") String name) throws Exception {
		InputStream is = request.getInputStream();
		 ByteArrayOutputStream   baos   =   new   ByteArrayOutputStream(); 
	        int   i=-1; 
	        while((i=is.read())!=-1){ 
	        baos.write(i); 
	        } 
	   String[] phone = baos.toString().split("\r\n");
	   List<String> phoneList = new ArrayList<String>();
		for (String string : phone) {
			phoneList.add(string);
		}
		return new ResponseRestEntity<Void>(HttpRestStatus.CREATED,localeMessageSourceService.getMessage("userRecv.create.created.message"));
	}	
	//接收上传文件
	@ApiOperation(value = "Tree Import", notes = "")
	@RequestMapping(value = "/upload", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<Void> upload(HttpServletRequest request,@RequestParam("name") String name)throws Exception{
		InputStream is = request.getInputStream();
		 ByteArrayOutputStream   baos   =   new   ByteArrayOutputStream(); 
	        int   i=-1; 
	        while((i=is.read())!=-1){ 
	        baos.write(i); 
	        } 
	        File file1 = new File(MMS_SENDCONTENT_UPLOAD_URL);
	        if(!file1.exists()){
	        	file1.mkdirs();
	        	//生成word文档
		        XWPFDocument doc = new XWPFDocument(); //创建word文件
		        XWPFParagraph p1 = doc.createParagraph(); //创建段落
		        XWPFRun r1 = p1.createRun(); //创建段落文本
		        r1.setText("hello world"); //设置文本
		        FileOutputStream out = new FileOutputStream(MMS_SENDCONTENT_UPLOAD_URL + name); //创建输出流 
		        doc.write(out);  //输出
		        out.close();  //关闭输出流
	        }
	        
	        OutputStream outputStream=null;
	        File file2 = new File(MMS_SENDCONTENT_UPLOAD_URL + name);
	        outputStream = new FileOutputStream(file2);
	        int bytesWritten = 0;
	        int byteCount = 0;
	        byte[] bytes = new byte[1024];
	        while ((byteCount = is.read(bytes)) != -1)
	        {
	            outputStream.write(bytes, bytesWritten, byteCount);
	            bytesWritten += byteCount;
	        }
	        is.close();
	        outputStream.close();
		return new ResponseRestEntity<Void>(HttpRestStatus.CREATED,localeMessageSourceService.getMessage("userRecv.create.created.message"));
	}

}
