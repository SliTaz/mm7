package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi;

import java.nio.ByteBuffer;

public class SGIP_Submit extends SGIP_Command {
    long NodeID;
    String SPNumber;
    String ChargeNumber;
    int UserCount;
    String[] usernumber;
    String CorpId;
    String ServiceType;
    int FeeType;
    String FeeValue;
    String GivenValue;
    int AgentFlag;
    int MOrelatetoMTFlag;
    int Priority;
    String ExpireTime;
    String ScheduleTime;
    int ReportFlag;
    int TP_pid;
    int TP_udhi;
    int MessageCoding;
    int MessageType;
    int MessageLength;
    String MessageContent;
    byte[] BinContent;
    int ContentLength;
    String linkId;

    public SGIP_Submit(long lSrcNodeID) {
        super(lSrcNodeID);
        this.NodeID = 0L;
        this.ContentLength = 0;
        this.NodeID = lSrcNodeID;
    }

    public SGIP_Submit(SGIP_SubmitResp sgip_command) {
        super(sgip_command);
        this.NodeID = 0L;
        this.ContentLength = 0;
    }

    public SGIP_Submit(SGIP_Command sgip_command) {
        super(sgip_command);
    }

    public SGIP_Submit(long lSrcNodeID, SGIP_Submit sgip_command) {
        super(sgip_command);
        Class var4 = SGIP_SequenceNo.seqclass;
        synchronized(SGIP_SequenceNo.seqclass) {
            if (seq == null) {
                seq = new SGIP_SequenceNo();
            }

            seq.setNodeId(lSrcNodeID);
            SGIP_SequenceNo.computeSequence();
            this.sgipHead.SetSeq_1(seq.getGlobalSeq_1());
            this.sgipHead.SetSeq_2(seq.getGlobalSeq_2());
            this.sgipHead.SetSeq_3(seq.getGlobalSeq_3());
        }

        this.NodeID = 0L;
        this.ContentLength = 0;
        this.NodeID = lSrcNodeID;
    }

    public void setSPNumber(String strSPNumber) {
        this.SPNumber = strSPNumber;
    }

    public String getSPNumber() {
        return this.SPNumber;
    }

    public void setChargeNumber(String strChargeNumber) {
        this.ChargeNumber = strChargeNumber;
    }

    public String getChargeNumber() {
        return this.ChargeNumber;
    }

    public int setUserNumber(String strUserNumber) throws SGIP_Exception {
        if (strUserNumber.length() == 0) {
            return 1;
        } else {
            this.UserCount = 0;

            int l;
            for(l = 0; l < strUserNumber.length(); ++l) {
                char c = strUserNumber.charAt(l);
                if (c != ',' && (c < '0' || c > '9')) {
                    throw new SGIP_Exception("Invalid Mobile Number");
                }

                if (c == ',') {
                    ++this.UserCount;
                }
            }

            ++this.UserCount;
            this.usernumber = new String[this.UserCount];
            l = 0;

            for(int j = 0; j < this.UserCount - 1; ++j) {
                int k = l;
                l = strUserNumber.indexOf(44, l + 1);
                this.usernumber[j] = strUserNumber.substring(k, l);
                ++l;
            }

            this.usernumber[this.UserCount - 1] = strUserNumber.substring(l, strUserNumber.length());
            return 0;
        }
    }

    public String[] getUserNumber() {
        return this.usernumber;
    }

    public int getUserCount() {
        return this.UserCount;
    }

    public void setCorpId(String strCorpID) {
        this.CorpId = strCorpID;
    }

    public String getCorpId() {
        return this.CorpId;
    }

    public void setServiceType(String strServiceType) {
        this.ServiceType = strServiceType;
    }

    public String getServiceType() {
        return this.ServiceType;
    }

    public void setFeeType(int nFeeType) {
        this.FeeType = nFeeType;
    }

    public int getFeeType() {
        return this.FeeType;
    }

    public void setFeeValue(String strFeeValue) {
        this.FeeValue = strFeeValue;
    }

    public String getFeeValue() {
        return this.FeeValue;
    }

    public void setGivenValue(String strGivenValue) {
        this.GivenValue = strGivenValue;
    }

