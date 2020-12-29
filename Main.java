package com.company;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {

        // creating Target tree
        Tree Target = new Tree();

        //creating arbitrary trees
        Tree S = new Tree();
        Tree S1 = new Tree();
        Tree S2 = new Tree();
        Tree S3 = new Tree();

        int n = 1000;

        Integer[] values = new Integer[n];

        for (int i = 1; i <= n; i++) {
            values[i - 1] = i;
        }

        //building the target tree
        int mid = values[values.length / 2];
        Target.root = new Node(mid);
        Target.root = Target.getlevelorder(values, Target.root, 0);
        Target.show_Inorder(Target.root);
        List<Integer> T_list = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            T_list.add(values[i]);
        }
        Target.set_inorder(Target.root, T_list);
        Target.HeightOfTree(Target.root);
       // System.out.println("Target Tree: \n");
        //Tree.print2D(Target.root);

        //building arbitrary trees
        List<Integer> S_list = new ArrayList<Integer>();
        List<Integer> S1_list = new ArrayList<Integer>();
        List<Integer> S2_list = new ArrayList<Integer>();
        List<Integer> S3_list = new ArrayList<Integer>();
        List<Integer> rot_list = new ArrayList<Integer>();


        S.root = new Node(mid);
        S1.root = new Node(mid);
        S2.root = new Node(mid);
        S3.root = new Node(mid);

        S.root = S.getlevelorder(values, S.root, 0);
        S1.root = S.getlevelorder(values, S1.root, 0);
        S2.root = S.getlevelorder(values, S2.root, 0);
        S3.root = S.getlevelorder(values, S3.root, 0);

        S.show_Inorder(S.root);
        S1.show_Inorder(S1.root);
        S2.show_Inorder(S2.root);
        S3.show_Inorder(S3.root);


        for (int i = 0; i < n; i++) {
            S_list.add(values[i]);
            S1_list.add(values[i]);
            S2_list.add(values[i]);
            S3_list.add(values[i]);
            rot_list.add(values[i]);
        }

        S.set_inorder(S.root, S_list);
        S.HeightOfTree(S.root);

        S1.set_inorder(S1.root, S1_list);
        S1.HeightOfTree(S1.root);

        S2.set_inorder(S2.root, S2_list);
        S2.HeightOfTree(S2.root);

        S3.set_inorder(S3.root, S3_list);
        S3.HeightOfTree(S3.root);


        List<Integer> leftT = Target.CalculateLeftForearm(Target.root.left);
        List<Integer> rightT = Target.CalculateRightForearm(Target.root.right);

        //System.out.println("Left Forearm for Target " + leftT);
        //System.out.println("Right Forearm for Target " + rightT);

        ArrayList<Integer> leaf_list = new ArrayList<Integer>();
        ArrayList<Integer> TLeaves = Target.get_Leaves(Target.root, leaf_list);
        System.out.println("Target Leaves " + TLeaves);

        //Applying rotations on S based on given conditions
        // Marking the edges as "do not rotate" by using boolean variable rotate as false with 0.01 probability
        List<Integer> randList = new ArrayList<Integer>();
        Random random = new Random();

        while (randList.size() < 1) {
            int rand = rot_list.get(random.nextInt(n));

            if (!randList.contains(rand)) {
                System.out.println("Rand : " + rand);
                S.show_preorder(S.root, rand, false);
                S1.show_preorder(S1.root, rand, false);
                S2.show_preorder(S2.root, rand, false);
                S3.show_preorder(S3.root, rand, false);
            }
            randList.add(rand);
        }

        //Marking the remaining edges as "do not rotate" by using boolean variable rotate as false with 0.5 probability
        int remaining = rot_list.size() / 2;

        List<Integer> dummyList = new ArrayList<Integer>();

        while (dummyList.size() < remaining) {
            int rand = rot_list.get(random.nextInt(n));

            if (!randList.contains(rand) && !dummyList.contains(rand)) {
                S.show_preorder(S.root, rand, false);
                S1.show_preorder(S1.root, rand, false);
                S2.show_preorder(S2.root, rand, false);
                S3.show_preorder(S3.root, rand, false);

            }
            dummyList.add(rand);
        }

        // Applying rotations on arbitrary trees
        S.root = S.Rotate_Algorithm(S.root);
        S1.root = S1.Rotate_Algorithm(S1.root);
        S2.root = S2.Rotate_Algorithm(S2.root);
        S3.root = S3.Rotate_Algorithm(S3.root);


        //A1 algorithm
        Tree.A1_Algorithm(Target, S);

        //A2 algorithm

        //System.out.println("Binary Representation of the Roots of S1 :");

        HashMap<Integer, String> map1 = S1.Binary_Assignment(S1.root, n, S1.root.height);
        //System.out.println("Map : " + map1);
        S1.discloseBinary(map1, S1.root);
        S1.show_BinaryTree(S1.root);


        S1.root = S1.Rotate_Algorithm(S.root);

        S2.root = S2.Rotate_Algorithm(S2.root);

        S1.Find_Intervals(S.root);

        HashMap<String, String> dummy_map = new HashMap<String, String>();

        HashMap<String, String> TMap = S1.Get_Mappings(Target.root, dummy_map);


        //System.out.println("Target Inorder : ");
        Target.show_Inorder(Target.root);
        //System.out.println("TMap :" + TMap);

