package telegram;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySQLAccess {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	private final String USERIDSQL = " and telegram.chat_form_template.UserID = '";
	
	private final String FORMIDSQL = " and telegram.chat_form_template.ChatFormID = '";
	
	private final String USERNAMESQL = " and telegram.telegram_reply.TelegramUserName = '";
	
	public void readDataBase() throws Exception {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/telegram?" + "user=sqluser&password=sqluserpw");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			/*
			 * // Result set get the result of the SQL query resultSet = statement
			 * .executeQuery("select * from telegram.comments"); writeResultSet(resultSet);
			 * 
			 * // PreparedStatements can use variables and are more efficient
			 */ preparedStatement = connect
					.prepareStatement("insert into  telegram.comments values (default, ?, ?, ?, ? , ?, ?)");
			// "myuser, webpage, datum, summary, COMMENTS from telegram.comments");
			// Parameters start with 1
			preparedStatement.setString(1, "Test");
			preparedStatement.setString(2, "TestEmail");
			preparedStatement.setString(3, "TestWebpage");
			preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
			preparedStatement.setString(5, "TestSummary");
			preparedStatement.setString(6, "TestComment");
			preparedStatement.executeUpdate();

			preparedStatement = connect
					.prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from telegram.comments");
			resultSet = preparedStatement.executeQuery();
			writeResultSet(resultSet);

			// Remove again the insert comment
			preparedStatement = connect.prepareStatement("delete from telegram.comments where myuser= ? ; ");
			preparedStatement.setString(1, "Test");
			preparedStatement.executeUpdate();

			resultSet = statement.executeQuery("select * from telegram.comments");
			writeMetaData(resultSet);

		} catch (Exception e) {
			
			throw e;
		} finally {
			close();
		}

	}

	public int insertRecord(List<ChatFormDTO> chatFormList, String chatFormTemplateName) throws Exception {
		int chatFormID = 0;
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/telegram?" + "user=sqluser&password=sqluserpw");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			String key[] = { "ChatFormID" };
			preparedStatement = connect
					.prepareStatement("insert into  telegram.chat_form_template values (default, ?, ?)", key);
			preparedStatement.setString(1, chatFormTemplateName);
			preparedStatement.setString(2, "123");
			preparedStatement.executeUpdate();

			ResultSet rs = preparedStatement.getGeneratedKeys();
			chatFormID = 0;
			if (rs.next()) {
				chatFormID = rs.getInt(1);
			}
			System.out.println(chatFormID);
			int position = 1;
			for (ChatFormDTO e : chatFormList) {
			int errorRequired = 0;
			if (e.isReplyRequired() ==  true)	 {
				
				errorRequired  = 1;
			}
			
			preparedStatement = connect
					.prepareStatement("insert into  telegram.chat_message_template (ChatFormID,MessageTemplate,ErrorMessageTemplate,RequestTypeID, ReplyRequired, Position) values (?,?, ?,?,?,?)");
			preparedStatement.setInt(1, chatFormID);
			preparedStatement.setString(2, e.getMessageTemplate());
			preparedStatement.setString(3, e.getErrorMessageTemplate());
			preparedStatement.setString(4, e.getRequestTypeID());
			preparedStatement.setInt(5, errorRequired);
			preparedStatement.setInt(6, position);
			preparedStatement.executeUpdate();
			
			position++;
			
			}
			position = 0 ;
			System.out.println(chatFormID);
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}
		return chatFormID;

	}
	
	 public List<ReplyDTO> getReplyDetails (String userName, String formID, String userID) throws Exception {
	        try {
	            // This will load the MySQL driver, each DB has its own driver
	            Class.forName("com.mysql.jdbc.Driver");
	            // Setup the connection with the DB
	            connect = DriverManager
	                    .getConnection("jdbc:mysql://localhost/telegram?"
	                            + "user=sqluser&password=sqluserpw");

	            // Statements allow to issue SQL queries to the database
	            statement = connect.createStatement();
	           
	            String sql = "SELECT * " 
                		+ "from telegram.chat_form_template, telegram.chat_message_template, telegram.telegram_reply  "
                		+ "where telegram.chat_form_template.chatFormID = telegram.chat_message_template.ChatFormID "
                		+ "and  telegram.chat_message_template.ChatMessageTemplateID = telegram.telegram_reply.ChatMessageTemplateID";
	           if( formID.isEmpty() && userID.isEmpty() && userName.isEmpty()) {
	        	   
	        	   return new ArrayList();
	           }
	            
	            if (!userName.isEmpty())  {
	            	sql = sql +USERNAMESQL + userName + "'";
	            			
	            }
	            if (!formID.isEmpty()) {
	            	sql = sql +FORMIDSQL + formID + "'";
	            }
	            
	            if (!userID.isEmpty()) {
	            	sql = sql + USERIDSQL + userID + "'";
	            	
	            }
	            System.out.println(sql);
	            preparedStatement = connect
	                    .prepareStatement(sql + " ;");
	            resultSet = preparedStatement.executeQuery();
	           List <ReplyDTO> replyList = new ArrayList();
	            while (resultSet.next()){
	                // e.g. resultSet.getSTring(2);
	            	ReplyDTO reply= new ReplyDTO();
	            	int chatFormID = resultSet.getInt("ChatFormID");
	                String messageTemplate = resultSet.getString("MessageTemplate");
	                String replyMessage = resultSet.getString("Reply");
	                int userIdentity = resultSet.getInt("UserID");
	                String username = resultSet.getString("TelegramUserName");
	                String dateOfReply = resultSet.getString("DateOfReply");
	                reply.setMemberReplyDate(dateOfReply);
	                reply.setFormID(chatFormID);
	                reply.setMessageTemplate(messageTemplate);
	                reply.setMemberReply(replyMessage);
	                reply.setUserName(username);
	                reply.setUserID(userIdentity);
	                replyList.add(reply);
	            }

	                

	      


	            return replyList;
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            close();
	        }

	    }
	

	 public List<ChatFormDetailsDTO> getFormTemplate (String formID, String userID) throws Exception {
	        try {
	            // This will load the MySQL driver, each DB has its own driver
	            Class.forName("com.mysql.jdbc.Driver");
	            // Setup the connection with the DB
	            connect = DriverManager
	                    .getConnection("jdbc:mysql://localhost/telegram?"
	                            + "user=sqluser&password=sqluserpw");

	            // Statements allow to issue SQL queries to the database
	            statement = connect.createStatement();
	           
	            String sql = "SELECT * " 
                		+ "from telegram.chat_form_template, telegram.chat_message_template  "
                		+ "where telegram.chat_form_template.chatFormID = telegram.chat_message_template.ChatFormID ";
	           if( formID.isEmpty() && userID.isEmpty()) {
	        	   
	        	   return new ArrayList();
	           }
	            
	 
	            if (!formID.isEmpty()) {
	            	sql = sql +FORMIDSQL + formID + "'";
	            }
	            
	            if (!userID.isEmpty()) {
	            	sql = sql + USERIDSQL + userID + "'";
	            	
	            }
	            System.out.println(sql);
	            preparedStatement = connect
	                    .prepareStatement(sql + " ;");
	            resultSet = preparedStatement.executeQuery();
	           List <ChatFormDetailsDTO> detailList = new ArrayList();
	            while (resultSet.next()){
	                // e.g. resultSet.getSTring(2);
	            	ChatFormDetailsDTO details= new ChatFormDetailsDTO();
	            	int chatFormID = resultSet.getInt("ChatFormID");
	                String messageTemplate = resultSet.getString("MessageTemplate");
	                int userIdentity = resultSet.getInt("UserID");
	                String chatFormTemplateName = resultSet.getString("ChatFormTemplateName");
	                boolean replyRequired = resultSet.getBoolean("ReplyRequired");
	                int position = resultSet.getInt("Position");
	                details.setFormID(chatFormID);
	                details.setMessageTemplate(messageTemplate);
	                details.setUserID(userIdentity);
	                details.setChatFormTemplateName(chatFormTemplateName);
	                details.setPosition(position);
	                details.setReplyRequired(replyRequired);
	               
	                detailList.add(details);
	            }

	                

	      


	            return detailList;
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            close();
	        }

	    }
	 
	 
	public void getConfirmation() throws Exception {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/telegram?" + "user=sqluser&password=sqluserpw");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			/*
			 * // Result set get the result of the SQL query resultSet = statement
			 * .executeQuery("select * from telegram.comments"); writeResultSet(resultSet);
			 * 
			 * // PreparedStatements can use variables and are more efficient
			 */ preparedStatement = connect
					.prepareStatement("insert into  telegram.comments values (default, ?, ?, ?, ? , ?, ?)");
			// "myuser, webpage, datum, summary, COMMENTS from telegram.comments");
			// Parameters start with 1
			preparedStatement.setString(1, "Test");
			preparedStatement.setString(2, "TestEmail");
			preparedStatement.setString(3, "TestWebpage");
			preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
			preparedStatement.setString(5, "TestSummary");
			preparedStatement.setString(6, "TestComment");
			preparedStatement.executeUpdate();

			preparedStatement = connect
					.prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from telegram.comments");
			resultSet = preparedStatement.executeQuery();
			writeResultSet(resultSet);

			// Remove again the insert comment
			preparedStatement = connect.prepareStatement("delete from telegram.comments where myuser= ? ; ");
			preparedStatement.setString(1, "Test");
			preparedStatement.executeUpdate();

			resultSet = statement.executeQuery("select * from telegram.comments");
			writeMetaData(resultSet);

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	void writeMetaData(ResultSet resultSet) throws SQLException {
		// Now get some metadata from the database
		// Result set get the result of the SQL query

		System.out.println("The columns in the table are: ");

		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i));
		}
	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			String user = resultSet.getString("myuser");
			String website = resultSet.getString("webpage");
			String summary = resultSet.getString("summary");
			Date date = resultSet.getDate("datum");
			String comment = resultSet.getString("comments");
			System.out.println("User: " + user);
			System.out.println("Website: " + website);
			System.out.println("summary: " + summary);
			System.out.println("Date: " + date);
			System.out.println("Comment: " + comment);
		}
	}

	// You need to close the resultSet
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}
	
	
	public int insertFaqTopicRecord(String faqTopic) throws Exception {
		int faqID = 0;
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/telegram?" + "user=sqluser&password=sqluserpw");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			String key[] = { "faqID" };
			preparedStatement = connect
					.prepareStatement("insert into  telegram.telegram_faq values (default, ?, ?)", key);
			preparedStatement.setString(1, faqTopic);
			preparedStatement.setInt(2, 123);
			preparedStatement.executeUpdate();

			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				faqID = rs.getInt(1);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}
		return faqID;

	}
	
	

	 public List<FaqTopicDTO> getFaqTopicDetails () throws Exception {
	        try {
	            // This will load the MySQL driver, each DB has its own driver
	            Class.forName("com.mysql.jdbc.Driver");
	            // Setup the connection with the DB
	            connect = DriverManager
	                    .getConnection("jdbc:mysql://localhost/telegram?"
	                            + "user=sqluser&password=sqluserpw");

	            // Statements allow to issue SQL queries to the database
	            statement = connect.createStatement();
	           
	            String sql = "SELECT * " 
               		+ "from telegram.telegram_faq ;";
	
	            preparedStatement = connect
	                    .prepareStatement(sql);
	            resultSet = preparedStatement.executeQuery();
	            List <FaqTopicDTO> faqTopicList = new ArrayList();
	            while (resultSet.next()){
	            	FaqTopicDTO dto= new FaqTopicDTO();
	            	int faqID = resultSet.getInt("faqID");
	                String faqTopicName = resultSet.getString("FaqTopicName");
	                int userIdentity = resultSet.getInt("UserID");
	                dto.setFaqID(faqID);
	                dto.setFaqTopicName(faqTopicName);
	                dto.setUserID(userIdentity);
	                faqTopicList.add(dto);
	            }

	                

	      


	            return faqTopicList;
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            close();
	        }

	    }
	 public List<FaqDTO> getFaqTopicQuestions (String faqTopic) throws Exception {
	        try {
	            // This will load the MySQL driver, each DB has its own driver
	            Class.forName("com.mysql.jdbc.Driver");
	            // Setup the connection with the DB
	            connect = DriverManager
	                    .getConnection("jdbc:mysql://localhost/telegram?"
	                            + "user=sqluser&password=sqluserpw");

	            // Statements allow to issue SQL queries to the database
	            statement = connect.createStatement();
	           
	            Integer faqID = Integer.parseInt(faqTopic);
	            preparedStatement = connect
						.prepareStatement("SELECT * " 
            		+ "from telegram.telegram_faq , telegram.faq_topics_template where telegram.telegram_faq.faqID =  telegram.faq_topics_template.faqID and telegram.telegram_faq.faqID = ? ;");
	    		preparedStatement.setInt(1, faqID);
	            resultSet = preparedStatement.executeQuery();
	            List <FaqDTO> faqList = new ArrayList();
	            while (resultSet.next()){
	            	FaqDTO dto= new FaqDTO();
	            	int faqID1 = resultSet.getInt("faqID");
	                String faqQuestion = resultSet.getString("faqQuestion");
	                String faqAnswer = resultSet.getString("faqAnswer");
	                int faqTemplateID = resultSet.getInt("faqTopicID");
	                dto.setFaqID(faqID1);
	                dto.setFaqAnswer(faqAnswer);
	                dto.setFaqQuestion(faqQuestion);
	                dto.setFaqTemplateID(faqTemplateID);
	                dto.setFaqTopic(faqTopic);
	                
	                faqList.add(dto);
	            }

	            return faqList;
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            close();
	        }
	                


	    }
	 
	 
	 public List<FaqDTO> getFaqDetails (String faqTopic) throws Exception {
	        try {
	            // This will load the MySQL driver, each DB has its own driver
	            Class.forName("com.mysql.jdbc.Driver");
	            // Setup the connection with the DB
	            connect = DriverManager
	                    .getConnection("jdbc:mysql://localhost/telegram?"
	                            + "user=sqluser&password=sqluserpw");

	            // Statements allow to issue SQL queries to the database
	            statement = connect.createStatement();
	            
	            String sql = "SELECT * " 
               		+ "from telegram.telegram_faq ;";
	
	            preparedStatement = connect
	                    .prepareStatement(sql);
	            resultSet = preparedStatement.executeQuery();
	            List <FaqTopicDTO> faqTopicList = new ArrayList();
	            while (resultSet.next()){
	            	FaqTopicDTO dto= new FaqTopicDTO();
	            	int faqID = resultSet.getInt("faqID");
	                String faqTopicName = resultSet.getString("FaqTopicName");
	                int userIdentity = resultSet.getInt("UserID");
	                dto.setFaqID(faqID);
	                dto.setFaqTopicName(faqTopicName);
	                dto.setUserID(userIdentity);
	                faqTopicList.add(dto);
	            }
	            Integer faqID = Integer.parseInt(faqTopic);
	            preparedStatement = connect
						.prepareStatement("SELECT * " 
            		+ "from telegram.telegram_faq , telegram.faq_topics_template where telegram.telegram_faq.faqID =  telegram.faq_topics_template.faqID and telegram.telegram_faq.faqID = ? ;");
	    		preparedStatement.setInt(1, faqID);
	            resultSet = preparedStatement.executeQuery();
	            List <FaqDTO> faqList = new ArrayList();
	            while (resultSet.next()){
	            	FaqDTO dto= new FaqDTO();
	            	int faqID1 = resultSet.getInt("faqID");
	                String faqQuestion = resultSet.getString("faqQuestion");
	                String faqAnswer = resultSet.getString("faqAnswer");
	                int faqTemplateID = resultSet.getInt("faqTopicID");
	                dto.setFaqID(faqID1);
	                dto.setFaqAnswer(faqAnswer);
	                dto.setFaqQuestion(faqQuestion);
	                dto.setFaqTemplateID(faqTemplateID);
	                dto.setFaqTopic(faqTopic);
	                
	                faqList.add(dto);
	            }

	            return faqList;
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            close();
	        }

	    }
	 
	 public List<FaqDTO> getFaqKeywords (String faqTopic) throws Exception {
	        try {
	            // This will load the MySQL driver, each DB has its own driver
	            Class.forName("com.mysql.jdbc.Driver");
	            // Setup the connection with the DB
	            connect = DriverManager
	                    .getConnection("jdbc:mysql://localhost/telegram?"
	                            + "user=sqluser&password=sqluserpw");

	            // Statements allow to issue SQL queries to the database
	            statement = connect.createStatement();
	            
	 
	            Integer faqTopicID = Integer.parseInt(faqTopic);
	            preparedStatement = connect
						.prepareStatement("SELECT * " 
         		+ "from telegram.faq_keyword  where telegram.faq_keyword.faqTopicID = ? ;");
	    		preparedStatement.setInt(1, faqTopicID);
	            resultSet = preparedStatement.executeQuery();
	            List <FaqDTO> faqList = new ArrayList();
	            while (resultSet.next()){
	            	FaqDTO dto= new FaqDTO();
	            	int keywordID = resultSet.getInt("KeywordID");
	                String keyword = resultSet.getString("faqKeyword");
	                int faqTopicID1 = resultSet.getInt("faqTopicID");
	                dto.setFaqKeywordID(keywordID);
	                dto.setKeywords(keyword);
	                dto.setFaqTemplateID(faqTopicID1);                
	                faqList.add(dto);
	            }

	            return faqList;
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            close();
	        }

	    }
	 
	 
	 
		public int insertFaqRecord(String faqQuestion, String faqAnswer, String faqTopic) throws Exception {
			int key1 = 0;
			try {
				// This will load the MySQL driver, each DB has its own driver
				Class.forName("com.mysql.jdbc.Driver");
				// Setup the connection with the DB
				connect = DriverManager
						.getConnection("jdbc:mysql://localhost/telegram?" + "user=sqluser&password=sqluserpw");

				// Statements allow to issue SQL queries to the database
				statement = connect.createStatement();

				Integer faqID = Integer.parseInt(faqTopic);
	           
				/*
				 * String sql = "SELECT * " +
				 * "from telegram.telegram_faq where telegram.telegram_faq.FaqTopicName = '"+
				 * faqTopic+"' ;";
				 * 
				 * preparedStatement = connect .prepareStatement(sql); resultSet =
				 * preparedStatement.executeQuery(); while (resultSet.next()){ faqID =
				 * resultSet.getInt("faqID"); }
				 */
				if(faqID != 0) {
				String key[] = { "faqTopicID" };
				preparedStatement = connect
						.prepareStatement("insert into  telegram.faq_topics_template values (default, ?, ?,?)", key);
				preparedStatement.setString(1, faqQuestion);
				preparedStatement.setString(2, faqAnswer);
				preparedStatement.setInt(3, faqID);
				preparedStatement.executeUpdate();
			
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if (rs.next()) {
					key1 = rs.getInt(1);
				}
				}
			} catch (Exception e) {
				throw e;
			} finally {
				close();
			}
			return key1;

		}
		
		public int insertFaqKeywordRecord(String faqKeyword,String question ) throws Exception {
			int key1 = 0;
			try {
				// This will load the MySQL driver, each DB has its own driver
				Class.forName("com.mysql.jdbc.Driver");
				// Setup the connection with the DB
				connect = DriverManager
						.getConnection("jdbc:mysql://localhost/telegram?" + "user=sqluser&password=sqluserpw");

				// Statements allow to issue SQL queries to the database
				statement = connect.createStatement();

				Integer questionID = Integer.parseInt(question);
	           
				/*
				 * String sql = "SELECT * " +
				 * "from telegram.telegram_faq where telegram.telegram_faq.FaqTopicName = '"+
				 * faqTopic+"' ;";
				 * 
				 * preparedStatement = connect .prepareStatement(sql); resultSet =
				 * preparedStatement.executeQuery(); while (resultSet.next()){ faqID =
				 * resultSet.getInt("faqID"); }
				 */
				if(questionID != 0) {
				String key[] = { "KeywordID" };
				preparedStatement = connect
						.prepareStatement("insert into  telegram.faq_keyword values (default, ?, ?,?)", key);
				preparedStatement.setString(2, faqKeyword);
				preparedStatement.setString(3, "123");
				preparedStatement.setInt(1, questionID);
				preparedStatement.executeUpdate();
			
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if (rs.next()) {
					key1 = rs.getInt(1);
				}
				}
			} catch (Exception e) {
				throw e;
			} finally {
				close();
			}
			return key1;

		}

}