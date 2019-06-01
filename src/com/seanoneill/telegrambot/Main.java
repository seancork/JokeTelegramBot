package com.seanoneill.telegrambot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
	public static void main(String[] args) {
		ApiContextInitializer.init();
		TelegramBotsApi telegramAPI = new TelegramBotsApi();	
		try {
			telegramAPI.registerBot(new TelegramBot());
		}catch(TelegramApiException e) {
			e.printStackTrace();
		}}}