package hr.fer.zemris.java.zi.prob1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.zi.prob1.model.ModelCrteza;
import hr.fer.zemris.java.zi.prob1.model.ModelCrtezaImpl;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -4926922465561213515L;

	public MainFrame() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(new Dimension(400,400));
		setLayout(new BorderLayout());
		Path p = Paths.get("files/krugovi.txt");
		
		ModelCrteza model = new ModelCrtezaImpl();
		
		JCanvas jc = new JCanvas(model);
		
		jc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1) {
					int index = model.najblizi(e.getX(), e.getY());
					if ( index != -1) {
						System.out.println("Care");
						model.postaviSelektirani(index);
					} else {
						model.postaviSelektirani(-1);
					}
				}
			}
		});
		
		try {
			stvoriKrugove(p, model);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		add(jc,BorderLayout.CENTER);
		JPanel gl = new JPanel(new GridLayout(1,2));
		BrojKrugovaLabel jl1 = new BrojKrugovaLabel(model);
		SelectedLabel jl2 = new SelectedLabel(model);

		gl.add(jl1);
		gl.add(jl2);
		add(gl,BorderLayout.NORTH);
		
		JPanel jp = new JPanel(new BorderLayout());
		JTextField jtf = new JTextField();
		jp.add(jtf, BorderLayout.CENTER);
		JButton btn = new JButton("Izvrši");
		jp.add(btn, BorderLayout.EAST);
		
		btn.addActionListener(l -> {
			String text = jtf.getText();
			System.out.println(text);
			
		});
		
		add(jp,BorderLayout.SOUTH);
		
	}
	
	private void stvoriKrugove(Path p, ModelCrteza jc) throws IOException {
		Files.readAllLines(p).forEach(l -> { 
			String[] atributi = l.split("\\s"); 
			int cx = Integer.parseInt(atributi[0]);
			int cy = Integer.parseInt(atributi[1]);
			int r = Integer.parseInt(atributi[2]);
			Color obrub = Color.decode("#"+atributi[3]);
			String ispuna = atributi[4];
			if (ispuna.equals("-")) {
				jc.dodajKrug(cx, cy, r, obrub, null);
			} else {
				Color isp = Color.decode("#"+ispuna);
				jc.dodajKrug(cx, cy, r, obrub, isp);
			}
			
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> new MainFrame().setVisible(true));
	}
}
