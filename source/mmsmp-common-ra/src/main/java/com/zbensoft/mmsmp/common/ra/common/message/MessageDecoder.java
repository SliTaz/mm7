package com.zbensoft.mmsmp.common.ra.common.message;

import org.apache.log4j.Logger;

public class MessageDecoder {
	private static final Logger log = Logger.getLogger(MessageDecoder.class);

	public static IMessage getMessage(String theMessage) throws DecodeMessageException {
		try {
			if (theMessage == null) {
				log.warn("The Message is NULL!");
				return null;
			}

			int pos = theMessage.indexOf("\n");
			String messageTypeStr = theMessage.substring(0, pos);
			theMessage = theMessage.substring(pos + 1);

			if (!messageTypeStr.startsWith("MessageType:")) {
				throw new DecodeMessageException("Invalid Message Format!", null);
			}
			pos = messageTypeStr.indexOf(":");
			String messageType = messageTypeStr.substring(pos + 1);

			if (messageType.equals(MessageType.MO_SM_MESSAGE.getMessageType())) {
				MO_SMMessage result = new MO_SMMessage();
				result.decodeString(theMessage);

				return result;
			}
			if (messageType.equals(MessageType.MT_MM_MESSAGE.getMessageType())) {
				MT_MMMessage result = new MT_MMMessage();
				result.decodeString(theMessage);

				return result;
			}
			if (messageType.equals(MessageType.USER_ONDEMAND_MESSAGE.getMessageType())) {
				UserOnDemandMessage result = new UserOnDemandMessage();
				result.decodeString(theMessage);

				return result;
			}
			if (messageType.equals(MessageType.USER_ORDER_MESSAGE.getMessageType())) {
				UserOrderMessage result = new UserOrderMessage();
				result.decodeString(theMessage);

				return result;
			}
			if (messageType.equals(MessageType.USER_ORDERUSE_MESSAGE.getMessageType())) {
				UserOrderUseMessage result = new UserOrderUseMessage();
				result.decodeString(theMessage);

				return result;
			}
			if (messageType.equals(MessageType.MT_SM_MESSAGE.getMessageType())) {
				MT_SMMessage result = new MT_SMMessage();
				result.decodeString(theMessage);

				return result;
			}
			if (messageType.equals(MessageType.MO_MM_MESSAGE.getMessageType())) {
				MO_MMMessage result = new MO_MMMessage();
				result.decodeString(theMessage);

				return result;
			}
			if (messageType.equals(MessageType.MT_WAPPUSH_MESSAGE.getMessageType())) {
				MT_WapPushMessage result = new MT_WapPushMessage();
				result.decodeString(theMessage);

				return result;
			}
			if (messageType.equals(MessageType.USER_CANCELORDER_MESSAGE.getMessageType())) {
				UserCancelOrderMessage result = new UserCancelOrderMessage();
				result.decodeString(theMessage);

				return result;
			}
			if (messageType.equals(MessageType.MO_MM_DR_MESSAGE.getMessageType())) {
				MO_MM_DRMessage result = new MO_MM_DRMessage();
				result.decodeString(theMessage);

				return result;
			}

			throw new DecodeMessageException("unknown MessageType: " + messageType, null);
		} catch (Exception e) {
			throw new DecodeMessageException("decode Message Exception", e);
		}
	}
}
