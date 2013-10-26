package pz.rg.domain;

public class Login {
	
	private String uid;
	private String uname;
	private String realname;
	private String sex;
	private String area;
	private String dorm;
	private String email;
	private String phone;
	private String pcnumber;
	private String identity;
	
	public Login(){
		
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDorm() {
		return dorm;
	}

	public void setDorm(String dorm) {
		this.dorm = dorm;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPcnumber() {
		return pcnumber;
	}

	public void setPcnumber(String pcnumber) {
		this.pcnumber = pcnumber;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	@Override
	public String toString() {
		return "Login [uid=" + uid + ", uname=" + uname + ", realname="
				+ realname + ", sex=" + sex + ", area=" + area + ", dorm="
				+ dorm + ", email=" + email + ", phone=" + phone
				+ ", pcnumber=" + pcnumber + ", identity=" + identity + "]";
	}
	
	
	



}
