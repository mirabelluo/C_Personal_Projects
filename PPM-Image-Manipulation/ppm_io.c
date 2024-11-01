#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <ctype.h>
#include "ppm_io.h"

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


/* helper function for read_ppm, takes a filehandle
 * and reads a number, but detects and skips comment lines
 */
int read_num( FILE *fp ) {
  assert(fp);

  int ch;
  while((ch = fgetc(fp)) == '#') { // # marks a comment line
    while( ((ch = fgetc(fp)) != '\n') && ch != EOF ) {
      /* discard characters til end of line */
    }
  }
  ungetc(ch, fp); // put back the last thing we found

  int val;
  if (fscanf(fp, "%d", &val) == 1) { // try to get an int
    while(isspace(ch = fgetc(fp))) {
      // drop trailing whitespace
    }
    ungetc(ch, fp);
    return val; // we got a value, so return it
  } else {
    fprintf(stderr, "Error:ppm_io - failed to read number from file\n");
    return -1;
  }
}

Image read_ppm( FILE *fp ) {
  Image im = { NULL , 0 , 0 };
  
  /* confirm that we received a good file handle */
  if( !fp ){
	fprintf( stderr , "Error:ppm_io - bad file pointer\n" );
	return im;
  }

  int rows=-1 , cols=-1;

  /* read in tag; fail if not P6 */
  char tag[20];
  tag[19] = '\0';
  fscanf( fp , "%19s\n" , tag );
  if( strncmp( tag , "P6" , 20 ) ) {
	fprintf( stderr , "Error:ppm_io - not a PPM (bad tag)\n" );
	return im;
  }


  /* read image dimensions */

  //read in columns
  cols = read_num( fp ); // NOTE: cols, then rows (i.e. X size followed by Y size)
  //read in rows
  rows = read_num( fp );

  //read in colors; fail if not 255
  int colors = read_num( fp );
  if( colors!=255 ){
	fprintf( stderr , "Error:ppm_io - PPM file with colors different from 255\n" );
	return im;
  }

  //confirm that dimensions are positive
  if( cols<=0 || rows<=0 ){
	fprintf( stderr , "Error:ppm_io - PPM file with non-positive dimensions\n" );
	return im;
  }

  /* Allocate the new image */
  im = make_image( rows , cols );
  if( !im.data ){
	fprintf( stderr , "Error:ppm_io - Could not allocate new image\n" );
	return im;
  }
  /* finally, read in Pixels */

  /* read in the binary Pixel data */
  if( fread( im.data , sizeof(Pixel) , im.rows * im.cols , fp ) != (size_t)(im.rows * im.cols) ) {
    fprintf(stderr, "Error:ppm_io - failed to read data from file!\n");
	free_image( &im );
    return im;
  }

  //return the image struct pointer
  return im;
}



/* Write given image to disk as a PPM; assumes fp is not null */
int write_ppm( FILE *fp , const Image im ) {

  if(fp == NULL) {
    fprintf(stderr, "Error:write_ppm - failed to read data from file\n");
    return RC_OPEN_FAILED;
  }

  fprintf(fp, "P6\n");
  fprintf(fp, "%d %d\n%d\n", im.cols, im.rows, 255);

  size_t num_pixels = im.rows * im.cols;

  if(fwrite((void *) im.data, sizeof(Pixel), num_pixels, fp) != num_pixels) {
    fprintf(stderr, "Error:write_ppm - Output file I/O error");
    return RC_WRITE_FAILED;
  }

  return num_pixels;
}


/* allocate a new image of the specified size;
 * doesn't initialize pixel values */
Image make_image( int rows , int cols ) {

  Image im = {NULL, rows, cols}; //initializes Image struct
  im.data = (Pixel *) calloc(rows * cols, sizeof(Pixel));

  //error handling for improper memory allocation
  if (im.data == NULL) {
    fprintf(stderr, "Error:make_image - unable to allocate memory for image data.\n");
    im.rows = 0;
    im.cols = 0;
    return im;
  }

  return im;
}


/* output dimensions of the image to stdout */
void output_dims( const Image im ) {
  printf( "cols = %d, rows = %d" , im.cols , im.rows );
}

/* free_image
 * utility function to free inner and outer pointers, 
 * and set to null 
 */
void free_image( Image *im ) {
  
  if (im != NULL && (*im).data != NULL) {
    free((*im).data); 
    (*im).data = NULL; 
  }

}
