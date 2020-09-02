package telegram_bot.smartBot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class AppBot {
	public static void main(String[] args) {
		ApiContextInitializer.init();
		TelegramBotsApi telegramApi = new TelegramBotsApi();
		smartBot bot = new smartBot();
		
		try {
			telegramApi.registerBot(bot);
		}
		catch (TelegramApiRequestException e) {
			e.printStackTrace();
		}
	}
}
