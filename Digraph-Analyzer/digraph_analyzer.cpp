#include <iostream>
#include <string>
#include <fstream>
#include <sstream>
#include "digraph_functions.h"

using std::string;
using std::ifstream;
using std::vector;
using std::pair;
using std::map;
//stores digraphs as keys and pair with first element being digraph count and the second being a vector of containing words as values
using digraph_map = map<string, pair<int, vector<string>>>;
using std::cout;
using std::cin;

int main(int argc, char * argv[]) {
  // if number of arguments is not correct, exit with code 1!
  if (argc != 3) {
    cout<<"Invalid arguments";
    return 1;
  }

  // if the "order" argument is not valid, exit with code 1!
  string order(argv[2]);
  if (order != "r" && order != "a" && order != "c") {
    cout<<"Invalid arguments";
    return 1;
  }

  //reading in command line arguments
  string input = argv[1];
  string sorter = argv[2];

  //initializing containers/strings
  vector<string> digraphs;
  string bulk_text;
  digraph_map results;

  //executing program including processing, sorting, printing, and queries
  read_file(input, &digraphs, &bulk_text);
  analyze_text(bulk_text, digraphs, &results);
  sort_print(&results, sorter[0]);
  handle_query(results);

  return 0;
}


