//project.c

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ppm_io.h"
#include "image_manip.h"

// Return (exit) codes
#define RC_SUCCESS            0
#define RC_MISSING_FILENAME   1
#define RC_OPEN_FAILED        2
#define RC_INVALID_PPM        3
#define RC_INVALID_OPERATION  4
#define RC_INVALID_OP_ARGS    5
#define RC_OP_ARGS_RANGE_ERR  6
#define RC_WRITE_FAILED       7
#define RC_UNSPECIFIED_ERR    8

void print_usage();

int call_funcs(int argc, char** argv);
int call_blend(int argc, char** argv);
void close_free(FILE* in, FILE* out, Image im1, Image im2);

int main (int argc, char* argv[]) {
  
  if (argc < 4) { //checks if manndatory command line arguments are provided
    fprintf(stderr, "Error:main - Wrong usage, mandatory arguments not provided.\n");
    print_usage();
    return RC_MISSING_FILENAME;
  }

  int success_check = 8;

  if (strcmp(argv[3], "blend") == 0) {
    success_check = call_blend(argc, argv);
  }
  else {
    success_check = call_funcs(argc, argv);
  }
  
  return success_check;
}
int call_funcs(int argc, char** argv) {

  //opens input file for reading 
  FILE* in = fopen(argv[1], "r");

  if (in == NULL) { //error handling for file input
    fprintf(stderr, "Error:main - Input file I/O error.\n");
    return RC_OPEN_FAILED;
  }

  //opens output file for writing 
  FILE* out = fopen(argv[2], "w");

  if (out == NULL) { //error handling for output file
    fprintf(stderr, "Error:main - Input file I/O error.\n");
    fclose(in);
    return RC_OPEN_FAILED;
  }

  Image normal = read_ppm(in);
  if (normal.data == NULL) {
    fprintf(stderr, "Error:main - The input file cannot be read as a PPM file.\n");
    fclose(in);
    fclose(out);
    return RC_INVALID_PPM;
  }

  int success = 8;

  if (strcmp(argv[3], "grayscale") == 0) {
    Image gray = grayscale(normal);
    success = write_ppm(out, gray);

    if (gray.data == NULL) {
      close_free(in, out, normal, gray);
      return RC_UNSPECIFIED_ERR;
    }
    close_free(in, out, normal, gray);
    if (success == 2 || success == 7) {
      close_free(in, out, normal, gray);
      return success;
    }
    return RC_SUCCESS;
  }

  else if (strcmp(argv[3], "rotate-ccw") == 0) {
    Image rotated = rotate_ccw(normal);
    success = write_ppm(out, rotated);
    if (rotated.data == NULL) {
      close_free(in, out, normal, rotated);
    }
    if (success == 2 || success == 7) {
      close_free(in, out, normal, rotated);
      return success;
    }
    close_free(in, out, normal, rotated);
    return RC_SUCCESS;
  }
  else if (strcmp(argv[3], "pointilism") == 0) {
    Image point = pointilism(normal, 1);
    if (point.data == NULL) {
      close_free(in, out, normal, point);
      return RC_UNSPECIFIED_ERR;
    }
    success = write_ppm(out, point);
    if (success == 2 || success == 7) {
      close_free(in, out, normal, point);
      return success;
    }
    close_free(in, out, normal, point);
    return RC_SUCCESS;
  }
  else if (strcmp(argv[3], "blur") == 0) {
    if (argc != 5) {
      fprintf(stderr, "Error:main - Incorrect number of arguments for the specified operation\n");
      print_usage();
      return RC_INVALID_OP_ARGS;
    }
    char * endptr; //defining end point to use w/ strtod
    double sigma = strtod(argv[4], &endptr);

    if (*endptr != '\0') {
        fprintf(stderr, "Error:main - Invalid arguments for the specified operation\n");
        print_usage();
        return RC_OP_ARGS_RANGE_ERR;
    }
    Image blurred = blur(normal, sigma);
    if (blurred.data == NULL) {
      close_free(in, out, normal, blurred);
      return RC_UNSPECIFIED_ERR;
    }

    success = write_ppm(out, blurred);
    if (success == 2 || success == 7) {
      close_free(in, out, normal, blurred);
      return success;
    }
    close_free(in, out, normal, blurred);
    return RC_SUCCESS;
  }
  else if (strcmp(argv[3], "saturate") == 0) {
    if (argc != 5) {
      fprintf(stderr, "Error:main - Incorrect number of arguments for the specified operation\n");
      fclose(in);
      fclose(out);
      free_image(&normal);
      print_usage();
      return RC_INVALID_OP_ARGS;
    }

    char * endptr; //defining end point to use w/ strtod
    double scale = strtod(argv[4], &endptr);

    if (*endptr != '\0') {
        fprintf(stderr, "Error:main - Invalid arguments for the specified operation\n");
        fclose(in);
        fclose(out);
        free_image(&normal);
        print_usage();
        return RC_OP_ARGS_RANGE_ERR;
    }

    Image saturated = saturate(normal, scale);
    if (saturated.data == NULL) {
      close_free(in, out, normal, saturated);
      return RC_UNSPECIFIED_ERR;
    }

    success = write_ppm(out, saturated);
    if (success == 2 || success == 7) {
      close_free(in, out, normal, saturated);
      return success;
    }
    close_free(in, out, normal, saturated);
    return RC_SUCCESS;
  }
  else{
    fprintf(stderr, "Error:main - Unsupported image processing operations\n");
    fclose(in);
    fclose(out);
    free_image(&normal);
    print_usage();
    return RC_INVALID_OPERATION;
  }
  fprintf(stderr, "An unspecified error has occured\n");
  fclose(in);
  fclose(out);
  free_image(&normal);
  return RC_UNSPECIFIED_ERR;
}

