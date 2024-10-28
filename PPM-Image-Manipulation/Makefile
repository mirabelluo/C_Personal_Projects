# Makefile Midterm Project
# Mirabel Luo and Sophie Noureddine

CC=gcc
CFLAGS=-std=c99 -pedantic -Wall -Wextra -g

# Links together files needed to create executable
project: project.o image_manip.o ppm_io.o
	$(CC) -o project project.o image_manip.o ppm_io.o -lm

checkerboard: checkerboard.o image_manip.o ppm_io.o image_manip.o
	$(CC) -o checkerboard checkerboard.o image_manip.o ppm_io.o -lm

img_cmp: img_cmp.o image_manip.o ppm_io.o image_manip.o
	$(CC) -o img_cmp img_cmp.o image_manip.o ppm_io.o -lm
 
#compiles project.o
project.o: project.c ppm_io.h image_manip.h
	$(CC) $(CFLAGS) -c project.c

checkerboard.o: checkerboard.c ppm_io.h
	$(CC) $(CFLAGS) -c checkerboard.c

#compiles image_manip.o
image_manip.o: image_manip.c ppm_io.h image_manip.h
	$(CC) $(CFLAGS) -c image_manip.c -lm

#compiles img_cmp.o
img_cmp.o: img_cmp.c ppm_io.h
	$(CC) $(CFLAGS) -c img_cmp.c

#compiles ppm_io.c
ppm_io.o: ppm_io.c ppm_io.h
	$(CC) $(CFLAGS) -c ppm_io.c

# Removes all object files and the executable named main,
# so we can start fresh
clean:
	rm -f *.o project
