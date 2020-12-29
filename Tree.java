package com.company;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tree implements Cloneable {

    // Implementing the function to print tree in 2D
    static int COUNT = 1000;
    int rotations = 0;
    Node root;
    Node dupe = null;



    // implementation of A1 algorithm
    static void A1_Algorithm(Tree T, Tree S) throws CloneNotSupportedException {

//        Tree T = new Tree();
//        t = T.root;
//
//        Tree S = new Tree();
//        s = S.root;


        int n = 1000;
        //Algorithm A1(S, n, T)

        //Computing the root of target Tree T
        double rootOfT = T.CalculatingRoot(n, T.root.height + 1);
        System.out.println("TRoot = " + rootOfT);

        //finding the root of target tree T and making it as root node in arbitrary tree S with rotations
        S.root = S.findRoot(S.root, (int) rootOfT, S.root.height, S);
        System.out.println("After root top number of rotations : " + S.rotations);

        //Generating left forearms and right forearms


        //LeftForeArm
        List<Integer> leftList = S.CalculateLeftForearm(S.root.left);
        List<Integer> rightList = S.CalculateRightForearm(S.root.right);


        System.out.println("Left Forearm : " + leftList);
        System.out.println("Right Forearm : " + rightList);


        ArrayList<Node> leftForeArm = S.EnterLeftForearms(S, S.root.left);

        System.out.println("Total Rotations after intoLeftFormarm : " + S.rotations);


        S.root.left = null;
        S.root.left = leftForeArm.get(0);
        leftForeArm.remove(0);

        S.Inorder_Left(S.root.left, leftForeArm);


        //Right Forearm
        ArrayList<Node> rightForeArm = S.EnterRightForeArms(S, S.root.right);

        System.out.println("Total Rotations after intoRightForearm : " + S.rotations);

        int r = rightForeArm.size();
        S.root.right = null;
        S.root.right = rightForeArm.get(0);
        rightForeArm.remove(0);

        S.rightInorder(S.root.right, rightForeArm);

        S.newHeightofTree(S.root);

        S.Left_ParentNode(S.root.left, S.root);

        //Getting Skewed Right Tree and cloning arbitrary tree

        Tree skewedTree = new Tree();
        Node skewedNode = (Node) S.root.clone();
        skewedTree.root = skewedNode;

        skewedTree.newHeightofTree(skewedTree.root);

        skewedTree.Left_ParentNode(skewedTree.root.right, skewedTree.root);

        System.out.println("Skewed Tree before AR :");
        //print2D(skewedTree.root);

        int original_node = skewedTree.root.data;

        System.out.println("2d of S before AL : ");
        //print2D(S.root);

        S.Algorithm_AL(S.root.left, S, T.root.height + 1, n);
        System.out.println("Inorder of S Left after AL : " + S);
        S.show_Inorder(S.root);
        //print2D(S.root);

        //Right
        System.out.println("2d of S before algoAR : ");
        //print2D(skewedTree.root.right);

        skewedTree.Algorithm_AR(skewedTree.root.right, skewedTree, T.root.height + 1, n, r);
        System.out.println("Inorder of S Right after algoR : " + S);
        skewedTree.show_Inorder(skewedTree.root);
        //print2D(skewedTree.root);

//
//        System.out.println("Total Rotations after performRightRot : " + skewedTree.rotations);
//        System.out.println("Total Rotations after performRightRot for S: " + S.rotations);

        Tree finalTree = new Tree();
        finalTree.root = new Node(original_node);
        finalTree.root.left = S.root;
        finalTree.root.right = skewedTree.root;

        System.out.println("Final Tree : ");
        skewedTree.show_Inorder(finalTree.root);
        //print2D(finalTree.root);


        System.out.println("\nTotal Rotations A1: " + (S.rotations + skewedTree.rotations));


    }
    static void print2DUtil(Node root, int space) {

        if (root == null)
            return;

        space += COUNT;


        //print2DUtil(root.right, space);

        System.out.print("\n");
        for (int i = COUNT; i < space; i++)
            System.out.print(" ");
        System.out.print(root.data + " rotate:" + root.rotate + "height:" + root.height + "\n");

        //print2DUtil(root.left, space);
    }

    static void print2D(Node root) {
        print2DUtil(root, 0);
    }

    // Function to insert nodes in level order
    public Node getlevelorder(Integer[] arr, Node root,int i) {
        // Base case for recursion
        if (i < arr.length) {
            Node temp = new Node(arr[i]);
            root = temp;

            // insert left child
            root.left = getlevelorder(arr, root.left,
                    2 * i + 1);

            // insert right child
            root.right = getlevelorder(arr, root.right,
                    2 * i + 2);
        }
        return root;
    }

    int height(Node N) {
        if (N == null)
            return 0;

        return N.height;
    }

    // Function to print height of node
    public void HeightOfTree(Node root) {
        if (root != null) {
            HeightOfTree(root.left);
            HeightOfTree(root.right);

            if (root.left != null && root.right != null) {
                root.height = 1 + Math.max(height(root.left), height(root.right));
            }

        }
    }

    /* Given a binary tree, print its nodes in inorder*/
    void show_Inorder(Node node) {
        if (node == null)
            return;

        /* first recur on left child */
        show_Inorder(node.left);

        /* then print the data of node */
        System.out.print(node.data + " ");

        /* now recur on right child */
        show_Inorder(node.right);
    }

    //function to implement inOrder Traversal
    public void set_inorder(Node node, List<Integer> list) {
        if (node != null && list.size() >= 0) {
            set_inorder(node.left, list);

            node.data = list.get(0);
            list.remove(0);
            set_inorder(node.right, list);
        }
    }

    //function to implement preOrder traversal
    void show_preorder(Node node, int key, boolean rot) {

        if (node == null) {
            return;
        }

        if (node.data == key) {
            node.rotate = rot;
        }
        show_preorder(node.left, key, rot);
        show_preorder(node.right, key, rot);

    }

    //function to implement left and right rotations
    Node Rotate_Algorithm(Node node) {

        if (node == null)
            return node;


        node.left = Rotate_Algorithm(node.left);

        node.height = 1 + Math.max(height(node.left),
                height(node.right));


        if (node.rotate && node.left != null && node.height > 1) {
            return rightRotate(node);
        }

        if (node.rotate && node.right != null && node.height > 1) {
            return leftRotate(node);
        }

        node.right = Rotate_Algorithm(node.right);

        return node;
    }

    //Function to implement right rotation
    Node RightRotations(Node y, Node Parent) {

        if ((y.right == null && y.left == null) || (y.left != null && y.right == null) || (y.right != null && y.left == null)) {
            Node T2 = y.right;
            y.right = Parent;
            Parent.left = T2;
            return y;

        } else {
            Node T2 = y.right;
            y.right = Parent;
            Parent.left = T2;

            return y;
        }

    }

    //function to implement left rotation
    Node LeftRotations(Node y, Node Parent) {
        if ((y.right == null && y.left == null) || (y.left != null && y.right == null) || (y.right != null && y.left == null)) {
            Node T2 = y.left;
            y.left = Parent;
            Parent.right = T2;
            return y;

        } else {
            Node T2 = y.left;
            y.left = Parent;
            Parent.right = T2;

            return y;
        }
    }

    //function to compute the root of the target tree T
    double CalculatingRoot(int n, int h) {

        if ((Math.pow(2, h - 1)) <= n && n <= ((Math.pow(2, h - 1)) + (Math.pow(2, h - 2) - 2))) {
            return (n - Math.pow(2, h - 2) + 1);
        }

        if ((Math.pow(2, h - 1) + (Math.pow(2, h - 2) - 1)) <= n && n <= (Math.pow(2, h) - 1)) {
            return Math.pow(2, h - 1);
        }

        return h;
    }

    //function to find root in arbitrary tree S
    Node findRoot(Node root, int key, int rootHeight, Tree S) {

        if (root != null) {
            findRoot(root.left, key, rootHeight, S);
            if (root.data == key) {
                int rot = rootHeight - root.height;
                int j = 0;
                while (j < rot) {
                    S.root = S.rootRotate(S.root, true);
                    S.rotations = S.rotations + 1;
                    System.out.println("Update rot to " + S.rotations);
                    j++;
                }
            }
            findRoot(root.right, key, rootHeight, S);
        }

        return S.root;
    }

    //function to  rotate root towards left or right in left and right Forearms
    Node Root_Rotate(Node node, boolean left, Node Parent) {
        if (node == null)
            return node;

        node.height = 1 + Math.max(height(node.left),
                height(node.right));

        if (left) {
            return LeftRotations(node, Parent);
        } else {
            return RightRotations(node, Parent);
        }

    }

    //function to get leftforearm of the tree
    List<Integer> CalculateLeftForearm(Node root) {
        List<Integer> list = new ArrayList<Integer>();
        while (root != null) {
            list.add(root.data);
            root = root.right;
        }
        return list;
    }

    //function to get rightforearm of the tree
    List<Integer> CalculateRightForearm(Node root) {
        List<Integer> list = new ArrayList<Integer>();
        while (root != null) {
            list.add(root.data);
            root = root.left;
        }
        return list;
    }

    //function to rotate elements present in leftForeArm
    ArrayList<Node> EnterLeftForearms(Tree S, Node root) {
        ArrayList<Node> list = new ArrayList<Node>();
        S.root.left = root;
        Tree ex = new Tree();
        ex.root = new Node(0);

        while (root != null) {
            while (root.left != null && root.ignore == false) {
                root = Root_Rotate(root.left, false, root);
                S.rotations = S.rotations + 1;
            }
            list.add(root);
            root = root.right;
        }
        return list;
    }

    //function to rotate elements present in rightForeArm
    ArrayList<Node> EnterRightForeArms(Tree S, Node root) {
        ArrayList<Node> nodez = new ArrayList<Node>();
        S.root.right = root;
        Tree ex = new Tree();
        ex.root = new Node(0);

        while (root != null) {
            while (root.left != null && root.ignore == false) {
                root = Root_Rotate(root.left, false, root);
                S.rotations = S.rotations + 1;

            }
            nodez.add(root);
            root = root.right;
        }
        return nodez;
    }

    //function to implement inorder traversal in left
    void Inorder_Left(Node node, ArrayList<Node> list) {

        if (list.size() == 0) {
            return;
        } else {
            node.right = new Node(0);
            node.right = list.get(0);
            list.remove(0);
            Inorder_Left(node.right, list);
        }
    }

    void Inorder_Right(Node root_s, ArrayList<Node> noder1) {
        if (noder1.size() == 0) {
            return;
        } else {
            root_s.right = new Node(0);
            root_s.right = noder1.get(0);
            noder1.remove(0);
            Inorder_Right(root_s.right, noder1);
        }

    }


    void listRotFalse(Node root, List<Integer> list_a, List<Integer> list_b) {
        if (root != null) {

            listRotFalse(root.left, list_a, list_b);
            listRotFalse(root.right, list_a, list_b);

            if (list_a.contains(root.data) || list_b.contains(root.data)) {
                root.rotate = false;

            }
            root.height = 1 + Math.max(height(root.left), height(root.right));
        }
    }

    void Algorithm_2(Node root, Tree g, int h, int n, List<Integer> leftT, Tree t) throws CloneNotSupportedException {
        boolean flag = false;
        dupe = (Node) root.clone();
        int s = h;
        boolean rotate = false;
        boolean step2 = false;
        if (dupe != null) {
            //int match = Integer.parseInt(Integer.toBinaryString(root.data));

            if ((Math.pow(2, h - 1) <= n) && (n <= Math.pow(2, h - 1) + Math.pow(2, h - 2) - 2)) {
                flag = true;
                step2 = true;
                int k = n - (int) Math.pow(2, h - 1) + 1;

                for (int i = 1; i <= k; i++) {
                    rotate = false;

                    if (dupe.right != null) {

                        if (dupe.rotate == true) {

                            if (dupe.binary == 1 && !leftT.contains(dupe.data)) {
                                dupe.rotate = false;
                                g.root = Left_Rotator_Function(dupe, dupe.parent, g);
                                rotate = true;
                            }
                        } else {

                            if (dupe.binary == 1 && !leftT.contains(dupe.data)) {
                                dupe.rotate = false;
                                g.root = Left_Rotator_Function(dupe, dupe.parent, g);
                                rotate = true;
                            }
                        }
                        if (rotate) {
                            dupe = dupe.parent.right;
                        } else {
                            dupe = dupe.right;
                        }
                    }
                }
                s = h - 1;
            }

            //Node duper = (Node) g.root.clone();
            int x;
            if (step2) {
                x = 10;
            } else {
                x = 1;
            }

            for (int j = s - 1; j >= 1; j--) {
                int k = (int) Math.pow(2, j) - 1;
                if (flag) {
                    dupe = (Node) g.root.clone();
                } else {
                    dupe = (Node) root.clone();
                    flag = true;
                }


                for (int i = 1; i < k; i++) {

                    rotate = false;
                    if (dupe != null) {
                        if (dupe.right != null) {


                            if (dupe.rotate == true) {
                                if (dupe.binary == x && !leftT.contains(dupe.data)) {
                                    dupe.rotate = false;
                                    g.root = Left_Rotator_Function(dupe, dupe.parent, g);
                                    rotate = true;
                                } else {
                                    dupe.rotate = false;
                                }
                            } else {
                                if (dupe.binary == x && !leftT.contains(dupe.data)) {
                                    dupe.rotate = false;
                                    g.root = Left_Rotator_Function(dupe, dupe.parent, g);
                                    rotate = true;
                                }
                            }
                            if (rotate) {
                                dupe = dupe.parent.right;
                            } else {
                                dupe = dupe.right;
                            }
                        }
                    }
                }
                x = x * 10;

                System.out.println("Inorder in ALgoAL2 after j = " + j);
                //print2D(g.root);
                if (see_identical(g.root.left, t.root.left)) {
                    return;
                }
            }

        }

    }

    ArrayList<Integer> get_Leaves(Node root, ArrayList<Integer> list) {

        if (root != null) {
            if (root.left == null && root.right == null) {
                list.add(root.data);
            } else {
                if (root.left == null && root.right != null) {
                    get_Leaves(root.right, list);
                } else if (root.left != null && root.right == null) {
                    get_Leaves(root.left, list);
                } else {
                    get_Leaves(root.left, list);
                    get_Leaves(root.right, list);
                }
            }
        }
        return list;
    }


    void Algorithm_AR2(Node root, Tree g, int h, int n, int r, List<Integer> rightT, Tree t) throws CloneNotSupportedException {
        boolean flag = false;
        dupe = (Node) root.clone();
        int s = h;
        boolean rotate = false;
        if (dupe != null) {
            //int match = Integer.parseInt(Integer.toBinaryString(root.data));

            if ((Math.pow(2, h - 1) + Math.pow(2, h - 2) <= n) && (n <= Math.pow(2, h) - 2)) {
                flag = true;
                int k = n - (int) Math.pow(2, h - 1) + 1;

                for (int i = 1; i <= (((r + 1) / 2) - 1); i++) {
                    if (dupe != null) {
                        if (dupe.right != null) {

                            if (dupe.rotate == true) {

                                if (dupe.binary == 1 && !rightT.contains(dupe.data)) {
                                    dupe.rotate = false;
                                    g.root = Left_Rotator_Function(dupe, dupe.parent, g);
                                }
                            } else {
                                if (dupe.binary == 1 && !rightT.contains(dupe.data)) {
                                    dupe.rotate = false;
                                    g.root = Left_Rotator_Function(dupe, dupe.parent, g);
                                }
                            }
                            if (rotate) {
                                dupe = dupe.parent.right;
                            } else {
                                dupe = dupe.right;
                            }
                        }
                    }
                    s = h - 1;
                }

                //Node duper = (Node) g.root.clone();

                int x = 10;
                for (int j = s - 1; j >= 1; j--) {

                    if (flag) {
                        dupe = (Node) root.clone();
                    } else {
                        dupe = (Node) root.clone();
                        flag = true;
                    }
                    for (int i = 1; i < k - 1; i++) {
                        if (dupe != null) {

                            if (dupe.right != null) {

                                if (dupe.rotate == true) {
                                    if (dupe.binary == x && !rightT.contains(dupe.data)) {
                                        dupe.rotate = false;
                                        g.root = Left_Rotator_Function(dupe, dupe.parent, g);
                                    }
                                } else {
                                    dupe.rotate = false;
                                }
                            } else {
                                if (dupe.binary == x && !rightT.contains(dupe.data)) {
                                    dupe.rotate = false;
                                    g.root = Left_Rotator_Function(dupe, dupe.parent, g);
                                }
                            }
                            if (rotate) {
                                dupe = dupe.parent.right;
                            } else {
                                dupe = dupe.right;
                            }
                        }
                    }
                    x = x * 10;
                    System.out.println("Inorder in ALgoR2 after j = " + j);
                    //print2D(g.root);
                    if (see_identical(g.root.right, t.root.right)) {
                        return;
                    }
                }

            }

        }
    }


    //function to implement inorder traversal in right
    void rightInorder(Node root_s, ArrayList<Node> noder1) {
        if (noder1.size() == 0) {
            return;
        } else {
            root_s.right = new Node(0);
            root_s.right = noder1.get(0);
            noder1.remove(0);
            rightInorder(root_s.right, noder1);
        }

    }

    //function to rotate root towards left or right
    Node rootRotate(Node node, boolean left) {
        if (node == null)
            return node;

        node.height = 1 + Math.max(height(node.left),
                height(node.right));

        if (left) {
            return leftRotate(node);
        } else {
            return rightRotate(node);
        }

    }

    //function to rotate root towards left
    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        //  Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    //function to rotate root towards right
    Node rightRotate(Node y) {

        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    //function to adjust heights
    public void newHeightofTree(Node root) {
        if (root != null) {
            newHeightofTree(root.left);
            newHeightofTree(root.right);
            root.rotate = true;
            root.height = 1 + Math.max(height(root.left), height(root.right));

        }
    }

    void Left_ParentNode(Node root, Node head) {
        root.parent = head;
        head = root;
        if (root.right != null) {
            Left_ParentNode(root.right, head);
        } else {
            return;
        }
    }

    // implementation of algorithm AL

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    void Algorithm_AL(Node root, Tree g, int h, int n) throws CloneNotSupportedException {
        boolean flag = false;
        dupe = (Node) root.clone();
        int s = h;
        if (dupe != null) {

            if ((Math.pow(2, h - 1) <= n) && (n <= Math.pow(2, h - 1) + Math.pow(2, h - 2) - 2)) {
                flag = true;
                int k = n - (int) Math.pow(2, h - 1) + 1;

                for (int i = 1; i <= k; i++) {

                    if (dupe.right != null) {

                        if (dupe.right.rotate == true) {
                            dupe.rotate = false;
                            g.root = Left_Rotator_Function(dupe, dupe.parent, g);
                        }
                    } else {
                        dupe.rotate = false;
                        g.root = Left_Rotator_Function(dupe, dupe.parent, g);
                    }
                    dupe = dupe.parent.right;
                }
                s = h - 1;
            }

            //Node duper = (Node) g.root.clone();


            for (int j = s - 1; j >= 1; j--) {
                int k = (int) Math.pow(2, j) - 1;
                if (flag) {
                    dupe = (Node) g.root.clone();
                } else {
                    dupe = (Node) root.clone();
                    flag = true;
                }
                for (int i = 1; i < k; i++) {
                    if (dupe.right != null) {

                        if (dupe.right.rotate == true) {
                            dupe.rotate = false;
                            g.root = Left_Rotator_Function(dupe, dupe.parent, g);
                        } else {
                            dupe.rotate = false;
                        }
                    } else {
                        dupe.rotate = false;
                        g.root = Left_Rotator_Function(dupe, dupe.parent, g);
                    }
                    dupe = dupe.parent.right;
                }

                System.out.println("Inorder in ALgoL after j = " + j);

            }

        }

    }

    //implementation of algorithm AR
    void Algorithm_AR(Node root, Tree g, int h, int n, int r) throws CloneNotSupportedException {
        boolean flag = false;
        dupe = (Node) root.clone();
        int s = h;
        if (dupe != null) {
            //int match = Integer.parseInt(Integer.toBinaryString(root.data));

            if ((Math.pow(2, h - 1) + Math.pow(2, h - 2) <= n) && (n <= Math.pow(2, h) - 2)) {
                flag = true;
                int k = n - (int) Math.pow(2, h - 1) + 1;

                for (int i = 1; i <= (((r + 1) / 2) - 1); i++) {

                    if (dupe.right != null) {

                        if (dupe.right.rotate == true) {
                            dupe.rotate = false;
                            g.root = Left_Rotator_Function(dupe, dupe.parent, g);
                        }
                    } else {
                        dupe.rotate = false;
                        g.root = Left_Rotator_Function(dupe, dupe.parent, g);
                    }
                    dupe = dupe.parent.right;
                }
                s = h - 1;
            }


            for (int j = s - 1; j >= 1; j--) {
                int k = (int) Math.pow(2, j) - 1;
                if (flag) {
                    dupe = (Node) g.root.clone();
                } else {
                    dupe = (Node) root.clone();
                    flag = true;
                }
                for (int i = 1; i < k - 1; i++) {
                    if (dupe != null) {

                        if (dupe.right != null) {

                            if (dupe.right.rotate == true) {
                                dupe.rotate = false;
                                g.root = Left_Rotator_Function(dupe, dupe.parent, g);
                            } else {
                                dupe.rotate = false;
                            }
                        } else {
                            dupe.rotate = false;
                            g.root = Left_Rotator_Function(dupe, dupe.parent, g);
                        }
                        dupe = dupe.parent.right;
                    }
                }

                System.out.println("Inorder in ALgoR after j = " + j);
            }

        }

    }

    Node Left_Rotator_Function(Node y, Node parent, Tree g) {
        if (y.parent.parent == null) { // g.root?
            if (y.right.left == null) {
                y.right.left = y;

                y = y.right;
                y.left.right = null;
                y.left.parent = y;
                y.parent = parent;
                g.root = y;
                g.rotations = g.rotations + 1;
                return g.root;
            } else {
                Node tmp = y.right.left;
                y.right.left = y;
                y = y.right;
                y.left.right = tmp;
                y.left.parent = y;
                y.parent = parent;
                y.left.right.parent = y.left;
                g.root = y;
                g.rotations = g.rotations + 1;
                return g.root;

            }


        } else if (y.right == null) {
//TO:DO
        } else {
            if (y.right.left == null) {

                Node tmp = y;
                y = y.right;
                parent.right = y;
                y.left = tmp;
                y.left.right = null;
                y.left.parent = y;
                y.parent = parent;
                g.rotations = g.rotations + 1;
                return g.root;

            } else {


                Node tmp = y.right.left;
                parent.right = y.right;
                y.right.left = y;
                y = y.right;
                y.left.right = tmp;
                y.left.parent = y;
                y.parent = parent;
                y.left.right.parent = y.left;
                g.rotations = g.rotations + 1;
                return g.root;
            }
        }
        return g.root;
    }

    Node Right_Rotator_Function(Node y, Node parent, Tree g) {
        if (y.parent.parent == null) { // g.root?
            if (y.left.right == null) {
                y.left.right = y;

                y = y.left;
                y.right.left = null;
                y.right.parent = y;
                y.parent = parent;
                g.root = y;
                g.rotations = g.rotations + 1;

                return g.root;
            } else {
                Node tmp = y.left.right;
                y.left.right = y;
                y = y.left;
                y.right.left = tmp;
                y.right.parent = y;
                y.parent = parent;
                y.right.left.parent = y.right;
                g.root = y;
                g.rotations = g.rotations + 1;
                return g.root;

            }


        } else if (y.left == null) {
//TO:DO
        } else {
            if (y.left.right == null) {

                Node tmp = y;
                y = y.left;
                parent.left = y;
                y.right = tmp;
                y.right.left = null;
                y.right.parent = y;
                y.parent = parent;
                g.rotations = g.rotations + 1;
                return g.root;

            } else {


                Node tmp = y.left.right;
                parent.left = y.left;
                y.left.right = y;
                y = y.left;
                y.right.left = tmp;
                y.right.parent = y;
                y.parent = parent;
                y.right.left.parent = y.right;
                g.rotations = g.rotations + 1;
                return g.root;
            }
        }
        return g.root;
    }

    HashMap<Integer, String> assigningBinaryTree(Node root, int n, int h) {
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        for (int x = 1; x <= n; x++) {
            int level = Math.abs(getLevel(root, x));
            if (level != 0) {

                map.put(x, String.valueOf((int) Math.pow(2, Math.abs(getLevel(root, x) - 1 - h))));
            }
        }

        return map;
    }

    int getLevel(Node node, int data) {

        return get_LevelUtil(node, data, 1);
    }

    int get_LevelUtil(Node node, int data, int level) {
        if (node == null)
            return 0;

        if (node.data == data)
            return level;

        int downlevel = get_LevelUtil(node.left, data, level + 1);
        if (downlevel != 0)
            return downlevel;

        downlevel = get_LevelUtil(node.right, data, level + 1);
        return downlevel;
    }

    HashMap<Integer, String> Binary_Assignment(Node root, int n, int h) {
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        for (int x = 1; x <= n; x++) {
            int level = Math.abs(getLevel(root, x));
            if (level != 0) {

                map.put(x, String.valueOf((int) Math.pow(2, Math.abs(getLevel(root, x) - 1 - h))));
            }
        }

        return map;
    }

    void Find_Intervals(Node root) {
        if (root != null) {

            root.leftInt = LeftCorner(root);
            root.rightInt = RightCorner(root);
            Find_Intervals(root.left);
            Find_Intervals(root.right);
        }
    }

    int LeftCorner(Node root) {
        while (root.left != null) {
            root = root.left;
        }
        return root.data;
    }

    int RightCorner(Node root) {
        while (root.right != null) {
            root = root.right;
        }
        return root.data;
    }

    void ignorelist(Node node, HashMap<String, String> map) {

        if (node != null) {
            ignorelist(node.left, map);

            String c = node.leftInt + "-" + node.rightInt;
            if (map.containsKey(c)) {
                String current_post = "";
                current_post = Get_String_PostOrder(node);
                if (map.get(c).equals(current_post) && node.left != null && node.right != null) {
                    node.ignore = true;
                    if (node.parent != null) {
                        node.parent.ignore = true;
                    }
                }
            }

            ignorelist(node.right, map);

        }

    }

    HashMap<String, String> Get_Mappings(Node root, HashMap<String, String> map) {
        if (root != null) {
            Get_Mappings(root.left, map);

            String h = "";
            String x = Get_String_PostOrder(root);
            String key = root.leftInt + "-" + root.rightInt;
            map.put(key, x);

            Get_Mappings(root.right, map);
        }
        return map;
    }


    void show_BinaryTree(Node root) {
        if (root != null) {
            show_BinaryTree(root.left);
            show_BinaryTree(root.right);
            System.out.println("Data : " + root.data + " Binary : " + root.binary);

        }
    }

    void discloseBinary(HashMap<Integer, String> map, Node root) {
        if (root != null) {
            discloseBinary(map, root.left);
            discloseBinary(map, root.right);
            int g = Integer.parseInt(map.get(root.data));
            root.binary = Double.parseDouble(Integer.toBinaryString(g));

        }
    }

    String Get_String_PostOrder(Node node) {
        String returnString = "";
        if (node != null) {
            returnString += Get_String_PostOrder(node.left);
            returnString += Get_String_PostOrder(node.right);
            returnString += String.valueOf(node.data);
        }
        return returnString;

    }

    boolean see_identical(Node sRoot, Node tRoot) {

        String S = Get_String_PostOrder(sRoot);
        String t = Get_String_PostOrder(tRoot);

        return S.equals(t);
    }
    Tree A3_IgnoreList(Node node, HashMap<Integer, Node> object_mapper, HashMap<String, Integer> interval_mapper, Tree S) throws CloneNotSupportedException {

        if (node != null) {
            A3_IgnoreList(node.left, object_mapper, interval_mapper, S);

            String c = String.valueOf(node.leftInt) + "-"+String.valueOf(node.rightInt);

            String[] arr = c.split("-");
            if(!arr[0].equals(arr[1])) {
                if (interval_mapper.containsKey(c)) {

                    int t_val = interval_mapper.get(c);
                    Node t_node = object_mapper.get(t_val);

                    System.out.println("Rotating for Root Interval " + arr[0] + " and "+ arr[1]);

//                    Node s_root = A1(t_node, node);
//                    System.out.println("Balanced S Root : ");
//                    print2D(s_root);

                    int a = Integer.parseInt(arr[0]);
                    int b = Integer.parseInt(arr[1]);
                    int n_len = b - a +1;

                    Integer[] arr1 = new Integer[n_len];
                    int y = 0;
                    for(int i= a;i<=b;i++){
                        arr1[y] = i;
                        y++;
                    }



                    Tree dump = new Tree();
                    int mid = Integer.parseInt(arr[0]) + Integer.parseInt(arr[1])/2;
                    dump.root = new Node(mid);
                    dump.root = dump.getlevelorder(arr1, dump.root, 0);
                    dump.show_Inorder(dump.root);

                    List<Integer> deep_list = new ArrayList<Integer>();

                    for(int i=0;i<n_len;i++){
                        deep_list.add(arr1[i]);
                    }

                    dump.set_inorder(dump.root, deep_list);
                    System.out.println("Now the balanced sub-tree : ");
                   // dump.print2D(dump.root);

                    if(dump.root.data > node.parent.data){
                        node.parent.right =dump.root;
                    } else {
                        node.parent.left = dump.root;
                    }

                }
            } else {
                System.out.println("Matched for "+ arr[0]+ " and "+ arr[1]+ " So, not rotating");
            }

            A3_IgnoreList(node.right, object_mapper, interval_mapper, S);

        }
        return S;
    }




        ////////////A2////////////////
        void A2(Tree t2, Tree S, int n,List<Integer> leftT , List<Integer> rightT , HashMap<String, String> TMap1 ) throws CloneNotSupportedException {

            //System.out.println("T Inorder : ");
            t2.show_Inorder(t2.root);
            System.out.println("TMap :"+ TMap1);

           // System.out.println("S before Blacklisting : ");
           // S.print2D(S.root);

            S.ignorelist(S.root, TMap1);

            //System.out.println("S after Blacklisting : ");
         //   S.print2D(S.root);
////


//        //===========================================>
//
//
            double x = t2.CalculatingRoot(n, t2.root.height+1);

            System.out.println("TRoot = "+ x);

            S.root  = S.findRoot(S.root, (int) x, S.root.height, S);


            int rootTop = S.rotations;
           // System.out.println("After root top number of rotations : "+ rootTop);

            //Step 5

            List<Integer> leftList = S.CalculateLeftForearm(S.root.left);
            List<Integer> rightList = S.CalculateRightForearm(S.root.right);


//            System.out.println("Left Forearm : "+ leftList +" and size "+ leftList.size());
//            System.out.println("Right Forearm : "+ rightList + " and size "+ rightList.size());


            ArrayList<Node> noder = S.EnterLeftForearms(S, S.root.left);

            int intoLeftForearm = S.rotations - rootTop;
            //System.out.println("Total Rotations after intoLeftFormarm : "+ intoLeftForearm);


            S.root.left = null;
            S.root.left = noder.get(0);
            noder.remove(0);

            S.Inorder_Left(S.root.left,noder);

            //========> Right Forearm Generation :

            ArrayList<Node> noder1 = S.EnterRightForeArms(S, S.root.right);

            int intoRightForearm = S.rotations;
           // System.out.println("Total Rotations after intoRightFormarm : "+ intoRightForearm);




            int r = noder1.size();
            S.root.right = null;
            S.root.right = noder1.get(0);
            noder1.remove(0);

            S.Inorder_Right(S.root.right,noder1);

            S.newHeightofTree(S.root);

            S.Left_ParentNode(S.root.left , S.root);



            S.listRotFalse(S.root, leftT, rightT);
            //System.out.println("S after generating left and right forearms :");
         //   S.print2D(S.root);






//        //Duplicate Tree
            Tree SS_Right = new Tree();
            Node S_Right = (Node) S.root.clone();
            SS_Right.root = S_Right;

            SS_Right.newHeightofTree(SS_Right.root);

            SS_Right.Left_ParentNode(SS_Right.root.right, SS_Right.root);

           // System.out.println("SSRight before algoR :");
           // SS_Right.print2D(SS_Right.root);

            int original_node = SS_Right.root.data;

            //System.out.println("2d of S before rotations : ");
           // S.print2D(S.root);
            //S.printPaths(S.root);

            S.Algorithm_2(S.root.left,  S, t2.root.height+1, n, leftT, t2);
           // System.out.println("Inorder of S Left after AlgoL : "+ S);
            S.show_Inorder(S.root);
           // S.print2D(S.root);

            S.Left_ParentNode(S.root.left , S.root);
            S.show_Inorder(S.root);
           // S.print2D(S.root);
            // }
//
            SS_Right.root.left = S.root;
            int performLeftRot  = S.rotations - intoLeftForearm;
           // System.out.println("Total Rotations after performLeftRot : "+ performLeftRot);

            //Right
           // System.out.println("2d of S before algoAR : ");
          //  SS_Right.print2D(SS_Right.root.right);

            SS_Right.Algorithm_AR2(SS_Right.root.right,  SS_Right, t2.root.height+1, n, r, rightT, t2);
            //System.out.println("Inorder of S Right after algoR : "+ S);
            SS_Right.show_Inorder(SS_Right.root);
          //  SS_Right.print2D(SS_Right.root);
//
////
//            System.out.println("Total Rotations after performRightRot : "+ SS_Right.rotations);
//            System.out.println("Total Rotations after performRightRot for S: "+ S.rotations);

            Tree fin = new Tree();
            fin.root = new Node(original_node);
            fin.root.left = S.root;
            fin.root.right = SS_Right.root.right;

            System.out.println("Final Tree : ");
            SS_Right.show_Inorder(fin.root);
            SS_Right.print2D(fin.root);


            System.out.println("Rotations A3: "+ (S.rotations + SS_Right.rotations) );
            //System.out.println("Total Rotations X: "+ (rootTop + 2*(performLeftRot+ intoLeftForearm)));

        }

    HashMap<String, Integer> root_interval_mapping(Node root, HashMap<String, Integer> map) {
        if (root != null) {
            root_interval_mapping(root.left, map);

            String key = String.valueOf(root.leftInt) +"-" + String.valueOf(root.rightInt);
            map.put(key,root.data);

            root_interval_mapping(root.right, map);
        }
        return map;
    }


    HashMap<Integer, Node> assignObject(HashMap<Integer, Node> map, Node root) {

        if (root != null) {
            assignObject(map, root.left);

            map.put(root.data, root);

            assignObject(map, root.right);
        }

        return map;

    }





}
