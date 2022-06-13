package telegram;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.validator.ValidatorException;

import org.primefaces.event.RowEditEvent;

@ManagedBean
@SessionScoped
public class EmployeeBean {

	// Member Variables
	private ChatFormTemplateDTO chatFormTemplateDTO = new ChatFormTemplateDTO();
	private String messageTemplate = "";
	private String errorMessageTemplate = "";
	private String requestTypeID;
	private int position;
	private List<String> replyRequiredList;
	private ReplyDTO replyDTO;
	private List<ChatFormDTO> chatFormList;
	private ChatFormDTO chat;
	private boolean replyRequired;
	private String replyR = "";
	private String replyE = "";
	private boolean replyErrorRequired;
	private Map<String, String> replyTypeList;
	private String chatFormTemplateName;
	private int chatFormID = 0;
	
	@PostConstruct
	public void init() {
		replyRequiredList = new ArrayList();
		replyRequiredList.add("True");
		replyRequiredList.add("False");
		replyTypeList = new HashMap();
		replyTypeList.put("None", "None");
		replyTypeList.put("String", "String");
		replyTypeList.put("Integer", "Integer");
		replyTypeList.put("Date", "Date");
		replyTypeList.put("Document", "Document");
	}

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

	public void setRequestTypeID(String requestTypeID) {
		this.requestTypeID = requestTypeID;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public List<String> getReplyRequiredList() {
		return replyRequiredList;
	}

	public void setReplyRequiredList(List<String> replyRequiredList) {
		this.replyRequiredList = replyRequiredList;
	}

	public ReplyDTO getReplyDTO() {
		return replyDTO;
	}

	public void setReplyDTO(ReplyDTO replyDTO) {
		this.replyDTO = replyDTO;
	}

	public ChatFormTemplateDTO getChatFormTemplateDTO() {
		return chatFormTemplateDTO;
	}

	public void setChatFormTemplateDTO(ChatFormTemplateDTO chatFormTemplateDTO) {
		this.chatFormTemplateDTO = chatFormTemplateDTO;
	}

	public List<ChatFormDTO> getChatFormList() {
		return chatFormList;
	}

	public String addAction() {
		chat = new ChatFormDTO();
		chat.setMessageTemplate(messageTemplate);
		chat.setErrorMessageTemplate(errorMessageTemplate);
		chat.setReplyRequired(replyRequired);
		if (replyRequired == false) {
			chat.setRequestTypeID("None");
		} else {
			chat.setRequestTypeID(requestTypeID);
		}
		chat.setErrorReplyRequired(replyErrorRequired);

		chatFormList = new ArrayList();
		chatFormList = chatFormTemplateDTO.getChatFormDTO();
		chatFormList.add(chat);
		messageTemplate = null;
		errorMessageTemplate = null;
		return null;
	}
	
	public void searchReply() {

	}


	public void onEdit(ChatFormDTO chat) {
		FacesMessage msg = new FacesMessage("Item Edited");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		chat.setMessageTemplate(messageTemplate);
		chat.setErrorMessageTemplate(errorMessageTemplate);
		chat.setReplyRequired(replyRequired);
		if (replyRequired == false) {
			chat.setRequestTypeID("None");
		} else {
			chat.setRequestTypeID(requestTypeID);
		}
		chat.setErrorReplyRequired(replyErrorRequired);
		
	}

	public void onCancel(ChatFormDTO chat) {
		System.out.println(chat.getMessageTemplate());
		FacesMessage msg = new FacesMessage("Item Cancelled");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		chatFormList.remove(chat);
		for (ChatFormDTO e : chatFormList) {
			System.out.println(e.getMessageTemplate());
		}
	}

	public ChatFormTemplateDTO getChatFormDTO() {
		return chatFormTemplateDTO;
	}

	public boolean isReplyRequired() {
		return replyRequired;
	}

	public void setReplyRequired(boolean replyRequired) {
		this.replyRequired = replyRequired;
	}

	public void setChatFormDTO(ChatFormTemplateDTO chatFormDTO) {
		this.chatFormTemplateDTO = chatFormTemplateDTO;
	}

	// Validate Email
	public void validateEmail(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
		String emailStr = (String) value;
		if (-1 == emailStr.indexOf("@")) {
			FacesMessage message = new FacesMessage("Email Address is invalid");
			throw new ValidatorException(message);
		}
	}

	// Action Methods
	public String storeTemplate() throws Exception {
		boolean stored = true;
		FacesMessage message = null;
		String outcome = null;
		if (stored) {
			message = new FacesMessage("Employee Information is stored Successfully.");
			MySQLAccess ms = new MySQLAccess();
			chatFormID = ms.insertRecord(chatFormList,chatFormTemplateName);
			outcome = "success.xhtml";
		} else {
			message = new FacesMessage("Employee Information is NOT stored Successfully.");
			outcome = "hello-world.xhtml";
		}
		FacesContext.getCurrentInstance().addMessage(null, message);
		return outcome;
	}
	

	public String confirm() {
		return "confirm.xhtml";
	}

	public void onReplyChange() {
		if (replyR.equalsIgnoreCase("True")) {
			replyRequired = true;
		} else {
			replyRequired = false;
		}
		if (replyE.equalsIgnoreCase("True")) {
			replyErrorRequired = true;
		} else {
			replyErrorRequired = false;
		}

	}

	public String getReplyR() {
		return replyR;
	}

	public void setReplyR(String replyR) {
		this.replyR = replyR;
	}

	public String getReplyE() {
		return replyE;
	}

	public void setReplyE(String replyE) {
		this.replyE = replyE;
	}

	public boolean isReplyErrorRequired() {
		return replyErrorRequired;
	}

	public void setReplyErrorRequired(boolean replyErrorRequired) {
		this.replyErrorRequired = replyErrorRequired;
	}

	public Map<String, String> getReplyTypeList() {
		return replyTypeList;
	}

	public void setReplyTypeList(Map<String, String> replyTypeList) {
		this.replyTypeList = replyTypeList;
	}

	public ChatFormDTO getChat() {
		return chat;
	}

	public void setChat(ChatFormDTO chat) {
		this.chat = chat;
	}

	public String getChatFormTemplateName() {
		return chatFormTemplateName;
	}

	public void setChatFormTemplateName(String chatFormTemplateName) {
		this.chatFormTemplateName = chatFormTemplateName;
	}

	public void setChatFormList(List<ChatFormDTO> chatFormList) {
		this.chatFormList = chatFormList;
	}

	public int getChatFormID() {
		return chatFormID;
	}

	public void setChatFormID(int chatformID) {
		this.chatFormID = chatformID;
	}

	
}