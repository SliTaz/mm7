 package com.zbensoft.mmsmp.vas.sjb.log;
 
 import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MT_MMHttpMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MT_MMMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MT_ReportMessage;
 import com.zbensoft.mmsmp.vas.sjb.common.Task;
 import java.util.Map;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class LogTask
   implements Task
 {
   private static final Log log = LogFactory.getLog(LogTask.class);
   private Map logMap;
   private MT_ReportMessage mtMsg;
   
   public LogTask(Map<String, AbstractMessage> logMap) { this.logMap = logMap; }
   
 
 
   private MO_ReportMessage moMsg;
   
 
   private String mapKey;
   
   public void doTask(AbstractMessage message)
   {
     try
     {
       if (message == null) {
         return;
       }
       if (!(message instanceof MT_ReportMessage))
       {
 
 
 
 
 
 
 
 
         if (!(message instanceof MO_ReportMessage))
         {
 
 
 
 
 
 
 
 
 
 
 
           if ((message instanceof MT_MMMessage)) {
             log.info("===========================receve a MT_MMMessage to corebiz===========================");
             MT_MMMessage _moMsg = (MT_MMMessage)message;
             this.mapKey = _moMsg.getMtTranId();
             this.logMap.put(this.mapKey, _moMsg);
             log.info("received MT_MMMessage and put it into logMap.mapKey:" + this.mapKey);
           } else if ((message instanceof MT_MMHttpMessage)) {
             log.info("===========================receve a MT_MMHttpMessage to corebiz===========================");
           }
           else
           {
             log.info("===========================receve a other message to corebiz===========================");
           } }
       }
     } catch (Exception e) {
       log.error(e.getMessage());
     }
   }
 }