    public String getGivenValue() {
        return this.GivenValue;
    }

    public void setAgentFlag(int nAgentFlag) {
        this.AgentFlag = nAgentFlag;
    }

    public int getAgentFlag() {
        return this.AgentFlag;
    }

    public void setMOrelatetoMTFlag(int nMOMTFlag) {
        this.MOrelatetoMTFlag = nMOMTFlag;
    }

    public int getMOrelatetoMTFlag() {
        return this.MOrelatetoMTFlag;
    }

    public void setPriority(int nPriority) {
        this.Priority = nPriority;
    }

    public int getPriority() {
        return this.Priority;
    }

    public void setExpireTime(String strExpireTime) {
        this.ExpireTime = strExpireTime;
    }

    public String getExpireTime() {
        return this.ExpireTime;
    }

    public void setScheduleTime(String strScheduleTime) {
        this.ScheduleTime = strScheduleTime;
    }

    public String getScheduleTime() {
        return this.ScheduleTime;
    }

    public void setReportFlag(int nReportFlag) {
        this.ReportFlag = nReportFlag;
    }

    public int getReportFlag() {
        return this.ReportFlag;
    }

    public void setTP_pid(int nTP_pid) {
        this.TP_pid = nTP_pid;
    }

    public int getTP_pid() {
        return this.TP_pid;
    }

    public void setTP_udhi(int nTP_udhi) {
        this.TP_udhi = nTP_udhi;
    }

    public int getTP_udhi() {
        return this.TP_udhi;
    }

    public void setMessageType(int nMessageType) {
        this.MessageType = nMessageType;
    }

    public int getMessageType() {
        return this.MessageType;
    }

    public int setContent(int nMessageCoding, String strContent) throws SGIP_Exception {
        if (nMessageCoding == 4) {
            throw new SGIP_Exception("MessageCoding Error! Use setBinContent() to set Binary Message!");
        } else {
            this.MessageCoding = nMessageCoding;
            this.MessageContent = strContent;
            return 0;
        }
    }

    public int setBinContent(int nContentLen, byte[] abyte0) {
        this.MessageCoding = 4;
        this.ContentLength = nContentLen;
        this.BinContent = new byte[nContentLen];
        SGIP_Common.BytesCopy(abyte0, this.BinContent, 0, nContentLen - 1, 0);
        return 0;
    }

    public int getMessageCoding() {
        return this.MessageCoding;
    }

    public int getMessageLength() {
        return this.ContentLength;
    }

    public SGIP_Submit(long lSrcNodeID, String strSPNumber, String strChargeNumber, int nUserCount, String strUserNumber, String strCorpID, String strServiceType, int nFeeType, String strFeeValue, String strGivenValue, int nAgentFlag, int nMOMTFlag, int nPriority, String strExpireTime, String strScheduleTime, int nReportFlag, int nTP_pid, int nTP_udhi, int nMessageCoding, int nMessageType, int nContentLength, String strMessageContent, String linkId) throws SGIP_Exception {
        super(lSrcNodeID);
        int count = 0;

        int l3;
        for(l3 = 0; l3 < strUserNumber.length(); ++l3) {
            char c = strUserNumber.charAt(l3);
            if (c != ',' && (c < '0' || c > '9')) {
                throw new SGIP_Exception("Invalid Mobile Number");
            }

            if (c == ',') {
                ++count;
            }
        }

        ++count;
        if (count > 100) {
            throw new SGIP_Exception("Too many users!");
        } else {
            String[] numbers = new String[count];
            l3 = 0;

            for(int j3 = 0; j3 < count - 1; ++j3) {
                int k3 = l3;
                l3 = strUserNumber.indexOf(44, l3 + 1);
                numbers[j3] = strUserNumber.substring(k3, l3);
                ++l3;
            }

            numbers[count - 1] = strUserNumber.substring(l3, strUserNumber.length());
            this.init(lSrcNodeID, strSPNumber, strChargeNumber, numbers, strCorpID, strServiceType, nFeeType, strFeeValue, strGivenValue, nAgentFlag, nMOMTFlag, nPriority, strExpireTime, strScheduleTime, nReportFlag, nTP_pid, nTP_udhi, nMessageCoding, nMessageType, strMessageContent, linkId);
        }
    }

