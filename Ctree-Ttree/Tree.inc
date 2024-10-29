#include <ostream>
#include <string>
#include <iostream>
#include <sstream>
#include <cstdlib>
template <typename T>
//initializes a default constructor 
Tree<T>::Tree(const T &value) : data(value), kids(nullptr), sibs(nullptr), prev(nullptr) {}

template <typename T>
bool Tree<T>::addChild(Tree<T> *root) {
    //invalid root check
    if (!root || root->sibs != nullptr || root->prev != nullptr) {
        return false;
    }

    if (!kids) { //if first child does not exist
        kids = root;
        root -> prev = this;
        return true;
    }
    else { //recursively calls addSibling
        return kids -> addSibling(root);
    }
}

template <typename T>
bool Tree<T>::addSibling(Tree<T> *root) { //similiar implementation to other addSibling

    //invalid root checks
    if (!root || root->sibs != nullptr || root->prev != nullptr) {
        return false;
    }
    if (!prev && !sibs) {
        return false;
    }

    if (data == root -> data) { //checking for duplicates
        return false;
    }

    if (!(data < root -> data)) { //inserting new root before current node
        root -> sibs = this; 
        if (prev->kids == this) { //if first child, updates 'kids' pointer to point to the new root.
            prev->kids = root;
        }
        else { //updating sibling pointers
            prev -> sibs = root;
        }
        //updating previous pointer to point to new root
        root -> prev = prev;
        prev = root;
        return true;
    }

    if (!sibs) { //if no siblings
        sibs = root;
        root -> prev = this;
        return true;
    }
    return sibs -> addSibling(root); //recursive to next sibling
}

template <typename T>
bool Tree<T>::addChild(const T& value) {
    Tree<T> *value_root = new Tree<T>(value);

    bool returned = addChild(value_root); //calling addChild(*root) for simplicity

    if (returned == false) { //handles possible memory leaks
        delete value_root;
    }
    return returned;
}

template <typename T>
bool Tree<T>::addSibling(const T& value) {

    Tree<T> *value_root = new Tree<T>(value);

    bool returned = addSibling(value_root); //calling addSibling(*root) for simplicity
    
    if (returned == false) { //handles possible memory leaks
        delete value_root;
    }
    return returned;
}

template <typename T>
bool Tree<T>::treeCompare(const Tree<T> *a, const Tree<T> *b) const {
    if (!a && !b) { //both are null, base case
        return true;
    }

    if (!a || !b || a -> data != b -> data) { //mismatch has been found
        return false;
    }
    //recursive comparison of children/siblings
    return treeCompare(a -> kids, b -> kids) && treeCompare(a -> sibs, b ->sibs);
}

template <typename T>
//overload to call addChild
Tree<T>& Tree<T>::operator+(Tree& rt) {
    this->addChild(&rt);
    return *this;
}

template <typename T>
//overload to call treeCompare
bool Tree<T>::operator==(const Tree<T> &root) {
    return treeCompare(this, &root);
}

template<typename T>
//helper function to toString(), makes handling non-char types easier
void Tree<T>::order(std::ostream& os) const {
    os << data << std::endl;
    //recursively outputs kids/sibs
    if (kids) kids->order(os); 
    if (sibs) sibs->order(os);
}

template<typename T>
std::string Tree<T>::toString() const{
    std::stringstream ss;
    order(ss); //calls helper function and stores in buffer
    return ss.str();
}

//overload to output tree structure
template<typename T>
std::ostream& operator<<(std::ostream& os, const Tree<T>& rt) {
    os << rt.toString(); 
    return os;
}

//destructor 
template<typename T>
Tree<T>::~Tree() {
    //deletes recursively until null pointer
    //implicit recursion
    delete kids;
    delete sibs; 
}
