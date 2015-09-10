package database;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import utils.HibernateSupport;
import interfaces.ISaveAndDelete;

@Entity
public class Transaction implements ISaveAndDelete {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	
	@ManyToOne
	private Location from;
	
	@ManyToOne
	private Location to;
	
	@ManyToOne
	@JoinColumn(name="transaction",updatable=false)
	private User editor;
	
	@ManyToMany
	@JoinTable(name="ProductTransactions",
				joinColumns={@JoinColumn(name="transaction_id")}, 
				inverseJoinColumns={@JoinColumn(name="product_id")})
	private List<ProductElement> elements;
	
	private Date date_moved;
	
	public Transaction() {}
	
	public Transaction(Location from, Location to, User editor, Date date_moved, List<ProductElement> elements) {
		this.from = from;
		this.to = to;
		this.editor = editor;
		this.date_moved = date_moved;
		this.elements = elements;
	}
	
	public int getId() {
		return id;
	}

	public Location getFrom() {
		return from;
	}

	public Location getTo() {
		return to;
	}

	public User getEditor() {
		return editor;
	}

	public List<ProductElement> getElements() {
		return elements;
	}

	public Date getDateMoved() {
		return date_moved;
	}

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see interfaces.ISaveAndDelete#saveToDB()
	 */
	@Override
	public boolean saveToDB() {
		return HibernateSupport.commit(this);
	}
	
	/* (non-Javadoc)
	 * @see interfaces.ISaveAndDelete#deleteFromDB(java.lang.Object)
	 */
	@Override
	public void deleteFromDB(Object obj) {
		HibernateSupport.deleteObject(this);	
	}
}
