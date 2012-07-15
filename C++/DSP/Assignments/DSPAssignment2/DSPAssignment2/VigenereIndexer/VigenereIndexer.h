/*
 * VigenereIndexer.h
 *
 *  Created on: Mar 27, 2011
 *      Author: Charlotte
 */

#ifndef VIGENEREINDEXER_H_
#define VIGENEREINDEXER_H_

#include "Vigenere.h"

class VigenereIndexer
{
private:
	Vigenere fCipher;
	bool fMode;

public:
	VigenereIndexer(char* aKey, bool aMode);
	char operator[](const char aChar);

	friend std::ostream& operator<<(std::ostream& aOStream, const VigenereIndexer& aIndexer);
};


#endif /* VIGENEREINDEXER_H_ */
