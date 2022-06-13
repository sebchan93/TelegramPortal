package telegram;

import java.util.ArrayList;
import java.util.List;

public class ChatFormTemplateDTO {

	private String chatFormTemplateName;
	private String userID;
	private List<ChatFormDTO> chatFormDTO = new ArrayList();
	
	public String getChatFormTemplateName() {
		return chatFormTemplateName;
	}
	public void setChatFormTemplateName(String chatFormTemplateName) {
		this.chatFormTemplateName = chatFormTemplateName;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public List<ChatFormDTO> getChatFormDTO() {
		return chatFormDTO;
	}
	public void setChatFormDTO(List<ChatFormDTO> chatFormDTO) {
		this.chatFormDTO = chatFormDTO;
	}

	
	
	
}
