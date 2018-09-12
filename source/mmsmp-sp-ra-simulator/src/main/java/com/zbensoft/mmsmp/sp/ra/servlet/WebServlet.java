package com.zbensoft.mmsmp.sp.ra.servlet;

import org.apache.axis.transport.http.AxisServlet;

@javax.servlet.annotation.WebServlet(
        urlPatterns =  "/services/*",
        loadOnStartup = 1,
        name = "AxisServlet"
)
public class WebServlet extends AxisServlet{

}
