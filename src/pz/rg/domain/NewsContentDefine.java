package pz.rg.domain;

public class NewsContentDefine {

	private String title;
	private String author;
	private String copyfrom;
	private String time;
	private String content;
	
	public NewsContentDefine(){
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCopyfrom() {
		return copyfrom;
	}

	public void setCopyfrom(String copyfrom) {
		this.copyfrom = copyfrom;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "NewsContentDefine [title=" + title + ", author=" + author
				+ ", copyfrom=" + copyfrom + ", time=" + time + ", content="
				+ content + "]";
	}
	
	
}
