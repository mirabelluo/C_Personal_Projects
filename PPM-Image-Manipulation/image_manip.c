#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <assert.h>
#include "image_manip.h"
#include "ppm_io.h"


//______grayscale______
/* convert an image to grayscale (NOTE: pixels are still
* RGB, but the three values will be equal)
*/
Image grayscale( const Image in ) {

    Image out = make_image(in.rows, in.cols);

    if (!out.data) {
        fprintf(stderr, "Error:grayscale - unable to allocate memory for image data.\n");
        out.rows = 0;
        out.cols = 0;
        out.data = NULL;
        return out;
    }

    for (int i = 0; i < out.rows * out.cols; i++) {
        
        //weights RGB values to create a grayscale
        unsigned char gray = (unsigned char)(0.3 * in.data[i].r + 0.59 * in.data[i].g + 0.11 * in.data[i].b);

        out.data[i].r = gray;
        out.data[i].g = gray;
        out.data[i].b = gray;
    }

    return out;
}

/* _______alpha blend________
* blend two images into one using the given alpha factor
*/
Image blend( const Image in1, const Image in2 , double alpha ) {

    // initializing dementions of new image
    int max_width;
    int max_height;
    
    if (in1.cols > in2.cols) {
        max_width = in1.cols;
    }
    else {
        max_width = in2.cols;
    }

    if (in1.rows > in2.rows) {
        max_height = in1.rows;
    }
    else {
        max_height = in2.rows;
    }

    // Initalizing variables to store the colors blended together
    int green = 0;
    int red = 0; 
    int blue = 0;


    // Creating new image to store the final result
    Image newImg = make_image(max_height, max_width);

    // checking that memory was allocted go the new image
    if (!newImg.data) {
        fprintf(stderr, "Error:blend - failed allocate memory for newImg.data\n");
        newImg.rows = 0;
        newImg.cols = 0;
        newImg.data = NULL;
        return newImg;
    }

    // Iterating through each pixel to store the final result
    for(int i = 0; i < max_height; i++) {
        for(int j = 0; j < max_width; j++) {

            //Checking if current pixels are within the bounds of both images
            if(i < in1.rows && j < in1.cols && i < in2.rows && j < in2.cols) {

                // Blending the colors using the alpha variable
                green = (in1.data[i * in1.cols + j].g * alpha) + (in2.data[i * in2.cols + j].g * (1 - alpha));
                red = (in1.data[i * in1.cols + j].r * alpha) + (in2.data[i * in2.cols + j].r * (1 - alpha));
                blue = (in1.data[i * in1.cols + j].b * alpha) + (in2.data[i * in2.cols + j].b * (1 - alpha));

                //Clamping color values to be within 0 and 255
                if (green < 0) {
                    green = 0;
                }
                else if (green > 255) {
                    green = 255;
                }

                if (red < 0) {
                    red = 0;
                }
                else if (red > 255) {
                    red = 255;
                }

                if (blue < 0) {
                    blue = 0;
                }
                else if (blue > 255) {
                    blue = 255;
                } 
                
                // Assigning the belnded colors to the pixels in the result image
                newImg.data[i * max_width + j].b = blue;
                newImg.data[i * max_width + j].r = red;
                newImg.data[i * max_width + j].g = green;

            }

            // Checking if the current pixels is outside the bound of one of the images 
            // to then just set the pixel to the original image
            else if (i < in1.rows && j < in1.cols) {
                newImg.data[i * max_width + j] = in1.data[i * in1.cols + j];
            }

            else if (i < in2.rows && j < in2.cols) {
                newImg.data[i * max_width + j] = in2.data[i * in2.cols + j];
            }

        }
    }

    //returning the blended image
    return newImg;

}


