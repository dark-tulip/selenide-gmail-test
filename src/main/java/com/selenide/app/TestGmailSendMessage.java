package com.selenide.app;


import java.util.Calendar;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.byText;

import java.text.SimpleDateFormat;
import java.time.Duration;

import org.junit.Before;
import org.junit.Test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;

// ----------------- ЗАДАНИЕ ------------------- //
// Создать проект на Java, который будет запускать 5 тестов на Selenide.
// Тесты на Selenide должны делать следующее:
// 1. Логин на gmail.com
// 2. Отправка письма с произвольным текстом
// 3. Проверка в отправленных, что письмо отправлено
// 4. В настройках добавить подпись и отправить еще одно письмо
// 5. В отправленных проверить, что письмо отправлено с подписью. 


public class TestGmailSendMessage {
	
	String LOGIN_USER_GMAIL = "donefortest@gmail.com";
	String LOGIN_USER_PASSWD = "12345Qwe#";
	
	String todayAsString = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
	String SIGNATURE_NAME = "ITS MY TEST SIGNATURE " + todayAsString;
	String SIGNATURE_TEXT = "SIGNATURE FROM UNKNOWN USER" + todayAsString;

	String USER_GMAIL_TOSEND = "donefortest@gmail.com";
	String MSG_THEME = "TEXT THEME";
	String FULL_MSG_TEXT = "main message text";
	
	
	// SOME CSS_LOCATORS
	String write_msg_btn = "div.T-I.T-I-KE.L3";

	
	@Before
	public void setUp()  {
		Configuration.browser = "chrome";
	}
	
	
	public void loginToGmail() {
		Selenide.sleep(2000);
		open("https://www.google.com/intl/ru/gmail/about/");

		// FROM MAIN PAGE CLICK BTN LOGIN
		String login_button = ".header__container a:nth-child(2)";
		$(login_button).shouldBe(enabled, Duration.ofSeconds(20)).click();
		
		// INPUT LOGIN
		if ($("#identifierId").isDisplayed()) {
			$("#identifierId").shouldBe(enabled, Duration.ofSeconds(10)).setValue(LOGIN_USER_GMAIL);
			$(".qhFLie button").shouldBe(enabled, Duration.ofSeconds(10)).click();
			// INPUT PASSWORD
			$(".eEgeR input").shouldBe(enabled).setValue(LOGIN_USER_PASSWD);
			$(".qhFLie button").shouldBe(enabled).click();
		} else {
			// выбараем только логин для авторизации
			$("div.tgnCOd").shouldBe(enabled, Duration.ofSeconds(20)).click();
		}
	}
	
	
	@Test
	public void UserCanLoginToGmail() {
		loginToGmail();
		$("a img.gb_rc").shouldBe(visible, Duration.ofSeconds(10));
	}
	
	
	@Test
	public void UserCanSendMessage() {
		// click write message button
		loginToGmail();

		$(write_msg_btn).shouldBe(enabled, Duration.ofSeconds(50)).click();
		
		// Кому
		$("div.wO.nr.l1 textarea.vO:nth-child(2)").shouldBe(enabled, Duration.ofSeconds(10)).setValue(USER_GMAIL_TOSEND);
		// Тема
		$("div.aoI input.aoT").shouldBe(enabled, Duration.ofSeconds(10)).setValue(MSG_THEME);
		// Текст сообщения
		$(".Am.Al.editable.LW-avf").shouldBe(enabled, Duration.ofSeconds(10)).setValue(FULL_MSG_TEXT);
		// Отправить
		$("div.T-I.J-J5-Ji.aoO.v7.T-I-atl.L3").shouldBe(enabled, Duration.ofSeconds(10)).click();
		
		$("span.bAq").shouldHave(text("Сообщение отправлено."), Duration.ofSeconds(30));  // Алерт вышел
		$("span#link_vsm").shouldBe(visible, Duration.ofSeconds(30)).click();             // Перейти к сообщению с алерта
		
		$("span.go").shouldHave(text(USER_GMAIL_TOSEND));  // Есть гмайл юзера, которому отправиоли сообщение
		Selenide.sleep(2000);
	 }	 
	
	
	@Test
	public void UserCanSendMessageWithNewSignature() {
		 
		loginToGmail();

		// Go to global settings from main page
		$("a.FH").shouldBe(visible, Duration.ofSeconds(50)).click();
		$("button.Tj").shouldBe(visible, Duration.ofSeconds(30)).click();
		$("div .nH.qZ.G-atb").shouldBe(visible, Duration.ofSeconds(60)).shouldHave(text("Настройки"));
		 
		// Переходим к кнопке создать новую подпись
		String createSignatureButton = "button.P5";
		$(createSignatureButton).scrollIntoView(true);
		$(createSignatureButton).shouldBe(enabled, Duration.ofSeconds(100)).click();
		 
		// Ввод названия и содержимого подписи
		$("input.xx.nr").shouldBe(enabled, Duration.ofSeconds(30)).setValue(SIGNATURE_NAME);
		$("button[name='ok']").shouldBe(enabled, Duration.ofSeconds(10)).click();  // Нажимаем создать (Введите название подписи)
		Selenide.sleep(2000);
		$("tr.r7:nth-child(29)").scrollIntoView(true);
		$("tr.r7:nth-child(29) tr div.Am").setValue(SIGNATURE_TEXT);
		 
		$("tr.r7:nth-child(29) label:nth-child(1) select").selectOptionContainingText(SIGNATURE_NAME);  // В НОВЫХ ПИСЬМАХ
		$("tr.r7:nth-child(29) label:nth-child(2) select").selectOptionContainingText(SIGNATURE_NAME);  // В ОТВЕТАХ И ПРИ ПЕРЕСЫЛКЕ
		$("tr.r7:nth-child(29) input[type=\"checkbox\"]").setSelected(true);	// Добавлять эту подпись перед цитируемым текстом
		Selenide.sleep(5000);  // пока подпись сохранится

		// Сохраняем настройки 
		String saveSettingsButton = "tr.r7:nth-child(33) button:nth-child(1)";  // PLEASE, WATCH ME https://ucarecdn.com/aa809e1f-2cf8-4e1a-94c0-55a76e4e345f/
		$(saveSettingsButton).scrollIntoView(true);
		$(saveSettingsButton).shouldBe(enabled, Duration.ofSeconds(20)).click();
		Selenide.sleep(7000);
		
		// Кнопка Написать на главное странице
		$(write_msg_btn).shouldBe(enabled, Duration.ofSeconds(50)).click();
		
	        // Кому
		$("div.wO.nr.l1 textarea.vO:nth-child(2)").shouldBe(enabled, Duration.ofSeconds(10)).setValue(USER_GMAIL_TOSEND);
		$("div.aoI input.aoT").shouldBe(enabled, Duration.ofSeconds(10)).setValue(MSG_THEME); 		      // Тема
		$(".Am.Al.editable.LW-avf").shouldBe(enabled, Duration.ofSeconds(10)).setValue(FULL_MSG_TEXT); 	  // message Text 
		// Добавляем подпись
		$("div.BP").shouldBe(enabled, Duration.ofSeconds(10)).click();  // pencil of signature icon
		$(byText(SIGNATURE_NAME)).shouldBe(enabled, Duration.ofSeconds(10)).click(); // По добавленной подписи выбираем из попам меню
		// Отправить
		$("div.T-I.J-J5-Ji.aoO.v7.T-I-atl.L3").shouldBe(enabled, Duration.ofSeconds(10)).click();
		Selenide.sleep(1000);
		$("span.bAq").shouldHave(text("Сообщение отправлено."), Duration.ofSeconds(30));  // Алерт, что сообщение отправлено
		$("span#link_vsm").shouldBe(visible, Duration.ofSeconds(30)).click();  // С алерта переходим на сообщение (в отправленные)
		 
		$("span.go").shouldHave(text(USER_GMAIL_TOSEND), Duration.ofSeconds(20));  // Сообщение отправлено тому (заданному отправителю)
		$("div[data-smartmail=gmail_signature]").shouldHave(text(SIGNATURE_TEXT), Duration.ofSeconds(20)); // Есть подпись	
		Selenide.sleep(2000);
		
	}	
}
