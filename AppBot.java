package org.telegram.telegrambots;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class AppBot {

	public static void main(String[] args) throws TelegramApiRequestException {
		ApiContextInitializer.init();
		
		TelegramBotsApi BotApi = new TelegramBotsApi();
		smartBot bot = new smartBot();
		try {
			BotApi.registerBot(bot);
		}
		catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
