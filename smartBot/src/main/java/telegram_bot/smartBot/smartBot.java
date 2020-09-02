package telegram_bot.smartBot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class smartBot extends TelegramLongPollingBot {
	long chat_Id;
	library lib = new library();

	@Override
	public void onUpdateReceived(Update update) {
		update.getUpdateId();
		
		//create a variable of the ID user
		chat_Id = update.getMessage().getChatId();
		//create the algorithm to send message (answer)
		SendMessage sendMessage = new SendMessage().setChatId(chat_Id);
		sendMessage.setText(answer(update.getMessage().getText()));
		
		try {
			execute(sendMessage);
		}
		catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	public String answer (String msg) {
		if (msg.contains("Hello") || msg.contains("Hi")) {
			return "Greetings to my world!!!" + "\n"
					+ "What do you prefer?";
		}
		if (msg.contains("Привет") || msg.contains("Здорова")) {
			return "Приветсвую тебя на своём портале!!!" + "\n" 
					+ "Чего желаешь?";
		}
		if (msg.contains("Читать")) {
				for (int i = 0; i < 10;) {
					return getInfo();
				}
		}
		return msg;
	}
	
	public String getInfo() {
		try {
			//getting the link to the image
			URL url = new URL(lib.get_image());
			//putting on buffer
			BufferedImage image = ImageIO.read(url);
			//creating new file for our image
			File file = new File("cover-image.jpg");
			ImageIO.write(image, "jpg", file);
			
			SendPhoto sendPhoto = new SendPhoto().setChatId(chat_Id);
			sendPhoto.setPhoto(file);
			execute(sendPhoto);
		}
		catch (Exception e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
		
		String info = lib.book_title()
				+ "\nАвтор: " + lib.get_Author_Name()
				+ "\nЖанр: " + lib.get_genres()
				+ "\nОписание: " + lib.get_book_description()
				+ "\nLikes: " + lib.get_likes();
		return info;
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
