package smartBot_v2.TopForUsers;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Book {
	private Document doc = null;
	private String url = null;
	
	public Book(String url) {
		this.url = url;
		connect();
	}
	
	private void connect() {
		try {
			doc = Jsoup.connect(url).get();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getAuthor() {
		Elements elements = doc.getElementsByClass("text-decoration-none column-author-name bold max-w-140 text-overflow-ellipsis");
		return elements.text();
	}
	
	public String getTitle() {
		Elements elements = doc.getElementsByClass("title d-block bold");
		return elements.text();
	}
	
	public String getGenre() {
		Elements elements = doc.getElementsByClass("genres d-block");
		return elements.text();
	}
	
	public String getImage() {
		Elements elements = doc.getElementsByClass("cover-book");
		String url = elements.attr("style");
		url = url.replace("background-image: url('", "");
		url = url.replace("');", "");
		
		return url;
	}
	
	public String getDescription() {
		Elements elements = doc.getElementsByClass("description mt-20 here_can_be_link");
		return elements.text();
	}
}
