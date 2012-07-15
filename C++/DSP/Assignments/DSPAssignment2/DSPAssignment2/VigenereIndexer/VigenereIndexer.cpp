/*
 * VigenereIndexer.cpp
 *
 *  Created on: Mar 27, 2011
 *      Author: Charlotte
 */

#include "VigenereIndexer.h"

VigenereIndexer::VigenereIndexer(char* aKey, bool aMode): fCipher(aKey)
{
	fMode = aMode; //true for encoding, false for decoding
}

char VigenereIndexer::operator[](const char aChar)
{
	if(fMode){
		//Encoding
		return fCipher.encode(aChar);
	}
	else{
		//Decoding
		return fCipher.decode(aChar);
	}
}

//Prints character frequencies
std::ostream& operator<<(std::ostream& aOStream, const VigenereIndexer& aIndexer)
{
	aOStream << aIndexer.fCipher;
	return aOStream;
}
