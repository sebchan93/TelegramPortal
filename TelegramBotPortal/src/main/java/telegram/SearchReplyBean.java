package telegram;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class SearchReplyBean {

	private String username;
	private String formID;
	private String userID;

	private List<ReplyDTO> replyList;
	private List <ChatFormDetailsDTO> detailList;

	@PostConstruct
	public void init() {
		username = "";
		formID = "";
		userID = "";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFormID() {
		return formID;
	}

	public void setFormID(String formID) {
		this.formID = formID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public void searchReply() throws Exception {

		MySQLAccess ms = new MySQLAccess();
		replyList = ms.getReplyDetails(username, formID, userID);
	}
	
	public void searchFormTemplate() throws Exception {

		MySQLAccess ms = new MySQLAccess();
		detailList = ms.getFormTemplate( formID, userID);
	}

	public List<ReplyDTO> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<ReplyDTO> replyList) {
		this.replyList = replyList;
	}

	public List<ChatFormDetailsDTO> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<ChatFormDetailsDTO> detailList) {
		this.detailList = detailList;
	}

}
