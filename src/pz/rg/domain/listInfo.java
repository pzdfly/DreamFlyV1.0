package pz.rg.domain;

public class listInfo {

	private String listid;
	private String type;
	private String uname;
	private String area;
	private String dorm;
	private String phone;
	private String computertype;
	private String booktime;
	private String time;
	private String pcnumber;
	private String state;
	private String reviewlevel;
	private String remark;
	
	public String getListid() {
		return listid;
	}
	public void setListid(String listid) {
		this.listid = listid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getComputertype() {
		return computertype;
	}
	public void setComputertype(String computertype) {
		this.computertype = computertype;
	}
	public String getBooktime() {
		return booktime;
	}
	public void setBooktime(String booktime) {
		this.booktime = booktime;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPcnumber() {
		return pcnumber;
	}
	public void setPcnumber(String pcnumber) {
		this.pcnumber = pcnumber;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getReviewlevel() {
		return reviewlevel;
	}
	public void setReviewlevel(String reviewlevel) {
		this.reviewlevel = reviewlevel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public String toString() {
		return "listInfo [listid=" + listid + ", type=" + type + ", uname="
				+ uname + ", area=" + area + ", dorm=" + dorm + ", phone="
				+ phone + ", computertype=" + computertype + ", booktime="
				+ booktime + ", time=" + time + ", pcnumber=" + pcnumber
				+ ", state=" + state + ", reviewlevel=" + reviewlevel
				+ ", remark=" + remark + "]";
	}
	
}
