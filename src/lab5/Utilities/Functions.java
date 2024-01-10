package lab5.Utilities;

import java.io.Serializable;
import java.util.ArrayList;

import lab5.Components.Activation;
import lab5.Components.Condition;
import lab5.Components.GuardMapping;
import lab5.Components.PetriNet;
import lab5.Components.PetriTransition;
import lab5.DataObjects.DataCar;
import lab5.DataObjects.DataCarQueue;
import lab5.DataObjects.DataFloat;
import lab5.DataObjects.DataInteger;
import lab5.DataObjects.DataREL;
import lab5.DataObjects.DataRELQueue;
import lab5.DataObjects.DataString;
import lab5.DataObjects.DataSubPetriNet;
import lab5.DataObjects.DataTransfer;
import lab5.DataOnly.TransferOperation;
import lab5.Enumerations.PetriObjectType;
import lab5.Interfaces.PetriObject;
import lab5.PetriDataPackage.PetriData;
import lab5.PetriDataPackage.Guard;
import lab5.PetriDataPackage.GuardActivation;
import lab5.PetriDataPackage.GuardCondition;
import lab5.PetriDataPackage.Place;
import lab5.PetriDataPackage.Transition;

public class Functions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PetriObject GetPetriObjectByName(String name, ArrayList<PetriObject> list) {
		for (PetriObject petriObject : list) {
			if (petriObject != null)
				if (petriObject.GetName().equals(name))
					return petriObject;
		}
		return null;
	}

	public Integer GetIndexByName(String name, ArrayList<PetriObject> list) {
		if (name == null)
			return -1;
		if (name.contains("-")) {
			String[] placePath = name.split("-");

			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null)
					if (list.get(i).GetName().equals(placePath[0])) {
						if (list.get(i).GetType() == PetriObjectType.DataSubPetri) {
							DataSubPetriNet sp = (DataSubPetriNet) list.get(i);
							for (int x = 0; x < sp.Value.Petri.PlaceList.size(); x++) {
								if (sp.Value.Petri.PlaceList.get(x) != null)
									if (sp.Value.Petri.PlaceList.get(x).GetName().equals(placePath[1]))
										return x;
							}
						}
					}
			}
		} else {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null)
					if (list.get(i).GetName().equals(name))
						return i;
			}
		}
		return -1;
	}

	public PetriObject GetFromListByName(String name, ArrayList<PetriObject> list) {
		if (name.contains("-")) {
			String[] placePath = name.split("-");

			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null)
					if (list.get(i).GetName().equals(placePath[0])) {
						if (list.get(i).GetType() == PetriObjectType.DataSubPetri) {
							DataSubPetriNet sp = (DataSubPetriNet) list.get(i);
							for (int x = 0; x < sp.Value.Petri.PlaceList.size(); x++) {
								if (sp.Value.Petri.PlaceList.get(x) != null)
									if (sp.Value.Petri.PlaceList.get(x).GetName().equals(placePath[1]))
										return sp.Value.Petri.PlaceList.get(x);
							}
						}
					}
			}
		} else {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null)
					if (list.get(i).GetName().equals(name))
						return list.get(i);
			}
		}
		return null;
	}

	public boolean SetToListByName(String name, ArrayList<PetriObject> list, PetriObject data) {
		int index = GetIndexByName(name, list);
		if (index == -1)
			return false;

		if (name.contains("-")) {
			String[] placePath = name.split("-");

			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null)
					if (list.get(i).GetName().equals(placePath[0])) {
						if (list.get(i).GetType() == PetriObjectType.DataSubPetri) {
							DataSubPetriNet sp = (DataSubPetriNet) list.get(i);
							sp.Value.Petri.PlaceList.set(index, data);
							return true;
						}
					}
			}
		} else {
			list.set(index, data);
			return true;
		}
		return false;
	}
	
	public boolean SetNullToListByName(String name, ArrayList<PetriObject> list) {
		int index = GetIndexByName(name, list);
		if (index == -1)
			return false;

		if (name.contains("-")) {
			String[] placePath = name.split("-");

			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null)
					if (list.get(i).GetName().equals(placePath[0])) {
						if (list.get(i).GetType() == PetriObjectType.DataSubPetri) {
							DataSubPetriNet sp = (DataSubPetriNet) list.get(i);
							sp.Value.Petri.PlaceList.get(index).SetValue(null);
							return true;
						}
					}
			}
		} else {
			list.get(index).SetValue(null);
			return true;
		}
		return false;
	}
	

	public boolean TransitionExist(String name, ArrayList<PetriTransition> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).GetName().equals(name))
				return true;
		}
		return false;
	}

	public boolean HaveCarForMe(PetriTransition t, ArrayList<DataCar> list) {
		if (list == null)
			return false;
		if (t == null)
			return false;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null && list.get(i).Value != null)
				if (list.get(i).Value.Targets.contains(t.TransitionName))
					return true;
		}
		return false;
	}

	public Integer CarIndexForMe(PetriTransition t, ArrayList<DataCar> list) {
		if (list == null)
			return -1;
		if (t == null)
			return -1;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null && list.get(i).Value != null)
				if (list.get(i).Value.Targets.contains(t.TransitionName))
					return i;
		}
		return -1;
	}

	public boolean HaveCar(ArrayList<DataCar> list) {
		if (list == null)
			return false;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null && list.get(i).Value != null)
				return true;
		}
		return false;
	}

	public boolean HaveREL(ArrayList<DataREL> list) {
		if (list == null)
			return false;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null && list.get(i).Value != null)
				return true;
		}
		return false;
	}

	public PetriNet PetriDataToPetriNet(PetriData pd) {
		PetriNet pn = new PetriNet();
		pn.PetriNetName = pd.Name;
		pn.NetworkPort = pd.Port;

		for (Place p : pd.Places) {
			PetriObject pls;
			switch (p.Type) {
			case DataFloat: {
				pls = new DataFloat();
				break;
			}
			case DataString: {
				pls = new DataString();
				break;
			}
			case DataInteger: {
				pls = new DataInteger();
				break;
			}
			case DataCar: {
				pls = new DataCar();
				break;
			}
			case DataCarQueue: {
				pls = new DataCarQueue();
				break;
			}
			case DataREL: {
				pls = new DataREL();
				break;
			}
			case DataRELQueue: {
				pls = new DataRELQueue();
				break;
			}
			case DataSubPetri: {
				pls = new DataSubPetriNet();
				break;
			}
			case DataTransfer: {
				pls = new DataTransfer();
				break;
			}
			default:
				pls = new DataFloat();
				break;
			}
			
			if (p.Type == PetriObjectType.DataTransfer) {
				DataTransfer trans = new DataTransfer();
				trans.SetName(p.Name);
				trans.Value = (TransferOperation) p.Val;
				pn.PlaceList.add(trans);
			} else {
				pls.SetName(p.Name);
				pls.SetValue(p.Val);
				pn.PlaceList.add(pls);
			}
		}
		
		for (Place p : pd.ConstantPlaces) {
			PetriObject pls;
			switch (p.Type) {
			case DataFloat: {
				pls = new DataFloat();
				break;
			}
			case DataString: {
				pls = new DataString();
				break;
			}
			case DataInteger: {
				pls = new DataInteger();
				break;
			}
			case DataCar: {
				pls = new DataCar();
				break;
			}
			case DataCarQueue: {
				pls = new DataCarQueue();
				break;
			}
			case DataREL: {
				pls = new DataREL();
				break;
			}
			case DataRELQueue: {
				pls = new DataRELQueue();
				break;
			}
			case DataSubPetri: {
				pls = new DataSubPetriNet();
				break;
			}
			case DataTransfer: {
				pls = new DataTransfer();
				break;
			}
			default:
				pls = new DataFloat();
				break;
			}
			
			if (p.Type == PetriObjectType.DataTransfer) {
				DataTransfer trans = new DataTransfer();
				trans.SetName(p.Name);
				trans.Value = (TransferOperation) p.Val;
				pn.ConstantPlaceList.add(trans);
			} else {
				pls.SetName(p.Name);
				pls.SetValue(p.Val);
				pn.ConstantPlaceList.add(pls);
			}
		}

		for (Transition t : pd.Transitions) {
			PetriTransition trs = new PetriTransition(pn);
			trs.TransitionName = t.Name;
			for (String s : t.InputPlaces) {
				trs.InputPlaceName.add(s);
			}

			for (Guard g : t.Guards) {

				GuardMapping tempGuard = new GuardMapping();
				tempGuard.condition = ConvertToCondition(g.GuardCondition, trs);

				for (GuardActivation ga : g.GuardActivations) {
					Activation act = new Activation(trs);
					act.InputPlaceName = ga.InputPlaceName;
					act.InputPlaceNames = ga.InputPlaceNames;
					act.OutputPlaceName = ga.OutputPlaceName;
					act.OutputPlaceNames = ga.OutputPlaceNames;
					act.Operation = ga.Operation;
					act.Parent = trs;
					tempGuard.Activations.add(act);
				}
				trs.GuardMappingList.add(tempGuard);
			}
			trs.Delay = t.Delay;
			pn.Transitions.add(trs);
		}

		return pn;
	}

	public PetriData PetriNetToPetriData(PetriNet pn) {
		PetriData pd = new PetriData();

		pd.Name = pn.PetriNetName;
		pd.Port = pn.NetworkPort;

		for (PetriObject p : pn.PlaceList) {
			pd.Places.add(new Place(p.GetName(), p.GetType(), p.GetValue()));
		}
		
		for (PetriObject p : pn.ConstantPlaceList) {
			pd.ConstantPlaces.add(new Place(p.GetName(), p.GetType(), p.GetValue()));
		}

		for (PetriTransition t : pn.Transitions) {
			Transition trs = new Transition();
			trs.Name = t.TransitionName;
			for (String s : t.InputPlaceName) {
				trs.InputPlaces.add(s);
			}

			for (GuardMapping g : t.GuardMappingList) {

				Guard tempGuard = new Guard();
				tempGuard.GuardCondition = ConvertToGuardCondition(g.condition);

				for (Activation ga : g.Activations) {
					GuardActivation act = new GuardActivation();
					act.InputPlaceName = ga.InputPlaceName;
					act.InputPlaceNames = ga.InputPlaceNames;
					act.OutputPlaceName = ga.OutputPlaceName;
					act.OutputPlaceNames = ga.OutputPlaceNames;
					act.Operation = ga.Operation;
					tempGuard.GuardActivations.add(act);
				}
				trs.Guards.add(tempGuard);
			}
			trs.Delay = t.Delay;
			pd.Transitions.add(trs);
		}
		return pd;
	}

	public Condition ConvertToCondition(GuardCondition gCon, PetriTransition parent) {
		Condition con1 = new Condition();
		con1.condition = gCon.condition;
		con1.PlaceName1 = gCon.PlaceName1;
		con1.PlaceName2 = gCon.PlaceName2;
		if (gCon.NextCondition != null) {
			con1.SetNextCondition(gCon.Connector, ConvertToCondition(gCon.NextCondition, parent));
		}
		con1.Parent = parent;
		return con1;
	}

	public GuardCondition ConvertToGuardCondition(Condition con) {
		GuardCondition con1 = new GuardCondition();
		con1.condition = con.condition;
		con1.PlaceName1 = con.PlaceName1;
		con1.PlaceName2 = con.PlaceName2;
		if (con.NextCondition != null) {
			con1.SetNextCondition(con.Connector, ConvertToGuardCondition(con.NextCondition));
		}
		return con1;
	}
}
