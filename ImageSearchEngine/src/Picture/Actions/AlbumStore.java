package Picture.Actions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jasig.cas.client.validation.Assertion;

import DBTool.DB18Util;

import com.ckcest.sso.UserProfile;
import com.opensymphony.xwork2.ActionSupport;

public class AlbumStore extends ActionSupport{
	/**
	 * 
	 */
	//username
	private String username="";
	//userid
	private String userid="";
	//useremail
	private String email="";
	//albumID
	private String albumID="";
	//tags
	private String tags="";
	//queryresult
	private String queryresult="";
	//pictureID
	public String pictureID;
	
	//global
	private static final long serialVersionUID = 1L;
	
	public static Connection conn = null;

	
	public String execute()throws Exception{
	
		username=getUserName();
		userid = username;
		
		if(conn==null) {
			conn=DB18Util.connectMySql();
		}
			
		
		if(tags.length()<1){
			queryresult="����д��ǩ";
			
			return SUCCESS;
			
		}
		
		Statement statement=conn.createStatement();
		String sql="select * from userinfo.albumStore where albumID="+albumID+" and userID="+ "'"+userid + "'";
		statement = conn.prepareStatement(sql);
		ResultSet rs=statement.executeQuery(sql);
		
		if(rs.next()){
			queryresult="���Ѿ��ղع���ͼ����";
			//conn.close();
			return SUCCESS;
		}
		
		sql="insert into userinfo.albumStore(userID,tags,storeTime,isDeleted,albumID) values(" +
				"'"+userid + "'"
				+",'"+tags
				+"',now()"
				+",0"
				+","+albumID+")";
		statement.execute(sql);
		//statement.executeQuery(sql);
		//username=getUserName();
		queryresult="�ղسɹ�";
		
		statement.close();
		return SUCCESS;
		
	}


	private String getUserName() {
		// TODO Auto-generated method stub
		String username="";
		HttpServletRequest request=ServletActionContext.getRequest();
		Object object = request.getSession().getAttribute("_const_cas_assertion_");
		if(object != null) {

		    Assertion assertion = (Assertion)object;
		    username = assertion.getPrincipal().getName();
		}
		return username;
	}

	private String getUserID() {
		// TODO Auto-generated method stub
		HttpServletRequest request=ServletActionContext.getRequest();
		Object object = request.getSession().getAttribute("_const_userprofile_assertion_");
		if(object!=null){
			UserProfile userProfile= (UserProfile)object;
			return userProfile.userId;
		}
		return null;
	}

	
	
	//////////////////////////////////////
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
	
	

	public String getAlbumID() {
		return albumID;
	}

	public void setAlbumID(String albumID) {
		this.albumID = albumID;
	}

	
	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	
	
	public String getQueryresult() {
		return queryresult;
	}

	public void setQueryresult(String queryresult) {
		this.queryresult = queryresult;
	}
	
	
	public String getPictureID() {
		return pictureID;
	}

	public void setPictureID(String pictureID) {
		this.pictureID = pictureID;
	}

}
