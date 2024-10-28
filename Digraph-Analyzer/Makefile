# Makefile HW 5
CC=g++
CFLAGS=-std=c++11 -pedantic -Wall -Wextra

# Links together files needed to create executable
digraph_analyzer: digraph_analyzer.o digraph_functions.o
	$(CC) -o digraph_analyzer digraph_analyzer.o digraph_functions.o

#compiles digigraph_analyzer
digraph_analyzer.o: digraph_analyzer.cpp digraph_functions.h
	$(CC) $(CFLAGS) -c digraph_analyzer.cpp

#compiles digraph_functions
digraph_functions.o: digraph_functions.cpp digraph_functions.h 
	$(CC) $(CFLAGS) -c digraph_functions.cpp 

# Removes all object files and the executable named main,
# so we can start fresh
clean:
	rm -f *.o digraph_analyzer
