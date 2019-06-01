package com.seanoneill.telegrambot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {

	@Override
	public String getBotUsername() {
		   // Return bot username
		return "YourBotName";
	}
	
	@Override
	public String getBotToken() {
		 // Return bot token from BotFather
		return "YourToken";
	}

	@Override
	public void onUpdateReceived(Update update) {
		//Check to see if the update has a message with message text.
	 if (update.hasMessage() && update.getMessage().hasText()) {
			String message_text = update.getMessage().getText(); // get message text
			String search_word  = "joke"; //keyword
			//covert message text to lower case and check if message contains keyword
	 if ( message_text.toLowerCase().indexOf(search_word.toLowerCase()) != -1 ) {	
		 	JSONObject json = null;
		try {
			json = readUrlJson("http://api.icndb.com/jokes/random?escape=javascript"); //api url
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  //get text from the key "joke" and send that text to the user
		    String getJoke = json.getJSONObject("value").getString("joke");
			SendMessage message = new SendMessage()
			.setChatId(update.getMessage().getChatId())
			.setText(getJoke);
		try {
			execute(message);
		}catch(TelegramApiException e) {
			e.printStackTrace();
		}
		}else {
			//Send info text to the user if the message does not contain the keyword
			SendMessage message = new SendMessage()
					.setChatId(update.getMessage().getChatId())
					.setText("Hey There! If you want to here a joke use the keyword joke :)");
			try {
				execute(message);
			}catch(TelegramApiException e) {
				e.printStackTrace();
			}}}}
	
	  private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }
         
	  //reads url and return json
		  public static JSONObject readUrlJson(String url) throws IOException, JSONException {
		    InputStream is = new URL(url).openStream();
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      JSONObject json = new JSONObject(jsonText);
		      return json;
		    } finally {
		      is.close();
		    }}}