/* _______rotate-ccw________
* rotate the input image counter-clockwise
*/
Image rotate_ccw( const Image in ) {

    // Calculating dimentions of the new image
    int new_height = in.cols;
    int new_width = in.rows;

    // Creating new image to store the output in
    Image newIm;
    newIm.cols = new_width;
    newIm.rows = new_height;

    // Allocating memory for the pixel data to the new image
    newIm.data = (Pixel *)malloc(newIm.cols * newIm.rows * sizeof(int));

    // Checking that memory was properly allocated for newIm
    if (!newIm.data) {
        fprintf(stderr, "Error:rotate-ccw - failed to allocate memory for new rotated image\n");
        newIm.rows = 0;
        newIm.cols = 0;
        newIm.data = 0;
        return newIm;
    }

    // Iterating over each pixel in the new Image
    for (int i = 0; i < newIm.rows; i++) {
        for(int j = 0; j < newIm.cols; j++) {

            // Calculating the coordinates of the new pixel from the old image to the new one
            int original_pixelcols = newIm.rows - i - 1;
            int original_pixelrows = j;

            // Copying the pixel value from the orginal image to the new image
            newIm.data[i * newIm.cols + j] = in.data[original_pixelrows * in.cols + original_pixelcols];

        }
    }

    // Returning the new Rotated image
    return newIm;

}


/* _______pointilism________
* apply a painting like effect i.e. poitilism technique.
*/
Image pointilism( const Image in , unsigned int seed ) {

    // intitializing the height andd the width of the new Image
    int height = in.rows;
    int width = in.cols;

    //creating the new image to store output in
    Image newImg = make_image(height, width);

    // checking that memory was allocted go the new image
    if (!newImg.data) {
        fprintf(stderr, "Error:pointilism - failed allocate memory for newImg.data\n");
        newImg.rows = 0;
        newImg.cols = 0;
        newImg.data = NULL;
        return newImg;
    }

    //finding total number of pixels and 3% of the total number of pixels
    int total_num_pixels = height * width;
    int three_percent = (0.03 * total_num_pixels);

    srand(seed);

    //looping through 3 percent of the  total number of pixels
    for(int a = 0; a < three_percent; a++) {

        // getting random cordinates for the pixels and a random number for radius 1-5
        int rand_row = rand() % height;
        int rand_col = rand() % width;
        int rand_radius = (rand() % 5) + 1;

        //creating pixel with the value of the same pixel from the original image
        Pixel pixel1 = in.data[rand_row * width + rand_col];

        //setting bounds for the randdom radius chosen and looping through all the pixels within those bounds
        for(int i = -rand_radius; i <= rand_radius; i++) {
            for(int j = -rand_radius; j <= rand_radius; j++) {
                //using distance formula 
                if(i * i + j * j <= rand_radius * rand_radius) {
                    int newRow = rand_row + i;
                    int newCol = rand_col + j;

                    //filing new image with data from orginal image as long as it is within the bounnds of the picture  and  the  radius
                    if(newRow >= 0 && newRow < height && newCol >= 0 && newCol < width) {
                        newImg.data[newRow * width + newCol] = pixel1;
                    }

                    //going through all 4 quadrants to apply pixel value
                    if (i != 0 && j != 0) {

                        //fourth quadrant
                        newRow = rand_row - i;
                        newCol = rand_col + j;
                        if(newRow >= 0 && newRow < height && newCol >= 0 && newCol < width) {
                            newImg.data[newRow * width + newCol] = pixel1;
                        }

                        //second quadrant
                        newRow = rand_row + i;
                        newCol = rand_col - j;
                        if(newRow >= 0 && newRow < height && newCol >= 0 && newCol < width) {
                            newImg.data[newRow * width + newCol] = pixel1;
                        }

                        //third quadrant
                        newRow = rand_row - i;
                        newCol = rand_col - j;
                        if(newRow >= 0 && newRow < height && newCol >= 0 && newCol < width) {
                            newImg.data[newRow * width + newCol] = pixel1;
                        }
                    }
                }   
            }
        }

    }
    //returning the new pixelated image
    return newImg;
}

//______make_gaussian______
/* makes a gaussian filter matrix
*/
double* make_gaussian(int n, double sigma) {

    int center = n / 2;
    int tot_size = n * n;

    double* filter = (double *) malloc(tot_size * sizeof(double));
    
    if (!filter) {
        fprintf(stderr, "Error:blur - failed to allocate memory for Gaussian filter\n");
        return NULL;
    }

    double sum = 0.0;

    //populating matrix
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            //calculating offsets from center
            int dx = i - center;
            int dy = j - center;

            double g = (1.0 / (2.0 * PI * sq(sigma))) * exp(-(sq(dx) + sq(dy)) / (2 * sq(sigma)));
            filter[i * n + j] = g;
            
            sum += g;
        }
    }
    return filter;
}

