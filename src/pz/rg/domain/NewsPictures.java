package pz.rg.domain;

public class NewsPictures {

	private String title;
	private String imgUrl;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	@Override
	public String toString() {
		return "NewsPictures [title=" + title + ", imgUrl=" + imgUrl + "]";
	}
	
	
}
