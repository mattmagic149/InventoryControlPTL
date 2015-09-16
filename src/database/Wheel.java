package database;

import interfaces.ISaveAndDelete;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.gson.annotations.Expose;

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
	@Expose(serialize = true)
	private TyreType tyre_type;
	
	@Expose(serialize = true)
	private int size_in_mm; //size in mm
	
	@Expose(serialize = true)
	private int height_in_percent; //in percent to size in mm
	
	@Expose(serialize = true)
	private float size_in_inch; //size in inch
	
	public Wheel() {
		
	}
	
	public Wheel(Wheel wheel) {
		this.tyre_type = wheel.tyre_type;
		this.size_in_mm = wheel.size_in_mm;
		this.height_in_percent = wheel.height_in_percent;
		this.size_in_inch = wheel.size_in_inch;
	}
	
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
	
	
	public int getSizeInmm() {
		return size_in_mm;
	}

	public int getHeightInPercent() {
		return height_in_percent;
	}

	public float getSizeInInch() {
		return size_in_inch;
	}

	public TyreType getTyreType() {
		return tyre_type;
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

	public int getId() {
		return id;
	}
	
	
}
