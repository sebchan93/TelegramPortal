package telegram;

public class ChatFormDetailsDTO {
	
	private String userName;
	private int userID;
	private int formID;
	private String messageTemplate;
	int position;
	boolean replyRequired;
	String chatFormTemplateName;
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
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public boolean isReplyRequired() {
		return replyRequired;
	}
	public void setReplyRequired(boolean replyRequired) {
		this.replyRequired = replyRequired;
	}
	public String getChatFormTemplateName() {
		return chatFormTemplateName;
	}
	public void setChatFormTemplateName(String chatFormTemplateName) {
		this.chatFormTemplateName = chatFormTemplateName;
	}
	
	
}
