# Makefile HW 7
CC=g++
CFLAGS=-std=c++11 -pedantic -Wall -Wextra

#links together files needed to create executable cTreeTest
cTreeTest: CTreeTest.o CTree.o
	$(CC) -o cTreeTest CTreeTest.o CTree.o

#links together files needed to create executable tTreeTest
tTreeTest: TTreeTest.o
	$(CC) $(CFLAGS) -o tTreeTest TTreeTest.o

#compiles digigraph_analyzer
CTreeTest.o: CTreeTest.cpp CTree.h
	$(CC) $(CFLAGS) -c CTreeTest.cpp

#compiles digraph_functions
CTree.o: CTree.cpp CTree.h 
	$(CC) $(CFLAGS) -c CTree.cpp 

#compiles TTreeTest.cpp - tree.h includes tree.inc
TTreeTest.o: TTreeTest.cpp Tree.h
	$(CC) $(CFLAGS) -c TTreeTest.cpp

# Removes all object files and the executable named main,
# so we can start fresh
clean:
	rm -f *.o cTreeTest
	rm -f *.o tTreeTest
