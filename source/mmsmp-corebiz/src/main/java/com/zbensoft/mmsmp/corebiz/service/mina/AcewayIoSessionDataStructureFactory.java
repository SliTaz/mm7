 package com.zbensoft.mmsmp.corebiz.service.mina;
 
 import java.util.Queue;
 import java.util.concurrent.ConcurrentHashMap;
 import org.apache.mina.core.session.IoSession;
 import org.apache.mina.core.session.IoSessionAttributeMap;
 import org.apache.mina.core.session.IoSessionDataStructureFactory;
 import org.apache.mina.core.write.WriteRequest;
 import org.apache.mina.core.write.WriteRequestQueue;
 
 
 
 
 public class AcewayIoSessionDataStructureFactory
   implements IoSessionDataStructureFactory
 {
   public IoSessionAttributeMap getAttributeMap(IoSession arg0)
     throws Exception
   {
     return new DefaultIoSessionAttributeMap();
   }
   
   public WriteRequestQueue getWriteRequestQueue(IoSession arg0) throws Exception
   {
     return new DefaultWriteRequestQueue();
   }
   
   private static class DefaultIoSessionAttributeMap implements IoSessionAttributeMap
   {
     private final ConcurrentHashMap<Object, Object> attributes = MinaDataStructure.getInstance().getMap();
     
 
 
 
 
 
 
 
 
 
     public Object getAttribute(IoSession session, Object key, Object defaultValue)
     {
       if (key == null) {
         throw new IllegalArgumentException("key");
       }
       
       if (defaultValue == null) {
         return this.attributes.get(key);
       }
       
       Object object = this.attributes.putIfAbsent(key, defaultValue);
       
       if (object == null) {
         return defaultValue;
       }
       return object;
     }
     
 
 
 
     public Object setAttribute(IoSession session, Object key, Object value)
     {
       if (key == null) {
         throw new IllegalArgumentException("key");
       }
       
       if (value == null) {
         return this.attributes.remove(key);
       }
       
       return this.attributes.put(key, value);
     }
     
 
 
     public Object setAttributeIfAbsent(IoSession session, Object key, Object value)
     {
       if (key == null) {
         throw new IllegalArgumentException("key");
       }
       
       if (value == null) {
         return null;
       }
       
       return this.attributes.putIfAbsent(key, value);
     }
     
 
 
     public Object removeAttribute(IoSession session, Object key)
     {
       if (key == null) {
         throw new IllegalArgumentException("key");
       }
       
       return this.attributes.remove(key);
     }
     
 
 
     public boolean removeAttribute(IoSession session, Object key, Object value)
     {
       if (key == null) {
         throw new IllegalArgumentException("key");
       }
       
       if (value == null) {
         return false;
       }
       try
       {
         return this.attributes.remove(key, value);
       } catch (NullPointerException e) {}
       return false;
     }
     
 
 
     public boolean replaceAttribute(IoSession session, Object key, Object oldValue, Object newValue)
     {
       try
       {
         return this.attributes.replace(key, oldValue, newValue);
       }
       catch (NullPointerException localNullPointerException) {}
       
       return false;
     }
     
 
 
     public boolean containsAttribute(IoSession session, Object key)
     {
       return this.attributes.containsKey(key);
     }
     
     
     public java.util.Set<Object> getAttributeKeys(IoSession session)
     {
		return null;

     }
     
     public void dispose(IoSession session)
       throws Exception
     {}
   }
   
   private static class DefaultWriteRequestQueue
     implements WriteRequestQueue
   {
     private final Queue<WriteRequest> q = MinaDataStructure.getInstance().getQueue();
     
 
 
 
 
 
 
 
     public void dispose(IoSession session) {}
     
 
 
 
 
 
 
 
     public void clear(IoSession session)
     {
       this.q.clear();
     }
     
 
 
     public synchronized boolean isEmpty(IoSession session)
     {
       return this.q.isEmpty();
     }
     
 
 
     public synchronized void offer(IoSession session, WriteRequest writeRequest)
     {
       this.q.offer(writeRequest);
     }
     
 
 
     public synchronized WriteRequest poll(IoSession session)
     {
       return (WriteRequest)this.q.poll();
     }
     
     public String toString()
     {
       return this.q.toString();
     }
     
 
 
     public int size()
     {
       return this.q.size();
     }
   }
 }


