# sets are lists where each element must be unique - adding a duplicate is ignored
# sets are unordered - items are added in a seeminly random position

# elements can't be accessed through a number, but sets are super fast and give you access to mathematical set functions

mind = set([42, "some string", (23, 4)])
print mind

mind = set([32, "string", 40, 0])
print mind

mind.add("hi there")
print mind

mind.add("hi")
mind.add("hi")
print mind