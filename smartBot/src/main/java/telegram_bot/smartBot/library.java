package telegram_bot.smartBot;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class library {
	private Document doc;
	
	library () {
		connect();
	}
	
	//connecting to the book
	private void connect () {
		try {
			doc = Jsoup.connect("https://www.surgebook.com/Gyurza/book/zakon-dolga-sredi-lyudey").get();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//getting the book title
	public String book_title() {
		return doc.title();
	}
	
	//getting the likes
	public String get_likes() {
		Element element = doc.getElementById("likes");
		return element.text();
	}
	
	//getting the description
	public String get_book_description() {
		Element element = doc.getElementById("description");
		return element.text();
	}
	
	//getting the book genre
	public String get_genres() {
		Element element = doc.getElementsByClass("genres d-block").get(0);
		return element.text();
	}
	
	//getting the book cover
	public String get_image() {
		Elements elements = doc.getElementsByClass("cover-book");
		String url = elements.attr("style");
		url = url.replace("background-image: url('","");
		url = url.replace("');","");
		return url;
	}
	
	//getting the author name
	public String get_Author_Name() {
		Elements elements = doc.getElementsByClass("text-decoration-none column-author-name bold max-w-140 text-overflow-ellipsis");
		return elements.text();
	}
}
