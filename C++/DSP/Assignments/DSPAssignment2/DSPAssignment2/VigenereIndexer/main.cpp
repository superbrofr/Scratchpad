/*
 * main.cpp
 *
 *  Created on: Mar 27, 2011
 *      Author: Charlotte
 */

#include "VigenereIndexer.h"
#include <iostream>
#include <fstream>
#include <string.h>
#include <stdlib.h>

using namespace std;

int main(int argc, char* argv[])
{
	ifstream lReader;
	ofstream lWriter;
	string strMode; //hold string representation of mode - char* causes issues
	bool lMode;

	//check arguments
	if(argc != 4){
		cout << "Invalid arguments!" << endl;
		cout << "Execution: VigenereIndexer encode/decode key fileToUse.txt" << endl;
	}
	//find mode
	strMode = argv[1];
	if(strMode == "encode"){
		lMode = true;
	}
	else if(strMode == "decode"){
		lMode = false;
	}
	else{
		cout << "Invalid mode! Mode must be 'encode' or 'decode'" << endl;
		exit(1);
	}

	//create and open files
	lReader.open(argv[3]);
	char temp[(strlen(argv[3]) + 11)];//create temp for file name, allow for appending
	strcpy(temp, argv[3]);
	if(lMode){
		//create file for encode
		lWriter.open(strcat(temp, ".encode.txt"));
	}
	else{
		//create file for decode
		lWriter.open(strcat(temp, ".decode.txt"));
	}
	//check files opened correctly
	if(!lReader.is_open()){
		cout << "Error opening file to be worked on!" << endl;
		exit(1);
	}
	else if(!lWriter.is_open()){
		cout << "Error opening file to save to!" << endl;
		exit(1);
	}

	//create indexer
	VigenereIndexer lScrambler(argv[2], lMode);

	//encode or decode specified file
	char lChar;
	while((lChar = lReader.get()) != EOF){
		lWriter << lScrambler[lChar];
	}

	//Print frequencies
	if(lMode){
		cout << "Encoding '";
	}
	else{
		cout << "Decoding '";
	}
	cout << argv[3] << "' using key: " << argv[2] << endl;
	cout << lScrambler;

	lReader.close();
	lWriter.close();

	return 0;
}
