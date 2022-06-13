package telegram;

public class FaqDTO {

	private int faqID;
	private int faqTemplateID;
	private String faqQuestion;
	private String faqAnswer;
	private String faqTopic;
	private String keywords;
	private int faqKeywordID;
	
	public int getFaqID() {
		return faqID;
	}
	public void setFaqID(int faqID) {
		this.faqID = faqID;
	}
	public int getFaqTemplateID() {
		return faqTemplateID;
	}
	public void setFaqTemplateID(int faqTemplateID) {
		this.faqTemplateID = faqTemplateID;
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
	public String getFaqTopic() {
		return faqTopic;
	}
	public void setFaqTopic(String faqTopic) {
		this.faqTopic = faqTopic;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public int getFaqKeywordID() {
		return faqKeywordID;
	}
	public void setFaqKeywordID(int faqKeywordID) {
		this.faqKeywordID = faqKeywordID;
	}
	
	
	
}
