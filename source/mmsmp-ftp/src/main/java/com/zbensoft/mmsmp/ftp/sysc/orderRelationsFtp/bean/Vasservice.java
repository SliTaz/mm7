package com.zbensoft.mmsmp.ftp.sysc.orderRelationsFtp.bean;

import java.util.Set;

public class Vasservice {
    private static final long serialVersionUID = -4503117296098452409L;
    private Integer uniqueid;
    private String vaspid;
    private String vasid;
    private String vassmsid;
    private String servicecode;
    private String servicename;
    private String servicetype;
    private String feetype;
    private String status;
    private String runstatus;
    private String createdate;
    private String updatedate;
    private String servicedesc;
    private Double orderfee;
    private Double ondemandfee;
    private int fee;
    private String ondemanddesc;
    private String feedesc;
    private String mtcontentmode;
    private String mtmode;
    private String smskey;
    private String treeid;
    private String treename;
    private String ordercode;
    private String cancelordercode;
    private String viewpic;
    private String smallpic;
    private String defaultflag;
    private String ondemandcode;
    private Integer featurestrnum;
    private String featurestrrange;
    private String applyDate;
    private Integer source;
    private String producttype;
    private String proorderid;
    private String bookManName;
    private String separateScale;
    private String composeTimeInterval;
    private String auditTimeInterval;
    private String proofTimeInterval;
    private String approver;
    private String opinion;
    private String proAuditTimeInterval;
    private String productdesc;
    private Set<Vasservice> products;
    private Set<Vasservice> packages;
    private String isPackage;
    private Integer priority;
    private String priority1;
    private Integer dealstatus;

    public String getTreename() {
        return this.treename;
    }

