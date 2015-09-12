package database;

import interfaces.ISaveAndDelete;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import utils.HibernateSupport;

@Entity
public class Wheel implements ISaveAndDelete {

	public enum TyreType {
		RADIAL, DIAGONAL
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	
	@Enumerated(value=EnumType.ORDINAL)
	private TyreType tyre_type;
	
	private int size_in_mm; //size in mm
	
	private int height_in_percent; //in percent to size in mm
	
	private float size_in_inch; //size in inch
	
	public Wheel(TyreType tyre_type, int size_in_mm, int height_in_percent, float size_in_inch) {
		this.tyre_type = tyre_type;
		this.size_in_mm = size_in_mm;
		this.height_in_percent = height_in_percent;
		this.size_in_inch = size_in_inch;
	}
	
	public String getTyreInfos() {
		String result = "";
		result = this.size_in_mm + "/" + this.height_in_percent + " ";
		result += (this.tyre_type == TyreType.DIAGONAL ? "D" : "R");
		result += this.size_in_inch;
		return result;
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
