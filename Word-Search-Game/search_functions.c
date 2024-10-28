// search_functions.c
// Mirabel Luo, mluo12, 4AD13F
//
//


#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include "search_functions.h"

/* 
 * Given a filename and a MAX_SIZExMAX_SIZE grid to fill, this function 
 * populates the grid and returns n, the actual grid dimension. 
 * If filename_to_read_from can't be opened, this function returns -1.
 * If the file contains an invalid grid, this function returns -2.
 */
int populate_grid(char grid[][MAX_SIZE], char filename_to_read_from[]){
  int row_counter = 0;
  int column_counter = 0;
  char tempLine[MAX_SIZE * 10] = {0};

  FILE *grid_file = fopen(filename_to_read_from, "r");

  //checks if file is opened properly
  if (grid_file == NULL) {
    fprintf(stdout, "Grid file failed to open.\n");
    return -1;
  }

  //read in first line of file and check validity
  if (fscanf(grid_file, " %s", tempLine) == 1) {
    column_counter = strlen(tempLine);
    
    if (column_counter > MAX_SIZE) {
      fprintf(stdout, "Invalid grid.\n");
      fclose(grid_file);
      return -2;
    }
    else {
      for (int index = 0; index < column_counter; index++) {
        grid[row_counter][index] = tolower(tempLine[index]);
      }
      row_counter++;
    }
  }
  
  while (fscanf(grid_file, " %9s", tempLine) == 1) {
      //assigns line to a temp array
      unsigned long int lineLength = strlen(tempLine);

      // checking if row is the same length as first row and if line is less than MAX_SIZE
      if (lineLength != strlen(grid[0])) {
        fprintf(stdout, "Invalid grid.\n");
        fclose(grid_file);
        return -2;
      }

    for (int i = 0; i < column_counter; i++) {
        grid[row_counter][i] = tempLine[i];
    }
    row_counter++;
  }

  //checks if grid is empty
  if (column_counter == 0) {
    fprintf(stdout, "Invalid grid.\n");
    fclose(grid_file);
    return -2;
  }
  //checks if row and column are same length
  if (row_counter != column_counter) {
    fprintf(stdout, "Invalid grid.\n");
    fclose(grid_file);
    return -2;
  }

  return column_counter;
}

/*
 * Returns the number of times the given word string was found in the grid facing
 * the right. Parameter n indicates the actual size of the grid. Outputs matched word
 * index and direction. 
 */
int find_right(char grid[][MAX_SIZE], int n, char word[], FILE *write_to) {
  int word_counter = 0;
  int length = strlen(word);
  
  //stores temporary string of characters to compare to word
  char comparebit[MAX_SIZE];

  for (int i = 0; i < n; i++) {
    for (int j = 0; j <= n - length; j++) {
      //some index
      int a = 0;
      while (a < length) {
        comparebit[a] = grid[i][j + a];
        a++;
      }
      comparebit[length] = '\0';
      
      //checks for similarity
      if (strcmp(comparebit, word) == 0) {
        fprintf(stdout, "%s %d %d R\n", word, i, j);
        fprintf(write_to, "%s %d %d R\n", word, i, j);
        word_counter++;
      }
    }
  }
  return word_counter; 
}


/*
 * Returns the number of times the given word string was found in the grid facing
 * the left. Parameter n indicates the actual size of the grid. Outputs matched word
 * index and direction. 
 */
int find_left (char grid[][MAX_SIZE], int n, char word[], FILE *write_to){
  int word_counter = 0;
  int length = strlen(word);
  char copy[MAX_SIZE];
  //copies word, then reverses letters to travers through grid in same way as find_right
  int num = 0;
  for (num = 0; word[num] != '\0'; num++) {
    copy[num] = word[num];
  }
  copy[num] = '\0';

  for (int a = 0, b = length - 1; a <= b; a++, b--) { 
      char c = copy[a]; 
      copy[a] = copy[b]; 
      copy[b] = c; 
    }
  
  //stores temporary string of characters to compare to word
  char comparebit[MAX_SIZE];

  for (int i = 0; i < n; i++) {
    for (int j = 0; j <= n - length; j++) {
      //some index
      int a = 0;
      while (a < length) {
        comparebit[a] = grid[i][j + a];
        a++;
      }
      comparebit[length] = '\0';
      
      //checks for similarity
      if (strcmp(comparebit, copy) == 0) {
        fprintf(stdout, "%s %d %d L\n", word, i, j + (length - 1) );
        fprintf(write_to, "%s %d %d L\n", word, i, j + (length - 1) );
        word_counter++;
      }
    }
  }
  return word_counter; 
}

