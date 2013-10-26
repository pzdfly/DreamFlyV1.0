package pz.rg.domain;

public class NewsHead {

	private String newsId;
	private String databaseId;
	private String title;
	private String introduce;
	private String time;
	private String imgUrl;
	
	public NewsHead() {
		// TODO Auto-generated constructor stub
	}

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(String databaseId) {
		this.databaseId = databaseId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	@Override
	public String toString() {
		return "NewsHead [newsId=" + newsId + ", databaseId=" + databaseId
				+ ", title=" + title + ", introduce=" + introduce + ", time="
				+ time + ", imgUrl=" + imgUrl + "]";
	}


}
