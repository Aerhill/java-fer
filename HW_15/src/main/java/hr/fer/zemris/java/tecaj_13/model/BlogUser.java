package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The Class BlogUser that models the very cool blog user.
 * 
 * @author Ante Spajic
 */
@Entity
@Table(name="blog_users")
@Cacheable(true)
public class BlogUser {

	/** The unique user id. */
	@Id @GeneratedValue
	private Long id;
	
	/** Blog entries that user created. */
	@OneToMany(mappedBy="creator")
	private List<BlogEntry> createdEntries = new ArrayList<>();
	
	/** The first name. */
	@Column(nullable=false, length=25)
	private String firstName;
	
	/** The last name. */
	@Column(nullable=false, length=25)
	private String lastName;
	
	/** The unique nick. */
	@Column(nullable=false, unique=true, length=25)
	private String nick;
	
	/** Users email. */
	@Column(nullable=false, unique=true, length=25)
	private String email;
	
	/** The password hash. */
	@Column(nullable=false, length=40)
	private String passwordHash;

	/**
	 * Instantiates a new blog user.
	 */
	public BlogUser() {
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the nick.
	 *
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Sets the nick.
	 *
	 * @param nick the new nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the password hash.
	 *
	 * @return the password hash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Sets the password hash.
	 *
	 * @param passwordHash the new password hash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
}
