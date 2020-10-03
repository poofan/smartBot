package smartBot_v2.TopForUsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Tops {
	Document doc = null;
	
	public String[] getTopBooks(String date) throws IOException {
		//Прописываем несколько вариантов подключения
		if(date.equals("За все время")) {
			doc = Jsoup.connect("https://www.surgebook.com/books/popular?order=popular").get();
		}
		else if(date.equals("За месяц")) {
			doc = Jsoup.connect("https://www.surgebook.com/books/popular?when=this_month&order=popular").get();
		}
		else if(date.equals("За неделю")) {
			doc = Jsoup.connect("https://www.surgebook.com/books/popular?when=this_week&order=popular").get();
		}
		else if(date.equals("Сегодня")) {
			doc = Jsoup.connect("https://www.surgebook.com/books/popular?when=today&order=popular").get();
		}
		
		//получаем элементы
		Elements elements = doc.getElementsByClass("book_view_mv1v2_title");
		//создаём список, который будет хранить ссылки на книги
		List<String> listBook = new ArrayList<String>();
		
		//добавляем через цикл
		for(int i = 0; i < elements.size(); i++) {
			if(i < 10) {//указываем 10 элементов, потому что будет скачано 2 разных эелмента по 10 раз каждый
				listBook.add(elements.get(i).attr("href"));
			}
		}
		
		//создаём массив, в который добавим ссылки из списка с помощью цикла
		String[] arrBook = new String[listBook.size()];
		
		for(int i = 0; i < listBook.size(); i++) {
			arrBook[i] = listBook.get(i);
		}
		
		return arrBook;
	}
}
