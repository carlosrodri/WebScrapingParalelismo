package controllers;

import java.io.IOException;

import views.MainWindow;
import web.WebManager;

public class Controller {
	private MainWindow mainWindow;
	private WebManager webManager;

	public Controller() {
		mainWindow = new MainWindow();
		webManager = new WebManager();
			try {
				webManager.read();
				mainWindow.refresh(webManager.getImageList());
				try {
					webManager.list(webManager.read());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				webManager.initThread();
			} catch (IOException e) {
				e.printStackTrace();
			}
			webManager.stopThread();
	}
	
	public static void main(String[] args) {
		new Controller();
	}
}