    public SGIP_Submit(long lSrcNodeID, String strSPNumber, String strChargeNumber, String[] userNumbers, String strCorpID, String strServiceType, int nFeeType, String strFeeValue, String strGivenValue, int nAgentFlag, int nMOMTFlag, int nPriority, String strExpireTime, String strScheduleTime, int nReportFlag, int nTP_pid, int nTP_udhi, int nMessageCoding, int nMessageType, String strMessageContent, String linkId) throws SGIP_Exception {
        super(lSrcNodeID);
        this.init(lSrcNodeID, strSPNumber, strChargeNumber, userNumbers, strCorpID, strServiceType, nFeeType, strFeeValue, strGivenValue, nAgentFlag, nMOMTFlag, nPriority, strExpireTime, strScheduleTime, nReportFlag, nTP_pid, nTP_udhi, nMessageCoding, nMessageType, strMessageContent, linkId);
    }

    private void init(long lSrcNodeID, String strSPNumber, String strChargeNumber, String[] userNumbers, String strCorpID, String strServiceType, int nFeeType, String strFeeValue, String strGivenValue, int nAgentFlag, int nMOMTFlag, int nPriority, String strExpireTime, String strScheduleTime, int nReportFlag, int nTP_pid, int nTP_udhi, int nMessageCoding, int nMessageType, String strMessageContent, String linkId) throws SGIP_Exception {
        this.NodeID = lSrcNodeID;
        this.SPNumber = strSPNumber;
        this.ChargeNumber = strChargeNumber;
        this.UserCount = userNumbers.length;
        if (this.UserCount > 100) {
            throw new SGIP_Exception("Too many users!");
        } else if (this.UserCount == 0) {
            throw new SGIP_Exception("no user number");
        } else {
            this.usernumber = new String[this.UserCount];
            this.usernumber = userNumbers;
            this.CorpId = strCorpID;
            this.ServiceType = strServiceType;
            this.FeeType = nFeeType;
            this.FeeValue = strFeeValue;
            this.GivenValue = strGivenValue;
            this.AgentFlag = nAgentFlag;
            this.MOrelatetoMTFlag = nMOMTFlag;
            this.Priority = nPriority;
            this.ExpireTime = strExpireTime;
            this.ScheduleTime = strScheduleTime;
            this.ReportFlag = nReportFlag;
            this.TP_pid = nTP_pid;
            this.TP_udhi = nTP_udhi;
            this.MessageCoding = nMessageCoding;
            this.MessageType = nMessageType;
            this.ContentLength = strMessageContent.length();
            this.MessageContent = strMessageContent;
            this.linkId = linkId;
            this.SetMsgBody();
        }
    }

    public SGIP_Submit(long lSrcNodeID, String strSPNumber, String strChargeNumber, int nUserCount, String strUserNumber, String strCorpID, String strServiceType, int nFeeType, String strFeeValue, String strGivenValue, int nAgentFlag, int nMOMTFlag, int nPriority, String strExpireTime, String strScheduleTime, int nReportFlag, int nTP_pid, int nTP_udhi, int nMessageCoding, int nMessageType, int nContentLength, String strMessageContent) throws SGIP_Exception {
        this(lSrcNodeID, strSPNumber, strChargeNumber, nUserCount, strUserNumber, strCorpID, strServiceType, nFeeType, strFeeValue, strGivenValue, nAgentFlag, nMOMTFlag, nPriority, strExpireTime, strScheduleTime, nReportFlag, nTP_pid, nTP_udhi, nMessageCoding, nMessageType, nContentLength, strMessageContent, "");
    }

