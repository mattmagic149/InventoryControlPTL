package utils;

import java.util.List;

import org.javatuples.Pair;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import database.Truck;


public class ProductExclusionStrategy implements ExclusionStrategy {
    private List<Pair<Class<?>, List<String>>> fields_to_skip;

    public ProductExclusionStrategy(List<Pair<Class<?>, List<String>>> fields_to_skip) {
    	this.fields_to_skip = fields_to_skip;
    }

    public boolean shouldSkipClass(Class<?> clazz) {
    	return false;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
    	for(int i = 0; i < this.fields_to_skip.size(); i++) {
    		for(int j = 0; j < fields_to_skip.get(i).getValue1().size(); j++) {
        		if((this.fields_to_skip.get(i).getValue0() == f.getDeclaringClass()) &&
        			f.getName().equals(this.fields_to_skip.get(i).getValue1().get(j))) {
        			
        			return true;
        		}

    		}
    	}
    	
      return (f.getDeclaringClass() == Truck.class && f.getName().equals("products_consumeable"));
    }

}