//        System.out.println("S1 before Blacklisting : ");
//        Tree.print2D(S1.root);
//
//        S1.ignorelist(S1.root, TMap);
//
//        System.out.println("S1 after Blacklisting : ");
//        Tree.print2D(S1.root);

        double x = Target.CalculatingRoot(n, Target.root.height + 1);

       // System.out.println("TRoot = " + x);

        S1.root = S1.findRoot(S1.root, (int) x, S1.root.height, S1);


        int rootTop = S1.rotations;
        //System.out.println("After root top number of rotations : " + rootTop);


        List<Integer> leftList = S1.CalculateLeftForearm(S1.root.left);
        List<Integer> rightList = S1.CalculateRightForearm(S1.root.right);


//        System.out.println("Left Forearm : " + leftList + " and size " + leftList.size());
//        System.out.println("Right Forearm : " + rightList + " and size " + rightList.size());


        ArrayList<Node> noder = S1.EnterLeftForearms(S, S.root.left);

        int intoLeftForearm = S1.rotations - rootTop;
        //System.out.println("Total Rotations after intoLeftFormarm : " + intoLeftForearm);


        S.root.left = null;
        S.root.left = noder.get(0);
        noder.remove(0);

        S1.Inorder_Left(S1.root.left, noder);

        //========> Right Forearm Generation :

        ArrayList<Node> noder1 = S1.EnterRightForeArms(S1, S1.root.right);

        int intoRightForearm = S1.rotations;
        System.out.println("Total Rotations after intoRightFormarm : " + intoRightForearm);


        int r = noder1.size();
        S1.root.right = null;
        S1.root.right = noder1.get(0);
        noder1.remove(0);

        S1.Inorder_Right(S1.root.right, noder1);

        S1.newHeightofTree(S1.root);

        S1.Left_ParentNode(S1.root.left, S1.root);


        S1.listRotFalse(S1.root, leftT, rightT);
        System.out.println("S1 after generating left and right forearms :");
        Tree.print2D(S1.root);


        Tree SS_Right1 = new Tree();
        Node S_Right1 = (Node) S1.root.clone();
        SS_Right1.root = S_Right1;

        SS_Right1.newHeightofTree(SS_Right1.root);

        SS_Right1.Left_ParentNode(SS_Right1.root.right, SS_Right1.root);

//        System.out.println("SSRight before algoR :");
//        Tree.print2D(SS_Right1.root);

        int original_node = SS_Right1.root.data;

//        System.out.println("2d of S before rotations : ");
//        Tree.print2D(S.root);
        //S.printPaths(S.root);
//Algorithm 2
        S1.Algorithm_2(S1.root.left, S1, Target.root.height + 1, n, leftT, Target);
        //System.out.println("Inorder of S Left after AlgoL : " + S1);
        S1.show_Inorder(S1.root);
        //Tree.print2D(S1.root);

        S1.Left_ParentNode(S1.root.left, S1.root);
        S1.show_Inorder(S1.root);
        //Tree.print2D(S1.root);

        SS_Right1.root.left = S1.root;
        int performLeftRot = S1.rotations - intoLeftForearm;
        SS_Right1.Algorithm_AR2(SS_Right1.root.right, SS_Right1, Target.root.height + 1, n, r, rightT, Target);
       // System.out.println("Inorder of S1 Right after algoR : " + S1);
        SS_Right1.show_Inorder(SS_Right1.root);
        //Tree.print2D(SS_Right1.root);

//        System.out.println("Total Rotations after performRightRot : " + SS_Right1.rotations);
//        System.out.println("Total Rotations after performRightRot for S: " + S1.rotations);

        Tree fin = new Tree();
        fin.root = new Node(original_node);
        fin.root.left = S1.root;
        fin.root.right = SS_Right1.root.right;

        //System.out.println("Final Tree : ");
        SS_Right1.show_Inorder(fin.root);
        //Tree.print2D(fin.root);


        System.out.println(" Rotations for Algorithm A1: " + (S.rotations + SS_Right1.rotations));
        System.out.println(" Rotations for Algorithm A2: " + ((rootTop) + 2 * (performLeftRot + intoLeftForearm)));

        //===========================A3=========================================>

        HashMap<String, String> dummy_map1 = new HashMap<String, String>();
        HashMap<Integer, Node> TObjectMap = new HashMap<Integer, Node>();
        HashMap<Integer, Node> TObjectMapper = Target.assignObject(TObjectMap, Target.root);
        HashMap<String, Integer> Int_Map = new HashMap<String, Integer>();
        HashMap<String, Integer> root_mapperz = S.root_interval_mapping(Target.root, Int_Map);


        Tree V = Target.A3_IgnoreList(S3.root,TObjectMapper ,root_mapperz, S3);
        V.A2(Target, S3, n, leftT, rightT,TMap);


    }
}