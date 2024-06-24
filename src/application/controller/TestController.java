package application.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Paths;

import application.Song;

public class TestController {
	
	/*
	 * Diese Methode, sowie die TestController-Klasse, existieren
	 * ausschlieﬂlich zum Testen des Programms. Hiermit werden
	 * beim ersten Start automatisch Beispiellieder importiert.
	 */
	public static void generateExampleSongs() {
		MediaController mediaController = MediaController.getInstance();

		File file = new File("./saves/allSongsFile.txt");
		if (file.exists()) return;

		mediaController.addToAllSongs(new Song(
					"Dancing Queen", "ABBA", "Gold", "Pop",
					false, addKidaPath("01 Dancing Queen.mp3"), 
					mediaController.randomVideoPath()));
		mediaController.addToAllSongs(new Song(
				"Knowing Me, Knowing You", "ABBA", "Greatest Hits", "Pop",
				false, addKidaPath("02 Knowing Me, Knowing You.mp3"), 
				mediaController.randomVideoPath()));
		mediaController.addToAllSongs(new Song(
				"Mamma Mia", "ABBA", "Gold", "Pop",
				false, addKidaPath("04 Mamma Mia.mp3"), 
				mediaController.randomVideoPath()));
		mediaController.addToAllSongs(new Song(
				"I Walk The Line", "Johnny Cash", "I Walk The Line", "Country",
				false, addKidaPath("01 I Walk the Line.mp3"), 
				mediaController.randomVideoPath()));
		mediaController.addToAllSongs(new Song(
				"I Got Stripes", "Johnny Cash", "The Greatest Hits", "Country",
				false, addKidaPath("04 I Got Stripes.mp3"), 
				mediaController.randomVideoPath()));
		mediaController.addToAllSongs(new Song(
				"Jackson", "Johnny Cash", "At Folsom Prison", "Country",
				false, addKidaPath("14 Jackson.mp3"), 
				mediaController.randomVideoPath()));
		mediaController.addToAllSongs(new Song(
				"Jet Blue Jet", "Major Lazer", "Free The Universe", "Electronic",
				false, addKidaPath("Major Lazer - Jet Blue Jet (feat. Leftside, GTA, Razz & Biggy).mp3"), 
				mediaController.randomVideoPath()));
		mediaController.addToAllSongs(new Song(
				"Bubble But", "Major Lazer", "Free The Universe", "Electronic",
				false, addKidaPath("Major Lazer - Bubble Butt (feat. Bruno Mars, Tyga & Mystic).mp3"), 
				mediaController.randomVideoPath()));
		mediaController.addToAllSongs(new Song(
				"Scare Me", "Major Lazer", "Free The Universe", "Electronic",
				false, addKidaPath("Major Lazer - Scare Me (feat. Peaches & Timberlee).mp3"), 
				mediaController.randomVideoPath()));
		mediaController.addToAllSongs(new Song(
				"Tu Amor", "Andrea Bocelli", "Bocelli Al Colosseo", "KlassikPop",
				false, addKidaPath("Tu Amor.mp3"), 
				mediaController.randomVideoPath()));
		mediaController.addToAllSongs(new Song(
				"O Mio Babbino", "Andrea Bocelli", "Bocelli Al Colosseo", "KlassikPop",
				false, addKidaPath("O Mio Babbino.mp3"), 
				mediaController.randomVideoPath()));
		mediaController.addToAllSongs(new Song(
				"Ah, La Paterna Mano", "Andrea Bocelli", "Il Mare Calmo Della Sera", "KlassikPop",
				false, addKidaPath("Ah, La Paterna Mano (from 'Macbeth').mp3"), 
				mediaController.randomVideoPath()));
		mediaController.addToAllSongs(new Song(
				"I Found A Boy", "Adele", "21", "Pop",
				false, addKidaPath("14 Adele - I Found A Boy.mp3"), 
				mediaController.randomVideoPath()));
		mediaController.addToAllSongs(new Song(
				"Rumor Has It", "Adele", "21", "Pop",
				false, addKidaPath("02 Adele - Rumor Has It.mp3"), 
				mediaController.randomVideoPath()));
	}
	
	
	private static String addKidaPath(String raw) {
		try {
			return (Paths.get(Paths.get("").toAbsolutePath().toString() + "/examplesSongs/" + raw)).toUri().toURL().toExternalForm();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
	
}
