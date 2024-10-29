#include <string>
#include <iostream>
#include <sstream>
#include <cstdlib>
#include "CTree.h"

using std::ostream;
using std::string;
using std::endl;
using std::cout;

//initializes a default constructor 
CTree::CTree(char ch) : data(ch), kids(nullptr), sibs(nullptr), prev(nullptr) {}

bool CTree::addChild(CTree *root) {
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

bool CTree::addSibling(CTree *root) { //similiar implementation to other addSibling

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

bool CTree::addChild(char ch) {
    CTree *ch_root = new CTree(ch);

    bool returned = addChild(ch_root); //calling addChild(*root) for simplicity

    if (returned == false) { //handle possible memory leaks
        delete ch_root;
    }
    return returned;
}

bool CTree::addSibling(char ch) {

    CTree *ch_root = new CTree(ch);

    bool returned = addSibling(ch_root); //calling addSibling(*root) for simplicity
    
    if (returned == false) { //handle possible memory leaks
        delete ch_root;
    }
    return returned;
}

bool CTree::treeCompare(const CTree *a, const CTree *b) const {
    if (!a && !b) { //both are null, base case
        return true;
    }

    if (!a || !b || a -> data != b -> data) { //mismatch has been found
        return false;
    }
    //recursive comparison of children/siblings
    return treeCompare(a -> kids, b -> kids) && treeCompare(a -> sibs, b ->sibs);
}

//overload to call addChild
CTree& CTree::operator+(CTree& rt) {
    this->addChild(&rt);
    return *this;
}

//overload to call treeCompare
bool CTree::operator==(const CTree &root) {
    return treeCompare(this, &root);
}

string CTree::toString() {
    std::stringstream ss;
    ss << data << endl; //current node

    if (kids) { //handles kids
        ss << kids -> toString();
    }
    if (sibs) { //handles sibs
        ss << sibs -> toString();
    }
    return ss.str();
}

//overload to output tree structure
ostream& operator<<(ostream& os, CTree& rt) {
    os << rt.toString();
    return os;
}

//destructor 
CTree::~CTree() {
    //deletes recursively until null pointer
    //implicit recursion
    delete kids;
    delete sibs; 
}
