package lab5.Enumerations;

import java.io.Serializable;

public enum TransitionOperation implements Serializable{
	Undefined,
	Add,
	Sub,
	Prod,
	Div,
	Mod,
	Move,
	Copy,
	AddElement,
	PopElementWithTarget,
	PopElementWithoutTarget,
	PopElementWithTargetToQueue,
	PopElementWithoutTargetToQueue,
	SendOverNetwork,
	SendROverNetwork,
	SendPetriNetOverNetwork,
	PopElement_R_E,
	ActivateSubPetri,
	StopPetriNet,
	MakeNull
}
