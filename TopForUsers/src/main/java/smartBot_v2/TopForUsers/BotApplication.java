package smartBot_v2.TopForUsers;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class BotApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		
		TelegramBotsApi telega = new TelegramBotsApi();
		Bot_v2 bot = new Bot_v2();
		
		try {
			telega.registerBot(bot);
		}
		catch(TelegramApiRequestException e) {
			e.printStackTrace();
		}
	}

}
