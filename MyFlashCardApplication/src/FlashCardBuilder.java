import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class FlashCardBuilder {

	private JTextArea question;
	private JTextArea answer;
	private ArrayList<FlashCard> cardList;
	private JFrame frame;
	
	
	public FlashCardBuilder() {
		//Build the User Interface
		frame = new JFrame("Flash Card");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create a JPanel to hold everything
		JPanel mainPanel = new JPanel();
		
		//Create font
		Font greatFont = new Font("Helvetica Neue", Font.BOLD, 21 );
		question = new JTextArea(6,20);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setFont(greatFont);
		
		//Question area
		JScrollPane qJScrollPane = new JScrollPane(question);
		qJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		//Answer area
		answer = new JTextArea(6,20);
		answer.setLineWrap(true);
		answer.setWrapStyleWord(true);
		answer.setFont(greatFont);
		
		
		//JscrollPane
		JScrollPane aJScrollPane = new JScrollPane(answer);
		aJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		aJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		//Button
		JButton nextButton = new JButton("Next Card");
		
		cardList = new ArrayList<FlashCard>();
		
		
		//Create a few labels
		JLabel qLabel = new JLabel("Question");
		JLabel aLabel = new JLabel("Answer");
		 
		//Add components to mainPanel
		mainPanel.add(qLabel); 
		mainPanel.add(qJScrollPane);
		mainPanel.add(aLabel);
		mainPanel.add(aJScrollPane);
		mainPanel.add(nextButton);
		nextButton.addActionListener(new NextCardListener());
		
		//MenuBar
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newMenuItem = new JMenuItem("New");
		
		JMenuItem saveMenuItem = new JMenuItem("Save");
		
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		
		menuBar.add(fileMenu);
		
		//Add Event Listeners
		newMenuItem.addActionListener(new NewMenuItemListener());
		saveMenuItem.addActionListener(new SaveMenuListener());
		
		frame.setJMenuBar(menuBar);
		
		//Add to the frame
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(500,600);
		frame.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			

			@Override
			public void run() {
				new FlashCardBuilder();
				
			}
			
		});
		
		
		

	}
	
	class NextCardListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//create a flashCard 
			
			FlashCard card = new FlashCard(question.getText(), answer.getText());
			cardList.add(card); 
			clearCard();
			
		}

		
		
	}
	
	class NewMenuItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("New Menu Clicked");			
		}
		
	}
	
	class SaveMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			FlashCard card = new FlashCard(question.getText(), answer.getText());
			cardList.add(card);
			
			//Create a file dialog with file chooser
			JFileChooser fileSave = new JFileChooser(); 
			fileSave.showSaveDialog(frame);
			saveFile(fileSave.getSelectedFile());
		}

		
		
	}
	
	private void clearCard() {
		question.setText("");
		answer.setText("");
		question.requestFocus();
		
	}
	
	private void saveFile(File selectedFile) {
		
		try {
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
			
			Iterator<FlashCard> cardIterator = cardList.iterator();
				while(cardIterator.hasNext()) {
					FlashCard card = (FlashCard)cardIterator.next();
					writer.write(card.getQuestion() + "/");
					writer.write(card.getAnswer() + "\n");
				
				//Format to be like this: Where is Mozambique/Africa
			}
			writer.close();
			
//			for (FlashCard card: cardList) {
//				writer.write(card.getQuestion() + "/");
//				writer.write(card.getAnswer()+"\n");
//			}
					
		} catch (Exception e) {
			System.out.println("System couldn't write the file");
			e.printStackTrace();
		}
		
	}

}
