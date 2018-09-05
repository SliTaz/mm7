

package com.zbensoft.mmsmp.ownbiz.ra.own.cache;

import com.zbensoft.mmsmp.ownbiz.ra.own.entity.CooperKeyEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.VasServiceRelationEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.VaspEnitiy;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DataCache {
    private static final Map dataMap = new HashMap();

    public DataCache() {
    }

    public Collection all() {
        return null;
    }

    public static Object get(Serializable key) {
        return dataMap.get(key);
    }

    public static CooperKeyEntity getCooperKey(String cooperId) {
        CooperKeyEntity entity = null;
        if (dataMap.containsKey("cooper_key")) {
            Map map = (Map)dataMap.get("cooper_key");
            if (map == null) {
                return null;
            }

            entity = (CooperKeyEntity)map.get(cooperId);
        }

        return entity;
    }

    public static CooperKeyEntity getCooperKeyMobile(Integer cooperCode) {
        CooperKeyEntity entity = null;
        if (dataMap.containsKey("cooper_key_mobile")) {
            Map map = (Map)dataMap.get("cooper_key_mobile");
            if (map == null) {
                return null;
            }

            entity = (CooperKeyEntity)map.get(cooperCode);
        }

        return entity;
    }

    public static VasServiceRelationEntity getVasServiceRelationEntity(String spProductId) {
        VasServiceRelationEntity entity = null;
        if (dataMap.containsKey("product_service_key")) {
            Map map = (Map)dataMap.get("product_service_key");
            if (map == null) {
                return null;
            }

            entity = (VasServiceRelationEntity)map.get(spProductId);
        }

        return entity;
    }

    public static VasServiceRelationEntity getDistributeProductRelation(String spProductId) {
        VasServiceRelationEntity entity = null;
        if (dataMap.containsKey("product_distribute_key")) {
            Map map = (Map)dataMap.get("product_distribute_key");
            if (map == null) {
                return null;
            }

            entity = (VasServiceRelationEntity)map.get(spProductId);
        }

        return entity;
    }

    public static VaspEnitiy getVaspEnitiyBySpId(String spid) {
        VaspEnitiy vasp = null;
        if (dataMap.containsKey("vasp_key")) {
            Map map = (Map)dataMap.get("vasp_key");
            if (map != null) {
                vasp = (VaspEnitiy)map.get(spid);
            }
        }

        return vasp;
    }

    public void put(Serializable key, Object value) {
        Map var3 = dataMap;
        synchronized(dataMap) {
            dataMap.put(key, value);
        }
    }

    public void remove(Serializable key) {
    }

    public void refresh(Serializable key, Object value) {
    }

    public boolean isContainsKey(Serializable key) {
        return dataMap.containsKey(key);
    }
}
