import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.chainsaw.Main;

public class WriteToCSV {
	private File saveFile = null; 
	private UserInput userInput;
	private ArrayList<FormattedData> data;
	
	public WriteToCSV(ArrayList<FormattedData> d) { 
		data = d;
	}
	
	public WriteToCSV(ArrayList<FormattedData> d, UserInput user) {
		userInput = user;
		data = d;
	}
	
	public String formatHeader(String head) { 
		if (head.contains("\n")) { 
			head = head.replace("\n", "").replace("\r", "");
		}
		return head;
	}
	
	public void saveParameters() throws IOException { 
		String filePath = userInput.getOutputFilePath(); 
		String fileName = userInput.getOutputFileName();
		
		if (data.isEmpty()) {
			JOptionPane.showMessageDialog(new JFrame(), "Incorrect las file directory or incorrect formation input",
					"Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		String township = "T" + data.get(0).getRow(0).substring(28, 30) + "R" + data.get(0).getRow(0).substring(31, 33);
		
		if (userInput.getOutputFileName().equals("")) {
			fileName = township + "masterfile";
		}
		if (userInput.getOutputFilePath().endsWith("csv")) { 
			filePath = userInput.getOutputFilePath().substring(0, filePath.lastIndexOf("\\"));
			saveFile = new File(filePath + "\\" + fileName + "parametersV2.txt");
			int i = 3; 
			while (!saveFile.createNewFile()) {
				saveFile = new File(filePath + "\\" + fileName + "parametersV" + String.valueOf(i) + ".txt"); 
				i++;
			}
			
			FileWriter saveLocationWrite = new FileWriter(saveFile);
			userInput.write(saveLocationWrite);
			return;
		}
		
		int i = 2;
		while (!new File(filePath + "\\" + fileName).mkdir()) {
			fileName = fileName + "V" + String.valueOf(i);
			i++;
		}
		
		saveFile = new File(filePath + "\\" + fileName + "\\" + fileName + "parameters.txt");
		if (saveFile.createNewFile()) {
			FileWriter saveLocationWrite = new FileWriter(saveFile);
			userInput.write(saveLocationWrite);
		}
	}
	
	public void saveToResourceFile() throws IOException{
		URL url = Main.class.getResource("/resources/previousParameters.txt"); 
		String saveFilePath = url.getPath();
		if (saveFilePath.contains("!")) {
			saveFilePath = saveFilePath.substring(0, saveFilePath.lastIndexOf("SpotfireDataProgram"));
			saveFilePath = saveFilePath + "Resources/previousParameters.txt";
			saveFilePath = saveFilePath.substring(5);
		}
		File previousSaveFile = new File(saveFilePath); 
		FileWriter previousParameterWrite = new FileWriter(previousSaveFile);
		userInput.write(previousParameterWrite); 
	}
	
	public boolean write(String header, ArrayList<MnemonicData> mnemonicList, boolean secondaryTopFileExist) { 
		try {
			saveToResourceFile();
			if (userInput.getOutputFilePath().equals("")) {
				return true;
			}
			saveParameters(); 
			
			String fileName = userInput.getOutputFileName();
			if (userInput.getOutputFileName().equals("")) {
				fileName = "masterfile";
			}
			
			String outputFilePath = saveFile.getPath().substring(0, saveFile.getPath().lastIndexOf("\\"));

			String outputFileName = userInput.getOutputFileName();
			
			header = formatHeader(header);
			FileWriter fileWriter; 

			String township = "T" + data.get(0).getRow(0).substring(28, 30) + "R" + data.get(0).getRow(0).substring(31, 33);
			
			System.out.println(userInput.getOutputFilePath());
			if (userInput.getOutputFilePath().endsWith("csv")) {
				fileWriter = new FileWriter(new File(userInput.getOutputFilePath()), true);
			}
			else if (!outputFilePath.equals("") && !outputFileName.equals("")) {
				fileWriter = new FileWriter(new File(outputFilePath + "\\" + outputFileName + ".csv"));
			}
			else if (!outputFilePath.equals("") && outputFileName.equals("")) {
				fileWriter = new FileWriter(new File(outputFilePath + "\\" + township + "MASTERFILE" + ".csv"));
			}
			else if (outputFileName.equals("")){ 
				fileWriter = new FileWriter(new File( township + "MASTERFILE" + ".csv"));
			}
			else {
				fileWriter = new FileWriter(new File( outputFileName + ".csv"));
			}
			
			int gwiOffset = header.split(",").length;
			if (secondaryTopFileExist) {
				header += "DEPT,User Formation,System Formation,VKNS Isopach,Interval (step)";
			}
			else {
				header += "DEPT,Formation,VKNS Isopach,Interval (step)";
			}

			for (int i = 0 ; i < mnemonicList.size() ; i++) {
				header += "," + mnemonicList.get(i).getName();
			}

			header += ",Caliper2,Bit,Service Co.,Separation,Medium-Shallow Separation,Deep-Medium Separation,Mudcakes,Subsea,Calculated Density Porosity Sandstone,Calculated Density Porosity Limestone,Calculated Density Porosity Dolomite";
			for (int i = 0; i < 10; i++) {
				header += ",Unknown Mnemonic?";
			}
		
			fileWriter.write("Break," + "Using MD values for directional well?," + header);
			fileWriter.write(System.lineSeparator());
			
			//fileWriter.write(",placeholder,placeholder,placeholder,placeholder,placeholder,placeholder,placeholder,-999.25,-999.25,-999.25,-999.25,-999.25,-999.25,-999.25,placeholder,placeholder,placeholder,placeholder,placeholder,7/6/2000,7/6/2000,7/6/2000,7/6/2000,placeholder,placeholder,placeholder,placeholder,-999.25,placeholder,placeholder,-999.25");
	
			for (int i = 0 ; i < gwiOffset + 2; i++) { 
				fileWriter.write(",");
			}
			if (secondaryTopFileExist) {
				fileWriter.write("-999.25,PLACEHOLDER,PLACEHOLDER, ,-999.25");
			}
			else { 
				fileWriter.write("-999.25,PLACEHOLDER, ,-999.25");
			}
			for (int i = 0; i < mnemonicList.size()+21; i++) {
				fileWriter.write(",-999.25");
			}
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			
			for (int i = 0 ; i < data.size() ; i++) {
				String uniqueWell = data.get(i).getRow(0).substring(18, 20);
				String uwi = data.get(i).getRow(0).substring(21, 34);
				if (data.get(i).isMdforDir()) {
					fileWriter.write("Break " + uniqueWell + "_" + uwi + "," + "Using MD values for directional well?," + data.get(i).getHeader());
					fileWriter.write(System.lineSeparator());
					data.get(i).write(fileWriter, true);
				}
				else {
					fileWriter.write("Break " + uniqueWell + "_" + uwi + "," + "Using MD values for directional well?," + data.get(i).getHeader());
					fileWriter.write(System.lineSeparator());
					data.get(i).write(fileWriter, false);
				}
			}
			
			fileWriter.close(); 
			return false;
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return true; 
		}
		catch (IOException e) {
			e.printStackTrace();
			return true; 
		}
	}	
}
