#ifndef TREEVISITOR_H_
#define TREEVISITOR_H_

#include <iostream>

template<class T>
class TreeVisitor
{
public:
	virtual ~TreeVisitor() {}	// virtual default destructor
	
	// default behavior
	virtual void preVisit( const T& aKey ) const
	{}

	virtual void postVisit( const T& aKey ) const
	{}

	virtual void inVisit( const T& aKey ) const
	{}

	virtual void visit( const T& aKey ) const
	{
		std::cout << aKey << " ";
	}
};

template<class T>
class PreOrderVisitor : public TreeVisitor<T> 
{
public:

	// override pre-order behavior
	virtual void preVisit( const T& aKey ) const 
	{
		visit( aKey ); 	// invoke default behavior
	}
};

template<class T>
class PostOrderVisitor : public TreeVisitor<T> 
{
public:

	// override post-order behavior
	virtual void postVisit( const T& aKey ) const 
	{
		visit( aKey ); 	// invoke default behavior
	}
};

template<class T>
class InOrderVisitor : public TreeVisitor<T> 
{
public:

	// override in-order behavior
	virtual void inVisit( const T& aKey ) const 
	{
		visit( aKey ); 	// invoke default behavior
	}
};

#endif /* TREEVISITOR_H_ */
