# Makefile HW 3
# Mirabel Luo, mluo12, 4AD13F

CC=gcc
CFLAGS=-std=c99 -pedantic -Wall -Wextra

# Links together files needed to create executable
word_search: word_search.o search_functions.o
	$(CC) -o word_search word_search.o search_functions.o

test: test_search_functions.o search_functions.o
	$(CC) -o test test_search_functions.o search_functions.o

test_search_functions.o: test_search_functions.c search_functions.H
	$(CC) $(CFLAGS) -c test_search_functions.c
#compiles word_search.o
word_search.o: word_search.c search_functions.h
	$(CC) $(CFLAGS) -c word_search.c

# compiles search_functions.c
search_functions.o: search_functions.c search_functions.h
	$(CC) $(CFLAGS) -c search_functions.c

# Removes all object files and the executable named main,
# so we can start fresh
clean:
	rm -f *.o word_search
