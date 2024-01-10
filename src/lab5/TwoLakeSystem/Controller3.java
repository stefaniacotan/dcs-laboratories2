package lab5.TwoLakeSystem;


import lab5.Components.*;
import lab5.DataObjects.DataFloat;
import lab5.DataObjects.DataString;
import lab5.DataObjects.DataTransfer;
import lab5.DataOnly.TransferOperation;
import lab5.Enumerations.LogicConnector;
import lab5.Enumerations.TransitionCondition;
import lab5.Enumerations.TransitionOperation;

public class Controller3 {
    public static void main(String[] args) {

        PetriNet pn = new PetriNet();
        pn.PetriNetName = "Controller 3";
        pn.NetworkPort = 1082;

        DataFloat hr1 = new DataFloat();
        hr1.SetName("hr1");
        hr1.SetValue(30.0f);
        pn.PlaceList.add(hr1);

        DataFloat h1 = new DataFloat();
        h1.SetName("h1"); //input a value to h1 from GUI input float
        pn.PlaceList.add(h1);

        DataString dc1 = new DataString();
        dc1.SetName("dc1");
        pn.PlaceList.add(dc1);

        DataString c1 = new DataString();
        c1.SetName("c1");
        pn.PlaceList.add(c1);

        DataString c1Previous = new DataString();
        c1Previous.SetName("c1Previous");
        c1Previous.SetValue("No Action");
        pn.PlaceList.add(c1Previous);


        DataString m13 = new DataString();
        m13.SetName("m13");
        pn.PlaceList.add(m13);

        DataTransfer m32 = new DataTransfer();
        m32.SetName("m32");
        m32.Value = new TransferOperation("localhost", "1081", "m12");
        pn.PlaceList.add(m32);

        DataString po = new DataString();
        po.SetName("po");
        pn.PlaceList.add(po);

        DataString po1 = new DataString();
        po1.SetName("po1");
        po1.SetValue("No Action");
        pn.PlaceList.add(po1);

        DataString NoAction = new DataString(); // constant value
        NoAction.SetName("No Action");
        NoAction.SetValue("No Action");
        pn.ConstantPlaceList.add(NoAction);

        DataString Increase = new DataString(); // constant value
        Increase.SetName("Increase");
        Increase.SetValue("Increase");
        pn.ConstantPlaceList.add(Increase);

        DataString Decrease = new DataString(); // constant value
        Decrease.SetName("Decrease");
        Decrease.SetValue("Decrease");
        pn.ConstantPlaceList.add(Decrease);

        // T0 ------------------------------------------------
        PetriTransition t0 = new PetriTransition(pn);
        t0.TransitionName = "T0";
        t0.InputPlaceName.add("h1");
        t0.InputPlaceName.add("hr1");
        t0.InputPlaceName.add("c1Previous");
        t0.InputPlaceName.add("po1");
        t0.InputPlaceName.add("m13");

        // -------Sub guard 1---------
        Condition T0Ct1 = new Condition(t0, "h1", TransitionCondition.NotNull);
        Condition T0Ct2 = new Condition(t0, "hr1", TransitionCondition.NotNull);
        Condition T0Ct3 = new Condition(t0, "c1Previous", TransitionCondition.NotNull);
        Condition T0Ct4 = new Condition(t0, "po1", TransitionCondition.NotNull);
        Condition T0Ct5 = new Condition(t0, "hr1", TransitionCondition.MoreThan, "h1");
        Condition T0CtM13 = new Condition(t0, "m13", TransitionCondition.Equal, "Decrease");

        T0Ct5.SetNextCondition(LogicConnector.AND,T0CtM13);
        T0Ct4.SetNextCondition(LogicConnector.AND, T0Ct5);
        T0Ct3.SetNextCondition(LogicConnector.AND, T0Ct4);
        T0Ct2.SetNextCondition(LogicConnector.AND, T0Ct3);
        T0Ct1.SetNextCondition(LogicConnector.AND, T0Ct2);

        GuardMapping grdT0 = new GuardMapping();
        grdT0.condition = T0Ct1;
        grdT0.Activations.add(new Activation(t0, "Decrease", TransitionOperation.Move, "dc1"));
        grdT0.Activations.add(new Activation(t0, "Decrease", TransitionOperation.Move, "c1"));
        grdT0.Activations.add(new Activation(t0, "Decrease", TransitionOperation.Move, "c1Previous"));
        grdT0.Activations.add(new Activation(t0, "hr1", TransitionOperation.Move, "hr1"));

        t0.GuardMappingList.add(grdT0);

        // -------Sub guard 2---------
        Condition T0Ct6 = new Condition(t0, "h1", TransitionCondition.NotNull);
        Condition T0Ct7 = new Condition(t0, "hr1", TransitionCondition.NotNull);
        Condition T0Ct8 = new Condition(t0, "c1Previous", TransitionCondition.NotNull);
        Condition T0Ct9 = new Condition(t0, "po1", TransitionCondition.NotNull);
        Condition T0Ct10 = new Condition(t0, "hr1", TransitionCondition.LessThan, "h1");
        Condition T0M13In = new Condition(t0, "m13", TransitionCondition.Equal, "Increase");

        T0Ct10.SetNextCondition(LogicConnector.AND,T0M13In);
        T0Ct9.SetNextCondition(LogicConnector.AND, T0Ct10);
        T0Ct8.SetNextCondition(LogicConnector.AND, T0Ct9);
        T0Ct7.SetNextCondition(LogicConnector.AND, T0Ct8);
        T0Ct6.SetNextCondition(LogicConnector.AND, T0Ct7);

        GuardMapping grdT02 = new GuardMapping();
        grdT02.condition = T0Ct6;
        grdT02.Activations.add(new Activation(t0, "Increase", TransitionOperation.Move, "dc1"));
        grdT02.Activations.add(new Activation(t0, "Increase", TransitionOperation.Move, "c1"));
        grdT02.Activations.add(new Activation(t0, "Increase", TransitionOperation.Move, "c1Previous"));
        grdT02.Activations.add(new Activation(t0, "hr1", TransitionOperation.Move, "hr1"));

        t0.GuardMappingList.add(grdT02);
        // -------Sub guard 3---------
        Condition T0Ct11 = new Condition(t0, "h1", TransitionCondition.NotNull);
        Condition T0Ct12 = new Condition(t0, "hr1", TransitionCondition.NotNull);
        Condition T0Ct13 = new Condition(t0, "c1Previous", TransitionCondition.NotNull);
        Condition T0Ct14 = new Condition(t0, "po1", TransitionCondition.NotNull);
        Condition T0Ct15 = new Condition(t0, "hr1", TransitionCondition.Equal, "h1");

        T0Ct14.SetNextCondition(LogicConnector.AND, T0Ct15);
        T0Ct13.SetNextCondition(LogicConnector.AND, T0Ct14);
        T0Ct12.SetNextCondition(LogicConnector.AND, T0Ct13);
        T0Ct11.SetNextCondition(LogicConnector.AND, T0Ct12);

        GuardMapping grdT03 = new GuardMapping();
        grdT03.condition = T0Ct11;
        grdT03.Activations.add(new Activation(t0, "No Action", TransitionOperation.Move, "dc1"));
        grdT03.Activations.add(new Activation(t0, "No Action", TransitionOperation.Move, "c1"));
        grdT03.Activations.add(new Activation(t0, "No Action", TransitionOperation.Move, "c1Previous"));
        grdT03.Activations.add(new Activation(t0, "hr1", TransitionOperation.Move, "hr1"));

        t0.GuardMappingList.add(grdT03);


        // -------Sub guard 4---------
        Condition T0Ct18 = new Condition(t0, "h1", TransitionCondition.NotNull);
        Condition T0Ct19 = new Condition(t0, "hr1", TransitionCondition.NotNull);
        Condition T0Ct20 = new Condition(t0, "c1Previous", TransitionCondition.NotNull);
        Condition T0Ct21 = new Condition(t0, "po1", TransitionCondition.NotNull);
        Condition T0Ct22 = new Condition(t0, "hr1", TransitionCondition.Equal, "h1");
        Condition T0Ct23 = new Condition(t0, "m13", TransitionCondition.Equal, "Decrease");

        T0Ct22.SetNextCondition(LogicConnector.AND, T0Ct23);
        T0Ct21.SetNextCondition(LogicConnector.AND, T0Ct22);
        T0Ct20.SetNextCondition(LogicConnector.AND, T0Ct21);
        T0Ct19.SetNextCondition(LogicConnector.AND, T0Ct20);
        T0Ct18.SetNextCondition(LogicConnector.AND, T0Ct19);

        GuardMapping grdT04 = new GuardMapping();
        grdT04.condition = T0Ct18;
        grdT04.Activations.add(new Activation(t0, "No Action", TransitionOperation.Move, "dc1"));
        grdT04.Activations.add(new Activation(t0, "No Action", TransitionOperation.Move, "c1"));
        grdT04.Activations.add(new Activation(t0, "No Action", TransitionOperation.Move, "c1Previous"));
        grdT04.Activations.add(new Activation(t0, "hr1", TransitionOperation.Move, "hr1"));

        t0.GuardMappingList.add(grdT04);

        // -------Sub guard 5---------
        Condition T0Ct24 = new Condition(t0, "h1", TransitionCondition.NotNull);
        Condition T0Ct25 = new Condition(t0, "hr1", TransitionCondition.NotNull);
        Condition T0Ct26 = new Condition(t0, "c1Previous", TransitionCondition.NotNull);
        Condition T0Ct27 = new Condition(t0, "po1", TransitionCondition.NotNull);
        Condition T0Ct28 = new Condition(t0, "hr1", TransitionCondition.Equal, "h1");
        Condition T0Ct29 = new Condition(t0, "m13", TransitionCondition.Equal, "Increase");

        T0Ct28.SetNextCondition(LogicConnector.AND, T0Ct29);
        T0Ct27.SetNextCondition(LogicConnector.AND, T0Ct28);
        T0Ct26.SetNextCondition(LogicConnector.AND, T0Ct27);
        T0Ct25.SetNextCondition(LogicConnector.AND, T0Ct26);
        T0Ct24.SetNextCondition(LogicConnector.AND, T0Ct25);

        GuardMapping grdT05 = new GuardMapping();
        grdT05.condition = T0Ct24;
        grdT05.Activations.add(new Activation(t0, "Increase", TransitionOperation.Move, "dc1"));
        grdT05.Activations.add(new Activation(t0, "Increase", TransitionOperation.Move, "c1"));
        grdT05.Activations.add(new Activation(t0, "Increase", TransitionOperation.Move, "c1Previous"));
        grdT05.Activations.add(new Activation(t0, "hr1", TransitionOperation.Move, "hr1"));

        t0.GuardMappingList.add(grdT05);

        t0.Delay = 0;
        pn.Transitions.add(t0);

        // T1 ------------------------------------------------
        PetriTransition t1 = new PetriTransition(pn);
        t1.TransitionName = "T1";
        t1.InputPlaceName.add("c1");

        Condition T1Ct1 = new Condition(t1, "c1", TransitionCondition.NotNull);

        GuardMapping grdT1 = new GuardMapping();
        grdT1.condition = T1Ct1;

        grdT1.Activations.add(new Activation(t1, "c1", TransitionOperation.Move, "po"));
        grdT1.Activations.add(new Activation(t1, "c1", TransitionOperation.SendOverNetwork, "m32"));

        t1.GuardMappingList.add(grdT1);

        t1.Delay = 0;
        pn.Transitions.add(t1);

        // T2 ------------------------------------------------
        PetriTransition t2 = new PetriTransition(pn);
        t2.TransitionName = "T2";
        t2.InputPlaceName.add("po");

        Condition T2Ct1 = new Condition(t2, "po", TransitionCondition.NotNull);

        GuardMapping grdT2 = new GuardMapping();
        grdT2.condition = T2Ct1;
        grdT2.Activations.add(new Activation(t2, "po", TransitionOperation.Move, "po1"));

        t2.GuardMappingList.add(grdT2);

        t2.Delay = 0;
        pn.Transitions.add(t2);

        // -------Run OER-TPN

        System.out.println("Exp1 started \n ------------------------------");

        pn.Delay = 2000;

        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);
    }

}