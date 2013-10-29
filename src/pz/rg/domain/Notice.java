package pz.rg.domain;


public class Notice{
	private String nid;
	private String title;
	private String notice;
	private String time;
	
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	@Override
	public String toString() {
		return "Notice [nid=" + nid + ", title=" + title + ", notice=" + notice
				+ ", time=" + time + "]";
	}

}