    public void setTreename(String treename) {
        this.treename = treename;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getIsPackage() {
        return this.isPackage;
    }

    public void setIsPackage(String isPackage) {
        this.isPackage = isPackage;
    }

    public String getProductdesc() {
        return this.productdesc;
    }

    public void setProductdesc(String productdesc) {
        this.productdesc = productdesc;
    }

    public String getApprover() {
        return this.approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getOpinion() {
        return this.opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getProAuditTimeInterval() {
        return this.proAuditTimeInterval;
    }

    public void setProAuditTimeInterval(String proAuditTimeInterval) {
        this.proAuditTimeInterval = proAuditTimeInterval;
    }

    public String getAuditTimeInterval() {
        return this.auditTimeInterval;
    }

    public void setAuditTimeInterval(String auditTimeInterval) {
        this.auditTimeInterval = auditTimeInterval;
    }

    public String getBookManName() {
        return this.bookManName;
    }

    public void setBookManName(String bookManName) {
        this.bookManName = bookManName;
    }

    public String getComposeTimeInterval() {
        return this.composeTimeInterval;
    }

    public void setComposeTimeInterval(String composeTimeInterval) {
        this.composeTimeInterval = composeTimeInterval;
    }

    public String getProofTimeInterval() {
        return this.proofTimeInterval;
    }

    public void setProofTimeInterval(String proofTimeInterval) {
        this.proofTimeInterval = proofTimeInterval;
    }

    public String getSeparateScale() {
        return this.separateScale;
    }

    public void setSeparateScale(String separateScale) {
        this.separateScale = separateScale;
    }

    public String getProducttype() {
        return this.producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
    }

    public String getProorderid() {
        return this.proorderid;
    }

    public void setProorderid(String proorderid) {
        this.proorderid = proorderid;
    }

    public Integer getSource() {
        return this.source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getFeaturestrrange() {
        return this.featurestrrange;
    }

    public void setFeaturestrrange(String featurestrrange) {
        this.featurestrrange = featurestrrange;
    }

    public Vasservice() {
    }

    public Vasservice(String vaspid, String vasid, String servicecode, String runstatus) {
        this.vaspid = vaspid;
        this.vasid = vasid;
        this.servicecode = servicecode;
        this.runstatus = runstatus;
    }

    public Vasservice(String vaspid, String vasid, String servicecode, String servicename, String servicetype, String feetype, String status, String runstatus, String createdate, String updatedate, String servicedesc, Double orderfee, Double ondemandfee, String ondemanddesc, String feedesc, String mtcontentmode, String mtmode, String smskey, String treeid, String ordercode, String cancelordercode, String viewpic, String smallpic, String defaultflag, String ondemandcode, Integer featurestrnum, Integer priocity) {
        this.vaspid = vaspid;
        this.vasid = vasid;
        this.servicecode = servicecode;
        this.servicename = servicename;
        this.servicetype = servicetype;
        this.feetype = feetype;
        this.status = status;
        this.runstatus = runstatus;
        this.createdate = createdate;
        this.updatedate = updatedate;
        this.servicedesc = servicedesc;
        this.orderfee = orderfee;
        this.ondemandfee = ondemandfee;
        this.ondemanddesc = ondemanddesc;
        this.feedesc = feedesc;
        this.mtcontentmode = mtcontentmode;
        this.mtmode = mtmode;
        this.smskey = smskey;
        this.treeid = treeid;
        this.ordercode = ordercode;
        this.cancelordercode = cancelordercode;
        this.viewpic = viewpic;
        this.smallpic = smallpic;
        this.defaultflag = defaultflag;
        this.ondemandcode = ondemandcode;
        this.featurestrnum = featurestrnum;
        this.priority = this.priority;
    }

    public Integer getUniqueid() {
        return this.uniqueid;
    }

    public void setUniqueid(Integer uniqueid) {
        this.uniqueid = uniqueid;
    }

    public String getVaspid() {
        return this.vaspid;
    }

    public void setVaspid(String vaspid) {
        this.vaspid = vaspid;
    }

    public String getVasid() {
        return this.vasid;
    }

    public void setVasid(String vasid) {
        this.vasid = vasid;
    }

    public String getServicecode() {
        return this.servicecode;
    }

    public void setServicecode(String servicecode) {
        this.servicecode = servicecode;
    }

    public String getServicename() {
        return this.servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getServicetype() {
        return this.servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    public String getFeetype() {
        return this.feetype;
    }

    public void setFeetype(String feetype) {
        this.feetype = feetype;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRunstatus() {
        return this.runstatus;
    }

    public void setRunstatus(String runstatus) {
        this.runstatus = runstatus;
    }

    public String getCreatedate() {
        return this.createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getUpdatedate() {
        return this.updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getServicedesc() {
        return this.servicedesc;
    }

    public void setServicedesc(String servicedesc) {
        this.servicedesc = servicedesc;
    }

    public Double getOrderfee() {
        return this.orderfee;
    }

    public void setOrderfee(Double orderfee) {
        this.orderfee = orderfee;
    }

    public Double getOndemandfee() {
        return this.ondemandfee;
    }

    public void setOndemandfee(Double ondemandfee) {
        this.ondemandfee = ondemandfee;
    }

    public String getOndemanddesc() {
        return this.ondemanddesc;
    }

    public void setOndemanddesc(String ondemanddesc) {
        this.ondemanddesc = ondemanddesc;
    }

    public String getFeedesc() {
        return this.feedesc;
    }

    public void setFeedesc(String feedesc) {
        this.feedesc = feedesc;
    }

    public String getMtcontentmode() {
        return this.mtcontentmode;
    }

    public void setMtcontentmode(String mtcontentmode) {
        this.mtcontentmode = mtcontentmode;
    }

    public String getMtmode() {
        return this.mtmode;
    }

    public void setMtmode(String mtmode) {
        this.mtmode = mtmode;
    }

    public String getSmskey() {
        return this.smskey;
    }

    public void setSmskey(String smskey) {
        this.smskey = smskey;
    }

    public String getTreeid() {
        return this.treeid;
    }

    public void setTreeid(String treeid) {
        this.treeid = treeid;
    }

    public String getOrdercode() {
        return this.ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }

    public String getCancelordercode() {
        return this.cancelordercode;
    }

    public void setCancelordercode(String cancelordercode) {
        this.cancelordercode = cancelordercode;
    }

    public String getViewpic() {
        return this.viewpic;
    }

    public void setViewpic(String viewpic) {
        this.viewpic = viewpic;
    }

    public String getSmallpic() {
        return this.smallpic;
    }

    public void setSmallpic(String smallpic) {
        this.smallpic = smallpic;
    }

    public String getDefaultflag() {
        return this.defaultflag;
    }

    public void setDefaultflag(String defaultflag) {
        this.defaultflag = defaultflag;
    }

    public Integer getFeaturestrnum() {
        return this.featurestrnum;
    }

    public void setFeaturestrnum(Integer featurestrnum) {
        this.featurestrnum = featurestrnum;
    }

    public String getOndemandcode() {
        return this.ondemandcode;
    }

    public void setOndemandcode(String ondemandcode) {
        this.ondemandcode = ondemandcode;
    }

    public String getVassmsid() {
        return this.vassmsid;
    }

    public void setVassmsid(String vassmsid) {
        this.vassmsid = vassmsid;
    }

    public Set<Vasservice> getPackages() {
        return this.packages;
    }

    public void setPackages(Set<Vasservice> packages) {
        this.packages = packages;
    }

    public Set<Vasservice> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Vasservice> products) {
        this.products = products;
    }

    public Integer getDealstatus() {
        return this.dealstatus;
    }

    public void setDealstatus(Integer dealstatus) {
        this.dealstatus = dealstatus;
    }

    public String getApplyDate() {
        return this.applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getPriority1() {
        return this.priority1;
    }

    public void setPriority1(String priority1) {
        this.priority1 = priority1;
    }

    public int getFee() {
        return this.fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}
