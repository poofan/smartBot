package org.telegram.telegrambots;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class smartBot extends TelegramLongPollingBot {
	private long chat_id;
	library book = new library();

	@Override
	public void onUpdateReceived(Update update) {
		update.getUpdateId();
		SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
		chat_id = update.getMessage().getChatId();
		sendMessage.setText(messageHandling(update.getMessage().getText()));
		
		try {
			execute(sendMessage);
		}
		catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getBotUsername() {
		return "@mnops_bot";
	}

	@Override
	public String getBotToken() {
		return "1397069463:AAEuZqU4F6hAsEDddP6GEJUc0GWALuaI9uY";
	}
	
	//функция для обработки сообщений
	public String messageHandling (String msg) {
		if (msg.contains("Hi")|| msg.contains("Hello") || msg.contains("Привет")) {
			return "Приветствую тебя на своём портале!";
		}
		
		if (msg.contains("Информация о книге")) {
			return getInfoBook();
		}
		return msg;
	}
	
	public String getInfoBook () {
		try {
			URL url = new URL(book.getImage());//ссылка на изображение
			BufferedImage image = ImageIO.read(url);//скачивание изображения в буфер
			File outputFile = new File("image.jpg");
			ImageIO.write(image, "jpg", outputFile);
			SendPhoto sendPhoto = new SendPhoto().setChatId(chat_id);
			sendPhoto.setPhoto(outputFile);
			execute(sendPhoto);
		}
		catch (Exception e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
		
		String info = book.getTitle()
				+ "\nАвтор" + book.getAuthorName()
				+ "\nЖанр" + book.getGenre()
				+ "\nОписание" + book.getDescription()
				+ "\nКол-во лайков" + book.getLikes()
				+ "\nПоследние комментарии" + book.getComments();
		return info;		
	}
}
