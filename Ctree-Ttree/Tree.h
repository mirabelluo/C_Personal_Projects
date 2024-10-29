#ifndef TREE_H
#define TREE_H

#include <cstdlib>
#include <string>

//generalized tree of any type

template <typename T>
class Tree;

template <typename T>
std::ostream& operator<<(std::ostream& os, const Tree<T>& rt);

template <typename T>
class Tree {
  friend class TTreeTest;
  friend std::ostream& operator<< <T>(std::ostream& os, const Tree<T>& rt);
  
  T data;        // the value stored in the tree node
  Tree * kids;   // children - pointer to first child of list, maintain order & uniqueness
  Tree * sibs;   // siblings - pointer to rest of children list, maintain order & uniqueness
                 // this should always be null if the object is the root of a tree
  Tree * prev;   // pointer to parent if this is a first child, or left sibling otherwise
                 // this should always be null if the object is the root of a tree

 public:
  Tree(const T &value);

  ~Tree();  // clear siblings to right and children and this node
  
  Tree& operator+(Tree& rt);  //^ operator to do the same thing as addChild
  bool operator==(const Tree &root); // return true if two Trees match node by node
  
  // siblings and children must be unique, return true if added, false otherwise
  bool addChild(const T &value);

  // add tree root for better building, root should have null prev and sibs 
  // returns false on any type of failure, including invalid root
  bool addChild(Tree<T> *root);

  std::string toString() const; // all characters, separated by newlines, including at the end

  
 private:
  // these should only be called from addChild, and have the same restrictions
  // the root of a tree should never have any siblings
  // returns false on any type of failure, including invalid root
  bool addSibling(const T &value);
  bool addSibling(Tree <T> *root);

  bool treeCompare(const Tree<T> *a, const Tree<T> *b) const;
  void order(std::ostream& os) const;
};

#include "Tree.inc"
#endif
