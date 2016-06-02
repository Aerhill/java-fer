package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Localization provider bridge is a intermediate class between a localizable
 * application component and localization provider to isolate the localizable
 * elements of the application so when a language change occurs replacement
 * elements are created for appropriate language. This class creates an island
 * of components that can be collected by GARBAGE COLLECTOR.
 * 
 * @author Ante Spajic
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/** The connected flag. */
	private boolean connected;

	/** The parent component. */
	private ILocalizationProvider parent;

	/** The listener. */
	private ILocalizationListener listener = () -> fire();

	/**
	 * Instantiates a new localization provider bridge.
	 *
	 * @param parent
	 *            the parent localization provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
	}

	/**
	 * Disconnects the component from localization.
	 */
	public void disconnect() {
		if (!connected) {
			return;
		}
		connected = false;
		parent.removeLocalizationListener(listener);
	}

	/**
	 * Connects the component to localization.
	 */
	public void connect() {
		if (connected) {
			return;
		}
		connected = true;
		parent.addLocalizationListener(listener);
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

}
