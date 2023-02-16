package com.tfxing.persondaily.entity.rbTree;

import lombok.Data;

@Data
public class RBTree<T extends Comparable<T>> {
    private RBNode<T> root;//根结点指针
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    
    //左旋
    private void leftRotate(RBNode<T> x) {
        RBNode<T> y = x.right;
        x.right = y.left;
        if(y.left != null)
            y.left.parent = x;
        y.parent = x.parent;
        if(x.parent != null) {
            if(x.parent.left == x)
                x.parent.left = y;
            else 
                x.parent.right = y;
        }else {
            this.root = y;
        }
        y.left = x;
        x.parent = y;
    }
    //右旋
    private void rightRotate(RBNode<T> x) {
        RBNode<T> y = x.left;
        x.left = y.right;
        if(y.right != null)
            y.right.parent = x;
        y.parent = x.parent;
        if(x.parent != null) {
            if(x.parent.left == x)
                x.parent.left = y;
            else 
                x.parent.right = y;
        }else {
            this.root = y;
        }
        y.right = x;
        x.parent = y;
    }

    //删除结点
    public void deleteNode(RBNode<T> node) {
        //replace表示删除之后顶替上来的结点
        //parent为replace结点的父结点
        RBNode<T> replace = null, parent = null;
        // 如果删除的结点左右孩子都有
        if (node.left != null && node.right != null) {
            RBNode<T> succ = null;
            for (succ = node.right; succ.left != null; succ = succ.left);//找到后继
            node.key = succ.key;//覆盖值
            deleteNode(succ);//递归删除，只可能递归一次
            return;
        } else {// 叶子或只有一个孩子的情况
            // 如果删除的是根，则root指向其孩子(有一个红孩子或者为nil)
            if (node.parent == null) {
                // 如果有左孩子，那根就指向左孩子，没有则指向右孩子（可能有或者为NIL）
                this.root = (node.left != null ? node.left : node.right);
                replace = this.root;
                if (this.root != null)
                    this.root.parent = null;
            } else {// 非根情况
                RBNode<T> child = (node.left != null ? node.left : node.right);
                if (node.parent.left == node)
                    node.parent.left = child;
                else
                    node.parent.right = child;

                if (child != null)
                    child.parent = node.parent;
                replace = child;
                parent = node.parent;
            }
        }
        //如果待删除结点为红色，直接结束
        if (node.color == BLACK)
            deleteFixUp(replace, parent);
    }

    private void deleteFixUp(RBNode<T> replace, RBNode<T> parent) {
        RBNode<T> brother = null;
        // 如果顶替结点是黑色结点，并且不是根结点。
        //由于经过了上面的deleteNode方法，这里面parent是一定不为null的
        while ((replace == null || replace.color == BLACK) && replace != this.root){
            //左孩子位置的所有情况，
            if (parent.left == replace) {
                brother = parent.right;
                // case1 红兄，brother涂黑，parent涂红，parent左旋，replace的兄弟改变了，变成了黑兄的情况
                if (brother.color == RED) {
                    brother.color = BLACK;
                    parent.color = RED;
                    leftRotate(parent);
                    brother = parent.right;
                }
                // 经过上面，不管进没进if，兄弟都成了黑色
                // case2 黑兄，且兄弟的两个孩子都为黑
                if ((brother.left == null || brother.left.color == BLACK) && (brother.right == null || brother.right.color == BLACK)) {
                    // 如果parent此时为红，则把brother的黑色转移到parent上
                    if (parent.color == RED) {
                        parent.color = BLACK;
                        brother.color = RED;
                        break;
                    } else {// 如果此时parent为黑，即此时全黑了，则把brother涂红，导致brother分支少一个黑，使整个分支都少了一个黑，需要对parent又进行一轮调整
                        brother.color = RED;
                        replace = parent;
                        parent = replace.parent;
                    }
                } else {
                    // case3 黑兄，兄弟的左孩子为红色
                    if (brother.left != null && brother.left.color == RED) {
                        brother.left.color = parent.color;
                        parent.color = BLACK;
                        rightRotate(brother);
                        leftRotate(parent);
                        // case4 黑兄，兄弟的右孩子为红色
                    } else if (brother.right != null && brother.right.color == RED) {
                        brother.color = parent.color;
                        parent.color = BLACK;
                        brother.right.color = BLACK;
                        leftRotate(parent);
                    }
                    break;
                }
            } else {//对称位置的情况，把旋转方向反回来
                brother = parent.left;
                // case1 红兄，brother涂黑，parent涂红，parent左旋，replace的兄弟改变了，变成了黑兄的情况
                if (brother.color == RED) {
                    brother.color = BLACK;
                    parent.color = RED;
                    rightRotate(parent);
                    brother = parent.left;
                }
                // 经过上面，不管进没进if，兄弟都成了黑色
                // case2 黑兄，且兄弟的两个孩子都为黑
                if ((brother.left == null || brother.left.color == BLACK)
                        && (brother.right == null || brother.right.color == BLACK)) {
                    // 如果parent此时为红，则把brother的黑色转移到parent上
                    if (parent.color == RED) {
                        parent.color = BLACK;
                        brother.color = RED;
                        break;
                    } else {// 如果此时parent为黑，即此时全黑了，则把brother涂红，导致brother分支少一个黑，使整个分支都少了一个黑，需要对parent又进行一轮调整
                        brother.color = RED;
                        replace = parent;
                        parent = replace.parent;
                    }
                } else {
                    // case3 黑兄，兄弟的左孩子为红色，右孩子随意
                    if (brother.right != null && brother.right.color == RED) {
                        brother.right.color = parent.color;
                        parent.color = BLACK;
                        leftRotate(brother);
                        rightRotate(parent);
                        // case4 黑兄，兄弟的右孩子为红色，左孩子随意
                    } else if (brother.left != null && brother.left.color == RED) {
                        brother.color = parent.color;
                        parent.color = BLACK;
                        brother.left.color = BLACK;
                        rightRotate(parent);
                    }
                    break;
                }
            }
        }
        //这里可以处理到删除结点为只有一个孩子结点的情况，如果是根，也会将其涂黑。
        if (replace != null)
            replace.color = BLACK;
    }
}