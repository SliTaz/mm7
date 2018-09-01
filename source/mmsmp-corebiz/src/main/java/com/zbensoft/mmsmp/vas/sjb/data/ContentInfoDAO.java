 package com.zbensoft.mmsmp.vas.sjb.data;
 
 import com.zbensoft.mmsmp.common.ra.common.db.entity.ContentInfo;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.ContentInfoAdapter;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.ContentInfoRelation;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.springframework.jdbc.core.support.JdbcDaoSupport;
 
 
 
 
 
 
 
 
 
 
 
 public class ContentInfoDAO
   extends JdbcDaoSupport
 {
   private static final Log logger = LogFactory.getLog(ContentInfoDAO.class);
   
 
 
 
 
 
 
   public List<ContentInfo> getNeedSendContentList(int serviceId, boolean isContentTime, boolean isNational)
   {
     logger.debug("getNeedSendContentList serviceId is: " + serviceId);
     Connection conn = null;
     PreparedStatement preStmt = null;
     ResultSet result = null;
     List<ContentInfo> contentList = new ArrayList();
     ContentInfo content = null;
     
     String sql = "select t.smstext, t.contentid, t.contentname, t.contentpath, t.contenttype, t.validstarttime, t.version_id, t.authorname from content_info t where status = '4' and  t.serviceid=? and t.sendflag='0' ";
     
 
 
     if (isContentTime) {
       sql = sql + "and t.validstarttime <= current timestamp and date(t.validstarttime)=current date ";
     }
     try {
       conn = getConnection();
       preStmt = conn.prepareStatement(sql);
       preStmt.setInt(1, serviceId);
       
       for (result = preStmt.executeQuery(); result.next();) {
         content = new ContentInfo();
         content.setContentid(Integer.valueOf(result.getInt("contentid")));
         content.setContentname(result.getString("contentname"));
         content.setContentpath(result.getString("contentpath"));
         content.setContenttype(result.getString("contenttype"));
         content.setValidstarttime(result.getTimestamp("validstarttime"));
         content.setVersionId(Integer.valueOf(result.getInt("version_id")));
         content.setAuthorname(result.getString("authorname"));
         content.setSmsText(result.getString("smstext"));
         
 
 
         Map<Integer, ContentInfoAdapter> adapterContentMap = getAdapterSendContentMap(content.getContentid());
         content.setAdapterContentMap(adapterContentMap);
         
         Map<String, ContentInfoRelation> provinceContent = getProviceSendContentMap(content.getContentid(), isNational);
         content.setProvinceContent(provinceContent);
         
 
 
         contentList.add(content);
       }
       
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
       return contentList;
     } catch (Exception e) {
       e.printStackTrace();
       logger.error("get contentList error! sql: " + sql + "ServiceId: " + serviceId, e);
     }
     finally {
       releaseConnection(conn);
     }
     if (logger.isInfoEnabled()) {
       logger.info("getNeedSendContentList return null...");
     }
     return null;
   }
   
 
 
 
 
 
 
   public List<ContentInfo> getLatestContentList(int serviceId, boolean isNational)
   {
     logger.debug("getLatestContentList serviceId is: " + serviceId);
     Connection conn = null;
     PreparedStatement preStmt = null;
     ResultSet result = null;
     List<ContentInfo> contentList = new ArrayList();
     ContentInfo content = null;
     
     String sql = "select t.smstext, t.contentid, t.contentname, t.contentpath, t.contenttype, t.validstarttime, t.version_id, t.authorname from content_info t where  t.serviceid=? and t.validstarttime =  (select max(validstarttime) from content_info where status = '4' and serviceid=? and validstarttime<current timestamp)";
     
 
 
 
     try
     {
       conn = getConnection();
       preStmt = conn.prepareStatement(sql);
       preStmt.setInt(1, serviceId);
       preStmt.setInt(2, serviceId);
       
       for (result = preStmt.executeQuery(); result.next();) {
         content = new ContentInfo();
         content.setContentid(Integer.valueOf(result.getInt("contentid")));
         content.setContentname(result.getString("contentname"));
         content.setContentpath(result.getString("contentpath"));
         content.setContenttype(result.getString("contenttype"));
         content.setValidstarttime(result.getTimestamp("validstarttime"));
         content.setVersionId(Integer.valueOf(result.getInt("version_id")));
         content.setAuthorname(result.getString("authorname"));
         content.setSmsText(result.getString("smstext"));
         
 
 
         Map<Integer, ContentInfoAdapter> adapterContentMap = getAdapterSendContentMap(content.getContentid());
         content.setAdapterContentMap(adapterContentMap);
         
         Map<String, ContentInfoRelation> provinceContent = getProviceSendContentMap(content.getContentid(), isNational);
         content.setProvinceContent(provinceContent);
         
         contentList.add(content);
       }
       
       return contentList;
     } catch (Exception e) {
       e.printStackTrace();
       logger.error("getLatestContentList error! sql: " + sql + "ServiceId: " + serviceId, e);
     }
     finally {
       releaseConnection(conn);
       if (preStmt != null) {
         try {
           preStmt.close();
         } catch (SQLException e) {
           e.printStackTrace();
         }
       }
     }
     if (logger.isInfoEnabled()) {
       logger.info("getLatestContentList return null...");
     }
     return null;
   }
   
 
 
 
 
 
   public boolean updateFlag(List<ContentInfo> list, String sendFlag)
   {
     if ((list == null) || (list.size() == 0)) {
       return true;
     }
     Connection conn = null;
     PreparedStatement preStmt = null;
     StringBuffer sb = new StringBuffer("update content_info set sendflag = '" + sendFlag + "' where  contentid in (");
     try {
       for (ContentInfo c : list) {
         sb.append(c.getContentid()).append(" ,");
       }
       sb.replace(sb.length() - 1, sb.length(), " )");
       
       conn = getConnection();
       preStmt = conn.prepareStatement(sb.toString());
       preStmt.executeUpdate();
       return true;
     } catch (Exception e) {
       logger.error("change sendFlag error！ sql：" + sb.toString(), e);
       return false;
     } finally {
       releaseConnection(conn);
       if (preStmt != null) {
         try {
           preStmt.close();
         } catch (SQLException e) {
           e.printStackTrace();
         }
       }
     }
   }
   

   private Map<String, ContentInfoRelation> getProviceSendContentMap(Integer contentID, boolean isNational)
   {
	return null;
	   
   }
   

   private Map<Integer, ContentInfoAdapter> getAdapterSendContentMap(Integer contentID)
   {
	return null;
	   
   }
   
   public List<ContentInfo> getContentByContentId(int contentId, boolean isNational)
   {
     String sql = "SELECT * FROM CONTENT_INFO t WHERE t.CONTENTID= ?";
     Connection conn = null;
     PreparedStatement preStmt = null;
     ResultSet result = null;
     List<ContentInfo> contentList = new ArrayList();
     ContentInfo content = null;
     try
     {
       conn = getConnection();
       preStmt = conn.prepareStatement(sql);
       preStmt.setInt(1, contentId);
       
       for (result = preStmt.executeQuery(); result.next();) {
         content = new ContentInfo();
         content.setContentid(Integer.valueOf(result.getInt("contentid")));
         content.setContentname(result.getString("contentname"));
         content.setContentpath(result.getString("contentpath"));
         content.setContenttype(result.getString("contenttype"));
         content.setValidstarttime(result.getTimestamp("validstarttime"));
         content.setVersionId(Integer.valueOf(result.getInt("version_id")));
         content.setAuthorname(result.getString("authorname"));
         content.setSmsText(result.getString("smstext"));
         
 
 
         Map<Integer, ContentInfoAdapter> adapterContentMap = getAdapterSendContentMap(content.getContentid());
         content.setAdapterContentMap(adapterContentMap);
         
         Map<String, ContentInfoRelation> provinceContent = getProviceSendContentMap(content.getContentid(), isNational);
         content.setProvinceContent(provinceContent);
         
 
 
         contentList.add(content);
       }
       
       return contentList;
     } catch (Exception e) {
       e.printStackTrace();
       logger.error("get contentList error! sql: " + sql + "contentId: " + contentId, e);
     }
     finally {
       releaseConnection(conn);
       if (preStmt != null) {
         try {
           preStmt.close();
         } catch (SQLException e) {
           e.printStackTrace();
         }
       }
     }
     return null;
   }
 }





