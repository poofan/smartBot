package org.telegram.telegrambots;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class library {
	private Document doc;
	
	public library () {
		connect("https://www.surgebook.com/arcpolus/book/teni-proshlogo");
	}
	
	private void connect (String url) {//подключение к URL
		try {
			doc = Jsoup.connect(url).get();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//получаем название обложки
	public String getTitle () {
		return doc.title();
	}
	
	//просмотр лайков
	public String getLikes () {
		Element elem = doc.getElementById("likes");
		return elem.text();
	}
	
	//описание книги
	public String getDescription () {
		Element elem = doc.getElementById("description");
		return elem.text();
	}
	
	//жанры книг
	public String getGenre () {
		Elements elements = doc.getElementsByClass("genres d-block");
		return elements.text();
	}
	
	//комментарии к книге
	public String getComments () {
		Elements elements = doc.getElementsByClass("comment_mv1_item");
		
		String comment = elements.text();
		comment = comment.replaceAll("Ответить", "\n\n");
		comment = comment.replaceAll("Нравится", "");
		comment = comment.replaceAll("\\d{4}-\\d{2}-\\d{4}", "");
		comment = comment.replaceAll("\\d{2}:\\d{2}:\\d{2}", "");
		return comment;
	}
	
	//обложка книги
	public String getImage () {
		Elements elements = doc.getElementsByClass("cover-book");
		String url = elements.attr("style");
		
		url = url.replaceAll("background-image: url('", "");
		url = url.replaceAll("');", "");
		return url;
	}
	
	//имя автора
	public String getAuthorName () {
		Element elem = doc.getElementById("text-decoration-none column-author-name bold max-w-140 text-overflow-ellipsis");
		return elem.text();
	}
}
