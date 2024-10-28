#ifndef DIGRAPH_FUNCTIONS_H
#define DIGRAPH_FUNCTIONS_H
#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <map>
#include <string>
#include <cctype>
#include <algorithm>
#include "digraph_functions.h"

/* The following functions are used to handle initial processing 
 * of the text fiel, such as reading in the number of digraphs/trigraphs
 * and digesting the text so nothing is case sensitive and removing 
 * punctuation.
*/
void read_file(const std::string filename, std::vector<std::string>* digraphs, std::string* text);
std::string lower(const std::string str);
std::string remove_punctuation(std::string word);

/* The following functions are used to search and store digraphs found, 
 * number of digraphs found, and words containing each digraph.
*/
std::vector<std::string> split_text(std::string text);
void analyze_text(std::string text, std::vector<std::string> digraphs, std::map<std::string, std::pair<int, std::vector<std::string>>>* results);

/* The following functions are used to output digraphs found in order
 * specified by user input. 
*/
bool c_sort(std::pair<std::string, std::pair<int, std::vector<std::string>>> a, std::pair<std::string, std::pair<int, std::vector<std::string>>> b);
void sort_print(const std::map<std::string, std::pair<int, std::vector<std::string>>> * results, char sort);

/* This function handles queries by prompting user for entry.
 * If user inputs "exit", function exits. If user inputs a count, 
 * function outputs digraphs of that count. If a user inputs a digraph,
 * function outputs words containing that digraph.
*/
void handle_query(std::map<std::string, std::pair<int, std::vector<std::string>>> results);

#endif // DIGRAPH_FUNCTIONS_H