package hr.fer.zemris.JIR2015.prob2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ImageFinderGUI extends JFrame {

	private static final long serialVersionUID = 3461137542205039806L;

	private MyCentralComponent mcc;
	
	public ImageFinderGUI() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(new Dimension(500,500));
		setLayout(new BorderLayout());
		
		JPanel topPanel = new JPanel(new GridLayout(1,2));
		JButton btn = new JButton("Choose");
		
		MojComboBoxModel model = new MojComboBoxModel(); 
		
		JComboBox<Path> dropDown = new JComboBox<>(model);
		
		topPanel.add(btn);
		topPanel.add(dropDown);
		
		add(topPanel, BorderLayout.NORTH);
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Open file");
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(fc.showOpenDialog(ImageFinderGUI.this) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				File fileName = fc.getSelectedFile();
				
				List<Path> slike = new ArrayList<>();
				
				try {
					Files.walk(fileName.toPath()).forEach(file -> {
						if(file.toString().endsWith("png") || file.toString().endsWith("gif") || file.toString().endsWith("jpg")) {
							slike.add(file);
						}
					});
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			
				Collections.sort(slike,new Comparator<Path>() {
					public int compare(Path o1, Path o2) {
						try {
							long f1 = Files.size(o1);
							long f2 = Files.size(o2);
							return Long.compare(f1, f2);
						} catch (IOException e) {
							e.printStackTrace();
						}
						return 0;
					}
				});

				List<Path> temp = new ArrayList<>(slike);
				
				temp = temp.subList(0,temp.size() < 3 ? temp.size() : 3);
				
				temp.forEach(slika -> {
					model.add(slika);
				});
			}
		});
		
		
		mcc = new MyCentralComponent();
		add(mcc,BorderLayout.CENTER);
		
		mcc.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					mcc.setTiled(!mcc.isTiled());
				}
			}
		});
		
		
		
		dropDown.addActionListener(l -> {
			
			Object item = dropDown.getSelectedItem();
			if(item == null) {
				return;
			}
			Path p = (Path) item;
			SwingUtilities.invokeLater(() -> {
				BufferedImage image;
				try {
					image = ImageIO.read(p.toFile());
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
				
				mcc.removeAll();
				mcc.setImage(image);
				mcc.repaint();
			});
		});
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->new ImageFinderGUI().setVisible(true));
		
	}

}
