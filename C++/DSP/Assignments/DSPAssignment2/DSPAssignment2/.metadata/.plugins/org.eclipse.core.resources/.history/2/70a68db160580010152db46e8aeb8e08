/*
 * main.cpp
 *
 *  Created on: Mar 27, 2011
 *      Author: Charlotte
 */

#include <Vigenere.h>
#include <iostream>
#include <fstream>
#include <stdlib.h>
#include <string.h>

using namespace std;

int main(int argc, char* argv[])
{
	ifstream lReader;
	ofstream lWriter;
	string lLine;

	//check correct number of arguments
	if(argc != 3){
		cout << "Incorrect number of arguments!" << endl;
		cout << "Program execution: 'scramble' 'key' fileToUnscramble.txt" << endl;
		exit(1);
	}

	Vigenere* lScrambler = new Vigenere(argv[1]);

	//create and open input and output files
	lReader.open(argv[2]);
	char temp[strlen(argv[2]) + 11]; //create temp character array 11 characters longer than file name (to allow for appending)
	strcpy(temp, argv[2]);
	lWriter.open(strcat(temp, ".public.txt")); //append original file with '.public.txt' and open for output

	//check files opened correctly
	if(!lReader.is_open()){
		cout << "Error opening specified file!" << endl;
		exit(1);
	}
	else if(!lWriter.is_open()){
		cout << "Error opening file to save decoded text!" << endl;
		exit(1);
	}

	//Read each line, scramble, and write to the output file
	while(getline(lReader, lLine)){
		lWriter << lScrambler->decode(lLine);
	}

	//Print character frequencies
	cout << "Unscrambling '" << argv[2] << "' using key: " << argv[1] << endl;
	cout << *lScrambler;

	lReader.close();
	lWriter.close();
	delete lScrambler;

	return 0;
}
