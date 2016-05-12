package Klassen;
import java.io.FileNotFoundException;
import java.util.List;

import Exceptions.EmptyFileException;
import Exceptions.MalformedInputException;
import Exceptions.WrongFileFormatException;

/**
 * Diese Klasse ist der Eintrittspunkt in das Programm
 * 
 * @author Joshua
 *
 */
public class Main {

	/**
	 * Mit diese Methode wird das Programm gestartet. Alle Dateipfade, die im
	 * Parameter args enthalten sind werden eingelesen und verarbeitet.
	 * 
	 * @param args
	 *            Dateipfade zu den einzulesenden Dateien
	 */
	public static void main(String[] args) {
		FileHandler fileHandler = new FileHandler();
		Verarbeiter v = new Verarbeiter();

		String[] dateien = {
				 "ulusd",
				"normal/IHK_Beispiel1.in", "normal/IHK_Beispiel2.in", "normal/IHK_Beispiel3.in",
				"normal/IHK_Beispiel4.in", "fehler/fehlerfall1_a.in", "fehler/fehlerfall1_b.in",
				"fehler/fehlerfall2.in", "fehler/fehlerfall3.in", "fehler/fehlerfall4.in", "sonder/sonderfall1.in",
				"sonder/sonderfall2.in", "test/test1.in", "test/test2.in", "test/test3.in",
				 "test/test4.in",
		};
		args = dateien;
				
		if (args.length == 0){
			System.out.println("[ERR] Es wurden keine Dateipfade angegeben");
		}
		for (String datei : args) {
			try {
				List<String> woerter = fileHandler.lese("files/" + datei);

				Long before = System.nanoTime();
				List<Wort> lsg = v.loese(woerter);
				List<Wort> lsgOpt = v.loeseOptimal(woerter);
				Long time = System.nanoTime() - before;

				Raetsel r = null; // bleibt null, wenn keine L�sung gefunden
									// wurde

				if (lsg != null) {
					r = new Raetsel(lsg);
				}
				Raetsel rOpt = null;
				if (lsgOpt != null) {

					rOpt = new Raetsel(lsgOpt);
				}

				fileHandler.schreibe(r, rOpt);
				System.out.println(" [OK] '" + datei + "' Time=" + time);
			} catch (WrongFileFormatException | EmptyFileException | MalformedInputException e) {
				fileHandler.schreibeFehler(e.getMessage());
				System.out.println("[ERR] '" + datei + "'");
			} catch (FileNotFoundException e) {
				System.out.println("[ERR] '" + datei + "' : Datei wurde nicht gefunden");
			}
		}
	}

}