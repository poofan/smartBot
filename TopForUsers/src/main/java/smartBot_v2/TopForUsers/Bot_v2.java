package smartBot_v2.TopForUsers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot_v2 extends TelegramLongPollingBot {
	ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
	long chat_id;
	String lastMes = "";

	@Override
	public void onUpdateReceived(Update update) {
		chat_id = update.getMessage().getChatId();
		
		SendMessage sendMessage = new SendMessage().setChatId(chat_id).setText(getMessage(update.getMessage().getText()));
		sendMessage.setReplyMarkup(replyKeyboardMarkup);
		
		try {
			execute(sendMessage);
		}
		catch(TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	public String getMessage(String text) {
		List<KeyboardRow> keyboardList = new ArrayList<KeyboardRow>();
		KeyboardRow keyboardRowFirst = new KeyboardRow();
		
		replyKeyboardMarkup.setOneTimeKeyboard(true);
		replyKeyboardMarkup.setResizeKeyboard(false);
		replyKeyboardMarkup.setSelective(true);
		
		if(text.equals("Привет")) {
			keyboardList.clear();
			keyboardRowFirst.add("Популярное, кажись...");
			keyboardList.add(keyboardRowFirst);
			replyKeyboardMarkup.setKeyboard(keyboardList);
			return "Чего изволишь?";
		}
		
		if(text.equals("Популярное, кажись...")) {
			keyboardList.clear();
			keyboardRowFirst.add("Книги");
			keyboardList.add(keyboardRowFirst);
			replyKeyboardMarkup.setKeyboard(keyboardList);
			return "Могу предложить это: ";
		}
		
		if(text.equals("Книги")) {
			lastMes = text;
			keyboardList.clear();
			keyboardRowFirst.add("Сегодня");
			keyboardRowFirst.add("За неделю");
			keyboardRowFirst.add("За месяц");
			keyboardRowFirst.add("За все время");
			keyboardList.add(keyboardRowFirst);
			replyKeyboardMarkup.setKeyboard(keyboardList);
			return "Выбери период";
		}
		
		if(lastMes.equals("Книги")) {
			if(text.equals("Сегодня") || text.equals("За неделю") || text.equals("За месяц") || text.equals("За все время")) {
				try {
					return getBookInfo(new Tops().getTopBooks(text));
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return "Если что, жмякай 'Привет'";
	}
	
	public String getBookInfo(String[] url) {
		for(int i = 0; i < url.length; i++) {
			Book book = new Book(url[i]);
			
			try {
				URL urlCover = new URL(book.getImage());
				BufferedImage img = ImageIO.read(urlCover);
				File file = new File("image.jpg");
				ImageIO.write(img, "jpg", file);
				
				SendPhoto sendPhoto = new SendPhoto().setChatId(chat_id);
				sendPhoto.setPhoto(file);
				execute(sendPhoto);
				
				String info = book.getTitle() + 
						"\nАвтор: " + book.getAuthor() +
						"\nЖанр: " + book.getGenre() + 
						"\nОписание: \n" + book.getDescription();
				
				SendMessage sendMessage = new SendMessage().setChatId(chat_id).setText(info);
				execute(sendMessage);
				
				if((i + 1) == url.length) {
					return info;
				}
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return "Дай подумать... :-)";
	}

	@Override
	public String getBotUsername() {
		return "@mnops_bot";
	}

	@Override
	public String getBotToken() {
		return "1397069463:AAEuZqU4F6hAsEDddP6GEJUcOGWALuaI9uY";
	}

}
