package pz.rg.domain;


public class Baoxiudan{
	private String listid;
	private String computertype;
	private String booktime;
	private String submittime;
	private String pcnumber;
	private String state;
	private String isreview;
	
	
	public String getBooktime() {
		return booktime;
	}
	public void setBooktime(String booktime) {
		this.booktime = booktime;
	}
	public String getListid() {
		return listid;
	}
	public void setListid(String listid) {
		this.listid = listid;
	}
	public String getComputertype() {
		return computertype;
	}
	public void setComputertype(String computertype) {
		this.computertype = computertype;
	}
	public String getSubmittime() {
		return submittime;
	}
	public void setSubmittime(String submittime) {
		this.submittime = submittime;
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
	public String getIsreview() {
		return isreview;
	}
	public void setIsreview(String isreview) {
		this.isreview = isreview;
	}

	@Override
	public String toString() {
		return "Baoxiudan [listid=" + listid + ", computertype=" + computertype
				+ ", booktime=" + booktime + ", submittime=" + submittime
				+ ", pcnumber=" + pcnumber + ", state=" + state + ", isreview="
				+ isreview + " ]";
	}
}