/*
 * Returns the number of times the given word string was found in the grid facing
 * down. Parameter n indicates the actual size of the grid. Outputs matched word
 * index and direction. 
 */
int find_down (char grid[][MAX_SIZE], int n, char word[], FILE *write_to){

  int length = strlen(word);
  int word_counter = 0;
  //stores temporary string of characters to compare to word
  char comparebit[MAX_SIZE];

  for (int i = 0; i <= n - length; i++) {
    for (int j = 0; j < n; j++) {
      //some index
      int a = 0;
      while (a < length) {
        comparebit[a] = grid[i + a][j];
        a++;
      }
      comparebit[length] = '\0';
      
      //checks for similarity
      if (strcmp(comparebit, word) == 0) {
        fprintf(stdout, "%s %d %d D\n", word, i, j);
        fprintf(write_to, "%s %d %d D\n", word, i, j);
        word_counter++;
      }
    }
  }
  return word_counter; 
}


/*
 * Returns the number of times the given word string was found in the grid facing
 * upwards. Parameter n indicates the actual size of the grid. Outputs matched word
 * index and direction. 
 */
int find_up (char grid[][MAX_SIZE], int n, char word[], FILE *write_to){

  int word_counter = 0;
  int length = strlen(word);
  char copy1[MAX_SIZE];
  //copies word and flips in order to traverse through grid in same way as find_down
  int num = 0;
  for (num = 0; word[num] != '\0'; num++) {
    copy1[num] = word[num];
  }
  copy1[num] = '\0';

  for (int a = 0, b = length - 1; a <= b; a++, b--) { 
      char c = copy1[a]; 
      copy1[a] = copy1[b]; 
      copy1[b] = c; 
    }
  
  //stores temporary string of characters to compare to word
  char comparebit[MAX_SIZE];

  for (int i = 0; i <= n - length; i++) {
    for (int j = 0; j < n; j++) {
      //some index
      int a = 0;
      while (a < length) {
        comparebit[a] = grid[i + a][j];
        a++;
      }
      comparebit[length] = '\0';
      
      //checks for similarity
      if (strcmp(comparebit, copy1) == 0) {
        fprintf(stdout, "%s %d %d U\n", word, i + (length - 1), j);
        fprintf(write_to, "%s %d %d U\n", word, i + (length - 1), j);
        word_counter++;
      }
    }
  }
  return word_counter; 
}


/*
 * Finds and reports ALL appearances of the given word, in the required 
 * R,L,D,U order.
 */
int find_all  (char grid[][MAX_SIZE], int n, char word[], FILE *write_to) {
  
  int sum = 0;
  sum += find_right(grid, n, word, write_to);
  sum += find_left(grid, n, word, write_to);
  sum += find_down(grid, n, word, write_to);
  sum += find_up(grid, n, word, write_to);

  if (sum == 0) {
    fprintf(stdout, "%s - Not Found\n", word);
    fprintf(write_to, "%s - Not Found\n", word);
  }
  return sum;
}

/*
 * Reads lhs and rhs character by character until either reaches eof.
 * Returns true if the files can be read and the two files match
 * character by character. Returns false if two files either can't be
 * opened or don't match. The definition of this function is provided 
 * for you.
 */
int file_eq(char lhs_name[], char rhs_name[]) {
  FILE* lhs = fopen(lhs_name, "r");
  FILE* rhs = fopen(rhs_name, "r");

  // don't compare if we can't open the files
  if (lhs == NULL || rhs == NULL) return 0;

  int match = 1;
  // read until both of the files are done or there is a mismatch
  while (!feof(lhs) || !feof(rhs)) {
	if (feof(lhs) ||                      // lhs done first
		feof(rhs) ||                  // rhs done first
		(fgetc(lhs) != fgetc(rhs))) { // chars don't match
	  match = 0;
	  break;
	}
  }
  fclose(lhs);
  fclose(rhs);
  return match;
}
