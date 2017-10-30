package org.firas.weixin.input;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@XmlRootElement(name = "xml")
public class ReceiveMessageInput {

    private String toUserName;
    @XmlElement(name = "ToUserName")
    public String getToUserName() {
        return toUserName;
    }
    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    private String fromUserName;
    @XmlElement(name = "FromUserName")
    public String getFromUserName() {
        return fromUserName;
    }
    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    private String createTime;
    @XmlElement(name = "CreateTime")
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    private String msgType;
    @XmlElement(name = "MsgType")
    public String getMsgType() {
        return msgType;
    }
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    private String content;
    @XmlElement(name = "Content")
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    private String msgId;
    @XmlElement(name = "MsgId")
    public String getMsgId() {
        return msgId;
    }
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public boolean isTextMessage() {
        return "text".equals(msgType) &&
                null != content && !content.isEmpty() &&
                null != fromUserName && !fromUserName.isEmpty() &&
                null != toUserName && !toUserName.isEmpty();
    }
}
