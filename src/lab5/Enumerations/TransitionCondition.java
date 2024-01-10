package lab5.Enumerations;

import java.io.Serializable;

public enum TransitionCondition implements Serializable{
	Undefined,
	Equal,
	NotEqual,
	MoreThan,
	MoreThanOrEqual,
	LessThan,
	LessThanOrEqual,
	Contains,
	NotContains,
	IsNull,
	NotNull,
	HaveCarForMe,
	CanAddCars,
	CanNotAddCars,
	HaveCar,
	HaveREL,
	SubPetriStopped
}
