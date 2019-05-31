package com.dto;

import com.thoughtworks.xstream.XStream;

public class ReplyTextMsg extends ReplyBaseMessage {

    private String Content;
    
    

    public ReplyTextMsg(String FromUserName,String ToUserName,String MsgType,String content) {
		super();
		Content = content;
		this.FromUserName=FromUserName;
		this.ToUserName=ToUserName;
		this.MsgType=MsgType;
		this.setCreateTime();
	}

	public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
    /**
     * 将对象转换为XML
     * @return
     */
    public String Msg2Xml(){
        XStream xstream=new XStream();
        xstream.alias("xml", this.getClass());
        return xstream.toXML(this);
    }
}
