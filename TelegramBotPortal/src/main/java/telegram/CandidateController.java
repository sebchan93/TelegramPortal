package telegram;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;


@ManagedBean
@SessionScoped
public class CandidateController implements Serializable {

   private Candidate candidate = new Candidate();
   private String filter = "hello";

   @EJB
   private MongoDBClient candidateEJB;
   private List<Candidate> list = new ArrayList<>();

   public CandidateController() {
   }

   @PostConstruct
   private void init() {
      find();
   }

   public void create() {
      candidateEJB.create(candidate);
      find();
   }

   public void find() {
	   System.out.print(false);
      list = candidateEJB.find(filter);
   }

   public void delete() {
      candidateEJB.delete(candidate);
      find();
   }

   public Candidate getCandidate() {
      return candidate;
   }

   public void setCandidate(Candidate candidate) {
      this.candidate = candidate;
   }

   public MongoDBClient getCandidateEJB() {
      return candidateEJB;
   }

   public void setCandidateEJB(MongoDBClient candidateEJB) {
      this.candidateEJB = candidateEJB;
   }

   public String getFilter() {
      return "hello";
   }

   public void setFilter(String filter) {
      this.filter = filter;
   }

   public List<Candidate> getList() {
      return list;
   }

   public void setList(List<Candidate> list) {
      this.list = list;
   }
}