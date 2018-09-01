package com.zbensoft.mmsmp.api.control;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zbensoft.mmsmp.api.common.HttpRestStatus;
import com.zbensoft.mmsmp.api.common.HttpRestStatusHelp;
import com.zbensoft.mmsmp.api.common.ResponseRestEntity;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/httpRestStatus")
@RestController
public class HttpRestStatusController {
	
	private static final Logger log = LoggerFactory.getLogger(HttpRestStatusController.class);

	@ApiOperation(value = "Query httpRestStatusMapper，Support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<HttpRestStatusHelp>> selectPage() {

		List<HttpRestStatusHelp> list = new ArrayList<HttpRestStatusHelp>();

		try {
			list = HttpRestStatus.getList(Class.forName("com.zbensoft.mmsmp.api.common.HttpRestStatus"), null);
		} catch (ClassNotFoundException e) {
			log.error("",e);
		}

		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<HttpRestStatusHelp>>(new ArrayList<HttpRestStatusHelp>(), HttpRestStatus.NOT_FOUND);
		}

		return new ResponseRestEntity<List<HttpRestStatusHelp>>(list, HttpRestStatus.OK);
	}

}
