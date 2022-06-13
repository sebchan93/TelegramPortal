package telegram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class CreateFAQTopicBean {
	private String faqTopics;
	private List <FaqTopicDTO> faqTopicList;
	private String faqTopicInput;
	private String faqQuestion;
	private String faqAnswer;
	private String faqTopicKeyword;
	private String createFaqTopicKeyword;
	private List <FaqDTO> faqList;
	private Map <String,Integer> faqTopic = new HashMap();
	private Map <String,Integer> faqTopicQuestion = new HashMap();
	private String faqTopicsSearch;
	private String faqKeywordsSearch;
	private String faqKeywordsInsert = "";
	

	public Map <String,Integer> getFaqTopicFromDB() throws Exception {
		MySQLAccess ms = new MySQLAccess();
		List <FaqTopicDTO> faqList =  ms.getFaqTopicDetails();
		for(FaqTopicDTO e: faqList) {
			faqTopic.put(e.getFaqTopicName(),e.getFaqID());
		
		}
		return faqTopic;
	}
	
	public List <FaqDTO> getFaq() throws Exception {
	//	List <FaqDTO> faqList = new ArrayList();
		if(!faqTopicsSearch.isEmpty()) {
		MySQLAccess ms = new MySQLAccess();
		faqList =  ms.getFaqDetails(faqTopicsSearch);
		}
		return faqList;
	}
	
	public List <FaqDTO> getFaqQuestionKeywordList() throws Exception {
	//	List <FaqDTO> faqList = new ArrayList();
		if(!faqKeywordsSearch.isEmpty()) {
		MySQLAccess ms = new MySQLAccess();
		faqList =  ms.getFaqKeywords(faqKeywordsSearch);
		}
		return faqList;
	}
	
	
	public Map <String,Integer>  getFaqTopicQns() throws Exception {
		MySQLAccess ms = new MySQLAccess();
		if(faqTopicKeyword == null || faqTopicKeyword.isEmpty()) {
			
			faqTopicKeyword = "0";
		}
		System.out.println(faqTopicKeyword);
		List <FaqDTO> faqList =  ms.getFaqDetails(faqTopicKeyword);
		for(FaqDTO e: faqList) {
			faqTopicQuestion.put(e.getFaqQuestion(),e.getFaqID());
		
		}
		return faqTopicQuestion;
	}
	
	
	
	public Map <String,Integer>  getCreateFaqTopicQns() throws Exception {
		MySQLAccess ms = new MySQLAccess();
		if(createFaqTopicKeyword == null || createFaqTopicKeyword.isEmpty()) {
			
			createFaqTopicKeyword = "0";
		}
		System.out.println(createFaqTopicKeyword);
		List <FaqDTO> faqList =  ms.getFaqDetails(createFaqTopicKeyword);
		for(FaqDTO e: faqList) {
			faqTopicQuestion.put(e.getFaqQuestion(),e.getFaqTemplateID());
		
		}
		return faqTopicQuestion;
	}
	
	
	public List <FaqDTO> getFaqKeyword() throws Exception {
	//	List <FaqDTO> faqList = new ArrayList();
		if(!faqTopicsSearch.isEmpty()) {
		MySQLAccess ms = new MySQLAccess();
		faqList =  ms.getFaqDetails(faqTopicsSearch);
		}
		return faqList;
	}
	
	
	public String createFaqTemplate() throws Exception {
		String outcome = "createFAQ.xhtml";
		FacesMessage message = null;
		if(!faqQuestion.isEmpty()&& !faqAnswer.isEmpty() && !faqTopicInput.isEmpty()) {
		MySQLAccess ms = new MySQLAccess();
		ms.insertFaqRecord(faqQuestion,faqAnswer,faqTopicInput);
		boolean exist = false;
		System.out.println(faqTopicInput);
		/*
		 * if(list != null && !list.isEmpty()) { for(FaqTopicDTO e : list) {
		 * if(e.getFaqTopicName().equalsIgnoreCase(faqTopics)) {
		 * System.out.println(e.getFaqTopicName()); exist = true; break; } } }
		 */
		if(exist) {
			message = new FacesMessage("FAQ topic existed");
		FacesContext.getCurrentInstance().addMessage(null, message);
		}else {
		
		message = new FacesMessage("FAQ topic created");
		FacesContext.getCurrentInstance().addMessage(null, message);;
		}
	}
		return outcome;
	}
	
	
	public String createFaqKeyword() throws Exception {
		String outcome = "createFAQKeywords.xhtml";
		FacesMessage message = null;
		if(!faqTopicKeyword.isEmpty()&& !faqKeywordsInsert.isEmpty()) {
		MySQLAccess ms = new MySQLAccess();
		ms.insertFaqKeywordRecord(faqTopicKeyword,faqKeywordsInsert);
		boolean exist = false;
		System.out.println(faqTopicKeyword + " "+faqKeywordsInsert);
		/*
		 * if(list != null && !list.isEmpty()) { for(FaqTopicDTO e : list) {
		 * if(e.getFaqTopicName().equalsIgnoreCase(faqTopics)) {
		 * System.out.println(e.getFaqTopicName()); exist = true; break; } } }
		 */
		if(exist) {
			message = new FacesMessage("FAQ keyword existed");
		FacesContext.getCurrentInstance().addMessage(null, message);
		}else {
		
		message = new FacesMessage("FAQ keyword created");
		FacesContext.getCurrentInstance().addMessage(null, message);;
		}
	}
		faqTopicQuestion.clear();
		return outcome;
	}
	
	public String getFaqTopics() {
		return faqTopics;
	}

	public void setFaqTopics(String faqTopics) {
		this.faqTopics = faqTopics;
	}
	
	public String createTopic() throws Exception {
	
		FacesMessage message = null;
		MySQLAccess ms = new MySQLAccess();
		List <FaqTopicDTO> list =  ms.getFaqTopicDetails();
		boolean exist = false;
		String outcome = "createFAQTopic.xhtml";
		System.out.println(faqTopics);
		if(list != null && !list.isEmpty()) {
			for(FaqTopicDTO e : list) {
				if(e.getFaqTopicName().equalsIgnoreCase(faqTopics)) {
				System.out.println(e.getFaqTopicName());
				exist = true;
				break;
			}
			}
		}
		if(exist) {
			message = new FacesMessage("FAQ topic existed");
		FacesContext.getCurrentInstance().addMessage(null, message);
		}else {
		
		message = new FacesMessage("FAQ topic created");
		FacesContext.getCurrentInstance().addMessage(null, message);
		ms.insertFaqTopicRecord(faqTopics);
		}
		return outcome;
	}
	
	public void searchFaqTopics() throws Exception {
		MySQLAccess ms = new MySQLAccess();
		faqTopicList =  ms.getFaqTopicDetails();
		
	}

	public List<FaqTopicDTO> getFaqTopicList() {
		return faqTopicList;
	}

	public void setFaqTopicList(List<FaqTopicDTO> faqTopicList) {
		this.faqTopicList = faqTopicList;
	}



	public Map<String, Integer> getFaqTopic() {
		return faqTopic;
	}

	public void setFaqTopic(Map<String, Integer> faqTopic) {
		this.faqTopic = faqTopic;
	}

	public String getFaqTopicsSearch() {
		return faqTopicsSearch;
	}

	public void setFaqTopicsSearch(String faqTopicsSearch) {
		this.faqTopicsSearch = faqTopicsSearch;
	}

	public String getFaqTopicInput() {
		return faqTopicInput;
	}


	public void setFaqTopicInput(String faqTopicInput) {
		this.faqTopicInput = faqTopicInput;
	}

	public List<FaqDTO> getFaqList() {
		return faqList;
	}

	public void setFaqList(List<FaqDTO> faqList) {
		this.faqList = faqList;
	}

	public String getFaqQuestion() {
		return faqQuestion;
	}

	public void setFaqQuestion(String faqQuestion) {
		this.faqQuestion = faqQuestion;
	}

	public String getFaqAnswer() {
		return faqAnswer;
	}

	public void setFaqAnswer(String faqAnswer) {
		this.faqAnswer = faqAnswer;
	}

	public String getFaqTopicKeyword() {
		return faqTopicKeyword;
	}

	public void setFaqTopicKeyword(String faqTopicKeyword) {
		this.faqTopicKeyword = faqTopicKeyword;
	}

	public Map<String, Integer> getFaqTopicQuestion() {
		return faqTopicQuestion;
	}

	public void setFaqTopicQuestion(Map<String, Integer> faqTopicQuestion) {
		this.faqTopicQuestion = faqTopicQuestion;
	}

	public String getFaqKeywordsSearch() {
		return faqKeywordsSearch;
	}

	public void setFaqKeywordsSearch(String faqKeywordsSearch) {
		this.faqKeywordsSearch = faqKeywordsSearch;
	}

	public String getCreateFaqTopicKeyword() {
		return createFaqTopicKeyword;
	}

	public void setCreateFaqTopicKeyword(String createFaqTopicKeyword) {
		this.createFaqTopicKeyword = createFaqTopicKeyword;
	}

	public String getFaqKeywordsInsert() {
		return faqKeywordsInsert;
	}

	public void setFaqKeywordsInsert(String faqKeywordsInsert) {
		this.faqKeywordsInsert = faqKeywordsInsert;
	}
	
	
	
	
}