    public SGIP_Submit(long lSrcNodeID, String strSPNumber, String strChargeNumber, String strUserNumber, String strCorpID, String strServiceType, int nFeeType, String strFeeValue, String strGivenValue, int nAgentFlag, int nMOMTFlag, int nPriority, String strExpireTime, String strScheduleTime, int nReportFlag, int nTP_pid, int nTP_udhi, int nMessageCoding, int nMessageType, int nContentLength, byte[] abyte0) throws SGIP_Exception {
        super(lSrcNodeID);
        this.NodeID = 0L;
        this.ContentLength = 0;
        this.NodeID = lSrcNodeID;
        this.SPNumber = strSPNumber;
        this.ChargeNumber = strChargeNumber;
        this.UserCount = 0;

        for(int i3 = 0; i3 < strUserNumber.length(); ++i3) {
            char c = strUserNumber.charAt(i3);
            if (c != ',' && (c < '0' || c > '9')) {
                throw new SGIP_Exception("Invalid Mobile Number");
            }

            if (c == ',') {
                ++this.UserCount;
            }
        }

        ++this.UserCount;
        if (this.UserCount > 100) {
            throw new SGIP_Exception("Too many users!");
        } else {
            this.usernumber = new String[this.UserCount];
            boolean flag = false;
            int l3 = 0;

            for(int j3 = 0; j3 < this.UserCount - 1; ++j3) {
                int k3 = l3;
                l3 = strUserNumber.indexOf(44, l3 + 1);
                this.usernumber[j3] = strUserNumber.substring(k3, l3);
                ++l3;
            }

            this.usernumber[this.UserCount - 1] = strUserNumber.substring(l3, strUserNumber.length());
            this.CorpId = strCorpID;
            this.ServiceType = strServiceType;
            this.FeeType = nFeeType;
            this.FeeValue = strFeeValue;
            this.GivenValue = strGivenValue;
            this.AgentFlag = nAgentFlag;
            this.MOrelatetoMTFlag = nMOMTFlag;
            this.Priority = nPriority;
            this.ExpireTime = strExpireTime;
            this.ScheduleTime = strScheduleTime;
            this.ReportFlag = nReportFlag;
            this.TP_pid = nTP_pid;
            this.TP_udhi = nTP_udhi;
            this.MessageCoding = nMessageCoding;
            this.MessageType = nMessageType;
            this.ContentLength = nContentLength;
            this.MessageLength = this.ContentLength;
            this.BinContent = new byte[this.ContentLength];
            SGIP_Common.BytesCopy(abyte0, this.BinContent, 0, this.ContentLength - 1, 0);
            this.SetBinBody();
        }
    }

