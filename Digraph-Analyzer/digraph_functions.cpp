#include "digraph_functions.h"

using std::string;
using std::vector;
using std::pair;
using std::map;
//stores digraphs as keys and pair with first element being digraph count and the second being a vector of containing words as values
using digraph_map = map<string, pair<int, vector<string>>>;
using std::cout;
using std::cin;

/*
 * Reads input file and stores listed digraphs/trigraphs into digraphs and 
 * the rest of the text in text.
*/
void read_file(const string filename, vector<string>* digraphs, string* text) {
    std::ifstream file(filename);

    int num_digis = 0;
    file >> num_digis; //reads number denoting amount of digraphs/trigraphs

    string digraph;
    for (int i = 0; i < num_digis; i++) { //reads and stores digraphs into vector
        file >> digraph;
        digraphs -> push_back(digraph);
    }

    //reads rest of text
    string line;
    std::stringstream text_stream;
    std::getline(file, line); //sets reading of text to start on next line
    while (std::getline(file,line)) {
        text_stream << line << ' ';
    }
    *text = text_stream.str();
    file.close();
}

/* Returns a lowercase version of any word inputted.
*/
string lower(const string str) {
    string lowered = str;
    for (size_t i = 0; i < str.length(); i++) {//parses through character by character 
        lowered[i] = tolower(str[i]);
    }
    return lowered;
}

/* Removes punctuation (comma, exclamation point, period, and question mark) 
 * from a word and returns the cleaned word.
*/
string remove_punctuation(string word) {
    string punctuated = "";
    for (size_t i = 0; i < word.length(); i++) {
        if (word[i] == ',' || word[i] == '!' || word[i] == '.' || word[i] == '?') {
            punctuated += "";
        }
        else {
            punctuated += word[i];
        }
    }
    return punctuated;
}

/* Splits a block of text into a vector of individual words.
 * Helper function to analyze_text
*/
vector<string> split_text(std::string text) {
    std::stringstream ss(text);
    vector<string> words;
    string word;

    while (ss >> word) {
        words.push_back(word);
    }

    return words;
}

/* Text analysis, finds words containing specified digraphs and stores digraphs as key
 * and stores count and vector of containing words as a value pair.
*/
void analyze_text(string text, vector<string> digraphs, digraph_map* results) {
    //process bulk text
    string processed = lower(text);
    processed = remove_punctuation(processed);

    vector<string> words = split_text(processed);

    //iterate through lowercase digraph and search for it in processed text
    for (size_t i = 0; i < digraphs.size(); i++) {
        digraphs[i] = lower(digraphs[i]); //lowers digraph

        (*results)[lower(digraphs[i])] = {0, vector<string>()}; // ensures entry even if not found

        for(size_t j = 0; j < words.size(); j++) {
            if (words[j].find(digraphs[i]) != string::npos) {
                //increments counter if word is found to have digraph in it
                (*results)[digraphs[i]].first++;
                //adds word to vector 
                (*results)[digraphs[i]].second.push_back(words[j]);
            }
        }
    }
}

/* Helper function to sort_print, a comparison function that sorts by count
 * and uses ASCII as a tiebreaker. Needed as argument to std::sort.
*/
bool c_sort(pair<string, pair<int, vector<string>>> a, pair<string, pair<int, vector<string>>> b) {    
    if (a.second.first == b.second.first) {
        return a.first < b.first; // break ties with ascii order
    }
    return a.second.first > b.second.first;
}

/* Sorts results based off of character input sort (either 'r', 'a', or 'c')
 * 'a' outputs in ascii order, 'r' in reverse ascii order, and 'c' outputs by count.
 * If counts are the same, a tie is broken by outputing in ascii order.
*/
void sort_print(const digraph_map * results, char sort) {
    //converted map entries into a vector because it's easier to sort
    vector<pair<string, pair<int, vector<string>>>> words(results->begin(), results->end());
    //switch case based off of which command line arg is inputted
    switch (sort) {
        case 'a': //ASCII
            //no additional sorting needed.
            break;
        case 'r': //reverse ASCII order
            std::reverse(words.begin(), words.end()); //reverses map, which is already in ascii order.
            break;
        case 'c': //count order (largest to smallest), tie breaker is ASCII value
            std::sort(words.begin(), words.end(), c_sort);
            break;
    }

    //print sorted items
    for (vector<std::pair<string, std::pair<int, vector<string>>>>::const_iterator words_it = words.begin(); words_it != words.end(); ++words_it) {
        cout << words_it->first << ": [";
        for (std::vector<std::string>::const_iterator word_it = words_it->second.second.begin(); word_it != words_it->second.second.end(); ++word_it) {
            cout << *word_it;
            if (std::next(word_it) != words_it->second.second.end()) {
                cout << ", ";
            }
        }
        std::cout << "]" << std::endl; //adds bracket after last word is printed
    }
}

/* This function handles queries by prompting user for entry.
 * If user inputs "exit", function exits. If user inputs a count, 
 * function outputs digraphs of that count. If a user inputs a digraph,
 * function outputs words containing that digraph.
*/
void handle_query(digraph_map results) {
    string query;
    while (true) {
        query = "";

        cout << "q?>";
        cin >> query; 

        query = remove_punctuation((query)); //so not case sensitive

        if (query == "exit") {
            break;
        }

        int count; 
        std::stringstream ss(query);

        if (ss >> count && ss.eof()) { //for if user inputs number 
            bool found = false;
            for (digraph_map::iterator it = results.begin(); it != results.end(); ++it) {
                if (it->second.first == count) {
                    found = true;
                    //similiar printing process as in sort_print
                    cout << it->first << ": ["; 
                    for (vector<string>::const_iterator word_it = it->second.second.begin(); word_it != it->second.second.end(); ++word_it) {
                        cout << *word_it;
                        if (std::next(word_it) != it->second.second.end()) {
                            cout << ", ";
                        }
                    }
                    cout << "]" << std::endl;
                }
            }
            if (!found) { //handles if no count of that number is found
                cout << "None" << std::endl;
            }
        }
        else { //handling digraph queries
            query = lower(query);
            digraph_map::const_iterator it = results.find(query);
            if (it != results.end()) {
                cout << it->second.first << ": [";
                for (vector<string>::const_iterator word_it = it->second.second.begin(); word_it != it->second.second.end(); ++word_it) {
                        cout << *word_it;
                        if (std::next(word_it) != it->second.second.end()) {
                            cout << ", ";
                        }
                }
                cout << "]" << std::endl;
            }
            else { //handles if entered digraph does not exist
                cout << "No such digraph" << std::endl;
            }
        }
    }
}


