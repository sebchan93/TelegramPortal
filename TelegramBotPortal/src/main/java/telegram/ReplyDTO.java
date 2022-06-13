package telegram;

public class ReplyDTO {
	
	private String replyID;
	private String memberReply;
	private String memberReplyDate;
	private String userName;
	private int userID;
	private int formID;
	private String messageTemplate;
	
	
	public String getReplyID() {
		return replyID;
	}
	public void setReplyID(String replyID) {
		this.replyID = replyID;
	}
	public String getMemberReply() {
		return memberReply;
	}
	public void setMemberReply(String memberReply) {
		this.memberReply = memberReply;
	}
	public String getMemberReplyDate() {
		return memberReplyDate;
	}
	public void setMemberReplyDate(String memberReplyDate) {
		this.memberReplyDate = memberReplyDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getFormID() {
		return formID;
	}
	public void setFormID(int formID) {
		this.formID = formID;
	}
	public String getMessageTemplate() {
		return messageTemplate;
	}
	public void setMessageTemplate(String messageTemplate) {
		this.messageTemplate = messageTemplate;
	}

	
	
}