    private void SetMsgBody() throws SGIP_Exception {
        byte[] messageBytes = (byte[])null;

        try {
            if (this.MessageCoding == 8) {
                messageBytes = this.MessageContent.getBytes("UTF-16BE");
                this.ContentLength = messageBytes.length;
            } else if (this.MessageCoding == 15) {
                messageBytes = this.MessageContent.getBytes("GBK");
                this.ContentLength = messageBytes.length;
            } else {
                messageBytes = this.MessageContent.getBytes("ISO8859-1");
                this.ContentLength = this.MessageContent.length();
            }
        } catch (Exception var5) {
            System.out.println("chinese code error:" + var5.toString());
        }

        this.TotalLength = 143 + 21 * this.UserCount + this.ContentLength;
        this.sgipHead.SetTotalLength(this.TotalLength);
        this.sgipHead.SetCommandID(3);
        SGIP_Command.seq = new SGIP_SequenceNo();
        SGIP_Command.seq.setNodeId(this.NodeID);
        SGIP_SequenceNo.computeSequence();
        this.sgipHead.SetSeq_1(SGIP_Command.seq.getGlobalSeq_1());
        this.sgipHead.SetSeq_2(SGIP_Command.seq.getGlobalSeq_2());
        this.sgipHead.SetSeq_3(SGIP_Command.seq.getGlobalSeq_3());
        this.byteBody = ByteBuffer.allocate(this.TotalLength - 20);
        if (this.SPNumber.length() > 21) {
            throw new SGIP_Exception("SPNumber Longer than 21 bytes:" + this.SPNumber);
        } else {
            this.byteBody.position(0);
            this.byteBody.put(this.SPNumber.getBytes());
            if (this.ChargeNumber.length() > 21) {
                throw new SGIP_Exception("ChargeNumber longer than 21 bytes:" + this.ChargeNumber);
            } else {
                this.byteBody.position(21);
                this.byteBody.put(this.ChargeNumber.getBytes());
                if (this.UserCount <= 100 && this.UserCount >= 1) {
                    this.byteBody.position(42);
                    this.byteBody.put((byte)this.UserCount);
                    int i = 43;

                    for(int j = 0; j < this.UserCount; ++j) {
                        String s = new String(this.usernumber[j]);
                        if (s.length() > 21) {
                            throw new SGIP_Exception("UserNumber longer than 21 bytes:" + s);
                        }

                        this.byteBody.position(i);
                        this.byteBody.put(s.getBytes());
                        i += 21;
                    }

                    if (this.CorpId.length() > 5) {
                        throw new SGIP_Exception("CorpId longer than 5 bytes:" + this.CorpId);
                    } else {
                        this.byteBody.position(i);
                        this.byteBody.put(this.CorpId.getBytes());
                        i += 5;
                        if (this.ServiceType.length() > 10) {
                            throw new SGIP_Exception("ServiceType longer than 10 bytes:" + this.ServiceType);
                        } else {
                            this.byteBody.position(i);
                            this.byteBody.put(this.ServiceType.getBytes());
                            i += 10;
                            this.byteBody.position(i);
                            this.byteBody.put((byte)this.FeeType);
                            ++i;
                            if (this.FeeValue.length() > 6) {
                                throw new SGIP_Exception("FeeValue longer than 6 bytes:" + this.FeeValue);
                            } else {
                                this.byteBody.position(i);
                                this.byteBody.put(this.FeeValue.getBytes());
                                i += 6;
                                if (this.GivenValue.length() > 6) {
                                    throw new SGIP_Exception("GivenValue longer than 6 bytes:" + this.GivenValue);
                                } else {
                                    this.byteBody.position(i);
                                    this.byteBody.put(this.GivenValue.getBytes());
                                    i += 6;
                                    this.byteBody.position(i);
                                    this.byteBody.put((byte)this.AgentFlag);
                                    ++i;
                                    this.byteBody.position(i);
                                    this.byteBody.put((byte)this.MOrelatetoMTFlag);
                                    ++i;
                                    this.byteBody.position(i);
                                    this.byteBody.put((byte)this.Priority);
                                    ++i;
                                    if (this.ExpireTime.length() > 16) {
                                        throw new SGIP_Exception("EXpireTime longer than 16 bytes:" + this.ExpireTime);
                                    } else {
                                        this.byteBody.position(i);
                                        this.byteBody.put(this.ExpireTime.getBytes());
                                        i += 16;
                                        if (this.ScheduleTime.length() > 16) {
                                            throw new SGIP_Exception("ScheduleTime longer than 16 bytes:" + this.ScheduleTime);
                                        } else {
                                            this.byteBody.position(i);
                                            this.byteBody.put(this.ScheduleTime.getBytes());
                                            i += 16;
                                            this.byteBody.position(i);
                                            this.byteBody.put((byte)this.ReportFlag);
                                            ++i;
                                            this.byteBody.position(i);
                                            this.byteBody.put((byte)this.TP_pid);
                                            ++i;
                                            this.byteBody.position(i);
                                            this.byteBody.put((byte)this.TP_udhi);
                                            ++i;
                                            this.byteBody.position(i);
                                            this.byteBody.put((byte)this.MessageCoding);
                                            ++i;
                                            this.byteBody.position(i);
                                            this.byteBody.put((byte)this.MessageType);
                                            ++i;
                                            this.byteBody.position(i);
                                            this.byteBody.putInt(this.ContentLength);
                                            i += 4;
                                            this.byteBody.position(i);
                                            this.byteBody.put(messageBytes);
                                            if (this.linkId != null && !this.linkId.equals("")) {
                                                i += this.ContentLength;
                                                this.byteBody.position(i);
                                                this.byteBody.put(this.linkId.getBytes());
                                            }

                                            this.byteBody.position(i + 8);
                                            this.byteBody.flip();
                                            byte[] tempbytes = this.byteBody.array();
                                            this.byteBody = ByteBuffer.wrap(tempbytes);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    throw new SGIP_Exception("UserCount should between 1 and 100:" + this.UserCount);
                }
            }
        }
    }

    private void SetBinBody() throws SGIP_Exception {
        this.TotalLength = 143 + 21 * this.UserCount + this.ContentLength;
        if (SGIP_Command.seq == null) {
            SGIP_Command.seq = new SGIP_SequenceNo();
            SGIP_Command.seq.setNodeId(this.NodeID);
            SGIP_SequenceNo.computeSequence();
        }

        this.sgipHead.SetTotalLength(this.TotalLength);
        this.sgipHead.SetCommandID(3);
        this.sgipHead.SetSeq_1(SGIP_Command.seq.getGlobalSeq_1());
        this.sgipHead.SetSeq_2(SGIP_Command.seq.getGlobalSeq_2());
        this.sgipHead.SetSeq_3(SGIP_Command.seq.getGlobalSeq_3());
        this.byteBody = ByteBuffer.allocate(this.TotalLength - 20);
        if (this.SPNumber.length() > 21) {
            throw new SGIP_Exception("SPNumber Longer than 21 bytes:" + this.SPNumber);
        } else {
            this.byteBody.position(0);
            this.byteBody.put(this.SPNumber.getBytes());
            if (this.ChargeNumber.length() > 21) {
                throw new SGIP_Exception("ChargeNumber longer than 21 bytes:" + this.ChargeNumber);
            } else {
                this.byteBody.position(21);
                this.byteBody.put(this.ChargeNumber.getBytes());
                if (this.UserCount <= 100 && this.UserCount >= 1) {
                    this.byteBody.position(42);
                    this.byteBody.put((byte)this.UserCount);
                    int i = 43;

                    for(int j = 0; j < this.UserCount; ++j) {
                        String s = new String(this.usernumber[j]);
                        if (s.length() > 21) {
                            throw new SGIP_Exception("UserNumber longer than 21 bytes:" + s);
                        }

                        this.byteBody.position(i);
                        this.byteBody.put(s.getBytes());
                        i += 21;
                    }

                    if (this.CorpId.length() > 5) {
                        throw new SGIP_Exception("CorpId longer than 5 bytes:" + this.CorpId);
                    } else {
                        this.byteBody.position(i);
                        this.byteBody.put(this.CorpId.getBytes());
                        i += 5;
                        if (this.ServiceType.length() > 10) {
                            throw new SGIP_Exception("ServiceType longer than 10 bytes:" + this.ServiceType);
                        } else {
                            this.byteBody.position(i);
                            this.byteBody.put(this.ServiceType.getBytes());
                            i += 10;
                            this.byteBody.position(i);
                            this.byteBody.put((byte)this.FeeType);
                            ++i;
                            if (this.FeeValue.length() > 6) {
                                throw new SGIP_Exception("FeeValue longer than 6 bytes:" + this.FeeValue);
                            } else {
                                this.byteBody.position(i);
                                this.byteBody.put(this.FeeValue.getBytes());
                                i += 6;
                                if (this.GivenValue.length() > 6) {
                                    throw new SGIP_Exception("GivenValue longer than 6 bytes:" + this.GivenValue);
                                } else {
                                    this.byteBody.position(i);
                                    this.byteBody.put(this.GivenValue.getBytes());
                                    i += 6;
                                    this.byteBody.position(i);
                                    this.byteBody.put((byte)this.AgentFlag);
                                    ++i;
                                    this.byteBody.position(i);
                                    this.byteBody.put((byte)this.MOrelatetoMTFlag);
                                    ++i;
                                    this.byteBody.position(i);
                                    this.byteBody.put((byte)this.Priority);
                                    ++i;
                                    if (this.ExpireTime.length() > 16) {
                                        throw new SGIP_Exception("EXpireTime longer than 16 bytes:" + this.ExpireTime);
                                    } else {
                                        this.byteBody.position(i);
                                        this.byteBody.put(this.ExpireTime.getBytes());
                                        i += 16;
                                        if (this.ScheduleTime.length() > 16) {
                                            throw new SGIP_Exception("ScheduleTime longer than 16 bytes:" + this.ScheduleTime);
                                        } else {
                                            this.byteBody.position(i);
                                            this.byteBody.put(this.ScheduleTime.getBytes());
                                            i += 16;
                                            this.byteBody.position(i);
                                            this.byteBody.put((byte)this.ReportFlag);
                                            ++i;
                                            this.byteBody.position(i);
                                            this.byteBody.put((byte)this.TP_pid);
                                            ++i;
                                            this.byteBody.position(i);
                                            this.byteBody.put((byte)this.TP_udhi);
                                            ++i;
                                            this.byteBody.position(i);
                                            this.byteBody.put((byte)this.MessageCoding);
                                            ++i;
                                            this.byteBody.position(i);
                                            this.byteBody.put((byte)this.MessageType);
                                            ++i;
                                            this.byteBody.position(i);
                                            this.byteBody.putInt(this.ContentLength);
                                            i += 4;
                                            this.byteBody.position(i);
                                            this.byteBody.put(this.BinContent);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    throw new SGIP_Exception("UserCount should between 1 and 100:" + this.UserCount);
                }
            }
        }
    }

    public int readbody() {
        this.TotalLength = this.byteBody.limit() + 20;
        byte[] b = new byte[21];
        this.byteBody.clear();
        this.byteBody.get(b, 0, 21);
        this.SPNumber = (new String(b)).trim();
        this.byteBody.get(b, 0, 21);
        this.ChargeNumber = (new String(b)).trim();
        this.UserCount = this.byteBody.get(42);
        this.usernumber = new String[this.UserCount];
        this.byteBody.position(43);

        for(int i = 0; i < this.UserCount; ++i) {
            b = new byte[21];
            this.byteBody.get(b, 0, 21);
            this.usernumber[i] = (new String(b)).trim();
        }

        this.byteBody.position(43 + this.UserCount * 21 + 15);
        this.FeeType = this.byteBody.get();
        b = new byte[6];
        this.byteBody.get(b, 0, 6);
        this.FeeValue = (new String(b)).trim();
        this.byteBody.position(43 + this.UserCount * 21 + 15 + 48);
        this.ReportFlag = this.byteBody.get();
        this.byteBody.position(43 + this.UserCount * 21 + 15 + 51);
        this.MessageCoding = this.byteBody.get();
        this.byteBody.position(43 + this.UserCount * 21 + 15 + 53);
        this.MessageLength = this.byteBody.getInt();
        b = new byte[this.MessageLength];
        this.byteBody.get(b, 0, b.length);
        this.MessageContent = (new String(b)).trim();
        b = new byte[8];
        this.byteBody.get(b, 0, 8);
        this.linkId = (new String(b)).trim();
        return 0;
    }

    public int hashCode() {
        return this.getSeqno_2() << 2 + this.getSeqno_3();
    }

    public boolean equals(Object o) {
        return o instanceof SGIP_Command && this.getSeqno_1() == ((SGIP_Command)o).getSeqno_1() && this.getSeqno_2() == ((SGIP_Command)o).getSeqno_2() && this.getSeqno_3() == ((SGIP_Command)o).getSeqno_3();
    }

    public String getLinkId() {
        return this.linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String toString() {
        String userNum = "";

        for(int i = 0; i < this.usernumber.length; ++i) {
            userNum = userNum + this.usernumber[i];
        }

        return "SGIP_Submit:seq3=" + super.getSeqno_3() + " UserCount=" + this.UserCount + " Usernumber={" + userNum + "}" + " MessageContent=" + this.MessageContent + " SPNumber=" + this.SPNumber + " ChargeNumber=" + this.ChargeNumber + " NodeID=" + this.NodeID + " ReportFlag=" + this.ReportFlag + " CorpId=" + this.CorpId + " ServiceType=" + this.ServiceType + " FeeType=" + this.FeeType + " FeeValue=" + this.FeeValue + " TP_pid=" + this.TP_pid + " TP_udhi=" + this.TP_udhi + " MessageCoding=" + this.MessageCoding + " MessageType=" + this.MessageType + " MessageLength=" + this.MessageLength + " linkId=" + this.linkId;
    }

    public String getMessageContent() {
        return this.MessageContent;
    }
}

