package lab5.PetriDataPackage;

import java.io.Serializable;
import java.util.ArrayList;

import lab5.Enumerations.PetriObjectType;
import lab5.Interfaces.PetriObject;

public class PetriData implements PetriObject, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String Name;
	public String IP;
	public Integer Port;
	public Integer Delay;

	public ArrayList<Place> Places;
	public ArrayList<Place> ConstantPlaces;
	public ArrayList<Transition> Transitions;

	public PetriData() {
		Places = new ArrayList<>();
		Transitions = new ArrayList<>();
		ConstantPlaces = new ArrayList<>();
	}

	@Override
	public void Execute() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public PetriObjectType GetType() {
		return PetriObjectType.PetriData;
	}

	@Override
	public Object GetValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void SetValue(Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void AddElement(Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public String GetName() {
		return Name;
	}

	@Override
	public void SetName(String name) {
		this.Name = name;
	}

	@Override
	public boolean IsPrintable() {
		// TODO Auto-generated method stub
		return false;
	}
}
