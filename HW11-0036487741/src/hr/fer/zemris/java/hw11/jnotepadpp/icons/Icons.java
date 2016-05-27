package hr.fer.zemris.java.hw11.jnotepadpp.icons;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * The Class Icons that holds all the needed icons for notepad actions.
 * 
 * @author Ante Spajic
 */
public class Icons {

	/** The Icon GREEN_ICON. */
	public static final Icon GREEN_ICON = getIcon("greenDisk.png");
	
	/** The Icon RED_ICON. */
	public static final Icon RED_ICON = getIcon("redDisk.png");
	
	/** The Icon SAVE_ICON. */
	public static final Icon SAVE_ICON = getIcon("saveFile.png");
	
	/** The Icon COPY_ICON. */
	public static final Icon COPY_ICON = getIcon("copy.png");
	
	/** The Icon NEWFILE_ICON. */
	public static final Icon NEWFILE_ICON = getIcon("newFile.png");
	
	/** The Icon SAVEAS_ICON. */
	public static final Icon SAVEAS_ICON = getIcon("saveAs.png");
	
	/** The Icon CLOSE_ICON. */
	public static final Icon CLOSE_ICON = getIcon("closeFile.png");
	
	/** The Icon CUT_ICON. */
	public static final Icon CUT_ICON = getIcon("cut.png");
	
	/** The Icon EXIT. */
	public static final Icon EXIT = getIcon("exit.png");
	
	/** The Icon INVERT_CASE. */
	public static final Icon INVERT_CASE = getIcon("invertCase.png");
	
	/** The Icon LOWER_CASE. */
	public static final Icon LOWER_CASE = getIcon("lowerCase.png");
	
	/** The Icon UPPER_CASE. */
	public static final Icon UPPER_CASE = getIcon("upperCase.png");
	
	/** The Icon SORT_ASC. */
	public static final Icon SORT_ASC = getIcon("sortAsc.png");
	
	/** The Icon SORT_DESC. */
	public static final Icon SORT_DESC = getIcon("sortDesc.png");
	
	/** The Icon PASTE. */
	public static final Icon PASTE = getIcon("paste.png");
	
	/** The OPEN_EXISTING icon. */
	public static final Icon OPEN_EXISTING = getIcon("openFile.png");
	
	/** The UNIQUE icon. */
	public static final Icon UNIQUE = getIcon("unique.png");
	
	/** The STATISTICS icon. */
	public static final Icon STATISTICS = getIcon("stats.png");
	
	/**
	 * Gets the icon.
	 *
	 * @param name the name
	 * @return the icon
	 */
	private static Icon getIcon(String name) {
		Icons icons = new Icons();
		InputStream is = icons.get(name);
		if (is == null) {
			throw new IllegalArgumentException("Input stream couldn't be opened, invalid color or something else");
		}
		byte[] bytes = readAllBytes(is);
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ImageIcon(bytes);
	}
	
	/**
	 * Gets the resource stream for icon.
	 *
	 * @param str the file to read the bytes from
	 * @return the input stream
	 */
	private InputStream get(String str){
		return this.getClass().getResourceAsStream(str);
	}
	
	/**
	 * Read all bytes.
	 *
	 * @param is the is
	 * @return the byte[]
	 */
	private static byte[] readAllBytes(InputStream is) {
		byte[] ret = new byte[1024];
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int n = 0;
		try {
			while((n = is.read(ret))!= -1){
				buffer.write(ret, 0, n);
			}
		} catch (IOException e) {}
		return buffer.toByteArray();
	}
	
	
}
