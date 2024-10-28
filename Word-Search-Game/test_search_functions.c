// test_search_functions.c
// Mirabel Luo, mluo12, 4AD13F

#include <stdio.h>
#include <assert.h>
#include <string.h>
#include <ctype.h>
#include "search_functions.h"

/* 
 * Declarations for tester functions whose definitions appear below.
 * (You will need to fill in the function definition details, at the
 * end of this file, and add comments to each one.) 
 * Additionally, for each helper function you elect to add to the 
 * provided search_functions.h, you will need to supply a corresponding
 * tester function in this file.  Add a declaration for it here, its
 * definition below, and a call to it where indicated in main.
 */
void test_file_eq();      // This one is already fully defined below.
void test_populate_grid();
void test_find_right();
void test_find_left();
void test_find_down();
void test_find_up();
void test_find_all();


/*
 * Main method which calls all test functions.
 */
int main() {
  printf("Testing file_eq...\n");
  test_file_eq();
  printf("Passed file_eq test.\n\n");

  printf("Running search_functions tests...\n");
  test_populate_grid();
  test_find_right();
  test_find_left();
  test_find_down();
  test_find_up();
  test_find_all();

  /* You may add calls to additional test functions here. */

  printf("Passed search_functions tests!!!\n");
}

/*
 * Test file_eq on same file, files with same contents, files with
 * different contents and a file that doesn't exist.
 * Relies on files test1.txt, test2.txt, test3.txt being present.
 */
void test_file_eq() {
  FILE* fptr = fopen("test1.txt", "w");
  fprintf(fptr, "this\nis\na test\n");
  fclose(fptr);

  fptr = fopen("test2.txt", "w");
  fprintf(fptr, "this\nis\na different test\n");
  fclose(fptr);

  fptr = fopen("test3.txt", "w");
  fprintf(fptr, "this\nis\na test\n");
  fclose(fptr);

  assert( file_eq("test1.txt", "test1.txt"));
  assert( file_eq("test2.txt", "test2.txt"));
  assert(!file_eq("test2.txt", "test1.txt"));
  assert(!file_eq("test1.txt", "test2.txt"));
  assert( file_eq("test3.txt", "test3.txt"));
  assert( file_eq("test1.txt", "test3.txt"));
  assert( file_eq("test3.txt", "test1.txt"));
  assert(!file_eq("test2.txt", "test3.txt"));
  assert(!file_eq("test3.txt", "test2.txt"));
  assert(!file_eq("", ""));  // can't open file
}



void test_populate_grid(){
  char grid [MAX_SIZE][MAX_SIZE] = {0};
  assert(populate_grid(grid, "nonexistent_file.txt") == -1);
  assert(populate_grid(grid, "test1.txt") == -2);
  assert(populate_grid(grid, "grid_uneven.txt") == -2);
  assert(populate_grid(grid, "grid_empty.txt") == -2);
  assert(populate_grid(grid, "grid_large.txt") == -2);
  assert(populate_grid(grid, "grid_rowbig.txt") == -2);
  assert(populate_grid(grid, "grid.txt") == 4);

  printf("populate_grid tests passed!");
}


void test_find_right(){
  char grid[MAX_SIZE][MAX_SIZE] = {
    {'t', 'e', 'a', 'k'},
    {'o', 'l', 'p', 'e'},
    {'p', 'k', 'e', 'y'},
    {'t', 'o', 'p', 'e'}
  };

  int n = 4; 

  FILE* tmpFile = tmpfile(); // creates a temporary file for output
  assert(find_right(grid, n, "tea", tmpFile) == 1);
  assert(find_right(grid, n, "key", tmpFile) == 1);
  fclose(tmpFile); // close the temporary file

  printf("find_right tests passed!\n");
}


void test_find_left(){

  char grid[MAX_SIZE][MAX_SIZE] = {
    {'p', 'i', 't', 'k'},
    {'o', 'l', 'p', 'e'},
    {'p', 'p', 'a', 'y'},
    {'t', 'o', 'p', 'e'}
  };

  int n = 4; 

  FILE* tmpFile = tmpfile(); // creates a temporary file for output
  assert(find_left(grid, n, "tip", tmpFile) == 1);
  assert(find_left(grid, n, "app", tmpFile) == 1);
  fclose(tmpFile); // close the temporary file

  printf("find_left tests passed!\n");
}


void test_find_down(){

  char grid[MAX_SIZE][MAX_SIZE] = {
    {'p', 'i', 'e', 'k'},
    {'o', 'l', 'w', 'e'},
    {'p', 'p', 'w', 'y'},
    {'t', 'o', 'w', 'e'}
  };

  int n = 4; 

  FILE* tmpFile = tmpfile(); // creates a temporary file for output
  assert(find_down(grid, n, "ewww", tmpFile) == 1);
  assert(find_down(grid, n, "eye", tmpFile) == 1);
  fclose(tmpFile); // close the temporary file

  printf("find_down tests passed!\n");

}


void test_find_up(){

  char grid[MAX_SIZE][MAX_SIZE] = {
    {'n', 'n', 't', 'k'},
    {'o', 'o', 'p', 'e'},
    {'o', 'o', 'a', 'y'},
    {'t', 't', 'p', 'e'}
  };

  int n = 4; 

  FILE* tmpFile = tmpfile(); // creates a temporary file for output
  assert(find_up(grid, n, "toon", tmpFile) == 2);
  assert(find_up(grid, n, "pap", tmpFile) == 1);
  fclose(tmpFile); // close the temporary file

  printf("find_up tests passed!\n");

}


void test_find_all(){

  char grid[MAX_SIZE][MAX_SIZE] = {
    {'e', 'y', 'e', 'K'},
    {'o', 'l', 'p', 'e'},
    {'p', 'P', 'a', 'y'},
    {'t', 'o', 'p', 'e'}
  };

  int n = 4; 

  FILE* tmpFile = tmpfile(); // creates a temporary file for output
  char input[] = "eYe";
  for (int idx = 0; idx < (int) strlen(input); idx++) {
      input[idx] = tolower(input[idx]);
  }
  assert(find_all(grid, n, input, tmpFile) == 4);
  assert(find_all(grid, n, "hmm", tmpFile) == 0);
  fclose(tmpFile); // close the temporary file
  
  //testing with fileq
  char grid_new[MAX_SIZE][MAX_SIZE] = {
    {'p', 'i', 't', 'k'},
    {'o', 'l', 'p', 'e'},
    {'p', 'k', 'e', 'y'},
    {'t', 'o', 'p', 'e'}
  };

  FILE* output = fopen("output.txt", "w");

  find_all(grid_new, n, "tip", output);
  find_all(grid_new, n, "pop", output);
  find_all(grid_new, n, "key", output);
  find_all(grid_new, n, "nope", output);
  fclose(output);
  assert( file_eq("expected.txt", "output.txt"));
  printf("find_all tests passed!\n");
}
