package com.tfxing.persondaily.entity.rbTree;

import lombok.Data;

/**
 * @author :tanfuxing
 * @date :2023/2/14
 * @description :
 */
@Data
public class RBNode<T extends Comparable<T>> {//树的结点
    boolean color;//红或黑
    T key;
    RBNode<T> left;//左孩子指针
    RBNode<T> right;//右孩子指针
    RBNode<T> parent;//父结点指针，红黑树经常涉及到兄弟，叔叔，侄子，有个父结点指针方便操作。
    public RBNode(boolean color, T key, RBNode<T> left, RBNode<T> right, RBNode<T> parent) {
        this.color = color;
        this.key = key;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }
    
    public RBNode(T key, boolean color){
        this(color,key,null,null,null);
    }
    
    public String toString() {
        return key.toString();
    }
}
