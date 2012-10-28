# This project-specific urls file maps regexes within the application to show specific views (i.e., generated pages)

from django.conf.urls import patterns, url
from polls import views

urlpatterns = patterns('',
	# url(regex, view, name, kwargs)
	# name and kwargs are optional parameters
	url(r'^$', views.index, name='index'), # maps empty pattern to the views.index view
# e.g., /polls/5/
	url(r'^(?P<poll_id>\d+)/$', views.detail, name='detail'), # defining 'name' means you can use names rather than absolute paths when creating hyperlinks - thus you only have to change the url here, as the name reference will be the same
# e.g., /polls/5/results/
	url(r'^(?P<poll_id>\d+)/results/$', views.results, name='results'),
# e.g., polls/5/vote/
	url(r'^(?P<poll_id>\d+)/vote/$', views.vote, name='vote'),
)

# Using parentheses around a pattern 'captures' the text matched by that pattern and sends it as an argument to the corresponding view function.
# So
# ?P<poll_id>
# defines the name that is used to identify the matched pattern;
# \d+ then matches a sequence of digits.
