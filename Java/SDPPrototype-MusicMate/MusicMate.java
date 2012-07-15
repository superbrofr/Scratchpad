import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class MusicMate
{
	private static final int TREBLE_CLEFF_IMAGE_X = 5;
	private static final int BASS_CLEFF_IMAGE_X = 20;
	private static final int CLEFF_IMAGE_Y = 89;
	
	private static final int NOTE_COLUMN_1 = 400;
	private static final int NOTE_COLUMN_2 = 465;
	private static final int NOTE_COLUMN_3 = 530;
	private static final int NOTE_COLUMN_4 = 595;
	private static final int NOTE_COLUMN_5 = 660;
	private static final int NOTE_COLUMN_6 = 725;
	
	private static final int F_TREBLE_MOD_Y = 108;
	private static final int C_TREBLE_MOD_Y = 148;
	private static final int G_TREBLE_MOD_Y = 98;
	private static final int D_TREBLE_MOD_Y = 135;
	private static final int A_TREBLE_MOD_Y = 178;
	private static final int E_TREBLE_MOD_Y = 120;
	private static final int B_TREBLE_MOD_Y = 163;
	
	private static final int G_FLAT_TREBLE_MOD_Y = 193;
	private static final int F_FLAT_TREBLE_MOD_Y = 208;
	
	private static final int F_BASS_MOD_Y = 135;
	private static final int C_BASS_MOD_Y = 178;
	private static final int G_BASS_MOD_Y = 120;
	private static final int D_BASS_MOD_Y = 163;
	private static final int A_BASS_MOD_Y = 208;
	private static final int E_BASS_MOD_Y = 148;
	private static final int B_BASS_MOD_Y = 193;
	
	private static final int G_FLAT_BASS_MOD_Y = 220;
	private static final int F_FLAT_BASS_MOD_Y = 233;
	
	private static final int KEY_SIGNATURE_1 = 168;
	private static final int KEY_SIGNATURE_2 = 198;
	private static final int KEY_SIGNATURE_3 = 228;
	private static final int KEY_SIGNATURE_4 = 258;
	private static final int KEY_SIGNATURE_5 = 288;
	private static final int KEY_SIGNATURE_6 = 318;
	private static final int KEY_SIGNATURE_7 = 348;
	
	//position for each note
	private static final int D4_TREBLE = 168;
	private static final int E4_TREBLE = 158;
	private static final int F4_TREBLE = 145;
	private static final int G4_TREBLE = 128;
	private static final int A4_TREBLE = 118;
	private static final int B4_TREBLE = 100;
	private static final int C5_TREBLE = 88;
	private static final int D5_TREBLE = 75;
	private static final int E5_TREBLE = 58;
	private static final int F5_TREBLE = 45;
	private static final int G5_TREBLE = 33;
	
	private static final int F2_BASS = 168;
	private static final int G2_BASS = 158;
	private static final int A2_BASS = 145;
	private static final int B2_BASS = 120;
	private static final int C3_BASS = 118;
	private static final int D3_BASS = 100;
	private static final int E3_BASS = 88;
	private static final int F3_BASS = 75;
	private static final int G3_BASS = 58;
	private static final int A3_BASS = 45;
	private static final int B3_BASS = 33;
	
	private ArrayList<Note> notesToDraw;
	private int notesToDrawSize;
	private ArrayList<Note> drawNotes;
	
	private ArrayList<NoteModification> keySignature;
	private boolean drawKeySignature;
	
	private boolean copying;
	private boolean major;
	
	private JFrame wholeScreen;
	private JPanel stavePanel;
	private BufferedImage clefImage;
	private BufferedImage staveImage;
	private boolean trebleClef;
	
	//all textfields
	private JTextField noteToDisplay;
	private JComboBox noteTypeSelected;
	private JComboBox sameColumn;
	private JTextField keySelected;
	private JComboBox tonalitySelected;
	private JComboBox clefSelected;
	
	private int currentColumn; //can be between 1 - 6 inclusive.
	private String nextNoteType;
	
	public MusicMate()
	{
		keySignature = new ArrayList<NoteModification>();
		drawKeySignature = false;
		trebleClef = true;
		try
		{
			clefImage = ImageIO.read(new File("trebleClef.gif"));
		}
		catch(IOException e)
		{
			System.out.println("Error initialising clef image.");
		}
		notesToDraw = new ArrayList<Note>();
		drawNotes = new ArrayList<Note>();
		notesToDrawSize = 0;
		currentColumn = 1;
		setUpGUI();
	}
	
	public static void main(String[] args)
	{
		new MusicMate().run();
	}
	
	private void run()
	{
		while(true){}
	}
	
	private void processInputs()
	{
		if(!processClefInput()){
			displayErrorDialog("Invalid clef type! Clef type must be 'treble' or 'bass'." );
			return;
		}
		else if(!processTonalityInput()){
			displayErrorDialog("Invalid tonality! Tonality must be 'major' or 'minor'.");
			return;
		}
		else if(!processNoteType()){
			displayErrorDialog("Invalid note type! Enter 'semibreve', 'minim', 'crotchet' or 'quaver'.");
			return;
		}
		else if(!processColumn()){
			displayErrorDialog("Max columns reached!");
			return;
		}
		else if(!processNote()){
			displayErrorDialog("Invalid note! Enter notes in the range of D4b - G5# (treble) and G2b - B3# (bass).");
			return;
		}
		else if(!processKey()){
			displayErrorDialog("Invalid key! Enter a character from A - G with a '#' or 'b' modifier.");
			return;
		}
		drawToStavePanel();
	}
	
	private boolean processColumn()
	{
		String columnInput = (String)sameColumn.getSelectedItem();
		if(columnInput.length() != 1){
			return false;
		}
		else if(columnInput.equals("y")){
			return true;
		}
		else if(columnInput.equals("n")){
			if(currentColumn < 6){
				currentColumn++;
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	private boolean processNote()
	{
		String noteInput = noteToDisplay.getText().trim().toUpperCase();
		if((noteInput.length() < 2) || (noteInput.length() > 3)){
			return false;
		}
		else if(trebleClef){
			if(noteInput.charAt(0) == 'A'){
				if(noteInput.charAt(1) == '4'){
					if(noteInput.length() == 3){
						if(noteInput.charAt(2) == '#'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, A4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_1 - 20, A4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, A4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_2 - 20, A4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, A4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_3 - 20, A4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, A4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_4 - 20, A4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, A4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_5 - 20, A4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, A4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_6 - 20, A4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
						}
						else if(noteInput.charAt(2) == 'B'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, A4_TREBLE, new NoteModification("flat", NOTE_COLUMN_1 - 20, A4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, A4_TREBLE, new NoteModification("flat", NOTE_COLUMN_2 - 20, A4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, A4_TREBLE, new NoteModification("flat", NOTE_COLUMN_3 - 20, A4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, A4_TREBLE, new NoteModification("flat", NOTE_COLUMN_4 - 20, A4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, A4_TREBLE, new NoteModification("flat", NOTE_COLUMN_5 - 20, A4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, A4_TREBLE, new NoteModification("flat", NOTE_COLUMN_6 - 20, A4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
						}
					}
					else{
						if(currentColumn == 1){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, A4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 2){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, A4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 3){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, A4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 4){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, A4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 5){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, A4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 6){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, A4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
					}
				}
				else if(noteInput.charAt(1) == '5'){
					return false;
				}
			}
			else if(noteInput.charAt(0) == 'B'){
				if(noteInput.charAt(1) == '4'){
					if(noteInput.length() == 3){
						if(noteInput.charAt(2) == '#'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, B4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_1 - 20, B4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, B4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_2 - 20, B4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, B4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_3 - 20, B4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, B4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_4 - 20, B4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, B4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_5 - 20, B4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, B4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_6 - 20, B4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
						}
						else if(noteInput.charAt(2) == 'B'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, B4_TREBLE, new NoteModification("flat", NOTE_COLUMN_1 - 20, B4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, B4_TREBLE, new NoteModification("flat", NOTE_COLUMN_2 - 20, B4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, B4_TREBLE, new NoteModification("flat", NOTE_COLUMN_3 - 20, B4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, B4_TREBLE, new NoteModification("flat", NOTE_COLUMN_4 - 20, B4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, B4_TREBLE, new NoteModification("flat", NOTE_COLUMN_5 - 20, B4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, B4_TREBLE, new NoteModification("flat", NOTE_COLUMN_6 - 20, B4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
						}
					}
					else{
						if(currentColumn == 1){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, B4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 2){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, B4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 3){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, B4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 4){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, B4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 5){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, B4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 6){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, B4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
					}
				}
				else if(noteInput.charAt(1) == '5'){
					return false;
				}
			}
			else if(noteInput.charAt(0) == 'C'){
				if(noteInput.charAt(1) == '4'){
					return false;
				}
				else if(noteInput.charAt(1) == '5'){
					if(noteInput.length() == 3){
						if(noteInput.charAt(2) == '#'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, C5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_1 - 20, C5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, C5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_2 - 20, C5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, C5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_3 - 20, C5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, C5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_4 - 20, C5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, C5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_5 - 20, C5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, C5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_6 - 20, C5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
						}
						else if(noteInput.charAt(2) == 'B'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, C5_TREBLE, new NoteModification("flat", NOTE_COLUMN_1 - 20, C5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, C5_TREBLE, new NoteModification("flat", NOTE_COLUMN_2 - 20, C5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, C5_TREBLE, new NoteModification("flat", NOTE_COLUMN_3 - 20, C5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, C5_TREBLE, new NoteModification("flat", NOTE_COLUMN_4 - 20, C5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, C5_TREBLE, new NoteModification("flat", NOTE_COLUMN_5 - 20, C5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, C5_TREBLE, new NoteModification("flat", NOTE_COLUMN_6 - 20, C5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
						}
					}
					else{
						if(currentColumn == 1){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, C5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 2){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, C5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 3){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, C5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 4){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, C5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 5){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, C5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 6){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, C5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
					}
				}
			}
			else if(noteInput.charAt(0) == 'D'){
				if(noteInput.charAt(1) == '4'){
					if(noteInput.length() == 3){
						if(noteInput.charAt(2) == '#'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, D4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_1 - 20, D4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, D4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_2 - 20, D4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, D4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_3 - 20, D4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, D4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_4 - 20, D4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, D4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_5 - 20, D4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, D4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_6 - 20, D4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
						}
						else if(noteInput.charAt(2) == 'B'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, D4_TREBLE, new NoteModification("flat", NOTE_COLUMN_1 - 20, D4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, D4_TREBLE, new NoteModification("flat", NOTE_COLUMN_2 - 20, D4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, D4_TREBLE, new NoteModification("flat", NOTE_COLUMN_3 - 20, D4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, D4_TREBLE, new NoteModification("flat", NOTE_COLUMN_4 - 20, D4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, D4_TREBLE, new NoteModification("flat", NOTE_COLUMN_5 - 20, D4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, D4_TREBLE, new NoteModification("flat", NOTE_COLUMN_6 - 20, D4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
						}
					}
					else{
						if(currentColumn == 1){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, D4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 2){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, D4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 3){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, D4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 4){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, D4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 5){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, D4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 6){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, D4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
					}
				}
				else if(noteInput.charAt(1) == '5'){
					if(noteInput.length() == 3){
						if(noteInput.charAt(2) == '#'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, D5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_1 - 20, D5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, D5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_2 - 20, D5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, D5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_3 - 20, D5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, D5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_4 - 20, D5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, D5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_5 - 20, D5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, D5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_6 - 20, D5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
						}
						else if(noteInput.charAt(2) == 'B'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, D5_TREBLE, new NoteModification("flat", NOTE_COLUMN_1 - 20, D5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, D5_TREBLE, new NoteModification("flat", NOTE_COLUMN_2 - 20, D5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, D5_TREBLE, new NoteModification("flat", NOTE_COLUMN_3 - 20, D5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, D5_TREBLE, new NoteModification("flat", NOTE_COLUMN_4 - 20, D5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, D5_TREBLE, new NoteModification("flat", NOTE_COLUMN_5 - 20, D5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, D5_TREBLE, new NoteModification("flat", NOTE_COLUMN_6 - 20, D5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
						}
					}
					else{
						if(currentColumn == 1){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, D5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 2){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, D5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 3){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, D5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 4){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, D5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 5){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, D5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 6){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, D5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
					}
				}
			}
			else if(noteInput.charAt(0) == 'E'){
				if(noteInput.charAt(1) == '4'){
					if(noteInput.length() == 3){
						if(noteInput.charAt(2) == '#'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, E4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_1 - 20, E4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, E4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_2 - 20, E4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, E4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_3 - 20, E4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, E4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_4 - 20, E4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, E4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_5 - 20, E4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, E4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_6 - 20, E4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
						}
						else if(noteInput.charAt(2) == 'B'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, E4_TREBLE, new NoteModification("flat", NOTE_COLUMN_1 - 20, E4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, E4_TREBLE, new NoteModification("flat", NOTE_COLUMN_2 - 20, E4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, E4_TREBLE, new NoteModification("flat", NOTE_COLUMN_3 - 20, E4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, E4_TREBLE, new NoteModification("flat", NOTE_COLUMN_4 - 20, E4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, E4_TREBLE, new NoteModification("flat", NOTE_COLUMN_5 - 20, E4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, E4_TREBLE, new NoteModification("flat", NOTE_COLUMN_6 - 20, E4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
						}
					}
					else{
						if(currentColumn == 1){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, E4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 2){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, E4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 3){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, E4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 4){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, E4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 5){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, E4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 6){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, E4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
					}
				}
				else if(noteInput.charAt(1) == '5'){
					if(noteInput.length() == 3){
						if(noteInput.charAt(2) == '#'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, E5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_1 - 20, E5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, E5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_2 - 20, E5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, E5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_3 - 20, E5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, E5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_4 - 20, E5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, E5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_5 - 20, E5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, E5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_6 - 20, E5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
						}
						else if(noteInput.charAt(2) == 'B'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, E5_TREBLE, new NoteModification("flat", NOTE_COLUMN_1 - 20, E5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, E5_TREBLE, new NoteModification("flat", NOTE_COLUMN_2 - 20, E5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, E5_TREBLE, new NoteModification("flat", NOTE_COLUMN_3 - 20, E5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, E5_TREBLE, new NoteModification("flat", NOTE_COLUMN_4 - 20, E5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, E5_TREBLE, new NoteModification("flat", NOTE_COLUMN_5 - 20, E5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, E5_TREBLE, new NoteModification("flat", NOTE_COLUMN_6 - 20, E5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
						}
					}
					else{
						if(currentColumn == 1){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, E5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 2){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, E5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 3){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, E5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 4){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, E5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 5){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, E5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 6){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, E5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
					}
				}
			}
			else if(noteInput.charAt(0) == 'F'){
				if(noteInput.charAt(1) == '4'){
					if(noteInput.length() == 3){
						if(noteInput.charAt(2) == '#'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, F4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_1 - 20, F4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, F4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_2 - 20, F4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, F4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_3 - 20, F4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, F4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_4 - 20, F4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, F4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_5 - 20, F4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, F4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_6 - 20, F4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
						}
						else if(noteInput.charAt(2) == 'B'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, F4_TREBLE, new NoteModification("flat", NOTE_COLUMN_1 - 20, F4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, F4_TREBLE, new NoteModification("flat", NOTE_COLUMN_2 - 20, F4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, F4_TREBLE, new NoteModification("flat", NOTE_COLUMN_3 - 20, F4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, F4_TREBLE, new NoteModification("flat", NOTE_COLUMN_4 - 20, F4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, F4_TREBLE, new NoteModification("flat", NOTE_COLUMN_5 - 20, F4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, F4_TREBLE, new NoteModification("flat", NOTE_COLUMN_6 - 20, F4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
						}
					}
					else{
						if(currentColumn == 1){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, F4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 2){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, F4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 3){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, F4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 4){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, F4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 5){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, F4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 6){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, F4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
					}
				}
				else if(noteInput.charAt(1) == '5'){
					if(noteInput.length() == 3){
						if(noteInput.charAt(2) == '#'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, F5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_1 - 20, F5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, F5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_2 - 20, F5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, F5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_3 - 20, F5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, F5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_4 - 20, F5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, F5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_5 - 20, F5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, F5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_6 - 20, F5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
						}
						else if(noteInput.charAt(2) == 'B'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, F5_TREBLE, new NoteModification("flat", NOTE_COLUMN_1 - 20, F5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, F5_TREBLE, new NoteModification("flat", NOTE_COLUMN_2 - 20, F5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, F5_TREBLE, new NoteModification("flat", NOTE_COLUMN_3 - 20, F5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, F5_TREBLE, new NoteModification("flat", NOTE_COLUMN_4 - 20, F5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, F5_TREBLE, new NoteModification("flat", NOTE_COLUMN_5 - 20, F5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, F5_TREBLE, new NoteModification("flat", NOTE_COLUMN_6 - 20, F5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
						}
					}
					else{
						if(currentColumn == 1){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, F5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 2){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, F5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 3){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, F5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 4){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, F5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 5){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, F5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 6){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, F5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
					}
				}
			}
			else if(noteInput.charAt(0) == 'G'){
				if(noteInput.charAt(1) == '4'){
					if(noteInput.length() == 3){
						if(noteInput.charAt(2) == '#'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, G4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_1 - 20, G4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, G4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_2 - 20, G4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, G4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_3 - 20, G4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, G4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_4 - 20, G4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, G4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_5 - 20, G4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, G4_TREBLE, new NoteModification("sharp", NOTE_COLUMN_6 - 20, G4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
						}
						else if(noteInput.charAt(2) == 'B'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, G4_TREBLE, new NoteModification("flat", NOTE_COLUMN_1 - 20, G4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, G4_TREBLE, new NoteModification("flat", NOTE_COLUMN_2 - 20, G4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, G4_TREBLE, new NoteModification("flat", NOTE_COLUMN_3 - 20, G4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, G4_TREBLE, new NoteModification("flat", NOTE_COLUMN_4 - 20, G4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, G4_TREBLE, new NoteModification("flat", NOTE_COLUMN_5 - 20, G4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, G4_TREBLE, new NoteModification("flat", NOTE_COLUMN_6 - 20, G4_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
						}
					}
					else{
						if(currentColumn == 1){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, G4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 2){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, G4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 3){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, G4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 4){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, G4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 5){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, G4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 6){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, G4_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
					}
				}
				else if(noteInput.charAt(1) == '5'){
					if(noteInput.length() == 3){
						if(noteInput.charAt(2) == '#'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, G5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_1 - 20, G5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, G5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_2 - 20, G5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, G5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_3 - 20, G5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, G5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_4 - 20, G5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, G5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_5 - 20, G5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, G5_TREBLE, new NoteModification("sharp", NOTE_COLUMN_6 - 20, G5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
						}
						else if(noteInput.charAt(2) == 'B'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, G5_TREBLE, new NoteModification("flat", NOTE_COLUMN_1 - 20, G5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, G5_TREBLE, new NoteModification("flat", NOTE_COLUMN_2 - 20, G5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, G5_TREBLE, new NoteModification("flat", NOTE_COLUMN_3 - 20, G5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, G5_TREBLE, new NoteModification("flat", NOTE_COLUMN_4 - 20, G5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, G5_TREBLE, new NoteModification("flat", NOTE_COLUMN_5 - 20, G5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, G5_TREBLE, new NoteModification("flat", NOTE_COLUMN_6 - 20, G5_TREBLE + 65)));
								notesToDrawSize++;
								return true;
							}
						}
					}
					else{
						if(currentColumn == 1){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, G5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 2){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, G5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 3){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, G5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 4){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, G5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 5){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, G5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 6){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, G5_TREBLE, null));
							notesToDrawSize++;
							return true;
						}
					}
				}
			}
		}
		else if(!trebleClef){
			if(noteInput.charAt(0) == 'F'){
				if(noteInput.charAt(1) == '2'){
				  return false;
					// if(noteInput.length() == 3){
					//             if(noteInput.charAt(2) == '#'){
					//               if(currentColumn == 1){
					//                 notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, F2_BASS, new NoteModification("sharp", NOTE_COLUMN_1 - 20, F2_BASS + 65)));
					//                 notesToDrawSize++;
					//                 return true;
					//               }
					//               else if(currentColumn == 2){
					//                 notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, F2_BASS, new NoteModification("sharp", NOTE_COLUMN_2 - 20, F2_BASS + 65)));
					//                 notesToDrawSize++;
					//                 return true;
					//               }
					//               else if(currentColumn == 3){
					//                 notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, F2_BASS, new NoteModification("sharp", NOTE_COLUMN_3 - 20, F2_BASS + 65)));
					//                 notesToDrawSize++;
					//                 return true;
					//               }
					//               else if(currentColumn == 4){
					//                 notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, F2_BASS, new NoteModification("sharp", NOTE_COLUMN_4 - 20, F2_BASS + 65)));
					//                 notesToDrawSize++;
					//                 return true;
					//               }
					//               else if(currentColumn == 5){
					//                 notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, F2_BASS, new NoteModification("sharp", NOTE_COLUMN_5 - 20, F2_BASS + 65)));
					//                 notesToDrawSize++;
					//                 return true;
					//               }
					//               else if(currentColumn == 6){
					//                 notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, F2_BASS, new NoteModification("sharp", NOTE_COLUMN_6 - 20, F2_BASS + 65)));
					//                 notesToDrawSize++;
					//                 return true;
					//               }
					//             }
					//             else if(noteInput.charAt(2) == 'B'){
					//               if(currentColumn == 1){
					//                 notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, F2_BASS, new NoteModification("flat", NOTE_COLUMN_1 - 20, F2_BASS + 65)));
					//                 notesToDrawSize++;
					//                 return true;
					//               }
					//               else if(currentColumn == 2){
					//                 notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, F2_BASS, new NoteModification("flat", NOTE_COLUMN_2 - 20, F2_BASS + 65)));
					//                 notesToDrawSize++;
					//                 return true;
					//               }
					//               else if(currentColumn == 3){
					//                 notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, F2_BASS, new NoteModification("flat", NOTE_COLUMN_3 - 20, F2_BASS + 65)));
					//                 notesToDrawSize++;
					//                 return true;
					//               }
					//               else if(currentColumn == 4){
					//                 notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, F2_BASS, new NoteModification("flat", NOTE_COLUMN_4 - 20, F2_BASS + 65)));
					//                 notesToDrawSize++;
					//                 return true;
					//               }
					//               else if(currentColumn == 5){
					//                 notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, F2_BASS, new NoteModification("flat", NOTE_COLUMN_5 - 20, F2_BASS + 65)));
					//                 notesToDrawSize++;
					//                 return true;
					//               }
					//               else if(currentColumn == 6){
					//                 notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, F2_BASS, new NoteModification("flat", NOTE_COLUMN_6 - 20, F2_BASS + 65)));
					//                 notesToDrawSize++;
					//                 return true;
					//               }
					//             }
					//           }
					//           else{
					//             if(currentColumn == 1){
					//               notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, F2_BASS, null));
					//               notesToDrawSize++;
					//               return true;
					//             }
					//             else if(currentColumn == 2){
					//               notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, F2_BASS, null));
					//               notesToDrawSize++;
					//               return true;
					//             }
					//             else if(currentColumn == 3){
					//               notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, F2_BASS, null));
					//               notesToDrawSize++;
					//               return true;
					//             }
					//             else if(currentColumn == 4){
					//               notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, F2_BASS, null));
					//               notesToDrawSize++;
					//               return true;
					//             }
					//             else if(currentColumn == 5){
					//               notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, F2_BASS, null));
					//               notesToDrawSize++;
					//               return true;
					//             }
					//             else if(currentColumn == 6){
					//               notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, F2_BASS, null));
					//               notesToDrawSize++;
					//               return true;
					//             }
					//           }
				}
				else if(noteInput.charAt(1) == '3'){
					if(noteInput.length() == 3){
						if(noteInput.charAt(2) == '#'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, F3_BASS, new NoteModification("sharp", NOTE_COLUMN_1 - 20, F3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, F3_BASS, new NoteModification("sharp", NOTE_COLUMN_2 - 20, F3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, F3_BASS, new NoteModification("sharp", NOTE_COLUMN_3 - 20, F3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, F3_BASS, new NoteModification("sharp", NOTE_COLUMN_4 - 20, F3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, F3_BASS, new NoteModification("sharp", NOTE_COLUMN_5 - 20, F3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, F3_BASS, new NoteModification("sharp", NOTE_COLUMN_6 - 20, F3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
						}
						else if(noteInput.charAt(2) == 'B'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, F3_BASS, new NoteModification("flat", NOTE_COLUMN_1 - 20, F3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, F3_BASS, new NoteModification("flat", NOTE_COLUMN_2 - 20, F3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, F3_BASS, new NoteModification("flat", NOTE_COLUMN_3 - 20, F3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, F3_BASS, new NoteModification("flat", NOTE_COLUMN_4 - 20, F3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, F3_BASS, new NoteModification("flat", NOTE_COLUMN_5 - 20, F3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, F3_BASS, new NoteModification("flat", NOTE_COLUMN_6 - 20, F3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
						}
					}
					else{
						if(currentColumn == 1){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, F3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 2){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, F3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 3){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, F3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 4){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, F3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 5){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, F3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 6){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, F3_BASS, null));
							notesToDrawSize++;
							return true;
						}
					}
				}
			}
			else if(noteInput.charAt(0) == 'G'){
				if(noteInput.charAt(1) == '2'){
					if(noteInput.length() == 3){
						if(noteInput.charAt(2) == '#'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, G2_BASS, new NoteModification("sharp", NOTE_COLUMN_1 - 20, G2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, G2_BASS, new NoteModification("sharp", NOTE_COLUMN_2 - 20, G2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, G2_BASS, new NoteModification("sharp", NOTE_COLUMN_3 - 20, G2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, G2_BASS, new NoteModification("sharp", NOTE_COLUMN_4 - 20, G2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, G2_BASS, new NoteModification("sharp", NOTE_COLUMN_5 - 20, G2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, G2_BASS, new NoteModification("sharp", NOTE_COLUMN_6 - 20, G2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
						}
						else if(noteInput.charAt(2) == 'B'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, G2_BASS, new NoteModification("flat", NOTE_COLUMN_1 - 20, G2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, G2_BASS, new NoteModification("flat", NOTE_COLUMN_2 - 20, G2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, G2_BASS, new NoteModification("flat", NOTE_COLUMN_3 - 20, G2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, G2_BASS, new NoteModification("flat", NOTE_COLUMN_4 - 20, G2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, G2_BASS, new NoteModification("flat", NOTE_COLUMN_5 - 20, G2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, G2_BASS, new NoteModification("flat", NOTE_COLUMN_6 - 20, G2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
						}
					}
					else{
						if(currentColumn == 1){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, G2_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 2){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, G2_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 3){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, G2_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 4){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, G2_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 5){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, G2_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 6){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, G2_BASS, null));
							notesToDrawSize++;
							return true;
						}
					}
				}
				else if(noteInput.charAt(1) == '3'){
					if(noteInput.length() == 3){
						if(noteInput.charAt(2) == '#'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, G3_BASS, new NoteModification("sharp", NOTE_COLUMN_1 - 20, G3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, G3_BASS, new NoteModification("sharp", NOTE_COLUMN_2 - 20, G3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, G3_BASS, new NoteModification("sharp", NOTE_COLUMN_3 - 20, G3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, G3_BASS, new NoteModification("sharp", NOTE_COLUMN_4 - 20, G3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, G3_BASS, new NoteModification("sharp", NOTE_COLUMN_5 - 20, G3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, G3_BASS, new NoteModification("sharp", NOTE_COLUMN_6 - 20, G3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
						}
						else if(noteInput.charAt(2) == 'B'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, G3_BASS, new NoteModification("flat", NOTE_COLUMN_1 - 20, G3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, G3_BASS, new NoteModification("flat", NOTE_COLUMN_2 - 20, G3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, G3_BASS, new NoteModification("flat", NOTE_COLUMN_3 - 20, G3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, G3_BASS, new NoteModification("flat", NOTE_COLUMN_4 - 20, G3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, G3_BASS, new NoteModification("flat", NOTE_COLUMN_5 - 20, G3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, G3_BASS, new NoteModification("flat", NOTE_COLUMN_6 - 20, G3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
						}
					}
					else{
						if(currentColumn == 1){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, G3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 2){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, G3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 3){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, G3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 4){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, G3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 5){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, G3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 6){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, G3_BASS, null));
							notesToDrawSize++;
							return true;
						}
					}
				}
			}
			else if(noteInput.charAt(0) == 'A'){
				if(noteInput.charAt(1) == '2'){
					if(noteInput.length() == 3){
						if(noteInput.charAt(2) == '#'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, A2_BASS, new NoteModification("sharp", NOTE_COLUMN_1 - 20, A2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, A2_BASS, new NoteModification("sharp", NOTE_COLUMN_2 - 20, A2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, A2_BASS, new NoteModification("sharp", NOTE_COLUMN_3 - 20, A2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, A2_BASS, new NoteModification("sharp", NOTE_COLUMN_4 - 20, A2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, A2_BASS, new NoteModification("sharp", NOTE_COLUMN_5 - 20, A2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, A2_BASS, new NoteModification("sharp", NOTE_COLUMN_6 - 20, A2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
						}
						else if(noteInput.charAt(2) == 'B'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, A2_BASS, new NoteModification("flat", NOTE_COLUMN_1 - 20, A2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, A2_BASS, new NoteModification("flat", NOTE_COLUMN_2 - 20, A2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, A2_BASS, new NoteModification("flat", NOTE_COLUMN_3 - 20, A2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, A2_BASS, new NoteModification("flat", NOTE_COLUMN_4 - 20, A2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, A2_BASS, new NoteModification("flat", NOTE_COLUMN_5 - 20, A2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, A2_BASS, new NoteModification("flat", NOTE_COLUMN_6 - 20, A2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
						}
					}
					else{
						if(currentColumn == 1){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, A2_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 2){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, A2_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 3){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, A2_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 4){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, A2_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 5){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, A2_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 6){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, A2_BASS, null));
							notesToDrawSize++;
							return true;
						}
					}
				}
				else if(noteInput.charAt(1) == '3'){
					if(noteInput.length() == 3){
						if(noteInput.charAt(2) == '#'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, A3_BASS, new NoteModification("sharp", NOTE_COLUMN_1 - 20, A3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, A3_BASS, new NoteModification("sharp", NOTE_COLUMN_2 - 20, A3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, A3_BASS, new NoteModification("sharp", NOTE_COLUMN_3 - 20, A3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, A3_BASS, new NoteModification("sharp", NOTE_COLUMN_4 - 20, A3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, A3_BASS, new NoteModification("sharp", NOTE_COLUMN_5 - 20, A3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, A3_BASS, new NoteModification("sharp", NOTE_COLUMN_6 - 20, A3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
						}
						else if(noteInput.charAt(2) == 'B'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, A3_BASS, new NoteModification("flat", NOTE_COLUMN_1 - 20, A3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, A3_BASS, new NoteModification("flat", NOTE_COLUMN_2 - 20, A3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, A3_BASS, new NoteModification("flat", NOTE_COLUMN_3 - 20, A3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, A3_BASS, new NoteModification("flat", NOTE_COLUMN_4 - 20, A3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, A3_BASS, new NoteModification("flat", NOTE_COLUMN_5 - 20, A3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, A3_BASS, new NoteModification("flat", NOTE_COLUMN_6 - 20, A3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
						}
					}
					else{
						if(currentColumn == 1){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, A3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 2){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, A3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 3){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, A3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 4){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, A3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 5){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, A3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 6){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, A3_BASS, null));
							notesToDrawSize++;
							return true;
						}
					}
				}
			}
			else if(noteInput.charAt(0) == 'B'){
				if(noteInput.charAt(1) == '2'){
					if(noteInput.length() == 3){
						if(noteInput.charAt(2) == '#'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, B2_BASS, new NoteModification("sharp", NOTE_COLUMN_1 - 20, B2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, B2_BASS, new NoteModification("sharp", NOTE_COLUMN_2 - 20, B2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, B2_BASS, new NoteModification("sharp", NOTE_COLUMN_3 - 20, B2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, B2_BASS, new NoteModification("sharp", NOTE_COLUMN_4 - 20, B2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, B2_BASS, new NoteModification("sharp", NOTE_COLUMN_5 - 20, B2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, B2_BASS, new NoteModification("sharp", NOTE_COLUMN_6 - 20, B2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
						}
						else if(noteInput.charAt(2) == 'B'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, B2_BASS, new NoteModification("flat", NOTE_COLUMN_1 - 20, B2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, B2_BASS, new NoteModification("flat", NOTE_COLUMN_2 - 20, B2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, B2_BASS, new NoteModification("flat", NOTE_COLUMN_3 - 20, B2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, B2_BASS, new NoteModification("flat", NOTE_COLUMN_4 - 20, B2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, B2_BASS, new NoteModification("flat", NOTE_COLUMN_5 - 20, B2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, B2_BASS, new NoteModification("flat", NOTE_COLUMN_6 - 20, B2_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
						}
					}
					else{
						if(currentColumn == 1){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, B2_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 2){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, B2_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 3){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, B2_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 4){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, B2_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 5){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, B2_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 6){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, B2_BASS, null));
							notesToDrawSize++;
							return true;
						}
					}
				}
				else if(noteInput.charAt(1) == '3'){
					if(noteInput.length() == 3){
						if(noteInput.charAt(2) == '#'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, B3_BASS, new NoteModification("sharp", NOTE_COLUMN_1 - 20, B3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, B3_BASS, new NoteModification("sharp", NOTE_COLUMN_2 - 20, B3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, B3_BASS, new NoteModification("sharp", NOTE_COLUMN_3 - 20, B3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, B3_BASS, new NoteModification("sharp", NOTE_COLUMN_4 - 20, B3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, B3_BASS, new NoteModification("sharp", NOTE_COLUMN_5 - 20, B3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, B3_BASS, new NoteModification("sharp", NOTE_COLUMN_6 - 20, B3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
						}
						else if(noteInput.charAt(2) == 'B'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, B3_BASS, new NoteModification("flat", NOTE_COLUMN_1 - 20, B3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, B3_BASS, new NoteModification("flat", NOTE_COLUMN_2 - 20, B3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, B3_BASS, new NoteModification("flat", NOTE_COLUMN_3 - 20, B3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, B3_BASS, new NoteModification("flat", NOTE_COLUMN_4 - 20, B3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, B3_BASS, new NoteModification("flat", NOTE_COLUMN_5 - 20, B3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, B3_BASS, new NoteModification("flat", NOTE_COLUMN_6 - 20, B3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
						}
					}
					else{
						if(currentColumn == 1){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, B3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 2){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, B3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 3){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, B3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 4){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, B3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 5){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, B3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 6){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, B3_BASS, null));
							notesToDrawSize++;
							return true;
						}
					}
				}
			}
			else if(noteInput.charAt(0) == 'C'){
				if(noteInput.charAt(1) == '2'){
					return false;
				}
				else if(noteInput.charAt(1) == '3'){
					if(noteInput.length() == 3){
						if(noteInput.charAt(2) == '#'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, C3_BASS, new NoteModification("sharp", NOTE_COLUMN_1 - 20, C3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, C3_BASS, new NoteModification("sharp", NOTE_COLUMN_2 - 20, C3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, C3_BASS, new NoteModification("sharp", NOTE_COLUMN_3 - 20, C3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, C3_BASS, new NoteModification("sharp", NOTE_COLUMN_4 - 20, C3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, C3_BASS, new NoteModification("sharp", NOTE_COLUMN_5 - 20, C3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, C3_BASS, new NoteModification("sharp", NOTE_COLUMN_6 - 20, C3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
						}
						else if(noteInput.charAt(2) == 'B'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, C3_BASS, new NoteModification("flat", NOTE_COLUMN_1 - 20, C3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, C3_BASS, new NoteModification("flat", NOTE_COLUMN_2 - 20, C3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, C3_BASS, new NoteModification("flat", NOTE_COLUMN_3 - 20, C3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, C3_BASS, new NoteModification("flat", NOTE_COLUMN_4 - 20, C3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, C3_BASS, new NoteModification("flat", NOTE_COLUMN_5 - 20, C3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, C3_BASS, new NoteModification("flat", NOTE_COLUMN_6 - 20, C3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
						}
					}
					else{
						if(currentColumn == 1){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, C3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 2){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, C3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 3){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, C3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 4){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, C3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 5){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, C3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 6){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, C3_BASS, null));
							notesToDrawSize++;
							return true;
						}
					}
				}
			}
			else if(noteInput.charAt(0) == 'D'){
				if(noteInput.charAt(1) == '2'){
					return false;
				}
				else if(noteInput.charAt(1) == '3'){
					if(noteInput.length() == 3){
						if(noteInput.charAt(2) == '#'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, D3_BASS, new NoteModification("sharp", NOTE_COLUMN_1 - 20, D3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, D3_BASS, new NoteModification("sharp", NOTE_COLUMN_2 - 20, D3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, D3_BASS, new NoteModification("sharp", NOTE_COLUMN_3 - 20, D3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, D3_BASS, new NoteModification("sharp", NOTE_COLUMN_4 - 20, D3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, D3_BASS, new NoteModification("sharp", NOTE_COLUMN_5 - 20, D3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, D3_BASS, new NoteModification("sharp", NOTE_COLUMN_6 - 20, D3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
						}
						else if(noteInput.charAt(2) == 'B'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, D3_BASS, new NoteModification("flat", NOTE_COLUMN_1 - 20, D3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, D3_BASS, new NoteModification("flat", NOTE_COLUMN_2 - 20, D3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, D3_BASS, new NoteModification("flat", NOTE_COLUMN_3 - 20, D3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, D3_BASS, new NoteModification("flat", NOTE_COLUMN_4 - 20, D3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, D3_BASS, new NoteModification("flat", NOTE_COLUMN_5 - 20, D3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, D3_BASS, new NoteModification("flat", NOTE_COLUMN_6 - 20, D3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
						}
					}
					else{
						if(currentColumn == 1){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, D3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 2){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, D3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 3){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, D3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 4){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, D3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 5){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, D3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 6){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, D3_BASS, null));
							notesToDrawSize++;
							return true;
						}
					}
				}
			}
			else if(noteInput.charAt(0) == 'E'){
				if(noteInput.charAt(1) == '2'){
					return false;
				}
				else if(noteInput.charAt(1) == '3'){
					if(noteInput.length() == 3){
						if(noteInput.charAt(2) == '#'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, E3_BASS, new NoteModification("sharp", NOTE_COLUMN_1 - 20, E3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, E3_BASS, new NoteModification("sharp", NOTE_COLUMN_2 - 20, E3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, E3_BASS, new NoteModification("sharp", NOTE_COLUMN_3 - 20, E3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, E3_BASS, new NoteModification("sharp", NOTE_COLUMN_4 - 20, E3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, E3_BASS, new NoteModification("sharp", NOTE_COLUMN_5 - 20, E3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, E3_BASS, new NoteModification("sharp", NOTE_COLUMN_6 - 20, E3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
						}
						else if(noteInput.charAt(2) == 'B'){
							if(currentColumn == 1){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, E3_BASS, new NoteModification("flat", NOTE_COLUMN_1 - 20, E3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 2){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, E3_BASS, new NoteModification("flat", NOTE_COLUMN_2 - 20, E3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 3){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, E3_BASS, new NoteModification("flat", NOTE_COLUMN_3 - 20, E3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 4){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, E3_BASS, new NoteModification("flat", NOTE_COLUMN_4 - 20, E3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 5){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, E3_BASS, new NoteModification("flat", NOTE_COLUMN_5 - 20, E3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
							else if(currentColumn == 6){
								notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, E3_BASS, new NoteModification("flat", NOTE_COLUMN_6 - 20, E3_BASS + 65)));
								notesToDrawSize++;
								return true;
							}
						}
					}
					else{
						if(currentColumn == 1){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_1, E3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 2){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_2, E3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 3){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_3, E3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 4){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_4, E3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 5){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_5, E3_BASS, null));
							notesToDrawSize++;
							return true;
						}
						else if(currentColumn == 6){
							notesToDraw.add(new Note(nextNoteType, NOTE_COLUMN_6, E3_BASS, null));
							notesToDrawSize++;
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean processKey()
	{
		String inputKey = keySelected.getText().trim();
		drawKeySignature = false;
		if(inputKey.toUpperCase().equals("A")){
			setKeyToA();
			return true;
		}
		else if(inputKey.toUpperCase().equals("B")){
			setKeyToB();
			return true;
		}
		else if(inputKey.toUpperCase().equals("C")){
			setKeyToC();
			return true;
		}
		else if(inputKey.toUpperCase().equals("D")){
			setKeyToD();
			return true;
		}
		else if(inputKey.toUpperCase().equals("E")){
			setKeyToE();
			return true;
		}
		else if(inputKey.toUpperCase().equals("F")){
			setKeyToF();
			return true;
		}
		else if(inputKey.toUpperCase().equals("G")){
			setKeyToG();
			return true;
		}
		else if(inputKey.toUpperCase().equals("BB")){
			setKeyToBb();
			return true;
		}
		else if(inputKey.toUpperCase().equals("EB")){
			setKeyToEb();
			return true;
		}
		else if(inputKey.toUpperCase().equals("AB")){
			setKeyToAb();
			return true;
		}
		else if(inputKey.toUpperCase().equals("C#")){
			setKeyToCSharp();
			return true;
		}
		else if(inputKey.toUpperCase().equals("F#")){
			setKeyToFSharp();
			return true;
		}
		else{
			drawKeySignature = false;
			return false;
		}
	}
	
	private void setKeyToFSharp()
	{
		if(trebleClef){
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_1, F_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_2, C_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_3, G_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_4, D_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_5, A_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_6, E_TREBLE_MOD_Y));
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_1, F_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_2, C_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_3, G_TREBLE_MOD_Y));
				copying = false;
			}
		}
		else{
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_1, F_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_2, C_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_3, G_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_4, D_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_5, A_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_6, E_BASS_MOD_Y));
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_1, F_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_2, C_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_3, G_BASS_MOD_Y));
				copying = false;
			}
		}
		drawKeySignature = true;
	}
	
	private void setKeyToCSharp()
	{
		if(trebleClef){
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_1, F_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_2, C_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_3, G_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_4, D_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_5, A_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_6, E_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_7, B_TREBLE_MOD_Y));
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_1, F_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_2, C_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_3, G_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_4, D_TREBLE_MOD_Y));
				copying = false;
			}
		}
		else{
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_1, F_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_2, C_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_3, G_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_4, D_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_5, A_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_6, E_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_7, B_BASS_MOD_Y));
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_1, F_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_2, C_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_3, G_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_4, D_BASS_MOD_Y));
				copying = false;
			}
		}
		drawKeySignature = true;
	}
	
	private void setKeyToAb()
	{
		if(trebleClef){
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_1, B_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_2, E_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_3, A_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_4, D_TREBLE_MOD_Y));
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_1, B_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_2, E_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_3, A_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_4, D_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_5, G_FLAT_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_6, C_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_7, F_FLAT_TREBLE_MOD_Y));
				copying = false;
			}
		}
		else{
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_1, B_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_2, E_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_3, A_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_4, D_BASS_MOD_Y));
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_1, B_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_2, E_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_3, A_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_4, D_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_5, G_FLAT_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_6, C_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_7, F_FLAT_BASS_MOD_Y));
				copying = false;
			}
		}
		drawKeySignature = true;
	}
	
	private void setKeyToEb()
	{
		if(trebleClef){
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_1, B_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_2, E_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_3, A_TREBLE_MOD_Y));
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_1, B_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_2, E_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_3, A_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_4, D_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_5, G_FLAT_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_6, C_TREBLE_MOD_Y));
				copying = false;
			}
		}
		else{
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_1, B_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_2, E_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_3, A_BASS_MOD_Y));
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_1, B_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_2, E_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_3, A_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_4, D_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_5, G_FLAT_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_6, C_BASS_MOD_Y));
				copying = false;
			}
		}
		drawKeySignature = true;
	}
	
	private void setKeyToBb()
	{
		if(trebleClef){
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_1, B_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_2, E_TREBLE_MOD_Y));
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_1, B_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_2, E_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_3, A_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_4, D_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_5, G_FLAT_TREBLE_MOD_Y));
				copying = false;
			}
		}
		else{
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_1, B_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_2, E_BASS_MOD_Y));
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_1, B_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_2, E_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_3, A_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_4, D_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_5, G_FLAT_BASS_MOD_Y));
				copying = false;
			}
		}
		drawKeySignature = true;
	}
	
	private void setKeyToA()
	{
		if(trebleClef){
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_1, F_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_2, C_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_3, G_TREBLE_MOD_Y));
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				copying = false;
			}
		}
		else{
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_1, F_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_2, C_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_3, G_BASS_MOD_Y));
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				copying = false;
			}
		}
		drawKeySignature = true;
	}
	
	private void setKeyToB()
	{
		if(trebleClef){
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_1, F_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_2, C_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_3, G_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_4, D_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_5, A_TREBLE_MOD_Y));
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_1, F_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_2, C_TREBLE_MOD_Y));
				copying = false;
			}
		}
		else{
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_1, F_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_2, C_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_3, G_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_4, D_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_5, A_BASS_MOD_Y));
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_1, F_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_2, C_BASS_MOD_Y));
				copying = false;
			}
		}
		drawKeySignature = true;
	}
	
	private void setKeyToC()
	{
		if(trebleClef){
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_1, B_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_2, E_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_3, A_TREBLE_MOD_Y));
				copying = false;
			}
		}
		else{
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_1, B_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_2, E_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_3, A_BASS_MOD_Y));
				copying = false;
			}
		}
		drawKeySignature = true;
	}
	
	private void setKeyToD()
	{
		if(trebleClef){
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_1, F_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_2, C_TREBLE_MOD_Y));
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_1, B_TREBLE_MOD_Y));
				copying = false;
			}
		}
		else{
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_1, F_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_2, C_BASS_MOD_Y));
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_1, B_BASS_MOD_Y));
				copying = false;
			}
		}
		drawKeySignature = true;
	}
	
	private void setKeyToE()
	{
		if(trebleClef){
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_1, F_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_2, C_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_3, G_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_4, D_TREBLE_MOD_Y));
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_1, F_TREBLE_MOD_Y));
				copying = false;
			}
		}
		else{
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_1, F_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_2, C_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_3, G_BASS_MOD_Y));
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_4, D_BASS_MOD_Y));
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_1, F_BASS_MOD_Y));
				copying = false;
			}
		}
		drawKeySignature = true;
	}
	
	private void setKeyToF()
	{
		if(trebleClef){
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_1, B_TREBLE_MOD_Y));
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_1, B_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_2, E_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_3, A_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_4, D_TREBLE_MOD_Y));
				copying = false;
			}
		}
		else{
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_1, B_BASS_MOD_Y));
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_1, B_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_2, E_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_3, A_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_4, D_BASS_MOD_Y));
				copying = false;
			}
		}
		drawKeySignature = true;
	}
	
	private void setKeyToG()
	{
		if(trebleClef){
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_1, F_TREBLE_MOD_Y));
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_1, B_TREBLE_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_2, E_TREBLE_MOD_Y));
				copying = false;
			}
		}
		else{
			if(major){
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("sharp", KEY_SIGNATURE_1, F_BASS_MOD_Y));
				copying = false;
			}
			else{
				copying = true;
				keySignature.clear();
				clearScreen();
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_1, B_BASS_MOD_Y));
				keySignature.add(new NoteModification("flat", KEY_SIGNATURE_2, E_BASS_MOD_Y));
				copying = false;
			}
		}
		drawKeySignature = true;
	}
	
	private boolean processNoteType()
	{
		String inputNoteType = (String)noteTypeSelected.getSelectedItem();
		if(inputNoteType.toUpperCase().equals("SEMIBREVE")){
			while(copying){}
			nextNoteType = "semibreve";
			return true;
		}
		else if(inputNoteType.toUpperCase().equals("MINIM")){
			while(copying){}
			nextNoteType = "minim";
			return true;
		}
		else if(inputNoteType.toUpperCase().equals("CROTCHET")){
			while(copying){}
			nextNoteType = "crotchet";
			return true;
		}
		else if(inputNoteType.toUpperCase().equals("QUAVER")){
			while(copying){}
			nextNoteType = "quaver";
			return true;
		}
		return false;
	}
	
	private boolean processTonalityInput()
	{
		String inputTonality = (String)tonalitySelected.getSelectedItem();
		if(inputTonality.toUpperCase().equals("MAJOR")){
			major = true;
			return true;
		}
		else if(inputTonality.toUpperCase().equals("MINOR")){
			major = false;
			return true;
		}
		else
			return false;
	}
	
	private boolean processClefInput()
	{
		try
		{
			String inputClef = (String)clefSelected.getSelectedItem();
			if(inputClef.toUpperCase().equals("TREBLE")){
				if(trebleClef){
					return true;
				}
				else{
					copying = true;
					notesToDraw.clear();
					notesToDrawSize = 0;
					currentColumn = 1;
					clefImage = ImageIO.read(new File("trebleClef.gif"));
					trebleClef = true;
					clearScreen();
					copying = false;
					return true;
				}
			}
			else if(inputClef.toUpperCase().equals("BASS")){
				if(!trebleClef){
					return true;
				}
				else{
					copying = true;
					notesToDraw.clear();
					notesToDrawSize = 0;
					currentColumn = 1;
					clefImage = ImageIO.read(new File("bassClef.gif"));
					trebleClef = false;
					clearScreen();
					copying = false;
					return true;
				}
			}
			else
				return false;
		}
		catch(IOException e)
		{
			System.out.println("Error reading clef image.");
			return false;
		}
	}
	
	private void clearAll()
	{
		currentColumn = 1;
		keySignature.clear();
		notesToDraw.clear();
		notesToDrawSize = 0;
		drawNotes.clear();
		nextNoteType = "";
		Graphics2D g = (Graphics2D)stavePanel.getRootPane().getGraphics();
		g.drawImage(staveImage, null, 0, 89);
	}
	
	private void clearScreen()
	{
		Graphics2D g = (Graphics2D)stavePanel.getRootPane().getGraphics();
		g.drawImage(staveImage, null, 0, 89);
		drawToStavePanel();
	}
	
	private void drawToStavePanel()
	{
		Graphics2D g = (Graphics2D)stavePanel.getRootPane().getGraphics();
		if(notesToDraw.size() != 0){
  		if(trebleClef)
  			g.drawImage(clefImage, null, TREBLE_CLEFF_IMAGE_X, CLEFF_IMAGE_Y);
  		else
  			g.drawImage(clefImage, null, BASS_CLEFF_IMAGE_X, CLEFF_IMAGE_Y);
		}
		
		if(notesToDrawSize != drawNotes.size()){
			copying = true;
			copyNoteList();
			copying = false;
		}
		for(Note n: drawNotes){
			g.drawImage(n.getImage(), null, n.getX(), n.getY());
			if(n.getMod() != null){
				g.drawImage(n.getMod().getImage(), null, n.getMod().getX(), n.getMod().getY());
			}
		}
		
		if(!copying){
			for(NoteModification nm : keySignature){
				g.drawImage(nm.getImage(), null, nm.getX(), nm.getY());
			}
		}
	}
	
	private void displayErrorDialog(String errorMessage)
	{
		final JDialog dialog = new JDialog(wholeScreen, "Error!", true);
		JPanel dialogPanel = new JPanel(new GridLayout(2, 1));
		
		JLabel errorMessageLabel = new JLabel("   " + errorMessage);
		errorMessageLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener(){
																public void actionPerformed(ActionEvent e){
																	dialog.setVisible(false);
																}
															});
		
		dialogPanel.add(errorMessageLabel);
		dialogPanel.add(okButton);
		dialog.add(dialogPanel);
		
		dialog.setSize(new Dimension(700, 100));
		dialog.setVisible(true);
	}
	
	private void setUpGUI()
	{
		try
		{
			staveImage = ImageIO.read(new File("stave.jpg"));
		}
		catch(IOException e)
		{
			System.out.println("Error reading stave file.");
		}
		
		
		wholeScreen = new JFrame("Music Mate");
		Container contentPane = wholeScreen.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		//create textareas for note, note type, same column text boxes
		//uses a border layout
		JPanel noteTypePositionPanel = new JPanel(new GridLayout(1, 3));
			//note to display
		JPanel notePanel = new JPanel(new GridLayout(1, 2));
		noteToDisplay = new JTextField();
		noteToDisplay.setBorder(new LineBorder(Color.BLACK));
		JLabel noteToDisplayLabel = new JLabel("  Note: ");
		notePanel.add(noteToDisplayLabel);
		notePanel.add(noteToDisplay);
			//note type to display
		JPanel noteTypePanel = new JPanel(new GridLayout(1, 2));
		String[] noteTypes = {"semibreve", "minim", "crotchet", "quaver"};
		noteTypeSelected = new JComboBox(noteTypes);
		JLabel noteTypeSelectedLabel = new JLabel("  Note Type: ");
		noteTypePanel.add(noteTypeSelectedLabel);
		noteTypePanel.add(noteTypeSelected);
			//same column
		JPanel columnPanel = new JPanel(new GridLayout(1, 2));
		String[] columnOptions = {"y", "n"};
		sameColumn = new JComboBox(columnOptions);
		JLabel sameColumnLabel = new JLabel("  Same Column [y/n]: ");
		columnPanel.add(sameColumnLabel);
		columnPanel.add(sameColumn);
		//add all to panels
		noteTypePositionPanel.add(notePanel);
		noteTypePositionPanel.add(noteTypePanel);
		noteTypePositionPanel.add(columnPanel);
		
		//create add button
		JPanel addButtonPanel = new JPanel();
		JButton addButton = new JButton("CONFIRM");
		addButton.setPreferredSize(new Dimension(800, 20));
		addButton.addActionListener(new ActionListener(){
																	public void actionPerformed(ActionEvent e){
																		processInputs();
																	}
																});
		addButtonPanel.add(addButton);
		
		//create gird and textareas for key, tonality and clef
		JPanel keyTonalityClefPanel = new JPanel(new GridLayout(1, 3));
			//key selected
		JPanel keyPanel = new JPanel(new GridLayout(1, 2));
		keySelected = new JTextField();
		JLabel keyLabel = new JLabel("  Key: ");
		keySelected.setBorder(new LineBorder(Color.BLACK));
		keyPanel.add(keyLabel);
		keyPanel.add(keySelected);
			//tonality selected
		JPanel tonalityPanel = new JPanel(new GridLayout(1, 2));
		String[] tonalityOptions = {"major", "minor"};
		tonalitySelected = new JComboBox(tonalityOptions);
		JLabel tonalityLabel = new JLabel("  Tonality: ");
		tonalityPanel.add(tonalityLabel);
		tonalityPanel.add(tonalitySelected);
			//clef selected
		JPanel clefPanel = new JPanel(new GridLayout(1, 2));
		String[] clefOptions = {"treble", "bass"};
		clefSelected = new JComboBox(clefOptions);
		JLabel clefLabel = new JLabel("  Clef: ");
		clefPanel.add(clefLabel);
		clefPanel.add(clefSelected);
		//add all to panels
		keyTonalityClefPanel.add(keyPanel);
		keyTonalityClefPanel.add(tonalityPanel);
		keyTonalityClefPanel.add(clefPanel);
		
		//create image of stave
		stavePanel = new JPanel();
		ImageIcon staveIcon = new ImageIcon("stave.jpg");
		JLabel staveLabel = new JLabel();
		staveLabel.setIcon(staveIcon);
		stavePanel.add(staveLabel);
		
		//create reset button
		JPanel resetButtonPanel = new JPanel();
		JButton resetButton = new JButton("RESET");
		resetButton.setPreferredSize(new Dimension(800, 20));
		resetButton.addActionListener(new ActionListener(){
																	public void actionPerformed(ActionEvent e){
																		clearAll();
																	}
																});
		resetButtonPanel.add(resetButton);
		
		//add panels to the content pane
		contentPane.add(noteTypePositionPanel);
		contentPane.add(addButtonPanel);
		contentPane.add(keyTonalityClefPanel);
		contentPane.add(stavePanel);
		contentPane.add(resetButtonPanel);
		
		wholeScreen.addWindowListener(new WindowAdapter(){
																		public void windowClosing(WindowEvent e){
																			System.exit(1);
																		}
																	});
		
		wholeScreen.pack();
		wholeScreen.setVisible(true);
	}
	
	private void copyNoteList()
	{
		drawNotes = new ArrayList<Note>();
		for(Note n : notesToDraw){
			if(n.getMod() != null)
				drawNotes.add(new Note(n.getNoteType(), n.getX(), n.getY(), new NoteModification(n.getMod().getNoteType(), n.getMod().getX(), n.getMod().getY())));
			else
				drawNotes.add(new Note(n.getNoteType(), n.getX(), n.getY(), null));
		}
	}
}