int call_blend(int argc, char** argv) {
  
  if (argc != 6) {
      fprintf(stderr, "Error:main - Incorrect number of arguments for the specified operation\n");
      print_usage();
      return RC_INVALID_PPM;
    }

    //opens input file for reading 
    FILE* in = fopen(argv[1], "r");

    if (in == NULL) { //error handling for file input
      fprintf(stderr, "Error:main - Input file I/O error.\n");
      fclose(in);
      return RC_OPEN_FAILED;
    }

    //opens output file for writing 
    FILE* in2 = fopen(argv[2], "r");

    if (in2 == NULL) { //error handling for output file
      fprintf(stderr, "Error:main - Input file I/O error.\n");
      fclose(in);
      fclose(in2);
      return RC_OPEN_FAILED;
    }

    Image normal = read_ppm(in);
    Image normal2 = read_ppm(in2);
    
    if (normal.data == NULL) {
      fprintf(stderr, "Error:main - The input file cannot be read as a PPM file.\n");
      fclose(in);
      fclose(in2);
      return RC_INVALID_PPM;
    }

    FILE * real_out = fopen(argv[4], "w");

    char * endptr; //defining end point to use w/ strtod
    double alpha = strtod(argv[5], &endptr);

    if (*endptr != '\0') {
        fprintf(stderr, "Error:main - Invalid arguments for the specified operation\n");
        print_usage();
        close_free(in, in2, normal, normal2);
        fclose(real_out);
        return RC_OP_ARGS_RANGE_ERR;
    }
    if (alpha > 1 || alpha < 0) {
        fprintf(stderr, "Error-blend: Alpha must be a value between 0 and 1\n");
        print_usage();
        close_free(in, in2, normal, normal2);
        fclose(real_out);
        return RC_OP_ARGS_RANGE_ERR;
    }
    Image blended = blend(normal, normal2, alpha);
    if (blended.data == NULL) {
      close_free(in, in2, normal, normal2);
      fclose(real_out);
      return RC_UNSPECIFIED_ERR;
    }
    int success = write_ppm(real_out, blended);
    if (success == 2 || success == 7) {
      close_free(in, in2, normal, normal2);
      fclose(real_out);
      free(blended.data);
      return success;
    }
    close_free(in, in2, normal, normal2);
    fclose(real_out);
    free(blended.data);
    return RC_SUCCESS;
} 
void close_free(FILE* in, FILE* out, Image im1, Image im2) {
  fclose(in);
  fclose(out);
  free_image(&im1);
  free_image(&im2);
}

void print_usage() {
  printf("USAGE: ./project <input-image> <output-image> <command-name> <command-args>\n");
  printf("SUPPORTED COMMANDS:\n");
  printf("   grayscale\n" );
  printf("   blend <target image> <alpha value>\n" );
  printf("   rotate-ccw\n" );
  printf("   pointilism\n" );
  printf("   blur <sigma>\n" );
  printf("   saturate <scale>\n" );
}