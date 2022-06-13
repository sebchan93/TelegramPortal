package telegram;

public class ChatFormDTO {

	
	private String messageTemplate;
	private String errorMessageTemplate;
	private String requestTypeID;
	private int position;
	private boolean replyRequired;
	private boolean errorReplyRequired;
	private ReplyDTO replyDTO = new ReplyDTO();
	
	public String getMessageTemplate() {
		return messageTemplate;
	}
	public void setMessageTemplate(String messageTemplate) {
		this.messageTemplate = messageTemplate;
	}
	public String getErrorMessageTemplate() {
		return errorMessageTemplate;
	}
	public void setErrorMessageTemplate(String errorMessageTemplate) {
		this.errorMessageTemplate = errorMessageTemplate;
	}
	public String getRequestTypeID() {
		return requestTypeID;
	}
	public void setRequestTypeID(String requestTypeID2) {
		this.requestTypeID = requestTypeID2;
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
	public ReplyDTO getReplyDTO() {
		return replyDTO;
	}
	public void setReplyDTO(ReplyDTO replyDTO) {
		this.replyDTO = replyDTO;
	}
	public boolean isErrorReplyRequired() {
		return errorReplyRequired;
	}
	public void setErrorReplyRequired(boolean errorReplyRequired) {
		this.errorReplyRequired = errorReplyRequired;
	}
	
	
}
