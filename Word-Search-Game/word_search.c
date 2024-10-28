// word_search.c
// Mirabel Luo, mluo12, 4AD13F
//
//

#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include "search_functions.h"

/*
 * This is the HW3 main function that performs a word search.
 */
int main(int argc, char* argv[]) {
  
  char grid[MAX_SIZE][MAX_SIZE] = {0}; //makes grid
  char input[MAX_SIZE];

  //checks if there is a command line argument
  if (argc != 2) {
    fprintf(stdout, "Please enter a command line argument.\n");
    return 1;
  }

  int dimension = populate_grid(grid, argv[1]);

  

  //collecting words
  FILE *output = fopen("test1.txt", "w");
  while (1) {
    if (scanf("%s", input) == EOF) {
      break;
    }
    //makes all inputs lowercase
    for (int idx = 0; idx < (int) strlen(input); idx++) {
      input[idx] = tolower(input[idx]);
    }
    find_all(grid, dimension, input, output);
  }
  fclose(output);
  return 0;
}