//______blur______
/* apply a blurring filter to the image
*/
Image blur( const Image in , double sigma ) {

    Image out = make_image(in.rows, in.cols);

    if (!out.data) {
        fprintf(stderr, "Error:blur - failed allocate memory for out.data\n");
        out.rows = 0;
        out.cols = 0;
        out.data = NULL;
        return out;
    }

    int n = (int) 10 * sigma;
    if (n % 2 == 0) {
        n = n + 1;
    }

    int center = n / 2;

    double* filter = make_gaussian(n, sigma); //uses helper make_gaussian
    
    if (filter == NULL) {
        out.rows = 0;
        out.cols = 0;
        out.data = NULL;
        return out;
    }

    if (!filter) {
        fprintf(stderr, "Error:blur - failed to make Gaussian filter\n");
        free(out.data);
        out.rows = 0;
        out.cols = 0;
        out.data = NULL;
        return out;
    }

    for (int y = 0; y < in.cols; y++) { //looping through columnns first
        for (int x = 0; x < in.rows; x++) {
            double summed_r = 0.0, summed_g = 0.0, summed_b = 0.0, sum = 0.0;
            for (int dy = 0; dy < n; dy++) { //looping over each filter dimension
                for (int dx = 0; dx < n; dx++) {
                    //calculating new offsets
                    int new_x = x + (dx - center);
                    int new_y = y + (dy - center);

                    if ((new_x >= 0 && new_x < in.rows) && (new_y >= 0 && new_y < in.cols)) { //checks if within image bounds
                        //get weights
                        double convolve = filter[(dx)*n + (dy)];
                        Pixel p = in.data[new_x * in.cols + new_y];

                        summed_r += p.r * convolve;
                        summed_g += p.g * convolve;
                        summed_b += p.b * convolve;
                        sum += convolve;
                    }
                }
            }
            //calculate output pixel location
            Pixel * out_p = &out.data[x * in.cols + y];
            if (sum > 0) { // checks to ensure sum is not zero to avoid division by zero
                out_p->r = summed_r / sum;
                out_p->g = summed_g / sum;
                out_p->b = summed_b / sum;
            } 
            else {
                // in case the sum is 0 (which should not happen in normal conditions), fallback to input pixel
                out.data[x * in.cols + y] = in.data[x * in.cols + y];
            }
        }
    }
    free(filter);
    return out;
}
//______saturate______
/* Saturate the image by scaling the deviation from gray
*/
Image saturate( const Image in , double scale ) {

    Image grayscaled = grayscale(in); //produces grayscale image

    //error handling
    if (grayscaled.data == NULL) {
        fprintf(stderr, "Error:saturate - unable to get grayscale image.\n");
        return grayscaled;
    }

    Image saturated = make_image(in.rows, in.cols);
    if (saturated.data == NULL) {
        fprintf(stderr, "Error:saturate - unable to allocate memory for saturated image.\n");
        return saturated;
    }

    //loops through each pixel and scales
    for (int i = 0; i < grayscaled.rows * grayscaled.cols; i++) {

        int diff_r = in.data[i].r - grayscaled.data[i].r;
        int diff_g = in.data[i].g - grayscaled.data[i].g;
        int diff_b = in.data[i].b - grayscaled.data[i].b;

        //stores scaled value as int to allow for vals less than 0 and greater than 255
        int temp_r = grayscaled.data[i].r + diff_r * scale;
        int temp_g = grayscaled.data[i].g + diff_g * scale;
        int temp_b = grayscaled.data[i].b + diff_b * scale;

        //clamping for r, g, and b
        if (temp_r < 0) {
            temp_r = 0;
        }
        else if (temp_r > 255) {
            temp_r = 255;
        }

        if (temp_g < 0) {
            temp_g = 0;
        }
        else if (temp_g > 255) {
            temp_g = 255;
        }

        if (temp_b < 0) {
            temp_b = 0;
        }
        else if (temp_b > 255) {
            temp_b = 255;
        }

        //assigning each pixel to correct scaling after clamping
        saturated.data[i].r = temp_r;
        saturated.data[i].g = temp_g;
        saturated.data[i].b = temp_b;

    }

    free(grayscaled.data);

    return saturated;
}